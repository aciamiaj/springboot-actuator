package com.humber.j2ee;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.info.Info.Builder;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import com.humber.j2ee.repository.UserDatabase;

@Component
public class UserStatusCountContributor implements InfoContributor{

	@Autowired
	private UserDatabase database;
	
	//Dynamic information in /actuator/info
	@Override
	public void contribute(Builder builder) {
		Map<String, Long> userStatusMap=new HashMap<>(); 
		userStatusMap.put("active", database.getUserStatusCountByStatus("active")); //adds an entry to the userStatusMap
		userStatusMap.put("inactive", database.getUserStatusCountByStatus("inactive"));
		builder.withDetail("userStatus", userStatusMap);
		//New HashMap called userStausMap is created. This map is intended to store the counts of user statuses (e.g., "active" and "inactive").
		//withDetail: to add details about user status. 
		//The first argument "userStatus" is the key for the detail, 
		//and the second argument userStatusMap is the value associated with that key. 
		
	}

}
