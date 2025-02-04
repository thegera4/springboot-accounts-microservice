package com.jgmedellin.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(name = "Customer", description = "Schema to hold Customer and Account information")
public class CustomerDto {

  @Schema(description = "Name of the customer", example = "John Doe")
  @NotEmpty(message = "Name can not be null or empty") // Validation annotation thanks to spring-boot-starter-validation
  @Size(min = 3, max = 30, message = "Name must be between 3 and 30 characters")
  private String name;

  @Schema(description = "Email of the customer", example = "jdoe@email.com")
  @NotEmpty(message = "Email can not be null or empty")
  @Email(message = "Email should be valid")
  private String email;

  @Schema(description = "Mobile number of the customer", example = "1234567899")
  @NotEmpty(message = "Mobile Number can not be null or empty")
  @Pattern(regexp = "^[0-9]{10}$", message = "Mobile Number should be 10 digits")
  private String mobileNumber;

  @Schema(description = "Account details of the customer")
  private AccountsDto accountsDto;
}