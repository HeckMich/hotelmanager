delete from room;
delete from event;


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
	 (205, 1, 59.99);

INSERT INTO event(
	name, date)
	VALUES
	 ('Bingo Night', '2024-07-09'),
	 ('Bingo Night', '2024-08-09'),
	 ('Bingo Night', '2024-10-09'),
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