package com.max_doc_server.document.controller;
import com.max_doc_server.document.domain.Document;
import com.max_doc_server.document.dto.DocumentDTO;
import com.max_doc_server.document.enums.PhaseEnum;
import com.max_doc_server.document.record.RequestCreateNewDocumentRecord;
import com.max_doc_server.document.record.RequestUpdateDocumentRecord;
import com.max_doc_server.document.service.DocumentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/v1/documents")
@RequiredArgsConstructor
public class DocumentController {
    private final DocumentService service;

    @Operation(summary = "Criar um novo documento", description = "Cria um novo documento com as informações fornecidas.")
    @PostMapping()
    public ResponseEntity<?> save(@RequestBody RequestCreateNewDocumentRecord body) {
        ResponseEntity<?> response = service.createNewDocument(body);
        if (Objects.nonNull(response)) {
            return response;
        }
        return ResponseEntity.badRequest().build();
    }

    @Operation(summary = "Atualizar um documento existente", description = "Atualiza um documento com base no ID fornecido e os novos dados.")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody RequestUpdateDocumentRecord body) {
        ResponseEntity<?> response = service.updateDocument(id, body);
        if (Objects.nonNull(response)) {
            return response;
        }
        return ResponseEntity.badRequest().build();
    }

    @Operation(summary = "Obter um documento pelo ID", description = "Retorna um documento com base no ID fornecido.")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
        ResponseEntity<?> response = service.getOne(id);
        if (Objects.nonNull(response)) {
            return response;
        }
        return ResponseEntity.badRequest().build();
    }

    @Operation(summary = "Atualizar a fase do documento", description = "Atualiza a fase de um documento existente com base no ID e na nova fase.")
    @PutMapping("/{id}/phase/{newPhase}")
    public ResponseEntity<?> update(@PathVariable String id, @PathVariable PhaseEnum newPhase) {
        ResponseEntity<?> response = service.updateDocumentPhase(id, newPhase);
        if (Objects.nonNull(response)) {
            return response;
        }
        return ResponseEntity.badRequest().build();
    }

    @Operation(summary = "Clonar um documento", description = "Cria uma cópia de um documento existente com base no ID fornecido.")
    @PutMapping("/{id}/clone-document")
    public ResponseEntity<?> cloneNewDocumentById(@PathVariable String id) {
        ResponseEntity<?> response = service.cloneDocument(id);
        if (Objects.nonNull(response)) {
            return response;
        }
        return ResponseEntity.badRequest().build();
    }


    @Operation(summary = "Listar documentos paginados", description = "Retorna uma lista de documentos com paginação.")
    @GetMapping("/list")
    public Page<Document> getDocuments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) PhaseEnum phase
            ) {
        return service.getDocumentsPaged(page, size, phase);
    }
}

