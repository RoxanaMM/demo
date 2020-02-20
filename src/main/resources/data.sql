DROP TABLE IF EXISTS documents;

CREATE TABLE documents
(
    document_id         INTEGER      NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (document_id),
    document_name       VARCHAR(128) NOT NULL,
    document_category   VARCHAR(128) NOT NULL,
    document_path VARCHAR(128)
);

INSERT INTO documents
VALUES ('1', 'invoice.pdf', 'finance', 'application/pdf');
INSERT INTO documents
VALUES ('2', 'receipt.png', 'finance', 'image/png');
INSERT INTO documents
VALUES ('3', 'contract.pdf', 'contracts', 'application/pdf');