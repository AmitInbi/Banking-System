//This is a shared project by Amit Inbar and Itay Grinberg

# about

This project will create a Virtual Banking branch using Java & Python with the ability to:
* Login to your account with password validation.
* Deposit & withdraw money from the account.
* Watch previous account transactions.
* Create new customers. 
* Many more banking applications.

# Implementation

The project is implemented using:
* Python-Flask as a web-GUI for all user operations (including html and minimal css).
* PY4J library to act as a gateway between Python and Java (implemented with designated Java API)
* Java back-end using Customer and Manager objects and Auth for validating login credentials.
* DB's implemented using JSON files updated for every operation.


# Usage:

To use the Banking System please make sure to run both:
Module/src/py4j_EntryPoint.java  ->  For the Java side PY4J entrypoint and API connection.
Module/src/Web Application/main.py  ->  For the Flask GUI user side system.
