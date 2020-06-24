# ServletsApp

## Requisitos previos al despliegue de la aplicación
- Tener instalado java en el equipo
- Tener instalado apache tomcat en su versión 9

## Pasos para el correcto despliegue de la aplicación
1. Clone el repositorio en su equipo
2. Encarguese de crear manualmente dentro de la carpeta WEB-INF un directorio llamado classes
3. Cree en la carpeta WEB-INF otro directorio llamado lib y agregue en él la librería en formato .jar javax.servlet-api en su versión 4.0.1, que podrá descargar de https://mvnrepository.com/artifact/javax.servlet/javax.servlet-api/4.0.1
4. Ubicandose fuera de la carpeta App, ejecute el siguiente comando:

        $ sudo sh initApp.sh

5. Abra su navegador de preferencia, y digite localhost:8080/App/api
6. Si se hicieron los pasos anteriores de forma correcta, deberá ver un mensaje que describe que la aplicación se está ejecutando exitosamente
