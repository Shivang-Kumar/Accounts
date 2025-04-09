package com.myproject.accounts.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.myproject.accounts.constants.AccountsConstant;
import com.myproject.accounts.dto.CustomerDto;
import com.myproject.accounts.dto.ResponseDto;
import com.myproject.accounts.service.IAccountsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;

@Tag(name = "CRUD REST api for Account microservice", description = "CRUD REST api for account microservice to provide functionality of creating , updating , deleting and fetching account details ")
@RestController
@RequestMapping(path = "/api", produces = { MediaType.APPLICATION_JSON_VALUE })
@AllArgsConstructor
@Validated
public class AccountsController {

	private IAccountsService iAccountService;

	@Operation(summary = "Create Account REST API", description = "Rest API to create new Customer & Account in my microservice")
	@ApiResponse(responseCode = "201", description = "Http Status CREATED")
	@PostMapping("/create")
	public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto) {
		iAccountService.createAccount(customerDto);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponseDto(AccountsConstant.STATUS_201, AccountsConstant.MESSAGE_201));
	}

	@Operation(summary = "Fetch Account REST API", description = "Rest API to fetch new Customer & Account in my microservice based on mobile number")
	@ApiResponse(responseCode = "200", description = "Http Status OK")
	@GetMapping("/fetch")
	public ResponseEntity<CustomerDto> fetchAccountsDetails(
			@RequestParam @Pattern(regexp = "(^|[0-9]{10})", message = "Mobile number must be 10 digits") String mobileNumber) {
		CustomerDto customerDto = iAccountService.fetchAccount(mobileNumber);
		return ResponseEntity.status(HttpStatus.OK).body(customerDto);

	}
	@Operation(summary = "Update Account REST API", description = "Rest API to Update new Customer & Account in my microservice based on account number")
	@ApiResponses({@ApiResponse(responseCode = "200", description = "Http Status OK"),@ApiResponse(responseCode = "500", description = "Http Status Internal Server Error")})
	@PutMapping("/update")
	public ResponseEntity<ResponseDto> updateAccountDetails(@Valid @RequestBody CustomerDto customerDto) {
		boolean isUpdated = iAccountService.updateAccount(customerDto);
		if (isUpdated) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseDto(AccountsConstant.STATUS_200, AccountsConstant.MESSAGE_200));
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseDto(AccountsConstant.STATUS_500, AccountsConstant.MESSAGE_500));
		}
	}
	@Operation(summary = "Delete Account REST API", description = "Rest API to Delete new Customer & Account in my microservice based on mobile number")
	@ApiResponses({@ApiResponse(responseCode = "200", description = "Http Status OK"),@ApiResponse(responseCode = "500", description = "Http Status Internal Server Error")})
	@DeleteMapping("/delete")
	public ResponseEntity<ResponseDto> deleteAccountDetails(
			@RequestParam @Pattern(regexp = "(^|[0-9]{10})", message = "Mobile number must be 10 digits") String mobileNumber) {
		boolean isDeleted = iAccountService.deleteAccount(mobileNumber);
		if (isDeleted) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseDto(AccountsConstant.STATUS_200, AccountsConstant.MESSAGE_200));
		} else {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.body(new ResponseDto(AccountsConstant.STATUS_500, AccountsConstant.MESSAGE_500));
		}
	}

}
