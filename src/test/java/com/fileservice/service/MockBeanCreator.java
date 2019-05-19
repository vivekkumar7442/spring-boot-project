package com.fileservice.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fileservice.entity.entities.FileDetails;
import com.fileservice.request.FileRequest;

@Service
public class MockBeanCreator {

	public FileDetails createFileObject() {
		FileDetails fileDetails=new FileDetails();
		fileDetails.setFileId(1);
		fileDetails.setFileName("code");
		return fileDetails;
	}
	
	public List<FileDetails> getFilesList(){
		List<FileDetails> fileDetails=new ArrayList<>();
		 fileDetails.add(createFileObject());
		 return fileDetails;
	}

	public FileRequest getFileRequest() {
		FileRequest fileRequest=new FileRequest();
		fileRequest.setFileIds(Arrays.asList(1,3,4));
		fileRequest.setFileId(2);
		fileRequest.setType("pdf");
		fileRequest.setFileName("codJava");
		return fileRequest;
	}

}
