FROM tomcat:8.0.20-jre8
ADD springBootTest-*.war /usr/local/tomcat/webapps/springBootTest.war
