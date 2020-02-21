package com.example.demo.repository;

import com.example.demo.exceptionHandler.CustomExceptionHandlerInternalServerError;
import com.example.demo.model.Document;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

public interface CustomDocumentRepository {
    Document findByName(String documentName) throws SQLException;

    List<Document> findByCategory(String documentCategory) throws SQLException;

    Document saveDocument(Document document, String fileName) throws SQLException, CustomExceptionHandlerInternalServerError;
}
