package com.example.demo.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.http.MediaType;

import javax.persistence.*;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@Entity
@Table(name = "documents")
@ApiModel(description = "All details about documents.")
public class Document {

    @Id
    @ApiModelProperty(notes = "The database generated document ID")
    private long documentId = (int) (new Date().getTime()/1000);

    @ApiModelProperty(notes = "The document name")
    private String documentName;

    @ApiModelProperty(notes = "The document category")
    private String documentCategory;

    private byte[] documentData;
    private String documentMediaType;

    public Document() {}

    public Document(long documentId, String documentName, String documentCategory, byte[] documentData, String documentMediaType) {
        this.documentId = documentId;
        this.documentName = documentName;
        this.documentCategory = documentCategory;
        this.documentData = documentData;
        this.documentMediaType = documentMediaType;
    }

    @Column(name = "document_id", nullable = false)
    public long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(long documentId) {
        this.documentId = documentId;
    }

    @Column(name = "document_name", nullable = false)
    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    @Column(name = "document_data", nullable = false)
    public byte[] getDocumentData() {
        return documentData;
    }

    public void setDocumentData(byte[] documentData) {
        this.documentData = documentData;
    }

    @Column(name = "document_category", nullable = false)
    public String getDocumentCategory() {
        return documentCategory;
    }

    public void setDocumentCategory(String documentCategory) {
        this.documentCategory = documentCategory;
    }

    @Column(name = "document_media_type", nullable = false)
    public String getDocumentMediaType() {
        return documentMediaType;
    }

    public void setDocumentMediaType(String documentMediaType) {
        this.documentMediaType = documentMediaType;
    }

    @Override
    public String toString() {
        return "Document [id=" + documentId + ", documentName=" + documentName + "]";
    }

}
