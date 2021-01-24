# Echat-SpringBoot
A lightweight online chat room project based on SpringBoot + WebSocket.
On the basis of MccreeFei's chat room, it is upgraded to the SpringBoot version, the JSP file is removed, the sql statement written in xml is removed, and thymeleaf and annotations are used. , More convenient to maintain and use.

# Run
1. First use JAVA keytool to generate pfx certificate for https server

keytool -genkey -alias test -keypass 123456 -keyalg RSA -sigalg sha256withrsa -keysize 1024 -validity 365 -keystore D:\KSEC\2021\1\WebSocket\Echat-springboot-master\src\main\resources\test.jks- storepass 123456

2. Run src/main/java/../util/JKS2PFX.main

3. Run .sql (mysql 8+)
Modify the port number to 8088 and database configuration (yml) and pfx certificate directory (application.properties)
4. Run and visit the homepage https://localhost:8088/

