## Project overview
Rest API for managing a movie database and handling the many-to-many relationships between genres, actors and movies. 

## Setup and installation instructions
1. Download Postman for testing
2. Download the Database folder
3. Open the folder in your preferred IDE
4. Run 'mvn clean install' to ensure all dependencies are downloaded and the project is built
5. Run 'DatabaseApplication.java' either through the terminal by using 'mvn spring-boot:run' or by opening the application file which is located at /src/main/java/FilmSociety/Database and clicking the run button somewhere in your editor.
6. The program should be running now :)
7. (Optional) You can also download and import my postman collections from the Postman folder so you dont have to manually add them yourself. 

# Usage guide
The program is mostly meant to be used through Postman, where you have to create CRUD requests for each entity.  
You can fetch data through your browser as well by putting the GET endpoints as the url. (eg. to get all movies localhost:8080/api/movies) (using Pretty-print is highly recommended for readability)  

Stuff to note:
* All lists should be alphabetically ordered so do not let the random ID numbers confuse you.  
* All lists are pageable
* All pages have default sizes if not provided with ?page={pageNumber}&size={pageSize}
* Total amount of pages can be found at the top of the lists ("totalPages")
* Total amount of elements in the list can be found at the bottom ("totalElements")
* The amount of elements on the given page is shown at the bottom as well ("elementsOnThisPage")
* Entities with associations will only be created if the given actors/movies/genres already exist. If they don't then the creation will throw an exception and the entity will not be made.
## Postman requests
The base of all of your request urls has to be localhost:8080/api (I will not add it to the examples)  
We will be adding all the GET, POST, PATCH and DELETE operations to the end of it.
* My premade postman collections can be found in the Postman folder. You can just import them if you don't want to add them manually.
### GET endpoints
There are a total of 12 different GET endpoints for fetching lists or specific objects.  
Make sure you use the GET method.
#### Actor
* Get all actors: /actors
* Get actor by ID: /actors/{id}
* Get all actors in a movie: /movies/{movieId}/actors
* Search actors by name: /actors/search?name={actorName}
#### Genre
* Get all genres: /genres
* Get genre by ID: /genres/{id}
#### Movie
* Get all movies: /movies
* Get movie by ID: /movies/{id}
* Get movies by release year: /movies?year={year}
* Get movies by genre: /movies?genre={genreId}
* Get all movies by actor: /movies?actor={actorId}
* Search movies by title: /movies/search?title={movieTitle}
### POST endpoints
There are a total of 3 POST endpoints. One for each entity.  
Make sure you use the POST method and add the fiels to a raw JSON body.
#### Actor
* Create a new actor: /actors  
You have to add "name" and "birthDate" fields to the JSON body.  
You can either create the actor on its own or add associations by adding "movies": [{"id":1}] to the JSON body for example.
#### Genre 
* Create a new genre: /genres  
You have to add a "name" field to the JSON body.  
You can also choose to create the genre on its own or add associations by adding "movies" to the body as well.
#### Movie
* Create a new movie: /movies  
You have to add "title", "releaseYear" and "duration" fields to the JSON body.  
You can choose to create the movie on its own or add associations by adding "genres" and "actors" to the JSON body.
### DELETE endpoints
There are a total of 3 DELETE endpoints. One for each entity.  
Make sure you use the DELETE method
#### Actor
* Delete an actor by ID: /actors/{id}  
If the actor has associated movies then it will throw an error.
* Force delete an actor: /actors/{id}?force=true  
Adding force=true will remove all the associations from the actor and deletes the actor.
#### Genre
* Delete a genre by ID: /genres/{id}  
If the genre has associated movies then it will throw an error.
* Force delete a genre: /genres/{id}?force=true  
This will remove all the associated movies and deletes the genre.
#### Movie
* Delete a movie by ID: /movies/{id}  
If the movie has associated genres or actors then it will throw an error.
* Force delete a movie: /movies/{id}?force=true  
This will remove all the associated genres and/or actors from the movie and deletes the movie.
### PATCH endpoints
There are a total of 3 PATCH endpoints. One for each entity.  
Make sure you use the PATCH method and also this will update only the fields that are given. 
#### Actor
* Update an actor by ID: /actors/{id}  
Put only the fields you want to update into the JSON body. If you want to remove all associated movies then you have to add "movies": []  
  (eg. To change only the name then only add "name" to the body)
#### Genre
* Update a genre by ID: /genres/{id}  
Put only the fields you want to update into the JSON body. If you want to remove all associated movies then you have to add "movies": [] 
#### Movie
* Update a movie by ID: /movies/{id}  
Put only the fields you want to update into the JSON body. If you want to remove all associated genres or actors then you just have to add "genres": [] or "actors": [] respectively.

## Bonus Functionality
* You can create genres and actors with associated movies as well. Not just movies with associated genres and actors.
* The database doesn't allow duplicate names and titles (Similarly how some actor unions don't allow multiple actors with same names in their databases)