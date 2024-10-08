package dev.sharanggupta.account.controller;

import dev.sharanggupta.account.dto.AccountContactInfoDto;
import dev.sharanggupta.account.dto.CustomerDto;
import dev.sharanggupta.account.dto.ErrorResponseDto;
import dev.sharanggupta.account.dto.ResponseDto;
import dev.sharanggupta.account.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(
    name = "CRUD REST APIs for Accounts in EazyBank",
    description = "CRUD REST APIs in EazyBank to CREATE, UPDATE, FETCH AND DELETE account details")
@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountController {

  private final AccountService accountService;
  private final AccountContactInfoDto accountContactInfoDto;

  @Operation(
      summary = "Create Account REST API",
      description = "REST API to create new Customer & Account inside EazyBank")
  @ApiResponses({
    @ApiResponse(responseCode = "201", description = "HTTP Status CREATED"),
    @ApiResponse(
        responseCode = "400",
        description = "HTTP Bad Request",
        content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
  })
  @PostMapping("/create")
  public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto) {
    accountService.createAccount(customerDto);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(
            new ResponseDto(
                String.valueOf(HttpStatus.CREATED.value()), HttpStatus.CREATED.getReasonPhrase()));
  }

  @Operation(
      summary = "Fetch Account Details REST API",
      description = "REST API to fetch Customer & Account details based on a mobile number")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
    @ApiResponse(
        responseCode = "404",
        description = "HTTP Status Not Found",
        content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
  })
  @GetMapping("/fetch")
  public ResponseEntity<CustomerDto> fetchAccount(
      @RequestParam @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
          String mobileNumber) {
    return ResponseEntity.ok().body(accountService.fetchAccount(mobileNumber));
  }

  @Operation(
      summary = "Update Account Details REST API",
      description = "REST API to update Customer &  Account details based on a account number")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
    @ApiResponse(responseCode = "400", description = "Bad Request")
  })
  @PutMapping("/update")
  public ResponseEntity<ResponseDto> updateAccountDetails(
      @Valid @RequestBody CustomerDto customerDto) {
    accountService.updateAccount(customerDto);
    return ResponseEntity.ok()
        .body(
            new ResponseDto(
                String.valueOf(HttpStatus.OK.value()), HttpStatus.OK.getReasonPhrase()));
  }

  @Operation(
      summary = "Delete Account & Customer Details REST API",
      description = "REST API to delete Customer &  Account details based on a mobile number")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
    @ApiResponse(
        responseCode = "404",
        description = "HTTP Status Not Found",
        content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
  })
  @DeleteMapping("/delete")
  public ResponseEntity<ResponseDto> deleteAccountDetails(
      @RequestParam @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
          String mobileNumber) {
    accountService.deleteAccount(mobileNumber);
    return ResponseEntity.ok()
        .body(
            new ResponseDto(
                String.valueOf(HttpStatus.OK.value()), HttpStatus.OK.getReasonPhrase()));
  }

  @Operation(
      summary = "Fetch Contact Information REST API",
      description = "REST API to fetch Contact Information")
  @ApiResponse(responseCode = "200", description = "HTTP Status OK")
  @GetMapping("/contact-info")
  public ResponseEntity<AccountContactInfoDto> fetchContactInfo() {
    return ResponseEntity.ok().body(accountContactInfoDto);
  }
}
