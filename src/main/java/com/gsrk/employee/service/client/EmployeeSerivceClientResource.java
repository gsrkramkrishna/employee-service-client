package com.gsrk.employee.service.client;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.UnknownHttpStatusCodeException;

@RestController
@RequestMapping("/v1")
public class EmployeeSerivceClientResource {
	private Logger logger = LoggerFactory.getLogger(EmployeeSerivceClientResource.class);

	@Autowired
	private RestTemplate restTemplate;

	@RequestMapping(value = "/emp", method = RequestMethod.POST)
	public ResponseEntity<String> saveEmployee(@RequestBody Employee employee) {
		logger.info("Employee Reuest Payload:{}", employee);
		// setting up the HTTP Basic Authentication header value
		// String authorizationHeader = "Basic " +
		// DatatypeConverter.printBase64Binary((username + ":" + password).getBytes());

		HttpHeaders requestHeaders = new HttpHeaders();
		// set up HTTP Basic Authentication Header
		// requestHeaders.add("Authorization", authorizationHeader);
		requestHeaders.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);

		// request entity is created with request headers
		ResponseEntity<String> out = null;
		try {
			StringBuffer sbURL = new StringBuffer(serviceUrl() + "/v1/emp");
			HttpEntity<Employee> requestEntity = new HttpEntity<Employee>(employee);
			out = restTemplate.exchange(sbURL.toString(), HttpMethod.POST, requestEntity, String.class,
					employee);
			logger.info("Employee Response code:{}", out.getStatusCode());
			return out;
		} catch (HttpClientErrorException ex) {
			logger.error("HttpClient Error Message:"+ex.getMessage());
			  ResponseEntity<String> clientError = new ResponseEntity<String>(ex.getResponseBodyAsString(),ex.getResponseHeaders(),ex.getStatusCode());
			  return clientError;
		} catch (HttpServerErrorException  ex) {
			logger.error("HttpServer Error Message:"+ex.getMessage());
			  ResponseEntity<String> serverError = new ResponseEntity<String>(ex.getResponseBodyAsString(),ex.getResponseHeaders(),ex.getStatusCode());
			  return serverError;
		}catch (UnknownHttpStatusCodeException ex) {
			logger.error("Unknown Host Error Message:"+ex.getMessage());
			 ResponseEntity<String> unknownHostError = new ResponseEntity<String>(ex.getResponseBodyAsString(),ex.getResponseHeaders(),null);
			  return unknownHostError;
		} 
		catch (Exception ex) {
			logger.error("Server error Message:"+ex.getMessage());
			 ResponseEntity<String> serverException = new ResponseEntity<String>(ex.getMessage(),null);
			 return serverException;
		}

	}

	@Autowired
	private DiscoveryClient discoveryClient;

	public String serviceUrl() {
		List<ServiceInstance> serviceInstances = discoveryClient.getInstances("EMPLOYEE-SERVICE");
		if (serviceInstances != null && serviceInstances.size() > 0) {
			for(ServiceInstance si:serviceInstances) {
				logger.info("list of hosts:"+si.getUri());
			}
			String URL = serviceInstances.get(0).getUri().toString();
			logger.info("Employee Reuest URL:{}", URL);
			return URL;
		}
		return null;
	}
}
