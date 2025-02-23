package com.max_doc_server.document.dto;

import com.max_doc_server.document.enums.PhaseEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@Builder
public class DocumentDTO {
    private String id;
    private String title;
    private String description;
    private int version;
    private String acronym;
    private PhaseEnum phase;

    public DocumentDTO(String id, String title, String description, int version, String acronym, PhaseEnum phase) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.version = version;
        this.acronym = acronym;
        this.phase = phase;
    }
}
