package com.isfood.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.exc.IgnoredPropertyException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.isfood.core.validation.ValidationException;
import com.isfood.domain.exception.ControllerException;
import com.isfood.domain.exception.EntityInUseException;
import com.isfood.domain.exception.EntityNotFoundException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler{
	
	private static final String MSG_GENERIC_FINAL_USER = "An unexpected internal system error has occurred. "
	        + "Try again and if the problem persists, contact your system administrator.";
	
	@Autowired
	private MessageSource messages_pt_BR;

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
	    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;		
	    ProblemType problemType = ProblemType.ERRO_OF_SYSTEM;
	    String detail = MSG_GENERIC_FINAL_USER;

	    // Importante colocar o printStackTrace (pelo menos por enquanto, que não estamos
	    // fazendo logging) para mostrar a stacktrace no console
	    // Se não fizer isso, você não vai ver a stacktrace de exceptions que seriam importantes
	    // para você durante, especialmente na fase de desenvolvimento
	    ex.printStackTrace();
	    
	    Problem problem = createProblemBuilder(status, problemType, detail, MSG_GENERIC_FINAL_USER)
	    		.build();

	    return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}

	@Override
	protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		return handleValidationInternal(ex, ex.getBindingResult(), new HttpHeaders(),
				HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler( EntityNotFoundException.class )
	public ResponseEntity<?> handleEntityNotFoundException( EntityNotFoundException ex, WebRequest request ) {

		
		HttpStatus status = HttpStatus.NOT_FOUND;
		ProblemType problemType = ProblemType.RECURSE_NOT_FOUND;
		String detail = ex.getMessage();

		Problem problem = createProblemBuilder(status, problemType, detail, detail).build();
//		Problem problem = createProblemBuilder(status, problemType, detail, MSG_GENERIC_FINAL_USER).build();

		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	@Override
    public ResponseEntity<Object> handleNoHandlerFoundException(
			NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request){
		
    	String recurse = ex.getRequestURL();
        ProblemType problemType = ProblemType.RECURSE_NOT_FOUND;
        String detail = String.format("The recurse '%s', which you tried to access, is non-existent.", recurse);

        Problem problem = createProblemBuilder(status, problemType, detail, MSG_GENERIC_FINAL_USER).build();

        return handleExceptionInternal(ex, problem, headers,
                status, request);
    }	

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
                                                          WebRequest request){
    	String required = ex.getRequiredType().getName();
    	String value = ex.getValue().toString();
    	String parameterName = ex.getParameter().getParameterName();
    	
    	Map <String, List<String>> newMap = new HashMap<>();
    	newMap.put(parameterName, Arrays.asList(required, value ));
    	
    	newMap.forEach((k, v) -> System.out.println("key: " + k + ", value: " + v.toString()));    	    
    	
    	final String[] msg = {""};    
    	
    	newMap.entrySet().stream()
    			.forEach(e -> {
    				msg[0] += String.format("The parameter of URL = '%s', received the value '%s', witch is a type invalid, "
    						+ "please, correct and put a new value compatible with type '%s'.", e.getKey(), e.getValue().get(1), e.getValue().get(0)); 
    			});
    	
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ProblemType problemType = ProblemType.ARGUMENT_TYPE_MISMATCH;
        String detail = msg[0];

        Problem problem = createProblemBuilder(status, problemType, detail, MSG_GENERIC_FINAL_USER).build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(),
                status, request);
    }

	@ExceptionHandler( { EntityInUseException.class } )
	public ResponseEntity<?> handleEntityInUseException( EntityInUseException ex, WebRequest request ) {

		String detail = ex.getMessage();
		ProblemType problemType = ProblemType.ENTITY_IN_USE;
		HttpStatus status = HttpStatus.CONFLICT;

		Problem problem = createProblemBuilder(status, problemType, detail, detail).build();

		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}

	@ExceptionHandler( { ControllerException.class } )
	public ResponseEntity<?> handleControllerException( ControllerException ex, WebRequest request ) {

		String detail = ex.getMessage();
		ProblemType problemType = ProblemType.CONTROLLER_EXCEPTION;
		HttpStatus status = HttpStatus.BAD_REQUEST;

		Problem problem = createProblemBuilder(status, problemType, detail, detail).build();

		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler({ ValidationException.class })
	public ResponseEntity<Object> handleValidacaoException(ValidationException ex, WebRequest request) {
	    return handleValidationInternal(ex, ex.getBindingResult(), new HttpHeaders(), 
	            HttpStatus.BAD_REQUEST, request);
	}       
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid( MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request ) {
		return handleValidationInternal(ex, ex.getBindingResult(), new HttpHeaders(), 
	            HttpStatus.BAD_REQUEST, request);
	}
	
	
	protected ResponseEntity<Object> handleValidationInternal(Exception ex, BindingResult bindingResult,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		ProblemType problemType = ProblemType.ARGUMENT_TYPE_MISMATCH;
		String detail = "One or more fields are missing.";
		
		List<Problem.Object> proObjects = bindingResult.getAllErrors().stream()
				.map(objectError -> { 
					String message = messages_pt_BR.getMessage(objectError, LocaleContextHolder.getLocale());
				
					var name = objectError.getObjectName();
					
					if (objectError instanceof FieldError) {
						name = ((FieldError) objectError).getField();
					}
					
					return Problem.Object.builder()
						.name(name)
						.userMessage(message)
						.build();
				})
				.collect(Collectors.toList());
		
		
		Problem problem = createProblemBuilder(status, problemType, detail, MSG_GENERIC_FINAL_USER)
				.objects(proObjects)
				.build();
		
		
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable( HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request ) {

		Throwable rootCause = ExceptionUtils.getRootCause(ex);
		if ( rootCause instanceof InvalidFormatException ) {
			return handleInvalidFormatException((InvalidFormatException) rootCause, headers, status, request);
		} else if ( rootCause instanceof IgnoredPropertyException ) {
			return handleIgnoredPropertyException((IgnoredPropertyException) rootCause, headers, status, request);
		} else if ( rootCause instanceof UnrecognizedPropertyException ) {
			return handleUnrecognizedPropertyException((UnrecognizedPropertyException) rootCause, headers, status,
					request);
		}

		String detail = "The body of request is invalid. Please check syntax error";
		ProblemType problemType = ProblemType.MESSAGE_NOT_READABLE;

		Problem problem = createProblemBuilder(status, problemType, detail, MSG_GENERIC_FINAL_USER).build();

		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}

	private ResponseEntity<Object> handleUnrecognizedPropertyException( UnrecognizedPropertyException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request ) {
		String path = ex.getPath().stream().map(ref -> ref.getFieldName()).collect(Collectors.joining("."));

		ProblemType problemType = ProblemType.PROPERTY_NOT_EXSISTS;
		String detail = String.format("The property '%s' not exists, please, correct and " + "repeat the process.",
				path);

		Problem problem = createProblemBuilder(status, problemType, detail, MSG_GENERIC_FINAL_USER).build();

		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	private ResponseEntity<Object> handleInvalidFormatException( InvalidFormatException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request ) {

		String path = ex.getPath().stream().map(ref -> ref.getFieldName()).collect(Collectors.joining("."));

		ProblemType problemType = ProblemType.MESSAGE_NOT_READABLE;
		String detail = String.format(
				"The property '%s' received the value '%s', "
						+ "which is an invalid type, please, correct and enter a value compatible with the type '%s'",
				path, ex.getValue(), ex.getTargetType().getSimpleName());

		Problem problem = createProblemBuilder(status, problemType, detail, MSG_GENERIC_FINAL_USER).build();

		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	private ResponseEntity<Object> handleIgnoredPropertyException( IgnoredPropertyException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request ) {

		String path = ex.getPropertyName();

		ProblemType problemType = ProblemType.PROPERTY_NOT_ALLOWED;
		String detail = String.format(
				"The property '%s' is set to receive no values.  " + "Please remove this property from the body.",
				path);

		Problem problem = createProblemBuilder(status, problemType, detail, MSG_GENERIC_FINAL_USER).build();

		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	

	@Override
	protected ResponseEntity<Object> handleExceptionInternal( Exception ex, @Nullable Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request ) {

		if ( body == null ) {
			body = Problem.builder().status(status.value()).title(status.getReasonPhrase())
					.detail(MSG_GENERIC_FINAL_USER)
					.build();
		} else if ( body instanceof String ) {
			body = Problem.builder().status(status.value()).title((String) body).build();
		}
		return new ResponseEntity<Object>(body, headers, status);
	}


	private Problem.ProblemBuilder createProblemBuilder( HttpStatus status, ProblemType problemType, String detail, String userMessage) {
		return Problem.builder().status(status.value()).type(problemType.getUri()).title(problemType.getTitle())
				.detail(detail).userMessage(userMessage).timestamp(OffsetDateTime.now());
	}	
}
