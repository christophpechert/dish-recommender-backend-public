package com.project.dishrecommender.exception;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import java.time.LocalDateTime;

@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorDetails> handleAllException(Exception ex, WebRequest request) throws Exception {
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({
            UserNotFoundException.class,
            UserGroupNotFoundException.class,
            MenuNotFoundException.class,
            DishNotFoundException.class,
            KeywordNotFoundException.class,
            RecipeImageDataNotFoundException.class,
            RecommendationNotFoundException.class})
    public final ResponseEntity<ErrorDetails> handleNotFoundException(Exception ex, WebRequest request) throws Exception {
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({
            UserAlreadyExistException.class,
            MenuAlreadyExistException.class,
            KeywordAlreadyExistException.class,
            InviteAlreadyExistException.class,
            InviteWrongStatusException.class,
            UserAlreadyAssignToMenuException.class,
            UserAlreadyAssignToUserGroupException.class,
            DishAlreadyAssignToMenuException.class,
            UserRoleNotPossibleException.class,
            UserNoAuthoritiesException.class,
            NoDishesForRecommendationException.class,
            DishNoMemberOfUserGroupException.class,
            EmailAlreadyExistException.class,
            DisabledException.class,
            BadCredentialsException.class,
            NotAUserRoleException.class})
    public final ResponseEntity<ErrorDetails> handleAlreadyExistException(Exception ex, WebRequest request) throws Exception {
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({
            UserNoMemberException.class,
            AccessDeniedException.class,
            UserNotActivatedException.class,
            RecommendationNoMemberException.class})
    public final ResponseEntity<ErrorDetails> handleForbiddenException(Exception ex, WebRequest request) throws Exception {
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        StringBuilder errorMessage = new StringBuilder();

        for (int i = 0; i < ex.getErrorCount(); i++) {
            errorMessage.append("Error ");
            errorMessage.append(i);
            errorMessage.append(": ");
            errorMessage.append(ex.getFieldErrors().get(i).getDefaultMessage());
            errorMessage.append(".");
            if (i < ex.getErrorCount() - 1) {
                errorMessage.append(" ");
            }
        }

        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), errorMessage.toString(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
}
