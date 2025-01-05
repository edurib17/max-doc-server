package com.max_doc_server;

import com.max_doc_server.document.domain.Document;
import com.max_doc_server.document.enums.PhaseEnum;
import com.max_doc_server.document.record.RequestCreateNewDocumentRecord;
import com.max_doc_server.document.record.RequestUpdateDocumentRecord;
import com.max_doc_server.document.repository.DocumentRepository;
import com.max_doc_server.document.service.DocumentService;
import com.max_doc_server.infra.error.ErrorResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class DocumentServiceTest {

    @Mock
    private DocumentRepository repository;

    @InjectMocks
    private DocumentService documentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createNewDocument_shouldReturnConflict_whenAcronymAndVersionExist() {
        RequestCreateNewDocumentRecord documentRecord = new RequestCreateNewDocumentRecord("Document Title 01", "Description...", "DOC-01");
        Document newDocument = Document.newDocument(documentRecord);

        when(repository.save(newDocument)).thenReturn(newDocument);
        when(documentService.checkIfAcronymAndVersionExist(newDocument.getAcronym(), newDocument.getVersion())).thenReturn(true);


        ResponseEntity<?> response = documentService.createNewDocument(documentRecord);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertTrue(response.getBody() instanceof ErrorResponse);
        ErrorResponse errorResponse = (ErrorResponse) response.getBody();
        assertEquals("Documento com essa sigla e versão já existe.", errorResponse.getMessage());
    }


    @Test
    void getDocument_ShouldReturnNotFound_WhenDocumentDoesNotExist(){
        String id = String.valueOf(UUID.randomUUID());
        when(repository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<?> response = documentService.getOne(id);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody() instanceof ErrorResponse);
        ErrorResponse errorResponse = (ErrorResponse) response.getBody();
        assertEquals("Documento não encontrado. Verifique o ID informado.", errorResponse.getMessage());
    }

    @Test
    void updateDocument_shouldReturnNotFound_whenDocumentDoesNotExist() {
        String documentId = String.valueOf(UUID.randomUUID());
        RequestUpdateDocumentRecord updateRecord = new RequestUpdateDocumentRecord("Updated Title", "Updated Description");

        when(repository.findById(documentId)).thenReturn(Optional.empty());
        ResponseEntity<?> response = documentService.updateDocument(documentId, updateRecord);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody() instanceof ErrorResponse);
        ErrorResponse errorResponse = (ErrorResponse) response.getBody();
        assertEquals("Documento não encontrado. Verifique o ID informado.", errorResponse.getMessage());
    }

    @Test
    void updateDocument_shouldReturnBadRequest_whenDocumentIsNotInMinutaPhase() {
        String documentId = String.valueOf(UUID.randomUUID());
        RequestUpdateDocumentRecord updateRecord = new RequestUpdateDocumentRecord("Updated Title", "Updated Description");
        Document existingDocument = new Document(String.valueOf(UUID.randomUUID()), "Old Title", "Old Description", 1, "DOC", PhaseEnum.OBSOLETO);
        when(repository.findById(documentId)).thenReturn(Optional.of(existingDocument));
        ResponseEntity<?> response = documentService.updateDocument(documentId, updateRecord);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody() instanceof ErrorResponse);
        ErrorResponse errorResponse = (ErrorResponse) response.getBody();
        assertEquals("Alteração permitida apenas para documentos na fase de 'MINUTA'.", errorResponse.getMessage());
    }

}
