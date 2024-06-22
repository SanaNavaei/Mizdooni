# Mizdooni

## Description

This repository contains the projects of the Internet Engineering course at the University of Tehran.  
Mizdooni is an online restaurant table reservation system.

## Phases

### CA1: Back-end Logic

The basic back-end logic of the system is implemented in Java.  
A command-line interface is provided for the user to interact with the system.  
Some JUnit tests are also written to assure correctness.

### CA2: MVC Architecture with JSP

The codebase is refactored to follow the MVC architecture by separating models, controllers, services, and views.  
Services are the business logic of the system and make use of the models.  
Controllers are implemented using the Jakarta Servlet API.  
The basic HTML views are implemented using Java Server Pages (JSP).  
The required request context is passed from controllers to the views, which are then rendered using JSP tags.  
The system is deployed on a Tomcat server and can be accessed through the browser.

### CA3: HTML & CSS

The views are redesigned using HTML and CSS.  
The given Figma designs are implemented as closely as possible.  
Static web pages are created using Bootstrap.  
The pages are fully responsive from mobile devices (360px width) to desktop screens.

### CA4: Spring & React

The back-end is rewritten using the Spring REST framework.  
The MVC pattern is replaced with RESTful architecture, having JSON endpoints for resources.  
A custom response format is defined for the API.

The front-end is implemented using React.  
The previous static pages have been replaced with React components.  
React Router is used for navigation between pages and API calls are made using fetch.

### CA5: Database & ORM

The system is connected to a MySQL database.  
The models now have JPA annotations, and Hibernate ORM is used for database operations.  
Database queries are made using the Spring Data JPA repository.  
Searching restaurants and paging are handled in the database.  
Initial application data is read from a given endpoint.

### CA6: JWT & OAuth

User passwords are now stored as hashed values in the database.  
The system uses JSON Web Tokens for authentication and authorization.  
The front-end stores the token and sends it as a Bearer token in the request headers.  
The system also supports OAuth authorization using Google Sign-In.

### CA7: Application Performance Monitoring

Elastic APM is integrated into the system to monitor its performance.  
The system is run using the Elastic APM Java agent.  
Several metrics are defined and visualized in the Kibana dashboard.  
Custom transactions and spans are defined for better tracing.  
Domain specific metrics are also added to the system.

### CA8: Docker

Dockerfiles are written to containerize the back-end and front-end applications.  
The Docker images are built and pushed to the Docker Hub.  
Docker Compose is used to run the system with a MySQL database and Elastic APM.  
The environment variables, secrets, and health checks are handled in the Docker Compose file.  
The front-end is served using the nginx web server, which proxies API requests to the back-end.

### CA9: Kubernetes

Kubernetes manifests are written to deploy the system on a Kubernetes cluster.  
Kubectl is configured to connect to the given cluster namespace.  
Deployments, services, and configmaps are defined for the back-end, the front-end, and the MySQL database.  
The database uses a persistent volume claim to store data, and the credentials are stored in a secret.  
The front-end service is exposed to the internet.  

---

[**SanaNavaei**](https://github.com/SanaNavaei) & [**MisaghM**](https://github.com/MisaghM)
