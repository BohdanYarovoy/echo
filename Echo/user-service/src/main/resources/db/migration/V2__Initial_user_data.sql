-- Вставка ролей
INSERT INTO roles (role_name)
VALUES ('USER'),
       ('ADMIN'),
       ('MODERATOR');

-- Вставка користувачів
INSERT INTO users (nickname, avatar)
VALUES ('john_doe', NULL),
       ('jane_smith', NULL),
       ('admin_user', NULL);

-- Вставка даних користувачів
INSERT INTO user_details (first_name, last_name, patronymic, phone, about, sex, date_of_birth, user_id)
VALUES ('John', 'Doe', 'Michael', '1234567890', 'Just a regular user.', 1, '1990-01-01', 1),
       ('Jane', 'Smith', 'Alice', '0987654321', 'Loves programming.', 0, '1995-05-15', 2),
       ('Admin', 'User', NULL, '1112233445', 'Admin user for testing.', NULL, '1985-10-10', 3);

-- Вставка даних для автентифікації користувачів
INSERT INTO user_auths (email, password, user_id)
VALUES ('john.doe@example.com', '$2a$12$J0hgtU7W39Ow41PEDkRT6OFVtbTVMg87our3QUroCaEp7NNy1COTq', 1),     -- password123
       ('jane.smith@example.com', '$2a$12$PJ8bNPiouzk/3iJjKf1zh.V9nMwSP7LOk11nSl4XBsSTf6VAS//4C', 2),   -- password456
       ('admin@example.com', '$2a$12$FS0DUXaYq1PgkrzbfqUb6eGEgOlA1fjZ2rh2NSBcp6PpBotf6SyRO', 3);        -- adminpassword

-- Вставка зв’язків між користувачами та ролями
INSERT INTO users_roles (user_id, role_id)
VALUES (1, 1), -- John Doe is a USER
       (1, 3), -- John Doe is a MODERATOR
       (2, 1), -- Jane Smith is a USER
       (3, 2); -- Admin User is an ADMIN