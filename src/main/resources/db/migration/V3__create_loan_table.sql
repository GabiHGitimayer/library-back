CREATE TABLE loan (
    loan_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    book_id BIGINT NOT NULL,
    loan_date DATE NOT NULL,
    return_date DATE,
    efective_return_date DATE,
    loan_status VARCHAR(50),
    CONSTRAINT fk_loan_user FOREIGN KEY (user_id) REFERENCES user (user_id) ON DELETE CASCADE,
    CONSTRAINT fk_loan_book FOREIGN KEY (book_id) REFERENCES book (book_id) ON DELETE CASCADE
);
