INSERT INTO users(user_id, nickname, first_name, last_name, patronymic, sex, email, password, date_of_birth, created, changed)
VALUES (100,'maybyes','Bohdan','Yarovyi','Serhievich',
        0,'bogdan.yarovyi.01@gmail.com','pass1234',
        '2001-11-07','2024-09-23',null);


INSERT INTO roles(role_id, role_name)
VALUES (1,'ADMIN'),
       (2,'USER'),
       (3,'MODERATOR');

INSERT INTO users_roles(user_id, role_id)
VALUES (100, 2),
       (100, 3);