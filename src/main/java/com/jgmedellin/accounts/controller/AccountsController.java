package com.jgmedellin.accounts.controller;

import com.jgmedellin.accounts.constants.AccountsConstants;
import com.jgmedellin.accounts.dto.CustomerDto;
import com.jgmedellin.accounts.dto.ErrorResponseDto;
import com.jgmedellin.accounts.dto.ResponseDto;
import com.jgmedellin.accounts.service.IAccountsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "Accounts API",
        description = "This API allows to Create, Read, Update and Delete accounts and related customers in EazyBank"
)
@RestController
@RequestMapping(path="/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Validated // Perform validation on the request body
public class AccountsController {

  // No need to autowire here due to Lombok: @AllArgsConstructor annotation (constructor injection)
  private IAccountsService iAccountsService;

  @Operation(
          summary = "Create a new account",
          description = "This endpoint allows you to create a new account and customer in EazyBank"
  )
  @ApiResponses({
          @ApiResponse(responseCode = "201", description = "Account created successfully"),
          @ApiResponse(
                  responseCode = "500",
                  description = AccountsConstants.MESSAGE_500,
                  content = @Content(
                          schema = @Schema(implementation = ErrorResponseDto.class)
                  )
          )
  })
  @PostMapping("/create")
  public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto) {
    iAccountsService.createAccount(customerDto);
    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(new ResponseDto(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));
  }

  @Operation(summary = "Fetch account", description = "Endpoint to fetch account and customer details by mobile number")
  @ApiResponses({
          @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
          @ApiResponse(
                  responseCode = "500",
                  description = AccountsConstants.MESSAGE_500,
                  content = @Content(
                          schema = @Schema(implementation = ErrorResponseDto.class)
                  )
          )
  })
  @GetMapping("/fetch")
  public ResponseEntity<CustomerDto> fetchAccountDetails(@RequestParam @Pattern(
          regexp = "^[0-9]{10}$", message = "Mobile Number should be 10 digits") String mobileNumber) {
    CustomerDto customerDto = iAccountsService.fetchAccount(mobileNumber);
    return ResponseEntity.status(HttpStatus.OK).body(customerDto);
  }

  @Operation(
          summary = "Update account details",
          description = "This endpoint allows you to update account and customer details"
  )
  @ApiResponses({
          @ApiResponse(responseCode = "200", description = AccountsConstants.MESSAGE_200),
          @ApiResponse(responseCode = "417", description = AccountsConstants.MESSAGE_417_UPDATE),
          @ApiResponse(
                  responseCode = "500",
                  description = AccountsConstants.MESSAGE_500,
                  content = @Content(
                          schema = @Schema(implementation = ErrorResponseDto.class)
                  )
          )
  })
  @PutMapping("/update")
  public ResponseEntity<ResponseDto> updateAccountDetails(@Valid @RequestBody CustomerDto customerDto) {
    boolean isUpdated = iAccountsService.updateAccount(customerDto);
    if (isUpdated) {
      return ResponseEntity
              .status(HttpStatus.OK)
              .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
    } else {
      return ResponseEntity
              .status(HttpStatus.EXPECTATION_FAILED)
              .body(new ResponseDto(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_UPDATE));
    }
  }

  @Operation(
          summary = "Delete an account",
          description = "This endpoint allows you to delete an account and customer by mobile number"
  )
  @ApiResponses({
          @ApiResponse(responseCode = "200", description = AccountsConstants.MESSAGE_200),
          @ApiResponse(responseCode = "417", description = AccountsConstants.MESSAGE_417_DELETE),
          @ApiResponse(
                  responseCode = "500",
                  description = AccountsConstants.MESSAGE_500,
                  content = @Content(
                          schema = @Schema(implementation = ErrorResponseDto.class)
                  )
          )
  })
  @DeleteMapping("/delete")
  public ResponseEntity<ResponseDto> deleteAccount(@RequestParam @Pattern(
          regexp = "^[0-9]{10}$", message = "Mobile Number should be 10 digits") String mobileNumber) {
    boolean isDeleted = iAccountsService.deleteAccount(mobileNumber);
    if (isDeleted) {
      return ResponseEntity
              .status(HttpStatus.OK)
              .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
    } else {
      return ResponseEntity
              .status(HttpStatus.EXPECTATION_FAILED)
              .body(new ResponseDto(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_DELETE));
    }
  }

}