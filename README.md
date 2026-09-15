# Classic mistakes with Java concurrency stress-tested and statically checked

## Purpose
Present a corpus of thread safety mistakes adapted from Java Concurrency In Practice (Allison-Wesley, 2005) as hands-on examples supplied with JVM stress testing and static code analysis.

## Prerequisites
Java 21 (e.g. ```sdk use java 21.0.12+1.1-tem```)
Maven 3+

## Running
```bash
mvn clean install                      # also shows ErrorProne output
mvn spotbugs:check                     # Spotbugs summary (open results/index.html)
java -jar target/jcstress.jar  -f 1 -v # jcstress test
```
