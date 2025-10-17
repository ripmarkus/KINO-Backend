-- KinoXP Mock Data Setup
-- This file automatically loads realistic cinema data for testing

-- Clear existing data (in correct order to handle foreign keys)
DELETE FROM reservation_seat;
DELETE FROM reservation;
DELETE FROM ticket;
DELETE FROM snack_sale_item;
DELETE FROM snack_sale;
DELETE FROM employee_roles;
DELETE FROM seat;
DELETE FROM screening;
DELETE FROM movie;
DELETE FROM genre;
DELETE FROM theatre;
DELETE FROM snack_item;
DELETE FROM employee;
DELETE FROM customer;
SET FOREIGN_KEY_CHECKS = 1;

-- Reset auto-increment counters
ALTER TABLE genre AUTO_INCREMENT = 1;
ALTER TABLE movie AUTO_INCREMENT = 1;
ALTER TABLE theatre AUTO_INCREMENT = 1;
ALTER TABLE seat AUTO_INCREMENT = 1;
ALTER TABLE employee AUTO_INCREMENT = 1;
ALTER TABLE customer AUTO_INCREMENT = 1;
ALTER TABLE screening AUTO_INCREMENT = 1;
ALTER TABLE reservation AUTO_INCREMENT = 1;
ALTER TABLE snack_item AUTO_INCREMENT = 1;
ALTER TABLE snack_sale AUTO_INCREMENT = 1;

-- 1. GENRES
INSERT INTO genre (genre_name) VALUES 
('Action'),
('Comedy'),
('Drama'),
('Horror'),
('Sci-Fi'),
('Romance'),
('Thriller'),
('Animation'),
('Adventure'),
('Documentary');

-- 2. MOVIES  
INSERT INTO movie (title, age_limit, duration, description, genre_genre_id) VALUES
('The Matrix Resurrections', 15, 148, 'Neo må vælge mellem realitet og matrix endnu en gang', 5),
('Spider-Man: No Way Home', 11, 148, 'Peter Parker får hjælp fra andre Spider-Men fra multiverset', 1),
('Dune', 11, 155, 'Paul Atreides rejser til den farlige planet Arrakis for at sikre sin families fremtid', 5),
('No Time to Die', 15, 163, 'James Bond møder sin farligste modstander nogensinde i sin sidste mission', 1),
('The Batman', 15, 176, 'En ung Bruce Wayne jager Riddleren gennem Gothams mørke gader', 7),
('Top Gun: Maverick', 11, 130, 'Pete "Maverick" Mitchell vender tilbage som instruktør for en ny generation pilote', 1),
('Encanto', 7, 102, 'En ung pige opdager at hendes familie har magiske kræfter - alle undtagen hende selv', 8),
('Scream', 15, 114, 'Ghostface vender tilbage til Woodsboro 25 år efter de oprindelige mord', 4),
('The King''s Man', 15, 131, 'Historien om den første uafhængige efterretningstjeneste', 1),
('House of Gucci', 15, 158, 'Den sande historie bag mordet på Maurizio Gucci', 3);

-- 3. THEATRES
INSERT INTO theatre (name, num_rows, seats_per_row) VALUES
('Sal 1 - IMAX', 15, 20),
('Sal 2 - Premium', 12, 18),
('Sal 3 - Standard', 10, 16),
('Sal 4 - Lille Sal', 8, 12),
('Sal 5 - VIP', 6, 14);

-- 4. SEATS (This will create a lot of seats - let's do it efficiently)
-- Sal 1 - IMAX (300 seats: 15 rows x 20 seats)
INSERT INTO seat (seat_row, seat_number, theatre_theatre_id)
SELECT r.row_num, s.seat_num, 1
FROM (SELECT 1 as row_num UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9 UNION SELECT 10 UNION SELECT 11 UNION SELECT 12 UNION SELECT 13 UNION SELECT 14 UNION SELECT 15) r
CROSS JOIN (SELECT 1 as seat_num UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9 UNION SELECT 10 UNION SELECT 11 UNION SELECT 12 UNION SELECT 13 UNION SELECT 14 UNION SELECT 15 UNION SELECT 16 UNION SELECT 17 UNION SELECT 18 UNION SELECT 19 UNION SELECT 20) s;

-- Sal 2 - Premium (216 seats: 12 rows x 18 seats)  
INSERT INTO seat (seat_row, seat_number, theatre_theatre_id)
SELECT r.row_num, s.seat_num, 2
FROM (SELECT 1 as row_num UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9 UNION SELECT 10 UNION SELECT 11 UNION SELECT 12) r
CROSS JOIN (SELECT 1 as seat_num UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9 UNION SELECT 10 UNION SELECT 11 UNION SELECT 12 UNION SELECT 13 UNION SELECT 14 UNION SELECT 15 UNION SELECT 16 UNION SELECT 17 UNION SELECT 18) s;

-- Sal 3 - Standard (160 seats: 10 rows x 16 seats)
INSERT INTO seat (seat_row, seat_number, theatre_theatre_id)
SELECT r.row_num, s.seat_num, 3
FROM (SELECT 1 as row_num UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9 UNION SELECT 10) r
CROSS JOIN (SELECT 1 as seat_num UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9 UNION SELECT 10 UNION SELECT 11 UNION SELECT 12 UNION SELECT 13 UNION SELECT 14 UNION SELECT 15 UNION SELECT 16) s;

-- Sal 4 - Lille Sal (96 seats: 8 rows x 12 seats)
INSERT INTO seat (seat_row, seat_number, theatre_theatre_id)
SELECT r.row_num, s.seat_num, 4
FROM (SELECT 1 as row_num UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8) r
CROSS JOIN (SELECT 1 as seat_num UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9 UNION SELECT 10 UNION SELECT 11 UNION SELECT 12) s;

-- Sal 5 - VIP (84 seats: 6 rows x 14 seats)
INSERT INTO seat (seat_row, seat_number, theatre_theatre_id)
SELECT r.row_num, s.seat_num, 5
FROM (SELECT 1 as row_num UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6) r
CROSS JOIN (SELECT 1 as seat_num UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9 UNION SELECT 10 UNION SELECT 11 UNION SELECT 12 UNION SELECT 13 UNION SELECT 14) s;

-- 5. EMPLOYEES
INSERT INTO employee (name, username, password) VALUES
('Anna Hansen', 'anna.hansen', 'password123'),
('Lars Nielsen', 'lars.nielsen', 'password123'),
('Maria Andersen', 'maria.andersen', 'password123'),
('Peter Jensen', 'peter.jensen', 'password123'),
('Sophie Larsen', 'sophie.larsen', 'password123'),
('Michael Christensen', 'michael.christensen', 'password123');

-- Employee roles
INSERT INTO employee_roles (employee_employee_id, roles) VALUES
(1, 'SALES_CLERK'),
(2, 'OPERATOR'), 
(3, 'SALES_CLERK'),
(4, 'OPERATOR'),
(5, 'SALES_CLERK'),
(6, 'SALES_CLERK');

-- 6. CUSTOMERS
INSERT INTO customer (name, email, phone, age) VALUES
('Emma Nielsen', 'emma.nielsen@email.dk', '20123456', 28),
('Oliver Hansen', 'oliver.hansen@gmail.com', '30234567', 35),
('Ida Christensen', 'ida.christensen@hotmail.com', '40345678', 24),
('Lucas Andersen', 'lucas.andersen@yahoo.dk', '50456789', 31),
('Freja Jensen', 'freja.jensen@email.dk', '60567890', 22),
('Noah Larsen', 'noah.larsen@gmail.com', '70678901', 29),
('Agnes Pedersen', 'agnes.pedersen@email.dk', '80789012', 45),
('William Sørensen', 'william.sorensen@gmail.com', '90890123', 38),
('Clara Møller', 'clara.moller@hotmail.com', '21012345', 26),
('Victor Nielsen', 'victor.nielsen@email.dk', '32123456', 33);

-- 7. SCREENINGS (Next 7 days with multiple screenings per day)
INSERT INTO screening (start_time, end_time, status, theatre_theatre_id, movie_movie_id, operator_employee_id) VALUES
-- Today
('2024-12-23 16:00:00', '2024-12-23 18:28:00', 'SCHEDULED', 1, 1, 2),
('2024-12-23 19:00:00', '2024-12-23 21:28:00', 'SCHEDULED', 2, 2, 4),
('2024-12-23 20:30:00', '2024-12-23 23:05:00', 'SCHEDULED', 1, 3, 2),

-- Tomorrow  
('2024-12-24 14:00:00', '2024-12-24 16:28:00', 'SCHEDULED', 3, 7, 2),
('2024-12-24 17:00:00', '2024-12-24 19:28:00', 'SCHEDULED', 1, 1, 4),
('2024-12-24 18:30:00', '2024-12-24 21:18:00', 'SCHEDULED', 4, 4, 2),
('2024-12-24 21:45:00', '2024-12-25 00:33:00', 'SCHEDULED', 2, 5, 4),

-- Day after tomorrow
('2024-12-25 13:00:00', '2024-12-25 15:10:00', 'SCHEDULED', 5, 6, 2),
('2024-12-25 16:00:00', '2024-12-25 18:28:00', 'SCHEDULED', 1, 2, 4),
('2024-12-25 19:30:00', '2024-12-25 22:05:00', 'SCHEDULED', 3, 3, 2),

-- Continue for next few days
('2024-12-26 15:00:00', '2024-12-26 17:28:00', 'SCHEDULED', 2, 1, 4),
('2024-12-26 18:00:00', '2024-12-26 20:28:00', 'SCHEDULED', 4, 2, 2),
('2024-12-26 21:00:00', '2024-12-26 23:35:00', 'SCHEDULED', 1, 3, 4),

('2024-12-27 14:30:00', '2024-12-27 17:18:00', 'SCHEDULED', 3, 4, 2),
('2024-12-27 19:00:00', '2024-12-27 21:28:00', 'SCHEDULED', 2, 1, 4),

('2024-12-28 16:00:00', '2024-12-28 18:28:00', 'SCHEDULED', 1, 2, 2),
('2024-12-28 20:00:00', '2024-12-28 22:56:00', 'SCHEDULED', 4, 5, 4);

-- 8. SNACK ITEMS
INSERT INTO snack_item (name, price, stock) VALUES
('Popcorn Lille', 35.00, 200),
('Popcorn Mellem', 45.00, 180),
('Popcorn Stor', 55.00, 150),
('Sodavand 0.33L', 25.00, 300),
('Sodavand 0.5L', 35.00, 250),  
('Slik Mix', 40.00, 100),
('Nachos med Dip', 50.00, 80),
('Kaffe', 30.00, 120),
('Øl 0.33L', 45.00, 90),
('Vand 0.5L', 20.00, 200);

-- 9. RESERVATIONS (Some existing bookings)
INSERT INTO reservation (reservation_date, paid, total_price, customer_customer_id, screening_show_id, sales_clerk_employee_id) VALUES
('2024-12-20 14:30:00', true, 320.00, 1, 1, 1),
('2024-12-21 10:15:00', true, 240.00, 3, 2, 3),
('2024-12-21 16:45:00', false, 480.00, 5, 3, 5),
('2024-12-22 11:20:00', true, 160.00, 2, 4, 1),
('2024-12-22 15:10:00', true, 200.00, 7, 5, 3),
('2024-12-23 09:30:00', false, 360.00, 4, 6, 5);

-- 10. RESERVATION SEATS (Book some specific seats for the reservations)
-- Reservation 1: 2 seats for Matrix in IMAX
INSERT INTO reservation_seat (reservation_reservation_id, screening_show_id, seat_seat_id) VALUES
(1, 1, 155), -- Row 8, Seat 15 (middle of IMAX)
(1, 1, 156); -- Row 8, Seat 16

-- Reservation 2: 2 seats for Spider-Man  
INSERT INTO reservation_seat (reservation_reservation_id, screening_show_id, seat_seat_id) VALUES
(2, 2, 350), -- Row 5, Seat 8 (Premium theater)
(2, 2, 351); -- Row 5, Seat 9

-- Reservation 3: 4 seats for Dune
INSERT INTO reservation_seat (reservation_reservation_id, screening_show_id, seat_seat_id) VALUES
(3, 3, 160), -- Row 8, Seat 20 (IMAX)
(3, 3, 140), -- Row 7, Seat 20
(3, 3, 159), -- Row 8, Seat 19  
(3, 3, 139); -- Row 7, Seat 19

-- Reservation 4: 2 seats for Encanto
INSERT INTO reservation_seat (reservation_reservation_id, screening_show_id, seat_seat_id) VALUES
(4, 4, 650), -- Row 3, Seat 8 (Small theater)
(4, 4, 651); -- Row 3, Seat 9

-- Reservation 5: 2 seats for Batman  
INSERT INTO reservation_seat (reservation_reservation_id, screening_show_id, seat_seat_id) VALUES
(5, 5, 100), -- Row 5, Seat 20 (IMAX)
(5, 5, 80);  -- Row 4, Seat 20

-- Reservation 6: 3 seats for Top Gun
INSERT INTO reservation_seat (reservation_reservation_id, screening_show_id, seat_seat_id) VALUES
(6, 6, 750), -- Row 6, Seat 8 (Small theater)
(6, 6, 751), -- Row 6, Seat 9
(6, 6, 752); -- Row 6, Seat 10