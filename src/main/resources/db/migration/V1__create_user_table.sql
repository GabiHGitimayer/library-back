CREATE TABLE user (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(255) NOT NULL,
    user_email VARCHAR(255) NOT NULL,
    user_cpf VARCHAR(255) NOT NULL UNIQUE,
    user_password VARCHAR(255) NOT NULL,
    user_type VARCHAR(50) NOT NULL,
    CONSTRAINT chk_user_type CHECK (user_type IN ('ADMIN', 'EMPLOYEE', 'USER'))
);
