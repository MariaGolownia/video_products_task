select * from director_film
join director on director.id=director_film.director_id
join film on film.id=director_film.film_id
where director_film.id=21;
