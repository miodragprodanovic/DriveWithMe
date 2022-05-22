delete from tokens;
delete from rides_requests;
delete from rides_passengers;
delete from rides;
delete from users;

insert into users(id, email, password, first_name, last_name, address, city, country, mobile_number, biography, car, user_role, confirmed)
values (1, 'jonwright@gmail.com', '12345678', 'Jon', 'Wright', '70 W Red Oak Ln', 'New York', 'United States', '+1-212-555-0159', '...', 'Ford F-150', 1, true);
insert into users(id, email, password, first_name, last_name, address, city, country, mobile_number, biography, car, user_role, confirmed)
values (2, 'iancurry@gmail.com', '12345678', 'Ian', 'Curry', '7609 Mckinley Ave', 'Los Angeles', 'United States', '+1-213-555-0105', '...', 'Chevrolet Silverado 1500', 1, true);
insert into users(id, email, password, first_name, last_name, address, city, country, mobile_number, biography, car, user_role, confirmed)
values (3, 'monicabrooks@gmail.com', '12345678', 'Monica', 'Brooks', '1679 Philli Lane', 'Miami', 'United States', '+1-305-555-0121', '...', 'Ram Pickup 1500', 1, true);
insert into users(id, email, password, first_name, last_name, address, city, country, mobile_number, biography, car, user_role, confirmed)
values (4, 'chriswallace@gmail.com', '12345678', 'Chris', 'Wallace', '86 Hilltop Street', 'New York', 'United States', '+1-212-555-0120', '...', 'Honda Civic', 1, true);
insert into users(id, email, password, first_name, last_name, address, city, country, mobile_number, biography, car, user_role, confirmed)
values (5, 'benwhite@gmail.com', '12345678', 'Ben', 'White', '2284 Todds Lane', 'San Antonio', 'United States', '+1-210-555-0146', '...', 'Nissan Rogue', 1, true);
insert into users(id, email, password, first_name, last_name, address, city, country, mobile_number, biography, car, user_role, confirmed)
values (6, 'sandralee@gmail.com', '12345678', 'Sandra', 'Lee', '3120 Grove Avenue', 'Oklahoma City', 'United States', '+1-405-555-0111', '...', 'Toyota Camry', 1, true);
insert into users(id, email, password, first_name, last_name, address, city, country, mobile_number, biography, car, user_role, confirmed)
values (7, 'claudiasherman@gmail.com', '12345678', 'Claudia', 'Sherman', '1380 Union Street', 'Seattle', 'United States', '+1-206-555-0102', '...', 'Chevrolet Equinox', 1, true);
insert into users(id, email, password, first_name, last_name, address, city, country, mobile_number, biography, car, user_role, confirmed)
values (8, 'nicoleparsons@gmail.com', '12345678', 'Nicole', 'Parsons', '1737 Valley Lane', 'Austin', 'United States', '+1-512-555-0147', '...', 'Ford Escape', 1, true);
insert into users(id, email, password, first_name, last_name, address, city, country, mobile_number, biography, car, user_role, confirmed)
values (9, 'adamporter@gmail.com', '12345678', 'Adam', 'Porter', '1177 Barnes Avenue', 'Cincinnati', 'United States', '+1-513-555-0173', '...', 'Toyota RAV4', 1, true);
insert into users(id, email, password, first_name, last_name, address, city, country, mobile_number, biography, car, user_role, confirmed)
values (10, 'penelopeharris@gmail.com', '12345678', 'Penelope', 'Harris', '1153 Elk City Road', 'Indianapolis', 'United States', '+1-317-555-0162', '...', 'Honda Accord', 1, true);

insert into rides(id, departure_time, arrival_time, starting_point_country, starting_point_city, starting_point_address, destination_country, destination_city, destination_address, driver_id, max_passengers, worth, currency, rules)
values (1, '2022-05-16 17:30:00', '2022-05-18 18:30:00', 'USA', 'New York', 'Address', 'USA', 'Los Angeles', 'Address', 1, 3, 500, 'RSD', 'Rules');
insert into rides(id, departure_time, arrival_time, starting_point_country, starting_point_city, starting_point_address, destination_country, destination_city, destination_address, driver_id, max_passengers, worth, currency, rules)
values (2, '2022-05-16 20:45:00', '2022-05-18 14:05:00', 'USA', 'Miami', 'Address', 'USA', 'Cincinnati', 'Address', 2, 3, 10, 'USD', 'Rules');
insert into rides(id, departure_time, arrival_time, starting_point_country, starting_point_city, starting_point_address, destination_country, destination_city, destination_address, driver_id, max_passengers, worth, currency, rules)
values (3, '2022-05-17 00:00:00', '2022-05-18 22:30:00', 'USA', 'Oklahoma City', 'Address', 'USA', 'San Antonio', 'Address', 1, 4, 27, '$', 'Rules');
insert into rides(id, departure_time, arrival_time, starting_point_country, starting_point_city, starting_point_address, destination_country, destination_city, destination_address, driver_id, max_passengers, worth, currency, rules)
values (4, '2022-05-18 14:30:00', '2022-05-19 14:00:00', 'USA', 'New York', 'Address', 'USA', 'Los Angeles', 'Address', 8, 3, 200, 'RSD', 'Rules');
insert into rides(id, departure_time, arrival_time, starting_point_country, starting_point_city, starting_point_address, destination_country, destination_city, destination_address, driver_id, max_passengers, worth, currency, rules)
values (5, '2022-05-19 20:00:00', '2022-05-20 04:45:00', 'USA', 'Miami', 'Address', 'USA', 'Cincinnati', 'Address', 9, 3, 11, 'USD', 'Rules');
insert into rides(id, departure_time, arrival_time, starting_point_country, starting_point_city, starting_point_address, destination_country, destination_city, destination_address, driver_id, max_passengers, worth, currency, rules)
values (6, '2022-05-20 07:00:00', '2022-05-21 11:30:00', 'USA', 'Oklahoma City', 'Address', 'USA', 'San Antonio', 'Address', 10, 4, 24, '$', 'Rules');


insert into rides_passengers(ride_id, passengers_id)
values (1, 3);
insert into rides_passengers(ride_id, passengers_id)
values (1, 4);
insert into rides_passengers(ride_id, passengers_id)
values (1, 5);
insert into rides_passengers(ride_id, passengers_id)
values (2, 6);
insert into rides_passengers(ride_id, passengers_id)
values (2, 7);
insert into rides_passengers(ride_id, passengers_id)
values (3, 5);

insert into rides_requests(ride_id, requests_id)
values (3, 9);
insert into rides_requests(ride_id, requests_id)
values (3, 10);