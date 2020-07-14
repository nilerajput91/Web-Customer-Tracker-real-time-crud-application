package com.luv2code.springdemo.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.luv2code.springdemo.entity.Customer;

@Repository
public class CustomerDaoImpl implements CustomerDao {

	// need to inject the session factory
	@Autowired
	private SessionFactory sessionFactory;

	@Override

	public List<Customer> getCustomers() {

		// get the current Hibernate session

		Session currentSession = sessionFactory.getCurrentSession();

		// create a query
		Query<Customer> theQuery = currentSession.createQuery("from Customer order by lastName", Customer.class);

		// execute query and get result list
		List<Customer> customers = theQuery.getResultList();

		// return the results
		return customers;
	}

	@Override
	public void saveCustomer(Customer theCustomer) {
		// get the hibernate Session

		Session currentSession = sessionFactory.getCurrentSession();

		// save/update the customer ....finally
		currentSession.saveOrUpdate(theCustomer);

	}

	@Override
	public Customer getCustomers(int theId) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();

		// now the reterive/read from database using the primary key id

		Customer theCustomer = currentSession.get(Customer.class, theId);
		return theCustomer;
	}

	@Override
	public void deleteCustomers(int theId) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();

		// delete the Object with primary key

		Query theQuery = currentSession.createQuery("delete from Customer where id=:customerId");

		theQuery.setParameter("customerId", theId);

		theQuery.executeUpdate();
	}

	@Override
	public List<Customer> searchCustomers(String theSearchName) {
		// get the current hibernate session

		Session currentSession = sessionFactory.getCurrentSession();

		Query theQuery = null;

		// only serach by name if name is not empty
		if (theSearchName != null && theSearchName.trim().length() > 0) {
			// serach for firstName and lastName case insenstive

			theQuery = currentSession.createQuery(
					"from Customer where lower(firstName) " + "like :theName or lower(lastName)" + " like:theName",
					Customer.class);

			theQuery.setParameter("theName", "%" + theSearchName.toLowerCase() + "%");
		} else {
			// theSearchName is empty so get all customer
			theQuery = currentSession.createQuery("from Customer", Customer.class);
		}

		// execute the Query and get result list
		List<Customer> customers = theQuery.getResultList();
		return customers;
	}

}
