# Fetch Rewards Coding Exercise

## Steps to setup and run this service ##
From terminal:
1. Install Java (OpenJDK 19 was used for development)
2. Install Maven (Apache Maven 3.8.6 was used for development)
3. Run ‘mvn clean’
4. Run ‘mvn install’
5. Run ‘./mvnw spring-boot:run’<br>
(If run into any maven folder (.mvn) related issue, Run ‘mvn -N io.takari:maven:wrapper’)

This will start the service. In a new terminal window, run the API curls.
API curls used for manual testing according to the problem statement doc can be found in resources folder - src/main/resources/curls.txt

Alternatively, this service can also be run in an editor / development environment such as IntelliJ IDEA.
API curls can also be run using an API platform such as Postman. Postman collection can be found in same resources folder - src/main/resources/postman_apis_collection.json. This can be directly imported into Postman.

## Assumptions and design decisions used ##
1. APIs are only for a specific user.
2. Points are integers and not decimals.
3. Reads need to be faster than writes - Sorting is done during write (adding a new transaction) to keep reads (spend points) faster.
4. Spend points should only be positive.
5. Spend points should not be more than points available (balance).
6. If more than one transactions have same date, they are processed in the order of insertion.
7. Negative points are treated same in the order of timestamp.
8. Dao uses in-memory list for transactions, instead of any persistent storage system.

## Further possible improvements ##
1. Response body can be improved.
2. Appropriate response codes can be added.
3. Separate archive list for inactive transactions can be maintained and removed from the list of active transactions. Right now all transactions stay in the main list in cache like a ledger.
4. JUnit testing can be added for all apis.
5. Swagger Tools can be integrated for API documentation. Right now, comments in source code provide this documentation.
