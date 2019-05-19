package com.fileservice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.fileservice.integration.FileServiceIntegrationTest;
import com.fileservice.service.FileOperationServiceTest;



@RunWith(Suite.class)
@Suite.SuiteClasses({ 
	
	FileOperationServiceTest.class,
	FileServiceIntegrationTest.class
	
})
public class FileServiceApplicationTests {

	@Test
	public void contextLoads() {
	}

}
