package com.fileservice.aspect;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.fileservice.beans.ResponseResource;
import com.fileservice.beans.Status;
import com.fileservice.exception.FileServiceBuisnessException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


/**
 *@author vivek
* Controller Aspect to perform logging and exception handling
 * @link http://www.tatacommunications.com/
 * @copyright 2018 Tata Communications Limited
 * 
 */
@Aspect
@Component
@Order(0)
public class CommonControllerAspect {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CommonControllerAspect.class);

	@Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
	public void controller() {
		// Point cut definition
	}

	@Pointcut("execution(@org.springframework.amqp.rabbit.annotation.RabbitListener * *.*(..))")
	void annotatedMethod() {
		// Point cut definition
	}

	@Pointcut("execution(* (@org.springframework.amqp.rabbit.annotation.RabbitListener *).*(..))")
	void methodOfAnnotatedClass() {
		// Point cut definition
	}

	@Pointcut("execution(* *.*(..))")
	protected void allMethod() {
		// Point cut definition
	}

	@Pointcut("execution(public * *(..))")
	protected void loggingPublicOperation() {
		// Point cut definition
	}

	@Pointcut("execution(* *.*(..))")
	protected void loggingAllOperation() {
		// Point cut definition
	}

	@Pointcut("within(com.tcl.dias..*)")
	private void logAnyFunctionWithinResource() {
		// Point cut definition
	}
	
	/**
	 * Around -> Any method within resource annotated with @Controller annotation
	 * 
	 * @param joinPoint
	 * @return
	 * @throws Throwable
	 */
	@Around("controller() && allMethod()")
	public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {

		long start = System.currentTimeMillis();
		HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getResponse();
		try {
			LOGGER.info("Entering in Method : {} ", joinPoint.getSignature().getName());
			LOGGER.info("Class Name :  {}", joinPoint.getSignature().getDeclaringTypeName());
			String args = Arrays.toString(joinPoint.getArgs());
			LOGGER.info("Arguments :  {}", args);
			LOGGER.info("Target class : {}", joinPoint.getTarget().getClass().getName());
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
					.getRequest();
			if (null != request) {
				if ((request.getUserPrincipal()) != null) {
					LOGGER.info("User principal {} ", request.getUserPrincipal());
				}

				LOGGER.info("Http Method : {}", request.getMethod());
				LOGGER.info("Remote Address {} :: Remote host {}", request.getRemoteAddr(), request.getRemoteHost());

				Enumeration<?> headerNames = request.getHeaderNames();
				while (headerNames.hasMoreElements()) {
					String headerName = (String) headerNames.nextElement();
					String headerValue = request.getHeader(headerName);
				
					LOGGER.info("Header Name: {} => Header Value : {}", headerName, headerValue);
				}
				LOGGER.info("Request Path info : {}", request.getServletPath());
			}
	
			String className = joinPoint.getSignature().getDeclaringTypeName();
			String methodName = joinPoint.getSignature().getName();
			Object result = joinPoint.proceed();
			String returnValue = this.getValue(result);
			LOGGER.info("Method Return value : {}", returnValue);
			long elapsedTime = System.currentTimeMillis() - start;
			LOGGER.info("Exiting Method {}.{} () with an execution time : {} ms", className, methodName, elapsedTime);
			return result;
		} catch (FileServiceBuisnessException e) {
			LOGGER.error("Error occured " + e.getMessage() + e.getCause(), e);
			return new ResponseResource<>( ResponseResource.R_CODE_ERROR,
					e.getMessage(), Status.FAILURE);
		}  catch (Exception e) {
			LOGGER.error("Error occured " + e.getMessage()
					+ Optional.ofNullable(e.getCause()).orElse(e), e);
			response.setStatus(ResponseResource.R_CODE_ERROR);
			return new ResponseResource<>(ResponseResource.R_CODE_ERROR, e.getMessage(), Status.ERROR);
		}
	}
	
	private String getValue(Object result) {
		String returnValue = null;
		if (null != result) {
			if (result.toString().endsWith("@" + Integer.toHexString(result.hashCode()))) {
				returnValue = result.toString();
			}
		}
		return returnValue;
	}

}
