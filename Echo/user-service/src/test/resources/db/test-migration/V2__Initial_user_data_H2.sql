-- Вставка ролей
INSERT INTO roles (role_name)
VALUES ('USER'),
       ('ADMIN'),
       ('MODERATOR');

-- Вставка користувачів
INSERT INTO users (nickname, avatar)
VALUES ('nickname1', NULL),
       ('nickname2', NULL),
       ('nickname3', NULL);

-- Вставка даних користувачів
INSERT INTO user_details (user_id ,first_name, last_name, patronymic, phone, about, sex, date_of_birth)
VALUES (1, 'firstname1', 'lastname1', 'patronymic1', '0971234567', 'Some details about user.', 0, '1990-01-01'),
       (2, 'firstname2', 'lastname2', 'patronymic1', '0972345678', 'Some details about user.', 1, '1995-01-01'),
       (3, 'firstname3', 'lastname3', 'patronymic1', '0973456789', 'Some details about user.', NULL, '1985-01-01');

-- Вставка даних для автентифікації користувачів
INSERT INTO user_auths (user_id, email, password)
VALUES (1, 'example1@gmail.com', 'password123'),
       (2, 'example2@gmail.com', 'password456'),
       (3, 'example3@gmail.com', 'password789');

-- Вставка зв’язків між користувачами та ролями
INSERT INTO users_roles (user_id, role_id)
VALUES (1, 1), -- John Doe is a USER
       (1, 3), -- John Doe is a MODERATOR
       (2, 1), -- Jane Smith is a USER
       (3, 2); -- Admin User is an ADMIN
