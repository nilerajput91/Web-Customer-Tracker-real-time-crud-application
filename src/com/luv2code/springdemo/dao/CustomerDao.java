package com.luv2code.springdemo.dao;

import java.util.List;

import com.luv2code.springdemo.entity.Customer;

public interface CustomerDao {
	public List<Customer> getCustomers();

	public Customer getCustomers(int theId);

	public void saveCustomer(Customer theCustomer);

	public void deleteCustomers(int theId);

	public List<Customer> searchCustomers(String theSearchName);

}
