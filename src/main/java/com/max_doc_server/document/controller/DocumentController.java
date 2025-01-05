package com.max_doc_server.document.controller;
import com.max_doc_server.document.domain.Document;
import com.max_doc_server.document.enums.PhaseEnum;
import com.max_doc_server.document.record.RequestCreateNewDocumentRecord;
import com.max_doc_server.document.record.RequestUpdateDocumentRecord;
import com.max_doc_server.document.service.DocumentService;
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

    @PostMapping()
    public ResponseEntity<?> save(@RequestBody RequestCreateNewDocumentRecord body) {
        ResponseEntity<?> response = service.createNewDocument(body);
        if (Objects.nonNull(response)) {
            return response;
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody RequestUpdateDocumentRecord body) {
        ResponseEntity<?> response = service.updateDocument(id, body);
        if (Objects.nonNull(response)) {
            return response;
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
        ResponseEntity<?> response = service.getOne(id);
        if (Objects.nonNull(response)) {
            return response;
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}/phase/{newPhase}")
    public ResponseEntity<?> update(@PathVariable String id, @PathVariable PhaseEnum newPhase) {
        ResponseEntity<?> response = service.updateDocumentPhase(id, newPhase);
        if (Objects.nonNull(response)) {
            return response;
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}/clone-document")
    public ResponseEntity<?> cloneNewDocumentById(@PathVariable String id) {
        ResponseEntity<?> response = service.cloneDocument(id);
        if (Objects.nonNull(response)) {
            return response;
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/list")
    public Page<Document> getDocuments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return service.getDocumentsPaged(page, size);
    }
}

