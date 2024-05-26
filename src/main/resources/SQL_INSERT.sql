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
    	(1, '2024-07-07', '2024-07-10', 201),
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
	(1,1)
	;

