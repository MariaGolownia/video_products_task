INSERT INTO director(first_name, last_name, birth_date)
VALUES ('Philipp', 'Stiolzl', '1967-01-01'),
('Duncan', 'Jones', '1971-03-30'),
('Ron', 'Howard', '1954-03-01'),
('Andrew', 'Stanton', '1965-12-03'),
('Christopher', 'Jonathan', '1970-07-30'),
('Eric', 'Bress', '1984-09-17'),
('Gore', 'Verbinski', '1964-03-16'),
('Peter', 'Jackson', '1961-10-31'),
('Ronald', 'Clements', '1953-04-25'),
('Richard', 'Matheson', '1926-02-20'),
('David', 'Michener', '1932-11-05');


INSERT INTO film (name, release_date, genre)
VALUES ('The Physician', '2013-12-25', 'adventure/history/drama'),
 ('Source code', '2011-03-11', 'fantastic/action/thriller'),
 ('A beautiful Mind', '2001-12-13', 'drama/biography/melodrama'),
 ('Finding Nemo', '2003-05-03', 'cartoon/comedy/adventure'),
 ('Finding Dory', '2016-06-17', 'cartoon/comedy/adventure'),
 ('Wally', '2008-06-21', 'cartoon/fantastic/adventure'),
 ('Interstellar', '2014-10-26', 'fantastic/drama/adventure'),
 ('Inception', '2014-10-26', 'fantastic/blockbuster/thriller'),
 ('The butterfly effect', '2004-01-22', 'fantastic/drama/thriller'),
 ('Pirates of the Caribbean: The Curse of the Black Pearl', '2003-06-28', 'fantasy/action/adventure'),
 ('Pirates of the Caribbean: Dead man’s chest', '2006-06-24', 'fantasy/action/adventure'),
 ('Pirates of the Caribbean: At world’s end', '2007-05-19', 'fantasy/action/adventure'),
 ('The Lord of the King: The two towers', '2002-12-15', 'fantasy/drama/adventure'),
 ('The Lord of the King: The Fellowship of the Ring', '2001-12-19', 'fantasy/drama/adventure'),
 ('The Lord of the King: The return of the King', '2003-12-01', 'fantasy/drama/adventure'),
 ('The Hobbit: The Battle of the five armies', '2003-12-01', 'fantasy/action/adventure'),
 ('The Hobbit: The Desolation of Smaug', '2013-12-02', 'fantasy/action/adventure'),
 ('The great mouse detective', '1986-07-02', 'cartoon/detective/musical');

INSERT INTO director_film (director_id, film_id)
VALUES ('1','1'),
 ('2', '2'),
 ('3', '3'),
 ('4', '4'),
 ('4', '5'),
 ('4', '6'),
 ('5', '7'),
 ('5', '8'),
 ('6', '9'),
 ('7', '10'),
 ('7', '11'),
 ('7', '12'),
 ('8', '13'),
 ('8', '14'),
 ('8', '15'),
 ('8', '16'),
 ('8', '17'),
 ('9', '18'),
 ('10', '18'),
 ('11', '18');