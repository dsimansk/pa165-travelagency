package cz.muni.fi.pa165.travelagency.data.dao.impl;

import cz.muni.fi.pa165.travelagency.data.dao.CustomerDAO;
import cz.muni.fi.pa165.travelagency.data.entity.Customer;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Sebastian Kunec
 */
@Repository(value = "customerDAO")
public class CustomerDAOImpl implements CustomerDAO {

    @PersistenceContext
    private EntityManager em;

    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public void create(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer to be created is null");
        }
        if (customer.getId() != null) {
            throw new IllegalArgumentException("Customer to be created has already assigned id");
        }
        
        validateCustomer(customer);
        em.persist(customer);
    }

    @Override
    public Customer get(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Customer id to be retrieved is null");
        }
        Customer result = null;
        result = em.find(Customer.class, id);
        return result;
    }

    @Override
    public void update(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer to be updated is null");
        }
        if (customer.getId() == null) {
            throw new IllegalArgumentException("Customer to be updated has null id");
        }
        if (get(customer.getId()) == null) {
            throw new IllegalArgumentException("Customer to be updated doesn't exist in DB");
        }
        
        validateCustomer(customer);
        em.merge(customer);
    }

    @Override
    public void delete(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer to be deleted is null");
        }
        if (customer.getId() == null) {
            throw new IllegalArgumentException("Customer to be deleted has null id");
        }
        if (get(customer.getId()) == null) {
            throw new IllegalArgumentException("Customer to be deleted doesn't exist in DB");
        }

        Customer remove = em.getReference(Customer.class, customer.getId());
        em.remove(remove);
    }

    @Override
    public List<Customer> getAll() {
        List<Customer> result = new ArrayList<>();
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Customer.class));
        Query q = em.createQuery(cq);
        result = q.getResultList();
        return result;
    }

    private void validateCustomer(Customer customer) {
        if (customer.getName() == null) {
            throw new IllegalArgumentException("Customer name must be set, it's null");
        }
        if (customer.getName().isEmpty()) {
            throw new IllegalArgumentException("Customer name must be set, it's empty");
        }
        if (customer.getAddress() == null) {
            throw new IllegalArgumentException("Customer address must be set, it's null");
        }
        if (customer.getAddress().isEmpty()) {
            throw new IllegalArgumentException("Customer address must be set, it's null");
        }
        if (customer.getPhoneNumber() == null) {
            throw new IllegalArgumentException("Customer phone no. must be set, it's null");
        }
        if (customer.getPhoneNumber().isEmpty()) {
            throw new IllegalArgumentException("Customer phone no. must be set, it's null");
        }

    }
}
