CREATE TABLE fine (
    fine_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    loan BIGINT NOT NULL,
    fine_value DECIMAL(10, 2) NOT NULL,
    calculation_date DATE NOT NULL,
    CONSTRAINT fk_fine_loan FOREIGN KEY (loan) REFERENCES loan (loan_id) ON DELETE CASCADE
);
