--Query 1 (job_type is placeholder)
SELECT
	B.MAINTENANCE_TYPE,
	START_DATE,
	END_DATE,
	ROOM_NR,
	C.FIRST_NAME,
	C.LAST_NAME
FROM
	PLANNED_MAINTENANCE A
	JOIN MAINTENANCE_TYPE B ON A.M_TYPE_ID = B.M_TYPE_ID
	JOIN EMPLOYEE C ON A.EMPLOYEE_ID = C.EMPLOYEE_ID
WHERE
	'2024-04-07' BETWEEN START_DATE AND END_DATE;

--Query 2
SELECT
	COUNT(*) NUMBER_BOOKED_ROOMS,
	(
		SELECT
			COUNT(*)
		FROM
			ROOM
	) - COUNT(*) NUMBER_EMPTY_ROOMS,
	SUM(COST) EXPECTED_EARNINGS,
	(
		SELECT
			SUM(COST)
		FROM
			ROOM
	) - SUM(COST) LOST_EARNINGS
FROM
	RESERVATION A
	JOIN ROOM B ON A.ROOM_NR = B.ROOM_NR
WHERE
	'2024-07-09' BETWEEN START_DATE AND END_DATE


	--Query 3
	--Part 1 - see available rooms for date
SELECT DISTINCT
	(A.ROOM_NR),
	A.MAX_OCCUPANTS,
	A.COST
FROM
	ROOM A
	FULL OUTER JOIN RESERVATION B ON A.ROOM_NR = B.ROOM_NR
WHERE
	'2024-07-02' NOT BETWEEN B.START_DATE AND B.END_DATE
	OR RESERVATION_ID IS NULL
ORDER BY
	ROOM_NR