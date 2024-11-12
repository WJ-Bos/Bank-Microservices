package com.wjbos.accounts.mapper;

import com.wjbos.accounts.dto.CustomerDto;
import com.wjbos.accounts.entity.Customer;

public class CustomerMapper {

    /**
     * @param customerDto
     * @param customer
     * @return
     */
    public static CustomerDto mapToCustomerDto(CustomerDto customerDto, Customer customer){
         customerDto.setEmail(customer.getEmail());
         customerDto.setName(customer.getName());
         customerDto.setMobileNumber(customer.getMobileNumber());
         return customerDto;
    }

    /**
     * @param customerDto
     * @param customer
     * @return
     */
    public static Customer mapToCustomer(CustomerDto customerDto, Customer customer){
        customer.setName(customerDto.getName());
        customer.setEmail(customerDto.getEmail());
        customer.setMobileNumber(customerDto.getMobileNumber());
        return customer;
    }
}
