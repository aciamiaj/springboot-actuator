package com.humber.j2ee.controller;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.boot.actuate.endpoint.annotation.DeleteOperation;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.stereotype.Component;

@Component //automatically detects and registers Spring-managed beans in the application context
@Endpoint(id="release-notes") //marks the class CustomEndpoint as a custom Actuator endpoint
public class CustomEndpoint {
	
	//It specifies that releaseNotesMap is a map that associates strings (keys) with lists of strings (values)
	Map<String, List<String>> releaseNotesMap = new LinkedHashMap<>();
	
	//It ensures that this method is executed automatically after the bean creation and dependency injection process is complete.
	@PostConstruct 
	public void initNotes() {
		releaseNotesMap.put("version-1.0", Arrays.asList("Home page created", "Adding a new item form added", "View the watchlist page added"));
		releaseNotesMap.put("version-1.1", Arrays.asList("Reading from OMDb API added", "Actuator endpoints added"));	
		//put: This map is intended to store release notes information. Adds entry to releaseNotesMap
		//Arrays.asList: It takes a variable number of arguments (in this case, strings) and returns a List containing those elements
	}
	
	//Used to define a custom read operation for an Actuator endpoint. 
	//same as GET Method in Springboot or Spring MVC /release-notes
	@ReadOperation 
	public Map<String, List<String>> getReleaseNotes() {
		return releaseNotesMap;
	}
	 
	// /actuator/release-notes/version-1.0 or /actuator/release-notes/version-1.1
	//@Selector: used in methods of a custom Actuator endpoint to dynamically handle different versions of release notes
	@ReadOperation
	public List<String> getNotesByVersionList(@Selector String version) {
		return releaseNotesMap.get(version);
	}
	
	//Used to define custom write operations for endpoint.
	//same as POST Method http://localhost:8080/actuator/release-notes/version-1.3
	//in POSTMAN -> POST  {"releaseNotes": "login implemented, reset password added"}
	@WriteOperation
	public void addReleaseNotes(@Selector String version, String releaseNotes) {
		releaseNotesMap.put(version, Arrays.stream(releaseNotes.split(",")) 
					   .collect(Collectors.toList()));
		//Arrays.stream: takes the releaseNotes string and splits it into an array of strings using a comma (,) as the delimiter
		//Collectors.toList(): collects the elements of the stream into a List 
	}
	
	//Used to define custom delete operations for endpoint.
	//same as DELETE Method
	//in POSTMAN -> DELETE http://localhost:8080/actuator/release-notes/version-1.3
	@DeleteOperation 
	public void deleteNotes(@Selector String version) {
		releaseNotesMap.remove(version);
	}
}