INSERT INTO users(nickname, first_name, last_name, patronymic, sex, email, password, date_of_birth, created, changed)
VALUES ('maybyes','Bohdan','Yarovyi','Serhievich',0,'bogdan.yarovoy.01@gmail.com',
        'pass1234','2001-11-07',current_timestamp,current_timestamp),
       ('jacoonda','Evgene','Olhovski',null,0,'evene.olhovski@gmail.com',
        'pass1234','2005-05-17',current_timestamp,current_timestamp),
       ('antss','Anton','Puhach','Serhievich',0,'anton.puhach@gmail.com',
        'pass1234','2005-02-11',current_timestamp,current_timestamp);

-- WARNING: IF YOU WANT TO CHANGE SOMETHING IN THIS SCRIPT, YOU NEED TO KNOW,
--          THAT WHOLE DATA HERE IS ESSENTIAL, AND SMALL CHANGE HERE CAN CAUSE PROBLEM IN MODAL TESTS


INSERT INTO roles(role_name)
VALUES ('ADMIN'),
       ('USER'),
       ('MODERATOR');

INSERT INTO users_roles(user_id, role_id)
VALUES (1, 2),
       (1, 3),
       (2, 2);