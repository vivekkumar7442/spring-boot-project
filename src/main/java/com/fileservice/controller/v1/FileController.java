package com.fileservice.controller.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fileservice.beans.ResponseResource;
import com.fileservice.beans.Status;
import com.fileservice.constants.Constants;
import com.fileservice.constants.SwaggerConstants;
import com.fileservice.exception.FileServiceBuisnessException;
import com.fileservice.request.FileRequest;
import com.fileservice.response.FileResponse;
import com.fileservice.service.v1.IFileService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * This class have all end Point related to file Operation
 * 
 * @author vivek
 */
@RestController
@RequestMapping("/v1/operation")
public class FileController extends BaseController {

	@Autowired
	IFileService ifileService;

	@ApiOperation(value = SwaggerConstants.ApiOperations.FileOperations.GET_ALL_FILE_DETAILS)
	@GetMapping(value="/files", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = Constants.SUCCESS, response = FileResponse.class),
			@ApiResponse(code = 403, message = Constants.FORBIDDEN),
			@ApiResponse(code = 422, message = Constants.NOT_FOUND),
			@ApiResponse(code = 417, message = Constants.EXCEPTION_FAILED) })
	public ResponseResource<List<FileResponse>> getAllFiles(@RequestParam(required=false,value="filetype") String fileType) throws FileServiceBuisnessException {
		List<FileResponse> response = ifileService.getAllFiles(fileType);
		return new ResponseResource<>(ResponseResource.R_CODE_OK, ResponseResource.RES_SUCCESS, response,
				Status.SUCCESS);

	}
	
	@ApiOperation(value = SwaggerConstants.ApiOperations.FileOperations.UPDATE_FILE_DETAILS)
	@PostMapping(value="/files/update", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = Constants.SUCCESS, response = FileResponse.class),
			@ApiResponse(code = 403, message = Constants.FORBIDDEN),
			@ApiResponse(code = 422, message = Constants.NOT_FOUND),
			@ApiResponse(code = 417, message = Constants.EXCEPTION_FAILED) })
	public ResponseResource<FileResponse> updateFileDetails(@RequestBody FileRequest fileRequest) throws FileServiceBuisnessException {
		FileResponse response = ifileService.updateFileDetails(fileRequest);
		return new ResponseResource<>(ResponseResource.R_CODE_OK, ResponseResource.RES_SUCCESS, response,
				Status.SUCCESS);

	}
	
	
	@ApiOperation(value = SwaggerConstants.ApiOperations.FileOperations.DELETE_FILE)
	@RequestMapping(value="/files/{fileId}",method=RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = Constants.SUCCESS, response = FileResponse.class),
			@ApiResponse(code = 403, message = Constants.FORBIDDEN),
			@ApiResponse(code = 422, message = Constants.NOT_FOUND),
			@ApiResponse(code = 417, message = Constants.EXCEPTION_FAILED) })
	public ResponseResource<FileResponse> deleteFile(@PathVariable Integer fileId) throws FileServiceBuisnessException {
		FileResponse response = ifileService.deleteFile(fileId);
		return new ResponseResource<>(ResponseResource.R_CODE_OK, ResponseResource.RES_SUCCESS, response,
				Status.SUCCESS);

	}
	
	@ApiOperation(value = SwaggerConstants.ApiOperations.FileOperations.UPLOAD_FILE)
	@RequestMapping(value="/files/upload",method=RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = Constants.SUCCESS, response = FileResponse.class),
			@ApiResponse(code = 403, message = Constants.FORBIDDEN),
			@ApiResponse(code = 422, message = Constants.NOT_FOUND),
			@ApiResponse(code = 417, message = Constants.EXCEPTION_FAILED) })
	public ResponseResource<FileResponse> uploadFile(@RequestParam("file") MultipartFile file,@RequestParam(required=true,value="filetype") String fileType,@RequestParam(required=true,value="filename") String fileName) throws FileServiceBuisnessException {
		FileResponse response = ifileService.uploadFile(file,fileName,fileType);
		return new ResponseResource<>(ResponseResource.R_CODE_OK, ResponseResource.RES_SUCCESS, response,
				Status.SUCCESS);

	}
	


}
