insert into users (id, username, password, active, email)
 values ( 1, 'admin', '123', true, 'admin@mail.ru');

insert into user_role
  values (1, 'ADMIN'), (1, 'USER');