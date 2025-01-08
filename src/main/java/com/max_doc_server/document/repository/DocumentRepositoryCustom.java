package com.max_doc_server.document.repository;

import com.max_doc_server.document.dto.DocumentDTO;
import com.max_doc_server.document.dto.DocumentFilterDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DocumentRepositoryCustom {
    Page<DocumentDTO> documentsFilterPaged(DocumentFilterDTO filter, Pageable pageable);
}
