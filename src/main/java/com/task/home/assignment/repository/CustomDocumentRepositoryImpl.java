package com.task.home.assignment.repository;

import com.task.home.assignment.exception.handler.CustomExceptionHandlerInternalServerError;
import com.task.home.assignment.model.Document;
import com.task.home.assignment.service.TransferObjectService;
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
    @Autowired
    private TransferObjectService transferObjectService;

    private static final String SELECT_FROM_DOCUMENTS_DOC_WHERE = "SELECT * FROM DOCUMENTS DOC WHERE";
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomDocumentRepositoryImpl.class);

    @Override
    public List<Document> findByCategory(String documentCategory) throws SQLException {
        List<Document> documents = new ArrayList<>();

        try (Connection conn = dataSource.getConnection()) {
            String sqlQuery = SELECT_FROM_DOCUMENTS_DOC_WHERE + " DOC.DOCUMENT_CATEGORY LIKE ?";
            try (PreparedStatement preparedStatement = conn.prepareStatement(sqlQuery)) {
                preparedStatement.setString(1, "%" + documentCategory + "%");
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Document document = transferObjectService.populateObjWithDbVals(resultSet);
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
            String sqlQuery = SELECT_FROM_DOCUMENTS_DOC_WHERE + " DOC.DOCUMENT_NAME LIKE ?";
            try (PreparedStatement preparedStatement = conn.prepareStatement(sqlQuery)) {
                preparedStatement.setString(1, "%" + documentName + "%");
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        document = transferObjectService.populateObjWithDbVals(resultSet);
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
                document.setDocumentName(fileName);
                transferObjectService.populateDbWithObjVals(preparedStatement, document);
                int rowsInserted = preparedStatement.executeUpdate();
                LOGGER.info("Rows inserted: {}", rowsInserted);
                document.setDocumentName(fileName);
            }
        }
        return document;
    }
}
