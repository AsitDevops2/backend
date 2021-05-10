From openjdk:8
Expose 8085
ADD /target/raleyRegistration-1.0.jar raleyRegistration-1.0.jar
ENTRYPOINT ["java","-jar","raleyRegistration-1.0.jar"]