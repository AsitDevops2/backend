From openjdk:8
Expose 8085
ADD /raleyRegistration.jar raleyRegistration.jar
ENTRYPOINT ["java","-jar","raleyRegistration.jar"]
CMD java - jar raleyRegistration.jar