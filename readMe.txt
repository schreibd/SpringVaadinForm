Build with:
mvn clean install
mvn package
java -jar testApplication-0.0.1-SNAPSHOT.jar

TomCat Server starts on port: 8080
Form to create account for company: http://localhost:8080

RestCalls:
http://localhost:8080/partners
http://localhost:8080/partner/{companyName}
http://localhost:8080/addresses
http://localhost:8080/address/{id}