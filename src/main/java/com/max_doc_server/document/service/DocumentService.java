package com.max_doc_server.document.service;

import com.max_doc_server.document.domain.Document;
import com.max_doc_server.document.enums.PhaseEnum;
import com.max_doc_server.document.record.RequestCreateNewDocumentRecord;
import com.max_doc_server.document.record.RequestUpdateDocumentRecord;
import com.max_doc_server.document.repository.DocumentRepository;
import com.max_doc_server.infra.error.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@Transactional
@RequiredArgsConstructor
public class DocumentService {
    private final DocumentRepository repository;
    @Autowired
    private ModelMapper modelMapper;

    public ResponseEntity<?> getOne(String id) {
        Optional<Document> document = repository.findById(id);
        if (document.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Documento não encontrado. Verifique o ID informado."));
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(document);

    }

    public ResponseEntity<?> createNewDocument(RequestCreateNewDocumentRecord documentRecord) {
        Document newDocument = Document.newDocument(documentRecord);
        if (checkIfAcronymAndVersionExist(newDocument.getAcronym(), newDocument.getVersion())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ErrorResponse("Documento com essa sigla e versão já existe."));
        }
        Document savedDocument = repository.save(newDocument);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedDocument);
    }

    public ResponseEntity<?> updateDocument(String id, RequestUpdateDocumentRecord documentRecord) {
        Optional<Document> existingDocument = repository.findById(id);

        if (existingDocument.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Documento não encontrado. Verifique o ID informado."));
        }

        if (existingDocument.get().getPhase() != PhaseEnum.MINUTA) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Alteração permitida apenas para documentos na fase de 'MINUTA'."));
        }

        Document documentToUpdate = existingDocument.get();
        documentToUpdate.setTitle(documentRecord.title());
        documentToUpdate.setDescription(documentRecord.description());

        Document updatedDocument = repository.save(documentToUpdate);

        return ResponseEntity.status(HttpStatus.OK)
                .body(updatedDocument);
    }

    public ResponseEntity<?> updateDocumentPhase(String id, PhaseEnum phaseEnum) {
        Optional<Document> existingDocument = repository.findById(id);

        if (existingDocument.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Documento não encontrado. Verifique o ID informado."));
        }

        Document documentToUpdate = existingDocument.get();
        PhaseEnum currentPhase = documentToUpdate.getPhase();

        if (currentPhase.equals(PhaseEnum.OBSOLETO)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Documento em fase OBSOLETO não pode ser alterado."));
        }

        if (currentPhase.equals(PhaseEnum.MINUTA) && phaseEnum.equals(PhaseEnum.VIGENTE)) {
            documentToUpdate.setPhase(phaseEnum);
            repository.updateOthersDocuments(id, documentToUpdate.getAcronym(), PhaseEnum.OBSOLETO);
        } else if (currentPhase.equals(PhaseEnum.VIGENTE) && phaseEnum.equals(PhaseEnum.OBSOLETO)) {
            documentToUpdate.setPhase(phaseEnum);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Transição inválida de fase para o documento em " + currentPhase.name() + "."));
        }

        Document updatedDocument = repository.save(documentToUpdate);
        return ResponseEntity.status(HttpStatus.OK).body(updatedDocument);
    }

    public ResponseEntity<?> cloneDocument(String id) {
        Optional<Document> existingDocument = repository.findById(id);

        if (existingDocument.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Documento não encontrado. Verifique o ID informado."));
        }

        if (existingDocument.get().getPhase() != PhaseEnum.VIGENTE) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("A clonagem de documentos só é permitida para aqueles que estão na fase 'VIGENTE'."));
        }

        Document documentToClone = existingDocument.get();
        Document documentToCreate = Document.newDocumentByClone(documentToClone);

        if (checkIfAcronymAndVersionExist(documentToCreate.getAcronym(), documentToCreate.getVersion())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ErrorResponse("Documento com essa sigla e versão já existe."));
        }

        Document updatedDocument = repository.save(documentToCreate);
        return ResponseEntity.status(HttpStatus.OK)
                .body(updatedDocument);
    }


    public Page<Document> getDocumentsPaged(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findAll(pageable);
    }

    private boolean checkIfAcronymAndVersionExist(String acronym, int version) {
        return repository.existsByAcronymAndVersion(acronym, version);
    }

}