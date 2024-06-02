--Query 1 (job_type is placeholder)
select b.maintenance_type ,start_date, end_date, room_nr, c.first_name, c.last_name
	from planned_maintenance a
	join maintenance_type b on a.m_type_id = b.m_type_id
	join employee c on a.employee_id = c.employee_id
where '2024-04-07' between start_date and end_date;

--Query 2
select count(*) number_booked_rooms,
(Select count(*) from room) - count(*)  number_empty_rooms,
sum(cost) expected_earnings,
(Select sum(cost) from room) - sum(cost) lost_earnings
	from reservation a
	join room b on a.room_nr = b.room_nr
where '2024-07-09' between start_date and end_date

--Query 3 --NOT FINISHED!!
select *
	from reservation a
	right outer join room b on a.room_nr = b.room_nr
where '2024-07-09' between start_date and end_date