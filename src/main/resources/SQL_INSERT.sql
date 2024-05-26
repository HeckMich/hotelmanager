TRUNCATE TABLE
	invoice,
	service_is_booked,
	will_attend,
	reservation,
	can_do_service,
	can_do_maintenance,
	service,
	employee,
	job,
	maintenance,
	maintenance_type,
	guest,
	plz,
	room,
	event
	RESTART IDENTITY;

INSERT INTO room(
	room_nr, max_occupants, cost)
	VALUES
	 (101, 1, 70),
	 (102, 1, 80.99),
	 (103, 2, 110),
	 (106, 2, 100.50),
	 (107, 4, 210),
	 (201, 2, 130.99),
	 (202, 2, 130.99),
	 (203, 4, 220.30),
	 (204, 1, 90),
	 (205, 1, 59.99)
	;

INSERT INTO event(
	name, date)
	VALUES
	 ('Bingo Night', '2024-07-09'),
	 ('Bingo Night', '2024-08-09'),
	 ('Bingo Night', '2024-09-09'),
	 ('Samba Night', '2024-07-01'),
	 ('Samba Night', '2024-08-01'),
	 ('Samba Night', '2024-09-01'),
	 ('Fete Blanche', '2024-07-19'),
	 ('Fete Blanche', '2024-08-19'),
	 ('Fete Blanche', '2024-09-19'),
	 ('Poker Afternoon', '2024-07-23'),
	 ('Poker Afternoon', '2024-08-23'),
	 ('Poker Afternoon', '2024-09-23')
	;

INSERT INTO plz(
	plz, city)
	VALUES
	 ('7000', 'Eisenstadt'),
	 ('1040', 'Vienna'),
	 ('1010', 'Vienna'),
	 ('6020', 'Innsbruck'),
	 ('10001', 'New York'),
	 ('13125', 'Berlin')
	;

INSERT INTO guest(
	plz, last_name, first_name, house_number, street)
	VALUES
	(7000, 'Smith', 'John', 450, 'Hauptstraße'),
	(1010, 'Dupont', 'Marie', 78, 'Ringstraße'),
	(1010, 'Muller', 'Franz', 324, 'Operngasse'),
	(6020, 'Rossi', 'Lucia', 152, 'Maria-Theresien-Straße'),
	(13125, 'Suzuki', 'Hiroshi', 196, 'Berliner Allee'),
	(13125, 'Fernandez', 'Carlos', 12, 'Unter den Linden'),
	(7000, 'Johansson', 'Anna', 55, 'Grazer Straße'),
	(7000, 'Ivanov', 'Alexei', 407, 'Salzburger Straße'),
	(1040, 'Zhang', 'Wei', 88, 'Wiedner Hauptstraße'),
	(1040, 'OBrien', 'Sean', 22, 'Karlsplatz')
	;

INSERT INTO reservation(
	guest_id, start_date, end_date, room_nr)
	VALUES
		(1, '2024-07-08', '2024-07-11', 101),
    	(3, '2024-07-07', '2024-07-10', 201),
    	(2, '2024-07-01', '2024-07-28', 103),
    	(3, '2024-08-08', '2024-08-11', 101),
    	(5, '2024-08-22', '2024-08-29', 204),
    	(6, '2024-08-03', '2024-08-22', 205),
    	(7, '2024-08-13', '2024-08-19', 201),
    	(8, '2024-08-20', '2024-09-13', 201),
    	(8, '2024-09-05', '2024-09-15', 102),
    	(9, '2024-09-14', '2024-09-19', 107)
	;

INSERT INTO will_attend(
	reservation_id, event_id)
	VALUES
	(1,1),
	(2,1),
	(3,1),
	(3,7),
	(3,10),
	(4,2),
	(3,4),
	(10,9),
	(6,8),
	(5,11)
	;

INSERT INTO service(
	name, cost)
	VALUES
	('Massage', 65),
	('Room Service', 39),
	('Wake Up Call', 5),
	('Breakfast in Room', 25),
	('Golf-Lesson', 60),
	('Tennis-Lesson', 50),
	('Private Dinner', 90.99),
	('Bird Watching', 39.99),
	('Relax Spa', 44.44),
	('Washing Machine', 9.99)
	;

INSERT INTO maintenance_type(
	maintenance_type)
	VALUES
	('Room Cleaning'),
	('Climate Repair'),
	('Lighting Change'),
	('Ventilation Repair'),
	('Bathroom Restock'),
	('Carpet Renewal'),
	('Furniture Renewal'),
	('Bed Deep Cleaning'),
	('Plumbing'),
	('Window Cleaning')
	;

INSERT INTO job(
	name)
	VALUES
	('Receptionist'),
	('Cleaner'),
	('Masseur'),
	('Mechanic'),
	('Waiter'),
	('Tennis Coach'),
	('Golf Coach')
;



INSERT INTO employee(
	job_id, first_name, last_name)
	VALUES
	(1, 'Marie', 'Obergruber'),
	(2, 'Anna', 'Schneider'),
	(3, 'Jakob', 'Hoffmann'),
	(4, 'David', 'Fischer'),
	(5, 'Paul', 'Schneider'),
	(6, 'Franz', 'Peters'),
	(7, 'Ulrike', 'Obergruber'),
	(3, 'Jost', 'Verstappen'),
	(4, 'Marie', 'Hiroki'),
	(5, 'Walter', 'Obrist')

;

INSERT INTO maintenance(
	m_type_id, start_date, end_date, room_nr)
	VALUES
	(1, '2024-04-01', '2024-04-01', 205),
	(2, '2024-04-02', '2024-04-03', 201),
	(1, '2024-04-06', '2024-04-09', 203),
	(4, '2024-04-06', '2024-04-10', 204),
	(7, '2024-04-11', '2024-04-12', 204),
	(7, '2024-04-12', '2024-04-13', 203),
	(9, '2024-07-12', '2024-07-12', 101),
	(2, '2024-08-12', '2024-04-13', 101),
	(2, '2024-09-01', '2024-09-04', 102),
	(2, '2024-09-10', '2024-09-13', 107)
;

INSERT INTO can_do_maintenance(
	job_id, m_type_id)
	VALUES
	(2, 1),
	(2, 8),
	(2, 5),
	(2, 10),
	(4, 2),
	(4, 3),
	(4, 4),
	(4, 6),
	(4, 7),
	(4, 9)
;

INSERT INTO can_do_service(
	service_id, job_id)
	VALUES
	(1, 3),
	(2, 5),
	(3, 1),
	(4, 5),
	(5, 7),
	(6, 6),
	(7, 5),
	(8, 3),
	(8, 6),
	(8, 7),
	(9, 3),
	(10, 2)
;

INSERT INTO service_is_booked(
	reservation_id, service_id)
	VALUES
	(1, 9),
	(2, 8),
	(3, 8),
	(3, 7),
	(3, 9),
	(6, 7),
	(6, 8),
	(7, 2),
	(7, 3),
	(10, 10)
;

INSERT INTO invoice(
	guest_id, reservation_id, sum)
		(select a.guest_id,
				a.reservation_id,
				sum(d.cost) + sum(e.cost) --* (a.end_date::date - a.start_date::date)
			from reservation a
			join service_is_booked c on a.reservation_id = c.reservation_id
			join service d on d.service_id = c.service_id
			join room e on a.room_nr = e.room_nr
			group by a.guest_id, a.reservation_id)
	;