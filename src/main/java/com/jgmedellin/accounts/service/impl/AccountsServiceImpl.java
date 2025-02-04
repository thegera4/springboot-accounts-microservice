package com.jgmedellin.accounts.service.impl;

import com.jgmedellin.accounts.constants.AccountsConstants;
import com.jgmedellin.accounts.dto.AccountsDto;
import com.jgmedellin.accounts.dto.CustomerDto;
import com.jgmedellin.accounts.entity.Accounts;
import com.jgmedellin.accounts.entity.Customer;
import com.jgmedellin.accounts.exception.CustomerAlreadyExistsException;
import com.jgmedellin.accounts.exception.ResourceNotFoundException;
import com.jgmedellin.accounts.mapper.AccountsMapper;
import com.jgmedellin.accounts.mapper.CustomerMapper;
import com.jgmedellin.accounts.repository.AccountsRepository;
import com.jgmedellin.accounts.repository.CustomerRepository;
import com.jgmedellin.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService {

  private AccountsRepository accountsRepository;

  private CustomerRepository customerRepository;

  @Override
  public void createAccount(CustomerDto customerDto) {
    // Map the customer details to the new Customer object
    Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());

    // Validate if the customer already exists
    Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
    if (optionalCustomer.isPresent()) {
      throw new CustomerAlreadyExistsException("Customer already registered with the mobile number: " + customerDto.getMobileNumber());
    }

    // Save the customer details
    Customer savedCustomer = customerRepository.save(customer);

    // Create a new account for the customer
    accountsRepository.save(createNewAccount(savedCustomer));
  }

  /**
   * Utility method to create a new account for the customer
   * @param customer Customer Object (information of the customer)
   * @return the new account details
   */
  private Accounts createNewAccount(Customer customer) {
    // Create a new account instance
    Accounts newAccount = new Accounts();

    // Set the customer id in the account by getting the customer id from the saved customer object
    newAccount.setCustomerId(customer.getCustomerId());

    // Generate a random account number
    long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

    // Set the account details
    newAccount.setAccountNumber(randomAccNumber);
    newAccount.setAccountType(AccountsConstants.SAVINGS);
    newAccount.setBranchAddress(AccountsConstants.ADDRESS);
    return newAccount;
  }

  @Override
  public CustomerDto fetchAccount(String mobileNumber) {
    // Fetch the customer details by mobile number or throw an exception if not found
    Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
            () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
    );

    // Fetch the account details by customer id or throw an exception if not found
    Accounts account = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
            () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
    );

    // Map the customer and account details to the CustomerDto object and return
    CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
    customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(account, new AccountsDto()));
    return customerDto;
  }

  @Override
  public boolean updateAccount(CustomerDto customerDto) {
    // Flag to indicate if the account details were updated
    boolean isUpdated = false;

    // Fetch the account details
    AccountsDto accountsDto = customerDto.getAccountsDto();

    // Validate if the account details exist
    if(accountsDto != null) {
      // Fetch the account details from the DB by account number or throw an exception if not found
      Accounts account = accountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
              () -> new ResourceNotFoundException("Account", "accountNumber", accountsDto.getAccountNumber().toString())
      );
      // Map the account details to the Account object
      AccountsMapper.mapToAccounts(accountsDto, account);
      // Update the account details
      account = accountsRepository.save(account);

      // Fetch the customer details by customer id or throw an exception if not found
      Long customerId = account.getCustomerId();
      Customer customer = customerRepository.findById(customerId).orElseThrow(
              () -> new ResourceNotFoundException("Customer", "customerId", customerId.toString())
      );

      // Map the customer details to the Customer object
      CustomerMapper.mapToCustomer(customerDto, customer);
      // Update the customer details
      customerRepository.save(customer);
      // Set the flag to true
      isUpdated = true;
    }

    return isUpdated;
  }

  @Override
  public boolean deleteAccount(String mobileNumber) {
    // Fetch the customer details by mobile number or throw an exception if not found
    Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
            () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
    );

    // Delete the customer and account in the DB by customer id and return true
    accountsRepository.deleteByCustomerId(customer.getCustomerId());
    customerRepository.deleteById(customer.getCustomerId());
    return true;
  }

}