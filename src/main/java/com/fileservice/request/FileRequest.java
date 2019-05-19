package com.fileservice.request;

import java.util.List;

/**
 * @author vivek
 * 
 *         FileRequest used to map the request attributes from Client side
 *
 */
public class FileRequest {

	private Integer fileId;
	
	private List<Integer> fileIds;

	private String type;

	private String fileName;
	
	private String url;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<Integer> getFileIds() {
		return fileIds;
	}

	public void setFileIds(List<Integer> fileIds) {
		this.fileIds = fileIds;
	}

}
