package com.task.home.assignment.service;

import com.task.home.assignment.model.Document;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class TransferObjectService {
    public Document populateObjWithDbVals(ResultSet resultSet) throws SQLException {
        Document document = new Document();
        document.setDocumentId(resultSet.getLong("document_id"));
        document.setDocumentName(resultSet.getString("document_name"));
        document.setDocumentCategory(resultSet.getString("document_category"));
        document.setDocumentMimeType(resultSet.getString("document_mime_type"));
        return document;
    }

    public PreparedStatement populateDbWithObjVals(PreparedStatement preparedStatement, Document document) throws SQLException {
        preparedStatement.setLong(1, document.getDocumentId());
        preparedStatement.setString(2, document.getDocumentName());
        preparedStatement.setString(3, document.getDocumentCategory());
        preparedStatement.setString(4, document.getDocumentMimeType());
        return preparedStatement;
    }

}
