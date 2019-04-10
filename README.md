# N26 Coding Challenge

We would like to have a RESTful API for our statistics. The main use case for the API is to calculate real time statistics for the last 60 seconds of transactions.
The API needs the following endpoints:
  
  * POST /transactions – called every time a transaction is made. It is also the sole input of this rest API.
  * GET /statistics – returns the statistic based of the transactions of the last 60 seconds.
  * DELETE /transactions – deletes all transactions.
  
### Running the application
mvn spring-boot:run

## Available Endpoints requested by challenge

- POST /transactions : called every time a transaction is made. It is also the sole input of this rest API.
- GET /statistics    : returns the statistic based of the transactions of the last 60 seconds.
- DELETE /transactions : deletes all transactions.

## Code explanation
#### Implementation od in memory transaction container
As it was requested that no database are used (including in-memory). Application is using fixed array for storing incoming transaction. Size of array can be configured using application.yaml. Two properites are relevant time.frame.duration (set to 60000 ms) and time.frame.unit.interval (set to 1000 ms, this property defines duration of interval in time frame).
Size of array will be set to time.frame.duration/time.frame.unit.interval value.

Once transactions comes in system, following steps are going to be executed:
1. Index of transaction will be calculated (position in array where transaction will be stored)
2. When index is calculated logic proceeds to storing transaction:
  * If array at index position is empty transaction will be stored in it (as statistic)
  * If there is stored valid transaction, two of them will be combined (into statistic)
  * If there is stored invalid transaction, invalid transaction will be deleted and fresh one will be stored

#### Calculating Statistics
According to current time, all valid statistics will be fetched, and based on them final result will be calculated

## Time Complexity
- Storing transaction O(1) storing of transaction is done in constant number of operations (placing transaction in fixed array)

- Fetching statistics will be done in two steps: fetching all valid items from array (maximum number of iterations time.frame.duration/time.frame.unit.interval value) - O(1), and calculating final result (maximum number of merging 60) - O(1)

## Space Complexity
O(1) as explained above array size is constant (defined using application.yaml properties). It size doesn't depend on number of transaction processed by application


## Concurrency Handling
Concurrency handling is accomplished using ReadWriteLocks
