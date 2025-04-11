package com.myproject.accounts.service;

import com.myproject.accounts.dto.CustomerDto;

public interface IAccountsService {

	/**
	 * 
	 * @param customerDto  - CustomerDto Object
	 */
	void createAccount(CustomerDto customerDto);
	
	
	/**
	 * 
	 * @param mobileNumber --Input mobile number
	 * @return  -- Account details based on mobile number
	 */
	CustomerDto fetchAccount(String mobileNumber);
	
	/**
	 * 
	 * @param customerDto --CustomerDto object
	 * @return --Boolean indicating whether update was successful or not
	 */
	boolean updateAccount(CustomerDto customerDto);
	
	/**
	 * 
	 * @param mobileNumber -Input Mobile Number
	 * @return boolean indicating if deleted account details is successful or not
	 */
	boolean deleteAccount(String mobileNumber);

}
