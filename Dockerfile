FROM tomcat:8.0.20-jre8
ENV SPRING_CONFIG_LOCATION /usr/local/tomcat/config-overrides
ADD springBootTest-*.war /usr/local/tomcat/webapps/springBootTest.war
