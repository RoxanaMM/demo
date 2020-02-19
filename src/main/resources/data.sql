DROP TABLE IF EXISTS documents;

CREATE TABLE documents (
    document_id   INTEGER      NOT NULL AUTO_INCREMENT,
    document_name VARCHAR(128) NOT NULL,
    PRIMARY KEY (document_id),
    document_extension VARCHAR(6) NOT NULL,
    document_category VARCHAR(128) NOT NULL
);

INSERT INTO documents VALUES ('1','invoice.pdf', 'pdf', 'finance');
INSERT INTO documents VALUES ('2','receipt.png', 'png', 'finance');
INSERT INTO documents VALUES ('3','contract.pdf', 'pdf', 'contracts');