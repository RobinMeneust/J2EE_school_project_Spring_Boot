# J2EE_school_project_Spring_Boot

## About

Example of an e-selling website coded in Java using Spring Boot, Jakarta EE, Hibernate and Persistence for a student project.

## Summary

## If you run this project for the first time:

1. Install the dependencies (sections "Dependencies" and "Installation")
2. In this project root folder, run `mvn clean package` ("Compilation" section)
3. In the same folder, run `docker-compose up -d` ("Run" section)
4. Go to the tomcat URL (http://localhost:8082/J2EE_Project-1.0-SNAPSHOT/)

## If the containers are running and the project has been compiled at least once (there is a WAR archive in the target folder):
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Run `mvn clean package` to update your changes and deploy the new version to the Tomcat server.

## Dependencies

- Docker
- Docker Compose
- Maven
- JDK (tested on JDK 20)

## Installation

### How to install Docker & Docker Compose

#### Linux

1. Install Docker: `sudo apt install docker.io`
2. Install Docker Compose: `sudo apt install docker-compose`

#### Windows

- Run `wsl --update` in the terminal
- Download & install: https://docs.docker.com/desktop/install/windows-install/

### How to build the containers

If you are on windows, Docker Desktop must be running.

- Run the following command in the root folder of this project: `docker-compose up -d`
- In IntelliJ, you can also right-click on docker-compose.yml in the project root folder and select Run docker-compose.yml

### How to stop and delete the containers

Run the following command in the root folder of this project: `docker-compose down`

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
  "username": "PLACEHOLDER_EMAIL_ADDRESS",
  "password": "PLACEHOLDER_PASSWORDS"
}
```

Replace `PLACEHOLDER_EMAIL_ADDRESS` with the email address used and `PLACEHOLDER_PASSWORD` with the password we generated in the previous step.

## Compile

Run `mvn clean package`

In IntelliJ, click on the Maven icon ("m" letter) in the right menu panel, open the Lifecycle list and click on "package" 

## Run

The containers must be running (See the section "Installation").<br>
The WAR archive, built after compiling this project, is automatically published on change (because of the Docker volumes). So you just need to open the website url as explained below.

### To open the website

Open in a web browser: http://localhost:8082/J2EE_Project-1.0-SNAPSHOT/

### To edit the database (admin)

#### Using PHP Admin

Go to http://localhost:8081/ and use the following account
- username: root
- password: root_password

#### Using the terminal

1. Run `docker ps`
2. Get the CONTAINER ID (1st column) of the mysql IMAGE
3. Replace CONTAINER_ID with what you got in (2.), in `docker exec -ti CONTAINER_ID bash` and run it
4. Enter `mysql -u root -p`
5. Type the password defined in the docker compose file: root_password
6. Enter `use j2ee_project_db;`
7. Run the queries you want to do (e.g. `select * from Customer`)

Note: The steps 1. to 3. included can be done through Docker Desktop. You just need to go to the containers tab, click on the group of containers of this project, and click on the container with the name "db", and go to the terminal tab.

## Connect to the database through your IDE

On IntelliJ for instance you can go to the right menu panel and click on "Database".<br>
Then, click on "+" and fill in the username and password of the MYSQL server (the root user) and finally add the URL: `jdbc:mysql://localhost:3307/j2ee_project_db`.<br>
The username for this project is "j2ee_user" and the password is "j2ee_password".

### Fixes for common issues

#### Docker containers can't be opened

- If you haven't restarted your PC since you installed Docker and WSL, try to do it
<br><br>
- If you can't run `docker ps`:
  1. `sudo groupadd docker`
  2. `sudo gpasswd -a $USER docker`
  3. `sudo service docker restart`
  4. `sudo chown $USER /var/run/docker.sock`
  5. Restart your computer again if it still doesn't work
     <br><br>
- If you can run `docker-compose up -d` but you get the error :<br>
   <code style="color:red">Error starting userland proxy: listen tcp4 0.0.0.0:3306: bind: address already in use</code><br>
   One or several ports used by the container may already be used so:
  1. `docker-compose down`
  2. Open the file docker-compose.yml in a text editor
  3. Change the ports that caused this error (3306 in the example above). You will change for instace the line `- "3306:3306` to `- "3307:3306`. <br>Note that the port on the left is for the host (http://localhost:3307) whereas the one on the right is for the other containers (e.g. to access the database from Tomcat we use the port 3306 and "db" instead of "localhost")
  4. `docker-compose up -d`
<br><br>
- If you get `JSP file [/J2EE_Project-1.0-SNAPSHOT/mailTest.jsp] not found`:
  1. Run `docker-compose down` to delete the containers or click on the delete button near the containers group in Docker Desktop
  2. `mvn clean package`
  3. `docker-compose up -d`
<br><br>
- If you have the error `...\J2EE_school_project\target\J2EE_Project-1.0-SNAPSHOT.war isn't a file.`:
  1. `mvn clean package`
     <br><br>
- If you have other errors, try to remove all the images, containers and volumes in Docker Desktop and run `docker-compose up -d` again.
## Authors

- Theo Gandy
- Robin Meneust
- Jeremy Saelen
- Lucas Velay