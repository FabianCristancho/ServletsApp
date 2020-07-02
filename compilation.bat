@echo off

cd App
javac -d WEB-INF/classes/ -cp WEB-INF/lib/javax.servlet-api-4.0.1.jar;WEB-INF/lib/jsch-0.1.55.jar;src src/Server.java
javac -d WEB-INF/classes/ -cp WEB-INF/lib/javax.servlet-api-4.0.1.jar;WEB-INF/lib/jsch-0.1.55.jar;src src/PingFile.java
javac -d WEB-INF/classes/ -cp WEB-INF/lib/javax.servlet-api-4.0.1.jar;WEB-INF/lib/jsch-0.1.55.jar;src src/Ping.java
javac -d WEB-INF/classes/ -cp WEB-INF/lib/javax.servlet-api-4.0.1.jar;WEB-INF/lib/jsch-0.1.55.jar;src src/Node.java
javac -d WEB-INF/classes/ -cp WEB-INF/lib/javax.servlet-api-4.0.1.jar;WEB-INF/lib/jsch-0.1.55.jar;src src/MyQueue.java

jar -cvf App.war *