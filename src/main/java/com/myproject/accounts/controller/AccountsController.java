package com.myproject.accounts.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path="/api", produces= {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
public class AccountsController{
	
	private IAccountsService iAccountService;
	
	@PostMapping("/create")
	public ResponseEntity<ResponseDto> createAccount(@RequestBody CustomerDto customerDto){
		iAccountService.createAccount(customerDto);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(new ResponseDto(AccountsConstant.STATUS_201,AccountsConstant.MESSAGE_201));
	}
	@GetMapping("/fetch")
	public ResponseEntity<CustomerDto> fetchAccountsDetails(@RequestParam String mobileNumber)
	{
		CustomerDto customerDto=iAccountService.fetchAccount(mobileNumber);
		return ResponseEntity.status(HttpStatus.OK).body(customerDto);
		
	}
	
	@PutMapping("/update")
	public ResponseEntity<ResponseDto> updateAccountDetails(@RequestBody CustomerDto customerDto){
		boolean isUpdated=iAccountService.updateAccount(customerDto);
		if(isUpdated) {
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(AccountsConstant.STATUS_200,AccountsConstant.MESSAGE_200));
		}else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto(AccountsConstant.STATUS_500,AccountsConstant.MESSAGE_500));
		}
	}
	
	  @DeleteMapping("/delete")
	    public ResponseEntity<ResponseDto> deleteAccountDetails(@RequestParam String mobileNumber) {
	        boolean isDeleted = iAccountService.deleteAccount(mobileNumber);
	        if(isDeleted) {
	            return ResponseEntity
	                    .status(HttpStatus.OK)
	                    .body(new ResponseDto(AccountsConstant.STATUS_200, AccountsConstant.MESSAGE_200));
	        }else{
	            return ResponseEntity
	                    .status(HttpStatus.EXPECTATION_FAILED)
	                    .body(new ResponseDto(AccountsConstant.STATUS_500, AccountsConstant.MESSAGE_500));
	        }
	    }


	
}
