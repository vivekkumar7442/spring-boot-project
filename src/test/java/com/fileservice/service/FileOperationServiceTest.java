package com.fileservice.service;

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

import com.fileservice.entity.entities.FileDetails;
import com.fileservice.entity.repository.IFileRepository;
import com.fileservice.exception.FileServiceBuisnessException;
import com.fileservice.response.FileResponse;
import com.fileservice.service.v1.IFileService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FileOperationServiceTest {

	@Autowired
	IFileService iFileService;

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
		
	    Mockito.when(iFileRepository.findAll(Mockito.any(Specifications.class))).thenReturn(mockBeanCreator.getFilesList());


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
		FileResponse response = iFileService.uploadFile(file, "codenin", "pdf");
		assertTrue(response != null);
	}

	/**
	 * negative test case
	 *  for upload file service
	 * @throws FileServiceBuisnessException
	 */
	@Test(expected = FileServiceBuisnessException.class)
	public void uploadFileNegativeCase() throws FileServiceBuisnessException {
		MockMultipartFile file = new MockMultipartFile("data", "filename.txt", "text/plain", "some xml".getBytes());
		FileResponse response = iFileService.uploadFile(file, null, null);

		assertTrue(response == null);
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
		List<FileResponse> response = iFileService.getAllFiles(Mockito.anyString());

		assertTrue(response != null);
	}

	/**
	 * negative test case
	 * to get all the files
	 * 
	 * @throws FileServiceBuisnessException
	 */
	@Test(expected = FileServiceBuisnessException.class)
	public void getALLFileNegativeCase() throws FileServiceBuisnessException {
		List<FileResponse> response = iFileService.getAllFiles(null);

		assertTrue(response.isEmpty());
	}

	/**
	 * positive test case
	 * for deletion of file
	 * 
	 * @throws FileServiceBuisnessException
	 */
	@Test
	public void deleteFile() throws FileServiceBuisnessException {
		FileResponse response = iFileService.deleteFile(Mockito.anyInt());

		assertTrue(response != null);
	}

	/**
	 * negative test case
	 * for deletion of file
	 * 
	 * @throws FileServiceBuisnessException
	 */
	@Test(expected = FileServiceBuisnessException.class)
	public void deleteFileNegativecase() throws FileServiceBuisnessException {
		FileResponse response = iFileService.deleteFile(null);

		assertTrue(response == null);
	}

	/**
	 * positive test case
	 * for update file details
	 * 
	 * @throws FileServiceBuisnessException
	 */
	@Test
	public void updateFileDetails() throws FileServiceBuisnessException {
		FileResponse response = iFileService.updateFileDetails(mockBeanCreator.getFileRequest());

		assertTrue(response != null);
	}

	/**
	 * negative test case
	 * for updateFileNegativeCase
	 * 
	 * @throws FileServiceBuisnessException
	 */
	@Test(expected = FileServiceBuisnessException.class)
	public void updateFileNegativeCase() throws FileServiceBuisnessException {
		FileResponse response = iFileService.updateFileDetails(null);

		assertTrue(response == null);
	}

}
