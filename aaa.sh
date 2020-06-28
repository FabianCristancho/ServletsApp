#!/bin/bash
cd App
## compilacion de la clase server.java
javac -d WEB-INF/classes/ -cp WEB-INF/lib/javax.servlet-api-4.0.1.jar:src src/Server.java
javac -d WEB-INF/classes/ -cp WEB-INF/lib/javax.servlet-api-4.0.1.jar:src src/Ping.java
javac -d WEB-INF/classes/ -cp WEB-INF/lib/javax.servlet-api-4.0.1.jar:src src/Ping.java
##crear el comprimido .war para si envio
jar cvf App.war *
## copiarlo en el directorio de despliegue de tomcat
cp App.war /var/lib/tomcat9/webapps
## reiniciar el servidor de tomcat.
systemctl restart tomcat9 

 
