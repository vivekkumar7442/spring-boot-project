package com.fileservice.integration;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

import com.fileservice.beans.ResponseResource;
import com.fileservice.beans.Status;
import com.fileservice.controller.v1.FileController;
import com.fileservice.entity.entities.FileDetails;
import com.fileservice.entity.repository.IFileRepository;
import com.fileservice.exception.FileServiceBuisnessException;
import com.fileservice.response.FileResponse;
import com.fileservice.service.MockBeanCreator;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FileServiceIntegrationTest {

	@Autowired
	FileController fileController;

	@MockBean
	IFileRepository iFileRepository;

	@Autowired
	MockBeanCreator mockBeanCreator;

	@SuppressWarnings("unchecked")
	@Before
	public void init() throws FileServiceBuisnessException {
		Mockito.when(iFileRepository.save(Mockito.any())).thenReturn(mockBeanCreator.createFileObject());
		Mockito.doThrow(new RuntimeException()).when(iFileRepository).delete(new FileDetails());
		Mockito.when(iFileRepository.findById(Mockito.anyInt()))
				.thenReturn(Optional.ofNullable(mockBeanCreator.createFileObject()));

		Mockito.when(iFileRepository.findAll(Mockito.any(Specifications.class)))
				.thenReturn(mockBeanCreator.getFilesList());

	}

	/**
	 * positive test case
	 * 
	 * for upload file service
	 * 
	 * @throws FileServiceBuisnessException
	 */
	@Test
	public void uploadFile() throws FileServiceBuisnessException {
		MockMultipartFile file = new MockMultipartFile("data", "filename.txt", "text/plain", "some xml".getBytes());
		ResponseResource<FileResponse> response = fileController.uploadFile(file, "codenin", "pdf");
		assertTrue(response != null && response.getStatus() == Status.SUCCESS);
	}

	/**
	 * negative test case for upload file service
	 * 
	 * @throws FileServiceBuisnessException
	 */
	@Test
	public void uploadFileNegativeCase() throws FileServiceBuisnessException {
		MockMultipartFile file = new MockMultipartFile("data", "filename.txt", "text/plain", "some xml".getBytes());
		ResponseResource<FileResponse> response = fileController.uploadFile(file, null, null);

		assertTrue(response.getStatus() == Status.FAILURE);
	}

	/**
	 * positive test case
	 * 
	 * to get all the files
	 * 
	 * @throws FileServiceBuisnessException
	 */
	@Test
	public void getALLFile() throws FileServiceBuisnessException {
		ResponseResource<List<FileResponse>> response = fileController.getAllFiles(Mockito.anyString());

		assertTrue(response != null && response.getStatus() == Status.SUCCESS);

	}

	/**
	 * negative test case to get all the files
	 * 
	 * @throws FileServiceBuisnessException
	 */
	@Test
	public void getALLFileNegativeCase() throws FileServiceBuisnessException {
		ResponseResource<List<FileResponse>> response = fileController.getAllFiles(null);

		assertTrue(response.getStatus() == Status.FAILURE);
	}

	/**
	 * positive test case for deletion of file
	 * 
	 * @throws FileServiceBuisnessException
	 */
	@Test
	public void deleteFile() throws FileServiceBuisnessException {
		ResponseResource<FileResponse> response = fileController.deleteFile(Mockito.anyInt());

		assertTrue(response != null && response.getStatus() == Status.SUCCESS);
	}

	/**
	 * negative test case for deletion of file
	 * 
	 * @throws FileServiceBuisnessException
	 */
	@Test
	public void deleteFileNegativecase() throws FileServiceBuisnessException {
		ResponseResource<FileResponse> response = fileController.deleteFile(null);
		assertTrue(response.getStatus() == Status.FAILURE);
	}

	/**
	 * positive test case for update file details
	 * 
	 * @throws FileServiceBuisnessException
	 */
	@Test
	public void updateFileDetails() throws FileServiceBuisnessException {
		ResponseResource<FileResponse> response = fileController.updateFileDetails(mockBeanCreator.getFileRequest());

		assertTrue(response != null && response.getStatus() == Status.SUCCESS);

	}

	/**
	 * negative test case for updateFileNegativeCase
	 * 
	 * @throws FileServiceBuisnessException
	 */
	@Test
	public void updateFileNegativeCase() throws FileServiceBuisnessException {
		ResponseResource<FileResponse> response = fileController.updateFileDetails(null);
		assertTrue(response.getStatus() == Status.FAILURE);
	}

}
