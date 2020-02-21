# Spring Boot Java Task Home Assignment

## Steps to Setup

### 1. Clone the repository

```bash
git clone https://github.com/RoxanaMM/demo.git

cd https://github.com/RoxanaMM/demo.git

mvn spring-boot:run
```
That's it! The application can be accessed at ```bash http://localhost:8090```

### 2. Go to browser and test functionalities

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



