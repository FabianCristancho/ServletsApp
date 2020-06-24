#!/bin/bash
# -*- ENCODING: UTF-8 -*-
cd App
## compilacion de la clase
javac -d WEB-INF/classes/ -cp WEB-INF/lib/javax.servlet-api-4.0.1.jar src/Server.java
## crear el comprimido .war
jar cvf App.war *
## copiarlo en el directorio de despliegue de tomcat
cp App.war /var/lib/tomcat9/webapps
## reiniciar el servicio de tomcat
systemctl restart tomcat9
