package com.example.demo.repository;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
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
    public List<String> findByCategory(String documentCategory) throws SQLException {
        List<String> documentNames = new ArrayList<>();

        try (Connection conn = dataSource.getConnection()) {
            String sqlQuery = "SELECT DOC.DOCUMENT_NAME FROM DOCUMENTS DOC WHERE DOC.DOCUMENT_CATEGORY LIKE ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sqlQuery);
            preparedStatement.setString(1, "%" + documentCategory + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                documentNames.add(resultSet.getString("document_name"));
            }
            resultSet.close();
            preparedStatement.close();
            return documentNames;
        }
    }
}
