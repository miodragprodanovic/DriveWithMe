delete from tokens;
delete from users;

insert into users(id, first_name, last_name, email, password, user_role, confirmed)
values (1, 'Jon', 'Wright', 'jonwright@gmail.com', '12345678', 1, true);
insert into users(id, first_name, last_name, email, password, user_role, confirmed)
values (2, 'Ian', 'Curry', 'iancurry@gmail.com', '12345678', 1, true);
insert into users(id, first_name, last_name, email, password, user_role, confirmed)
values (3, 'Monica', 'Brooks', 'monicabrooks@gmail.com', '12345678', 1, true);
insert into users(id, first_name, last_name, email, password, user_role, confirmed)
values (4, 'Chris', 'Wallace', 'chriswallace@gmail.com', '12345678', 1, true);
insert into users(id, first_name, last_name, email, password, user_role, confirmed)
values (5, 'Ben', 'White', 'benwhite@gmail.com', '12345678', 1, true);