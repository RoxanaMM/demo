package com.example.demo.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "documents")
@ApiModel(description = "All details about documents.")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "document_id", updatable = false, nullable = false)
    private long documentId;

    @ApiModelProperty(notes = "The document name")
    private String documentName;

    @ApiModelProperty(notes = "The document category")
    private String documentCategory;

    private String documentMimeType;

    public Document() {
    }

    public Document(String documentName) {
        this.documentName = documentName;
        this.documentId = getDocumentId();
    }

    public Document(long documentId, String documentName, String documentCategory, String documentMimeType) {
        this.documentId = documentId;
        this.documentName = documentName;
        this.documentCategory = documentCategory;
        this.documentMimeType = documentMimeType;
    }

    public long getDocumentId() {
        return documentId = randomUniqueIdGenerator();
    }

    public void setDocumentId(long documentId) {
        this.documentId = documentId;
    }

    private long randomUniqueIdGenerator() {
        return (long) (new Date().getTime() / 1000);
    }

    @Column(name = "document_name", nullable = false)
    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    @Column(name = "document_category", nullable = false)
    public String getDocumentCategory() {
        return documentCategory;
    }

    public void setDocumentCategory(String documentCategory) {
        this.documentCategory = documentCategory;
    }

    @Column(name = "document_mime_type", nullable = false)
    public String getDocumentMimeType() {
        return documentMimeType;
    }

    public void setDocumentMimeType(String documentMediaType) {
        this.documentMimeType = documentMediaType;
    }

    @Override
    public String toString() {
        return "Document [id=" + documentId + ", documentName=" + documentName + "]";
    }

}
