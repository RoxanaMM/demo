package com.example.demo.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@Entity
@Table(name = "documents")
@ApiModel(description = "All details about documents. ")
public class Document {
    @ApiModelProperty(notes = "The database generated document ID")
    private long documentId;

    @ApiModelProperty(notes = "The document name")
    private String documentName;

    @ApiModelProperty(notes = "The document extension")
    private String documentExtension;

    @ApiModelProperty(notes = "The document category")
    private String documentCategory;


    public Document() {}

    public Document(long documentId, String documentName, String documentExtension, String documentCategory) {
        this.documentId = documentId;
        this.documentName = documentName;
        this.documentExtension = documentExtension;
        this.documentCategory = documentCategory;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(long id) {
        this.documentId = documentId;
    }

    @Column(name = "document_name", nullable = false)
    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    @Column(name = "document_extension", nullable = false)
    public String getDocumentExtension() {
        return documentExtension;
    }

    public void setDocumentExtension(String documentExtension) {
        this.documentExtension = documentExtension;
    }

    @Column(name = "document_category", nullable = false)
    public String getDocumentCategory() {
        return documentCategory;
    }

    public void setDocumentCategory(String documentCategory) {
        this.documentCategory = documentCategory;
    }

    @Override
    public String toString() {
        return "Document [id=" + documentId + ", documentName=" + documentName + ", documentExtension=" + documentExtension + "]";
    }

}
