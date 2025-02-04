package com.jgmedellin.accounts.service;

import com.jgmedellin.accounts.dto.CustomerDto;

public interface IAccountsService {

  /**
   * Creates a new account given the customer details.
   * @param customerDto Customer details (name, email, mobile number)
   */
  void createAccount(CustomerDto customerDto);

  /**
   * Fetches the account details given the mobile number.
   * @param mobileNumber Mobile number of the customer
   * @return CustomerDto object with the account details
   */
  CustomerDto fetchAccount(String mobileNumber);

  /**
   * Updates certain account details given the customer details.
   * @param customerDto Customer details (name, email, mobile number, account details)
   * @return boolean value indicating the success of the update operation
   */
  boolean updateAccount(CustomerDto customerDto);

  /**
   * Deletes the account given the mobile number.
   * @param mobileNumber Mobile number of the customer
   * @return boolean value indicating the success of the delete operation
   */
  boolean deleteAccount(String mobileNumber);

}