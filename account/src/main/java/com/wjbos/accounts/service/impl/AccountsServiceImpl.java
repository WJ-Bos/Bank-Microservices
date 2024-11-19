package com.wjbos.accounts.service.impl;

import com.wjbos.accounts.constants.AccountsConstants;
import com.wjbos.accounts.dto.AccountsDto;
import com.wjbos.accounts.dto.CustomerDto;
import com.wjbos.accounts.entity.Accounts;
import com.wjbos.accounts.entity.Customer;
import com.wjbos.accounts.exception.CustomerAlreadyExistsException;
import com.wjbos.accounts.exception.ResourceNotFoundException;
import com.wjbos.accounts.mapper.AccountsMapper;
import com.wjbos.accounts.mapper.CustomerMapper;
import com.wjbos.accounts.repos.AccountRepository;
import com.wjbos.accounts.repos.CustomerRepository;
import com.wjbos.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService {

    private AccountRepository accountRepository;
    private CustomerRepository customerRepository;

    /**
     * @param customerDto
     */
    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if(optionalCustomer.isPresent()) {
           throw new CustomerAlreadyExistsException("Customer with Mobile Number "+customerDto.getMobileNumber()+" already exists.");
        }
        Customer savedCustomer = customerRepository.save(customer);
        accountRepository.save(createNewAccount(savedCustomer));
    }

    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(() ->
                new ResourceNotFoundException("Customer", "mobile Number", mobileNumber));

        Accounts account = accountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "id", customer.getCustomerId().toString())
        );

        CustomerDto customerDto =CustomerMapper.mapToCustomerDto(new CustomerDto(),customer);
        customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(account,new AccountsDto()));
        return customerDto;
    }


    private Accounts createNewAccount(Customer customer){
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(90000000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
        return newAccount;
    }

    @Override
    public boolean updateAccount(CustomerDto customerDto) {
      boolean isUpdated = false;

      AccountsDto accountsDto = customerDto.getAccountsDto();
      if(accountsDto != null){
          Accounts account = accountRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
                  () -> new ResourceNotFoundException("Account","AccountNumber",accountsDto.getAccountNumber().toString())
          );
          AccountsMapper.mapToAccounts(accountsDto,account);
          accountRepository.save(account);

          Long customerId = account.getCustomerId();

          Customer customer = customerRepository.findById(customerId).orElseThrow(
                  () -> new ResourceNotFoundException("Customer", "CustomerID",customerId.toString())
          );
          CustomerMapper.mapToCustomer(customerDto,customer);
          customerRepository.save(customer);
          isUpdated = true;
      }
      return isUpdated;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer","Mobile Number",mobileNumber.toString())
        );

        accountRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return false;
    }

}