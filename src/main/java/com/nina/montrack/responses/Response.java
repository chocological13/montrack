package com.nina.montrack.responses;

import lombok.Data;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
@ToString
public class Response<T> {

  private int statusCode;
  private String statusMessage;
  boolean success = false;
  private T data;

  // constructor
  public Response(int statusCode, String statusMessage) {
    this.statusCode = statusCode;
    this.statusMessage = statusMessage;

    if (statusCode == HttpStatus.OK.value()) {
      success = true;
    }
  }

  // Region - Failed responses
    // base function, other overloaded methods will recursively call this
  public static <T> ResponseEntity<Response<T>> failedResponse(int statusCode, String statusMessage, T data) {
    Response<T> response = new Response<>(statusCode, statusMessage);
    response.setSuccess(false);
    response.setData(data);
    return ResponseEntity.status(statusCode).body(response);
  }

    // ?overloaded methods for other possible params
  public static ResponseEntity<Response<Object>> failedResponse(String statusMessage) {
    return failedResponse(HttpStatus.BAD_REQUEST.value(), statusMessage, null);
  }

      // this one uses T because it takes in "T data"
  public static <T> ResponseEntity<Response<T>> failedResponse(T data) {
    return failedResponse(HttpStatus.BAD_REQUEST.value(), "Process has failed to execute.", data);
  }

  public static <T> ResponseEntity<Response<T>> failedResponse(int statusCode, String statusMessage) {
    return failedResponse(statusCode, statusMessage, null);
  }

  public static <T> ResponseEntity<Response<T>> failedResponse(int statusCode, T data) {
    return failedResponse(statusCode, "Process has failed to execute", null);
  }

  // End

  // Region

  // !success responses
    //base method
  public static <T> ResponseEntity<Response<T>> successfulResponse(int statusCode, String statusMessage, T data) {
    Response<T> response = new Response<>(statusCode, statusMessage);
    response.setSuccess(true);
    response.setData(data);
    return ResponseEntity.status(statusCode).body(response);
  }

  public static ResponseEntity<Response<Object>> successfulResponse(String statusMessage) {
    return successfulResponse(HttpStatus.OK.value(), statusMessage, null);
  }

  public static <T> ResponseEntity<Response<T>> successfulResponse(T data) {
    return successfulResponse(HttpStatus.OK.value(), "Process has been executed successfully!", data);
  }

  public static <T> ResponseEntity<Response<T>> successfulResponse(int statusCode, String statusMessage) {
    return successfulResponse(statusCode, statusMessage, null);
  }

  public static <T> ResponseEntity<Response<T>> successfulResponse(int statusCode, T data) {
    return successfulResponse(statusCode, "Process has been executed successfully", null);
  }

  // End

}
