# Spring Boot Java Task Home Assignment

## Steps to Setup

### 1. Clone the repository

```bash
git clone https://github.com/RoxanaMM/demo.git

cd https://github.com/RoxanaMM/demo.git

mvn spring-boot:run
```
That's it! The application can be accessed at ```bash http://localhost:8090```

### 2. Go to browser and open project UI

Open in browser Swagger2 UI 

```bash
http://localhost:8090/swagger-ui.html
```

Click on document-controller and test the functionalities.

For the upload/download functionality, the files will be uploaded/downloaded relative to the path where the project is saved.

Open in browser H2 embeded database 

```bash
http://localhost:8090/h2-console/
```

Standard H2 login: 
```bash
Url: jdbc:h2:mem:testdb
Username: sa
Password:
```

### 2.Test functionalities

The application 
#### 1. Upload documents (.pdf, .png, .txt, .doc, .docx, .png, .gif, .jpeg, .jpg, .zip, .rar)
Example:
```bash
curl -X POST "http://localhost:8090/api/v1/documents/upload?folderCategory=unknown" -H "accept: */*" -H "Content-Type: multipart/form-data" -F "file=@feed.txt;type=text/plain"
```

#### 2. List all documents
Example:
```bash
curl -X GET "http://localhost:8090/api/v1/documents" -H "accept: */*"
```

#### 3. List documents in a specific category
Example:
```bash
curl -X GET "http://localhost:8090/api/v1/documents/unknown" -H "accept: */*"
```

#### 4. Download document
Example:
```bash
curl -X GET "http://localhost:8090/api/v1/documents/download?documentCategory=unknown&documentName=unknown.txt" -H "accept: */*"
```

