package com.jgmedellin.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(name = "Accounts", description = "Schema to hold Account information")
public class AccountsDto {

  @Schema(description = "Randomly generated account number", example = "1234567890")
  @NotEmpty(message = "Account number can not be null or empty")
  @Pattern(regexp = "^[0-9]{10}$", message = "Account Number must be 10 digits")
  private Long accountNumber;

  @Schema(description = "Account type", example = "Savings")
  @NotEmpty(message = "Account type can not be null or empty")
  private String accountType;

  @Schema(description = "Branch address", example = "123 Calle Viva, Mexico")
  @NotEmpty(message = "Branch address can not be null or empty")
  private String branchAddress;

}