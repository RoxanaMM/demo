package com.task.home.assignment.repository;

import com.task.home.assignment.exceptionHandler.CustomExceptionHandlerInternalServerError;
import com.task.home.assignment.model.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomDocumentRepositoryImpl implements CustomDocumentRepository {

    @Autowired
    private DataSource dataSource;
    private static final String selectAllFromDB = "SELECT * FROM DOCUMENTS DOC WHERE";
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomDocumentRepositoryImpl.class);

    private Document populateDocumentWithValFromDb(ResultSet resultSet) throws SQLException {
        Document document = new Document();
        document.setDocumentId(resultSet.getLong("document_id"));
        document.setDocumentName(resultSet.getString("document_name"));
        document.setDocumentCategory(resultSet.getString("document_category"));
        document.setDocumentMimeType(resultSet.getString("document_mime_type"));
        return document;
    }

    @Override
    public List<Document> findByCategory(String documentCategory) throws SQLException {
        List<Document> documents = new ArrayList<>();

        try (Connection conn = dataSource.getConnection()) {
            String sqlQuery = selectAllFromDB + " DOC.DOCUMENT_CATEGORY LIKE ?";
            try (PreparedStatement preparedStatement = conn.prepareStatement(sqlQuery)) {

                preparedStatement.setString(1, "%" + documentCategory + "%");
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Document document = populateDocumentWithValFromDb(resultSet);
                        documents.add(document);
                    }
                    return documents;
                }
            }
        }
    }

    @Override
    public Document findByName(String documentName) throws SQLException {
        Document document = new Document();

        try (Connection conn = dataSource.getConnection()) {
            String sqlQuery = selectAllFromDB + " DOC.DOCUMENT_NAME LIKE ?";
            try (PreparedStatement preparedStatement = conn.prepareStatement(sqlQuery)) {
                preparedStatement.setString(1, "%" + documentName + "%");
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        document = populateDocumentWithValFromDb(resultSet);
                    }
                    return document;
                }
            }
        }
    }

    @Override
    public Document saveDocument(Document document, String fileName) throws CustomExceptionHandlerInternalServerError, SQLException {
        try (Connection conn = dataSource.getConnection()) {
            String sqlQuery = "INSERT INTO DOCUMENTS VALUES ( ?,?,?,?)";
            try (PreparedStatement preparedStatement = conn.prepareStatement(sqlQuery)) {
                preparedStatement.setLong(1, document.getDocumentId());
                preparedStatement.setString(2, fileName);
                preparedStatement.setString(3, document.getDocumentCategory());
                preparedStatement.setString(4, document.getDocumentMimeType());

                int rowsInserted = preparedStatement.executeUpdate();
                LOGGER.info("Rows inserted: {}", rowsInserted);
                document.setDocumentName(fileName);
            }
        }
        return document;
    }
}
