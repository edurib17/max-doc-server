package com.max_doc_server.document.repository;

import com.max_doc_server.document.domain.Document;
import com.max_doc_server.document.dto.DocumentDTO;
import com.max_doc_server.document.dto.DocumentFilterDTO;
import com.max_doc_server.document.enums.PhaseEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, String>, DocumentRepositoryCustom {

    @Query("SELECT COUNT(d) > 0 FROM Document d WHERE d.acronym = :acronym AND d.version = :version")
    boolean existsByAcronymAndVersion(@Param("acronym") String acronym, @Param("version") int version);

    @Modifying
    @Query("UPDATE Document d SET d.phase = :phase WHERE d.acronym = :acronym AND d.id != :id")
    void updateOthersDocuments(@Param("id") String id, @Param("acronym") String acronym, @Param("phase") PhaseEnum phase);
}
