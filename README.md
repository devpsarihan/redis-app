# Redis App
### Project Goal
This app was developed to explain redis and its features.

### Tech Stack
* Redis
* Jedis
* Java 21
* Spring Boot v3.3.2
* PostgreSQL
* Flyway
* Testcontainers
* Docker

### Run the Project
* Compile with Java 21
* Go to the project folder and run this commands
```
  $ cd redis-app
  $ mvn clean install 
  $ docker build -t redis-app-image .
  $ docker-compose -f docker-compose.yaml up -d
```
* If you want to add host file
```
0.0.0.0 redis-app
```

### Documentation
### * [`Swagger Link`](http://localhost:8080/doc)

### Curl Commands
```
curl --location 'http://localhost:8080/v1/products' \
--header 'Content-Type: application/json' \
--header 'Accept: */*' \
--data '{
  "title": "<string>",
  "description": "<string>",
  "price": "<number>",
  "count": "<integer>"
}'
```
```
curl --location 'http://localhost:8080/v1/products/{{productId}}' \
--header 'Accept: */*'
```
```
curl --location 'http://localhost:8080/v1/products' \
--header 'Accept: */*'
```
```
curl --location --request PUT 'http://localhost:8081/v1/products/{{productId}}' \
--header 'Content-Type: application/json' \
--header 'Accept: */*' \
--data '{
  "title": "<string>",
  "description": "<string>",
  "price": "<number>",
  "count": "<integer>"
}'
```
```
curl --location --request DELETE 'http://localhost:8080/v1/products/{{productId}}'
```
