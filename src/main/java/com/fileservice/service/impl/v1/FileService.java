package com.fileservice.service.impl.v1;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.fileservice.entity.FileSpecification;
import com.fileservice.entity.entities.FileDetails;
import com.fileservice.entity.repository.IFileRepository;
import com.fileservice.exception.ExceptionConstants;
import com.fileservice.exception.FileServiceBuisnessException;
import com.fileservice.request.FileRequest;
import com.fileservice.response.FileResponse;
import com.fileservice.service.v1.IFileService;

/**
 * @author vivek
 * 
 *         FileService has all the endpoints related to file operations
 *
 */
@Service
@Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
public class FileService implements IFileService {

	@Autowired
	IFileRepository iFileRepository;

	/**
	 * this method is used to return all the files based on type if type is null
	 * will return all the files irrespective of file type
	 * 
	 * @param fileType
	 * @return List<FileResponse>
	 * @throws FileServiceBuisnessException 
	 */
	@Override
	public List<FileResponse> getAllFiles(String filetype) throws FileServiceBuisnessException {
		if (filetype == null) {
			throw new FileServiceBuisnessException(ExceptionConstants.MANDATORY_REQUEST);

		}
		return iFileRepository.findAll(FileSpecification.getFilter(filetype)).stream().map(FileResponse::new)
				.collect(Collectors.toList());

	}

	/**
	 * this method is used to update the file details and return the updated file
	 * details
	 * 
	 * @param fileRequest
	 * @return
	 * @throws FileServiceBuisnessException
	 */

	@Override
	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
	public FileResponse updateFileDetails(FileRequest fileRequest) throws FileServiceBuisnessException {
		if (fileRequest == null)
			throw new FileServiceBuisnessException(ExceptionConstants.MANDATORY_REQUEST);

		FileResponse fileResponse = new FileResponse();

		iFileRepository.findById(fileRequest.getFileId()).map((file) -> {
			return updateFile(fileRequest, file, fileResponse);
		}).orElseThrow(() -> new FileServiceBuisnessException(ExceptionConstants.FILE_NOT_FOUNT));

		return fileResponse;
	}

	/**
	 * this method delete the files based on list of file Ids
	 * 
	 * @param fileRequest
	 * @return
	 * @throws FileServiceBuisnessException
	 */
	@Override
	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
	public FileResponse deleteFile(Integer fileId) throws FileServiceBuisnessException {
		if (fileId == null)
			throw new FileServiceBuisnessException(ExceptionConstants.MANDATORY_REQUEST);

		FileResponse fileResponse = new FileResponse();
			iFileRepository.findById(fileId).ifPresent(this::deleteEachFile);
		return fileResponse;

	}

	@Override
	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
	public FileResponse uploadFile(MultipartFile file, String fileName, String fileType)
			throws FileServiceBuisnessException {
		// TODO": since we as assuming file to be stored into data base so havent done
		// write operation of actual multipart file
		if (fileName == null || fileType == null)
			throw new FileServiceBuisnessException(ExceptionConstants.MANDATORY_FILEDS);

		return mapToBean(createFile(fileName, fileType));
	}

	/**
	 * used to save file
	 * 
	 * @param fileName
	 * @param fileType
	 * @return
	 */
	private FileDetails createFile(String fileName, String fileType) {
		FileDetails fileDetails = new FileDetails();
		fileDetails.setFileName(fileName);
		fileDetails.setFileType(fileType);
		// TODO:since we are not using any object storage for saving file for usage
		// hardcode value
		fileDetails.setUrl(
				"https://www.google.com/search?q=java+code+images&tbm=isch&source=iu&ictx=1&fir=eqTbsNTKKqJefM%253A%252C_mzKrkjZsSzMNM%252C_&vet=1&usg=AI4_-kToAwOljHfJiY6BNxMgtIXJYF_nmw&sa=X&ved=2ahUKEwjgtYn3y_rhAhULiHAKHeMpALMQ9QEwAnoECAcQCA#imgrc=eqTbsNTKKqJefM:");
		return iFileRepository.save(fileDetails);
	}

	/**
	 * 
	 * used to map entity to bean
	 * 
	 * @param file
	 * @return
	 */
	private FileResponse mapToBean(FileDetails file) {
		FileResponse fileResponse = new FileResponse();
		fileResponse.setFileId(file.getFileId());
		fileResponse.setUrl(file.getUrl());
		fileResponse.setFileName(file.getFileName());
		fileResponse.setFileType(file.getFileType());
		return fileResponse;
	}

	/**
	 * 
	 * used to update file details
	 * 
	 * @param fileRequest
	 * @param fileDetails
	 * @param fileResponse
	 * @return
	 */
	private FileResponse updateFile(FileRequest fileRequest, FileDetails fileDetails, FileResponse fileResponse) {
		if (fileRequest.getFileName() != null) {
			fileDetails.setFileName(fileRequest.getFileName());
		}
		if (fileRequest.getType() != null) {
			fileDetails.setFileType(fileRequest.getType());
		}
		if (fileRequest.getUrl() != null) {
			fileDetails.setUrl(fileRequest.getUrl());
		}
		constructFileResponse(fileResponse, fileDetails);

		iFileRepository.save(fileDetails);

		return fileResponse;

	}

	/**
	 * 
	 * used to set the updated response back to client
	 * 
	 * @param fileResponse
	 * @param fileDetails
	 * @return
	 */
	private FileResponse constructFileResponse(FileResponse fileResponse, FileDetails fileDetails) {
		fileResponse.setFileName(fileDetails.getFileName());
		fileResponse.setUrl(fileDetails.getUrl());

		fileResponse.setFileType(fileDetails.getFileType());

		return fileResponse;

	}

	/**
	 * used to delete file entity
	 * 
	 * @param fileDetails
	 */
	private void deleteEachFile(FileDetails fileDetails) {
		iFileRepository.delete(fileDetails);
	}

}
