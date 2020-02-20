package com.example.demo.repository;

import com.example.demo.model.Document;
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
    private final String selectAllFromDB = "SELECT * FROM DOCUMENTS DOC WHERE";

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
            PreparedStatement preparedStatement = conn.prepareStatement(sqlQuery);
            preparedStatement.setString(1, "%" + documentCategory + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Document document = populateDocumentWithValFromDb(resultSet);
                documents.add(document);
            }
            resultSet.close();
            preparedStatement.close();
            return documents;
        }
    }

    @Override
    public Document findByName(String documentName) throws SQLException {
        Document document = new Document();

        try (Connection conn = dataSource.getConnection()) {
            String sqlQuery = selectAllFromDB + " DOC.DOCUMENT_NAME LIKE ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sqlQuery);
            preparedStatement.setString(1, "%" + documentName + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                document = populateDocumentWithValFromDb(resultSet);
            }
            resultSet.close();
            preparedStatement.close();
            return document;
        }
    }

    @Override
    public Document saveDocument(String fileName) throws SQLException {
        Document document = new Document();
        try (Connection conn = dataSource.getConnection()) {
            String sqlQuery = "INSERT INTO DOCUMENTS VALUES ( ?,?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sqlQuery);
            preparedStatement.setLong(1, document.getDocumentId());
            preparedStatement.setString(2, fileName);
            preparedStatement.setString(3, document.getDocumentCategory());
            preparedStatement.setString(4, document.getDocumentMimeType());

            int rowsInserted = preparedStatement.executeUpdate();
            document.setDocumentName(fileName);
            preparedStatement.close();
        }
        return document;
    }
}
