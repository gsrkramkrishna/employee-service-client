# employee-service-client
Employee Service Client: 
Enable Eureka Cleint for the https://github.com/gsrkramkrishna/spring-cloud-stream-employee-service using @EnableDiscoveryClient
Run the below services:
eureka-server
spring-cloud-stream-employee-service
employee-service-client - Discovery Client is used to call the spring-cloud-stream-employee-service.
It's handling the client ,server and unknown host exceptions and returns the response.

Please use the below endpoint and make a call then it calls the spring-cloud-stream-employee-service using Discovery Client API.
http://localhost:9080/v1/emp
Post Method
Payload: 

{ "firstName":"James",
   "lastName":"Bond"
}


