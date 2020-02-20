DROP TABLE IF EXISTS documents;

CREATE TABLE documents
(
    document_id         INTEGER      NOT NULL AUTO_INCREMENT,
    document_name       VARCHAR(128) NOT NULL,
    PRIMARY KEY (document_id),
    document_category   VARCHAR(128) NOT NULL,
    document_data       blob,
    document_media_type VARCHAR(128)
);

INSERT INTO documents
VALUES ('1', 'invoice.pdf', 'finance', '', 'APPLICATION_PDF_VALUE');
INSERT INTO documents
VALUES ('2', 'receipt.png', 'finance', '', 'IMAGE_PNG_VALUE');
INSERT INTO documents
VALUES ('3', 'contract.pdf', 'contracts', '', 'APPLICATION_PDF_VALUE');