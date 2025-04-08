package com.myproject.accounts.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.myproject.accounts.constants.AccountsConstant;
import com.myproject.accounts.dto.AccountsDto;
import com.myproject.accounts.dto.CustomerDto;
import com.myproject.accounts.entities.Accounts;
import com.myproject.accounts.entities.Customer;
import com.myproject.accounts.exception.CustomerAlreadyExistsException;
import com.myproject.accounts.exception.ResourceNotFoundException;
import com.myproject.accounts.mapper.AccountsMapper;
import com.myproject.accounts.mapper.CustomerMapper;
import com.myproject.accounts.repository.AccountsRepository;
import com.myproject.accounts.repository.CustomerRepository;
import com.myproject.accounts.service.IAccountsService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService {

	private AccountsRepository accountsRepository;
	private CustomerRepository customerRepository;

	@Override
	public void createAccount(CustomerDto customerDto) {

		Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
		Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customer.getMobileNumber());
		if (optionalCustomer.isPresent()) {
			throw new CustomerAlreadyExistsException(
					"Customer already registered with given mobile number" + customer.getMobileNumber());
		}
		customer.setCreatedAt(LocalDateTime.now());
		customer.setCreatedBy("Annonymous");
		Customer savedCustomer = customerRepository.save(customer);
		accountsRepository.save(createNewAccount(savedCustomer));
	}

	private Accounts createNewAccount(Customer customer) {
		Accounts newAccount = new Accounts();
		newAccount.setCustomerId(customer.getCustomerId());
		long randomAccNumber = 100000000L + new Random().nextInt(900000000);
		newAccount.setAccountNumber(randomAccNumber);
		newAccount.setAccountType(AccountsConstant.SAVINGS);
		newAccount.setBranchAddress(AccountsConstant.ADDRESS);
		newAccount.setCreatedAt(LocalDateTime.now());
		newAccount.setCreatedBy("Annonymous");
		return newAccount;
	}

	@Override
	public CustomerDto fetchAccount(String mobileNumber) {
		Customer customer = customerRepository.findByMobileNumber(mobileNumber)
				.orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));
		Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
				() -> new ResourceNotFoundException("Accounts", "customerId", customer.getCustomerId().toString()));
		CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
		customerDto.setAccountsDto(AccountsMapper.mapToAccountDto(accounts, new AccountsDto()));

		return customerDto;
	}

	@Override
	public boolean updateAccount(CustomerDto customerDto) {
		boolean isUpdated=false;
		AccountsDto accountsDto=customerDto.getAccountsDto();
		if(accountsDto !=null)
		{
			Accounts accounts=accountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow(() -> new ResourceNotFoundException("Account","AccountNumber", accountsDto.getAccountNumber().toString()));
			AccountsMapper.mapToAccounts(accountsDto, accounts);
			Long customerId=accounts.getCustomerId();
			Customer customer=customerRepository.findById(customerId).orElseThrow(() -> new ResourceNotFoundException("Customer","Customer ID", customerId.toString()));
			CustomerMapper.mapToCustomer(customerDto, customer);
			customerRepository.save(customer);
			isUpdated=true;
		}
		

		return isUpdated;
	}

	  @Override
	    public boolean deleteAccount(String mobileNumber) {
	        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
	                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
	        );
	        accountsRepository.deleteByCustomerId(customer.getCustomerId());
	        customerRepository.deleteById(customer.getCustomerId());
	        return true;
	    }

}
