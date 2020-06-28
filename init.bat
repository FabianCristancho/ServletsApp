@echo off

cd App
javac -d WEB-INF/classes/ -cp WEB-INF/lib/javax.servlet-api-4.0.1.jar;src src/Server.java
javac -d WEB-INF/classes/ -cp WEB-INF/lib/javax.servlet-api-4.0.1.jar;src src/PingFile.java
javac -d WEB-INF/classes/ -cp WEB-INF/lib/javax.servlet-api-4.0.1.jar;src src/Ping.java

jar -cvf App.war *