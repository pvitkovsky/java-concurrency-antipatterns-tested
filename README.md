# JCTest for the concurrency examples

## Running
```bash
sdk use java 21.0.12+1.1-tem
mvn clean verify
java -jar target/jcstress.jar -t <YourTestClass> -f 10 -v
 ```


