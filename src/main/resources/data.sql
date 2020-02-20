DROP TABLE IF EXISTS documents;

CREATE TABLE documents
(
    document_id        INTEGER      NOT NULL AUTO_INCREMENT UNIQUE ,
    PRIMARY KEY (document_id),
    document_name      VARCHAR(128) NOT NULL UNIQUE,
    document_category  VARCHAR(128) NOT NULL,
    document_mime_type VARCHAR(128)
);

INSERT INTO documents
VALUES ('0123', 'contract.pdf', 'contract', 'application/pdf');
INSERT INTO documents
VALUES ('1234', 'joy.png', 'entertainment', 'image/png');
INSERT INTO documents
VALUES ('2345', 'fun.pdf', 'entertainment', 'application/pdf');

INSERT INTO documents
VALUES ('3456', 'invoice.pdf', 'finance', 'application/pdf');
INSERT INTO documents
VALUES ('4567', 'receipt.png', 'entertainment', 'image/png');
INSERT INTO documents
VALUES ('5678', 'unknown.txt', 'unknown', 'application/plain');