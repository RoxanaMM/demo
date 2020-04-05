package com.task.home.assignment.repository;

import com.task.home.assignment.model.Document;
import com.task.home.assignment.service.FileGeneratorService;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.*;

import java.sql.SQLException;

import static org.mockito.Mockito.mock;

public class CustomDocumentRepositoryImplTest {
    private CustomDocumentRepository customDocumentRepository;

   @Mock
   private DocumentRepository documentRepository;
   @Autowired
   private FileGeneratorService fileGeneratorService;


   @BeforeMethod
   public void beforeMethod(){
       customDocumentRepository = mock(documentRepository.getClass());
   }

   @AfterMethod
   public void afterMethod(){
       customDocumentRepository = null;
   }

    @DataProvider(name = "dataProvider")
    public Object[][] dataProvider() {
        return new Object[][]{new Object[]{0123, "contract.pdf", "contract", "application/pdf"},
                new Object[]{01234, "joy.png", "entertainment", "image/png"},
                new Object[]{02345, "fun.pdf", "entertainment", "application/pdf"},
                new Object[]{03456, "invoice.pdf", "finance", "application/pdf"},
                new Object[]{04567, "receipt.png", "entertainment", "image/png"},
                new Object[]{5678, "unknown.txt", "unknown", "application/plain"}};
    }

    @Test(dataProvider="dataProvider")
    public void testGetDocumentById(Document documentParam) throws SQLException {
        System.out.println(documentParam.getDocumentName());// so i want to mock this repository to be able to getById for tests

        customDocumentRepository = new CustomDocumentRepositoryImpl();
        fileGeneratorService.generateDocumentFromObject(documentRepository.findByName("contract.pdf"));
//
//        when(documentRepository.findById(documentParam.getDocumentId()).filter(document -> document.getDocumentName().equalsIgnoreCase("contract.pdf")));
//        doReturn(documentRepository.findById(documentParam.getDocumentId()));
//

    }
}