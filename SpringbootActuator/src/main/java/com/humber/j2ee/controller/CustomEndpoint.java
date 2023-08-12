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

@Component
@Endpoint(id="release-notes")
public class CustomEndpoint {
	
	Map<String, List<String>> releaseNotesMap = new LinkedHashMap<>();
	
	@PostConstruct
	public void initNotes() {
		releaseNotesMap.put("version-1.0", Arrays.asList("Home page created", "Adding a new item form added", "View the watchlist page added"));
		releaseNotesMap.put("version-1.1", Arrays.asList("Reading from OMDb API added", "Actuator endpoints added"));
		
	}
	
	@ReadOperation //same as GET Method /release-notes
	public Map<String, List<String>> getReleaseNotes() {
		return releaseNotesMap;
	}
	
	//same as GET Method /actuator/release-notes/version-1.0 or /actuator/release-notes/version-1.1
	@ReadOperation
	public List<String> getNotesByVersionList(@Selector String version) {
		return releaseNotesMap.get(version);
	}
	
	//same as POST Method http://localhost:8080/actuator/release-notes/version-1.3
	//in POSTMAN -> POST  {"releaseNotes": "login implemented, reset password added"}
	@WriteOperation
	public void addReleaseNotes(@Selector String version, String releaseNotes) {
		releaseNotesMap.put(version, Arrays.stream(releaseNotes.split(","))
				.collect(Collectors.toList()));
	}
	
	//same as DELETE Method
	@DeleteOperation
	public void deleteNotes(@Selector String version) {
		releaseNotesMap.remove(version);
	}
}