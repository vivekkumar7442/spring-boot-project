package com.fileservice.constants;

/**
 * @author vivek SwaggerConstants used for documentation details associated
 *         with Rest end Point
 */
public class SwaggerConstants {

	private SwaggerConstants() {
	}

	public static class ApiOperations {

		private ApiOperations() {

		}

		public static class FileOperations {
			private FileOperations() {
				// DO NOTHING
			}

			public static final String CREATE_FILE = "used for creation of file";
			public static final String GET_ALL_FILE_DETAILS = "used to get all the file details";
			public static final String UPDATE_FILE_DETAILS = "used to update file details based on fileID";
			public static final String DELETE_FILE = "Used to delete the file";
			public static final String UPLOAD_FILE = "Used to upload files";


		}

	}

}
