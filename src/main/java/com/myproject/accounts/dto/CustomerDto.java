package com.myproject.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(name = "Customer", description = "Schema to hold Customer and Account information")
public class CustomerDto {

	@Schema(description = "Name of the Customer", example = "WarnerBros")
	@NotEmpty(message = "Name cannot be null or empty")
	@Size(min = 5, max = 30, message = "The length of the customer name should be 50 and 30")
	private String name;

	@Schema(description = "Email of the Customer", example = "WarnerBros@gmail.com")

	@NotEmpty(message = "Email cannot be null or empty")
	@Email(message = "Email should be valid ")
	private String email;
	@Schema(description = "Mobile Number of the Customer", example = "8900504015")

	@NotEmpty(message = "Mobile number cannot be empty")
	@Pattern(regexp = "(^|[0-9]{10})", message = "Mobile number must be 10 digits")
	private String mobileNumber;
	@Schema(description = "Accounts Details of the Customer")
	private AccountsDto accountsDto;

}
