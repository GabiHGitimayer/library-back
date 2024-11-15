CREATE TABLE book (
    book_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255),
    genre VARCHAR(255),
    isbn VARCHAR(255),
    publication_year INT,
    copies_quantity INT NOT NULL
);
