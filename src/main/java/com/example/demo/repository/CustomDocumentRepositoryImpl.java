package com.example.demo.repository;

import com.example.demo.model.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

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

    @Override
    public List<Document> findByCategory(String documentCategory) throws SQLException {
        List<Document> documents = new ArrayList<>();

        try (Connection conn = dataSource.getConnection()) {
            String sqlQuery = "SELECT DOC.DOCUMENT_NAME, DOC.DOCUMENT_CATEGORY FROM DOCUMENTS DOC WHERE DOC.DOCUMENT_CATEGORY LIKE ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sqlQuery);
            preparedStatement.setString(1, "%" + documentCategory + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Document document = new Document();
                document.setDocumentName(resultSet.getString("document_name"));
                document.setDocumentCategory(resultSet.getString("document_category"));
                documents.add(document);
            }
            resultSet.close();
            preparedStatement.close();
            return documents;
        }
    }


    //ce se intampla daca am mai multe documente cu acelasi nume?
    @Override
    public List<Document> findByName(String documentName) throws SQLException {
        List<Document> documents = new ArrayList<>();

        try (Connection conn = dataSource.getConnection()) {
            String sqlQuery = "SELECT DOC.DOCUMENT_NAME, DOC.DOCUMENT_CATEGORY, DOC.DOCUMENT_MEDIA_TYPE, DOC.DOCUMENT_DATA FROM DOCUMENTS DOC WHERE DOC.DOCUMENT_NAME LIKE ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sqlQuery);
            preparedStatement.setString(1, "%" + documentName + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                Document document = new Document();
                document.setDocumentName(resultSet.getString("document_name"));
                document.setDocumentCategory(resultSet.getString("document_category"));
                document.setDocumentMediaType(resultSet.getString("document_media_type"));
                document.setDocumentData(resultSet.getBytes("document_data"));
                documents.add(document);
            }
            resultSet.close();
            preparedStatement.close();
            return documents;
        }
    }

    @Override
    public Document saveDocument(Document document, MultipartFile file) throws SQLException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try (Connection conn = dataSource.getConnection()) {
            String sqlQuery = "INSERT INTO DOCUMENTS VALUES ( ?,?,?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sqlQuery);
            preparedStatement.setLong(1, document.getDocumentId());
            preparedStatement.setString(2, fileName);
            preparedStatement.setString(3, document.getDocumentCategory());
            preparedStatement.setBytes(4, document.getDocumentData());
            preparedStatement.setString(5, document.getDocumentMediaType());

            int rowsInserted = preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        return document;
    }
}
