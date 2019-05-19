package com.fileservice.exception;

/**
 * @author vivek
 *FileServiceBuisnessException used for custome exception
 */
public class FileServiceBuisnessException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

		// For Business
		public FileServiceBuisnessException(String messageCode) {
			super(messageCode);
		}

		// For Checked and Unchecked
		public FileServiceBuisnessException(String messageCode, Throwable cause) {
			super(messageCode, cause);
		}

		// For Checked and Unchecked
		public FileServiceBuisnessException(Throwable cause) {
			super(cause);
		}

	

}
