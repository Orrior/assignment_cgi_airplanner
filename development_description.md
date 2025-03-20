## Development Description
I will use this file to track my development process and thoughts, and then reformat it in more readable format.

### Commit 0. Project Scopes.
I will start this assignment by defining what I need to do and how I think the tasks will be solved. It may vary from the finish product but this is a good way to keep goals clear and to not scope creep.

1. The flight data. Source of flight data should be easily interchangeable. To begin with, we'll make seed-able database that will imitate real flight data, later we'll make API solution. This way we can test our application during development by via database, so we wont be limited by quota. This is a good practice to not make data access layer hardcoded.
2. Create UI and logic to search flights. We need to be able to see all flights within desired conditions:
    - Date. By default, we need to be able to choose the day we want to travel. Optionally we can try to implement date range selection and to choose not only departure Date, but to search via arrival date.
    - Destination. The place where the passenger wants to fly. Ideally we shouldn't hardcode the departure location in logic, so that the code could be used for multiple departure airports.
    - Flight time. We should be able to limit max and min flight duration.
    - Price.
    - (Optionally) Add option to check if it is possible to get to the destination by transfers in addition to a direct flights. This may be complex since we don't want to make too much requests, but my guess that one transfer should be pretty easily doable.
3. Now we can make research about real-flight data API, to use it as Data access layer.
4. Create UI and logic to select seats in airplane. We need to implement ways to choose manually and add auto-chose by preferred options. The options are:
    - Seat next to window
    - Seat with extra legroom
    - Seats closer to the exit
    - Seats close to each other (An option for multiple tickets bought)
    We don't need to track real occupied seats, since we're allowed to generate them. But we still need to make seats configuration customizable, so we can make airplane of any form and size. Optionally we can also make multiple type of seats (1st class, business class, economy class), so we also should keep that in mind.
4. After all basic logic are written, tests should be written. Usually it is a good practice to write tests first, but I'm not sure how much time development will take and the structure of application is not so clear for me, so we'll write the tests post factum.
5. After tests application should be containerized for deployment simplification purposes.
6. Write a documentation for Application deployment and how to run tests.

### Commit 1.
For now, the best idea I have how to change between database and real data from API is to use Dependency injection. It will provide the demanded implementation of SchedFlightRepositoryI which may be Database or real data from API. This way if we want to replace or add new data sources we can just write new interface implementation.
### Commit 2.
I decided to make a first look at which API should be used later. And I haven't found any free APIs which would provide flight prices. 

All other data is fine, so I'll just generate prices on a flight duration basis for imitation purposes. This way we'll have generated fees unless real flight price is provided.

I tried to filter all data at once to create single Query request, and i found two ways to do it - Query By Example or via Specifications Executor. I decided to use the latest because it looks more flexible and clear to follow.
### Commit 3.
This is a rare case, but sometimes the flight may overlap, so instead of flight number primary key it is better to use the Composite Key consisting of flight number and both airport codes.

