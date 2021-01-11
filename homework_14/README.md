1.	Install docker environment from https://www.docker.com/get-started

2.	Dockerize spring-boot service that you created in scope of HT_5 
(create Dockerfile and build image using docker build command)

3.	Create docker-compose.yaml which consists of network configuration and two services (spring-boot application and database)


Success criteria

As a homework-checker I should:

1.	run ```docker-compose up``` in the directory which contains docker-compose.yaml as result I will get fully-functional CRUD service (I can send appropriate request and entity will be created-read-updated-deleted in the database)
