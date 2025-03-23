# **CGI internship application**
This is an application for CGI internship made by Eric Rodionov

## **How to start application?**
### Java Maven Build
***The First way requires the java21 to work properly.***

navigate to the project directory and run next command:

```
mvn spring-boot:run 
```
after initialization, by default, the web application will be available via `localhost:8080/flights` address

#### configuration
if you want in-memory database (data will be lost with every program restart), add next line to *resources/application.properties* file:
`spring.datasource.url=jdbc:h2:mem:testdb`

if you want to store database locally (application will create database file in project directory), add next line to *resources/application,properties* file:
`spring.datasource.url=jdbc:h2:file:./database`

### Docker
The second way requires docker

navigate to the project directory and run next command:
```
docker-compose up --build
```
after initialization, by default, the web application will be available via `localhost:8080/flights` address

## **Description**
The application has two main pages - searching for a suitable flight and choosing seats.

### Flights
The flights address looks like this:

`/flights`

on the page you can select a flight from the list, and also filter the list by parameters:
- Flight code
- Departure day
- Arrival day
- Departure airport code
- Arrival airport code

### Seats
The address of the seats is built according to the following logic:

`/seats?flightnumber={string}&depiata={string}&arriata={string}`

On the page you can view and order available seats.
You can select seats either manually or using the seat selection system.

The seat selection system provides:
- selection of seat class
- selection of window seats
- selection of multiple seats

If multiple seats are selected, the program will try to seat as many people as possible in one row.
If the "prefer window seats" option is selected, the program will try to split the tickets into groups so that each group has at least one window seat.
If there are too many people, the program will simply show all available seat groups near windows.
If the number of tickets exceeds the number of seats, the program will warn you about this by giving an error

The names of seat owners are not indicated for anonymity.

## Other
**NB!** *For development purposes, if we want check tickets owner, we can enter next address:*

`/greeting?flightnumber={string}&depiata={string}&arriata={string}`

This page will provide us list with all seat data for given flight.


