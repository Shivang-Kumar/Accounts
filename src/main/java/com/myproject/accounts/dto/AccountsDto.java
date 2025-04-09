package com.myproject.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema( name="Accounts",description = "Schema to hole Account information")
public class AccountsDto {
	
	@Schema(description = "Account Number of the Customer")
	@NotEmpty(message="Account number cannot be empty")
	@Pattern(regexp="(^|[0-9]{10})", message="Account number must be 10 digits")	
	private Long accountNumber;
	
	
	@NotEmpty(message="Account Type cannot be empty")
	@Schema(description = "Account Type of customer", example="savings")
	private String accountType;
	
	
	@NotEmpty(message="Branch Address cannot be empty")
	@Schema(description = "Branch Address of Account number")
	private String branchAddress;
	
}
