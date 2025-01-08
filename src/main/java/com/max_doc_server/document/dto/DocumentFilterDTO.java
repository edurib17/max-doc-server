package com.max_doc_server.document.dto;
import com.max_doc_server.document.enums.PhaseEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class DocumentFilterDTO {
    private String title;
    private String acronym;
    private PhaseEnum phase;

    public DocumentFilterDTO( String title,  String acronym, PhaseEnum phase) {
        this.title = title;
        this.acronym = acronym;
        this.phase = phase;
    }
}
