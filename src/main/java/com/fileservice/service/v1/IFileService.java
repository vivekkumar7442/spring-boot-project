package com.fileservice.service.v1;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fileservice.exception.FileServiceBuisnessException;
import com.fileservice.request.FileRequest;
import com.fileservice.response.FileResponse;

/**
 * @author vivek IFileService used for the abstract Service API
 */
public interface IFileService extends IBaseService {

	List<FileResponse> getAllFiles(String fileType)throws FileServiceBuisnessException;

	
	FileResponse updateFileDetails(FileRequest fileRequest)throws FileServiceBuisnessException;
	

	FileResponse deleteFile(Integer fileId)throws FileServiceBuisnessException;


	FileResponse uploadFile(MultipartFile file,String fileName,String fileType)throws FileServiceBuisnessException;


}
