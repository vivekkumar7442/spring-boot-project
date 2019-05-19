package com.fileservice.response;

import com.fileservice.entity.entities.FileDetails;

/**
 * @author vivek
 * FileResponse used to map file data from database to client response
 */
public class FileResponse {
	
	private Integer fileId;

	private String fileName;

	private String fileType;

	private String url;
	
	public FileResponse() {
		//do nothingh
	}
	
	public FileResponse(FileDetails fileDetails) {
		
		this.fileId=fileDetails.getFileId();
		this.fileName=fileDetails.getFileName();
		this.fileType=fileDetails.getFileType();
		this.url=fileDetails.getUrl();
	}

	public Integer getFileId() {
		return fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	

}
