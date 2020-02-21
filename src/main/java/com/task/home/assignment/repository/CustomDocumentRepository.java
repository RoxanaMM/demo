package com.task.home.assignment.repository;

import com.task.home.assignment.exceptionHandler.CustomExceptionHandlerInternalServerError;
import com.task.home.assignment.model.Document;

import java.sql.SQLException;
import java.util.List;

public interface CustomDocumentRepository {
    Document findByName(String documentName) throws SQLException;

    List<Document> findByCategory(String documentCategory) throws SQLException;

    Document saveDocument(Document document, String fileName) throws SQLException, CustomExceptionHandlerInternalServerError;
}
