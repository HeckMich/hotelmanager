DROP TABLE if exists invoice;
drop table if exists service_is_booked;
DROP TABLE if exists will_attend;
DROP TABLE if exists reservation;

drop table if exists can_do_service;
drop table if exists can_do_maintenance;
drop table if exists service;
drop table if exists employee;
drop table if exists job;
drop table if exists maintenance;
drop table if exists maintenance_type;


DROP TABLE if exists event;
DROP TABLE if exists guest;
DROP TABLE if exists plz;
DROP TABLE if exists room;

CREATE TABLE room (
	room_nr int not null, --In format 101, 102, 201, ... (no leading 0s)
	max_occupants int not null,
	cost decimal(5,2) not null,
	PRIMARY KEY(room_nr)
);

CREATE TABLE event (
	event_id serial,
	name varchar(100) not null,
	date date not null,
	PRIMARY KEY(event_id)
);

CREATE TABLE plz (
	plz varchar(10) not null, -- Leading 0s (e.g. germany), up to 10 digits (e.g. USA)
	city varchar(100) not null,
	PRIMARY KEY(plz)
);

CREATE TABLE guest (
	plz varchar(10) not null,
	guest_id serial,
	last_name varchar(50) not null,
	first_name varchar(50) not null,
	house_number int not null,
	street varchar(100) not null,
	PRIMARY KEY(guest_id),
	FOREIGN KEY(plz) REFERENCES plz(plz)
);

CREATE TABLE reservation (
	guest_id int not null,
	reservation_id serial,
	start_date date not null,
	end_date date not null,
	room_nr int not null,
	PRIMARY KEY(reservation_id),
	FOREIGN KEY(guest_id) REFERENCES guest(guest_id)
);

CREATE TABLE invoice (
	guest_id int not null,
	reservation_id int not null,
	invoice_id serial,
	sum decimal(10,2) not null,
	PRIMARY KEY(reservation_id),
	FOREIGN KEY(guest_id) REFERENCES guest(guest_id),
	FOREIGN KEY(reservation_id) REFERENCES reservation(reservation_id)
);


CREATE TABLE will_attend (
	reservation_id int not null,
	event_id int not null,
	PRIMARY KEY(reservation_id, event_id),
	FOREIGN KEY(reservation_id) REFERENCES reservation(reservation_id),
	FOREIGN KEY(event_id) REFERENCES event(event_id)
);




create table maintenance_type (
	m_type_id Serial,
	maintenance_type varchar(40) not null,
	Primary key (m_type_id)
);

create table job (
	job_id Serial,
	name varchar (20) not null,
	PRIMARY KEY (job_id)
);

CREATE TABLE can_do_maintenance(
	job_id int not null,
	m_type_id int not null,
	PRIMARY KEY (job_id, m_type_id),
	FOREIGN KEY (job_id) REFERENCES job (job_id),
	FOREIGN KEY (m_type_id) REFERENCES maintenance_type (m_type_id)
);

CREATE TABLE service(
	service_id Serial,
	name varchar(30) not null,
	cost decimal(5,2) not null,
	PRIMARY KEY (service_id)
);

CREATE TABLE can_do_service(
	service_id int not null,
	job_id int not null,
	PRIMARY KEY(service_id, job_id),
	FOREIGN KEY(service_id) REFERENCES service (service_id),
	FOREIGN KEY(job_id) REFERENCES job(job_id)
);

CREATE TABLE employee(
	job_id int not null,
	employee_id Serial,
	first_name varchar(20) not null,
	last_name varchar(30) not null,
	PRIMARY KEY(employee_id),
	FOREIGN KEY(job_id) REFERENCES job(job_id)
);

CREATE TABLE maintenance(
	m_type_id int not null,
	maint_id Serial,
	start_date date not null,
	end_date date not null,
	room_nr int not null,
	PRIMARY KEY(maint_id),
	FOREIGN KEY(m_type_id) REFERENCES maintenance_type(m_type_id),
	FOREIGN KEY(room_nr) REFERENCES room(room_nr)
);

CREATE TABLE service_is_booked(
	reservation_id int not null,
	service_id int not null,
	PRIMARY KEY(reservation_id, service_id),
	FOREIGN KEY (reservation_id) REFERENCES reservation,
	FOREIGN KEY (service_id) REFERENCES service
);

select * from service_is_booked