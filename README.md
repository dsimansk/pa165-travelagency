## TRAVEL AGENCY 2012 ##

a simple enterprise application for PA165 @ FI MUNI

[![Build Status](https://travis-ci.org/dsimansk/pa165-travelagency.svg?branch=master)](https://travis-ci.org/dsimansk/pa165-travelagency)


#### REQUIREMENTS ####

- JRE 7
- Apache Maven
- a running Java Database (Derby) on port 1527: 
```  
  DB name pa165
  username pa165    
  and password pa165
```


#### RUNNING THE APPLICATION ####

Simply run the startup.sh (Unix, Linux) or startup.bat (Windows)
located in ./travelagency and ./travelagency-client 

Web application runs at http://localhost:8080/pa165/ .

Client application runs at http://localhost:8081/pa165-client/ .



#### DEFAULT ACCESS CREDENTIALS ####

```
ADMIN ROLE
login: admin password: admin
```
```
CUSTOMER ROLE
login: customer password: customer
```
```
REST API
login: rest password: rest
```






