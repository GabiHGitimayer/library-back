CREATE TABLE history (
    history_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    book_id BIGINT NOT NULL,
    loan_id BIGINT NOT NULL,
    return_id BIGINT,
    loan_date DATE,
    return_date DATE,
    CONSTRAINT fk_history_user FOREIGN KEY (user_id) REFERENCES user (user_id) ON DELETE CASCADE,
    CONSTRAINT fk_history_book FOREIGN KEY (book_id) REFERENCES book (book_id) ON DELETE CASCADE,
    CONSTRAINT fk_history_loan FOREIGN KEY (loan_id) REFERENCES loan (loan_id) ON DELETE CASCADE,
    CONSTRAINT fk_history_return FOREIGN KEY (return_id) REFERENCES loan (loan_id) ON DELETE SET NULL
);
