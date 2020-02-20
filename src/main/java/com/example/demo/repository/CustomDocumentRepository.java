package com.example.demo.repository;

import com.example.demo.model.Document;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

public interface CustomDocumentRepository {
    List<Document> findByName(String documentName) throws SQLException;

    List<Document> findByCategory(String documentCategory) throws SQLException;

    Document saveDocument(Document document, MultipartFile file) throws SQLException;
}
