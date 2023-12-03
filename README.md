# J2EE_school_project_Spring_Boot

## About

Example of an e-selling website coded in Java using Spring Boot, Jakarta EE, Hibernate and Persistence for a student project.

## Dependencies

- Maven
- JDK (tested on JDK 20)

## Installation

### Mail Server

You can either create a new Gmail account or use an existing one from where the mails will be sent to the customers.
Once you are connected to this account, follow the following steps.

#### Configure your account

1. Click on your profile picture once you are connected to your Google account and select "Manage your Google Account"
2. Go to the "Security" tab
3. Click on 2-Step Verification in the section "How to sign in to Google" and follow the given steps
4. Once the 2-Step Verification is set up, go to the associated page and click on App passwords (it should be at the bottom of the page)
5. Create a new app specific password by typing a name for your project (choose whatever you want) and click on "Create"
6. Copy the displayed password, you will need it in the next step below

#### Add your credentials to this project

Create a file named "credentials.json" with the following content in src/main/resources/ <br>

```JSON
{
  "gmail": {
    "username": "PLACEHOLDER_EMAIL_ADDRESS",
    "password": "PLACEHOLDER_PASSWORD"
  }
}
```
Replace `PLACEHOLDER_EMAIL_ADDRESS` with the email address used and `PLACEHOLDER_PASSWORD` with the password we generated in the previous step.

### Stripe (payment system)

Use this mock credit card number for testing purposes: 4242424242424242, and use a date that isn't in the past and a random CVC (3 numbers)

#### Configure your account

1. Go to https://stripe.com/
2. Sign up
3. When you are logged in, go to https://dashboard.stripe.com/test/apikeys
4. Then copy the secret key and the publishable key, they will be added in credentials.json as mentioned below

#### Add your credentials to this project

Add to "credentials.json" your secret API key

```JSON
{
  "gmail": {
    "username": "PLACEHOLDER_EMAIL_ADDRESS",
    "password": "PLACEHOLDER_PASSWORD"
  },
  "stripe": {
    "publishable-key": "PLACEHOLDER_STRIPE_PUBLISHABLE_KEY",
    "secret-key": "PLACEHOLDER_STRIPE_SECRET_KEY"
  }
}
```


## How to use

`./mvnw spring-boot:run`

### To open the website

Open in a web browser: http://localhost:8082/J2EE_Project-1.0-SNAPSHOT/

### To edit the database (admin)

Go to http://localhost:8080/h2-console

Fill the form:

- Saved Settings: Generic H2 (Embedded)
- Driver Class: org.h2.Driver
- JDBC URL: `jdbc:h2:file:./data/j2ee_project_db;AUTO_SERVER=TRUE;MODE=MYSQL;DATABASE_TO_LOWER=TRUE;IGNORECASE=FALSE`
- Default username: j2ee_user
- Default password: j2ee_password

And click on "Connect"

## Connect to the database through your IDE

On IntelliJ for instance you can go to the right menu panel and click on "Database".<br>
Then, click on "+" and fill in the username and password of the H2 server (see credentials above) and finally add the URL: `jdbc:h2:file:./data/j2ee_project_db;AUTO_SERVER=TRUE;MODE=MYSQL;DATABASE_TO_LOWER=TRUE;IGNORECASE=FALSE`.<br>

## Authors

- Theo Gandy
- Robin Meneust
- Jeremy Saelen
- Lucas Velay