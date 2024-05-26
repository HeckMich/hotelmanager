--Query 1 (job_type is placeholder)
select d.maintenance_type ,start_date, end_date, room_nr, c.name
	from maintenance a
	join can_do_maintenance b on a.m_type_id = b.m_type_id
	join job c on b.job_id = c.job_id
	join maintenance_type d on a.m_type_id = d.m_type_id
where '2024-04-07' between start_date and end_date
