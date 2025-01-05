package com.max_doc_server.document.domain;


import com.max_doc_server.document.enums.PhaseEnum;
import com.max_doc_server.document.record.RequestCreateNewDocumentRecord;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="documents")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "version", nullable = false)
    private int version;

    @Column(name = "acronym", nullable = false, length = 10)
    private String acronym;

    @Enumerated(EnumType.STRING)
    @Column(name = "phase", nullable = false, length = 20)
    private PhaseEnum phase;

    public static Document newDocument(RequestCreateNewDocumentRecord record) {
        Document document = new Document();
        document.setTitle(record.title());
        document.setDescription(record.description());
        document.setVersion(1);
        document.setAcronym(record.acronym());
        document.setPhase(PhaseEnum.MINUTA);
        return document;
    }

    public static Document newDocumentByClone(Document currentDocument) {
        Document document = new Document();
        document.setTitle(currentDocument.getTitle());
        document.setDescription(currentDocument.getDescription());
        document.setVersion(currentDocument.getVersion() + 1);
        document.setAcronym(currentDocument.getAcronym());
        document.setPhase(PhaseEnum.MINUTA);
        return document;
    }
}
