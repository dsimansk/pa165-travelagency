package cz.muni.fi.pa165.travelagency.data.dao.impl;

import cz.muni.fi.pa165.travelagency.data.dao.AccountDAO;
import cz.muni.fi.pa165.travelagency.data.entity.Account;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Michal Jurc
 */
@Repository(value = "accountDAO")
public class AccountDAOImpl implements AccountDAO {
    
    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Account account) {
        if (account == null) {
            throw new IllegalArgumentException("Account to be created is null");
        }
        if (account.getId() != null) {
            throw new IllegalArgumentException("Account to be created has already assigned id");
        }
        
        validateAccount(account);
        em.persist(account);
    }

    @Override
    public Account get(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Account id to be retrieved is null");
        }
        Account result = null;
        result = em.find(Account.class, id);
        return result;
    }

    @Override
    public Account getByUsername(String username) {
        Account result = null;
        final String qstring = "SELECT e FROM Account e WHERE e.name = :name";
        TypedQuery<Account> query = em.createQuery(qstring, Account.class);
        query.setParameter("name", username);
        result = query.getSingleResult();
        return result;
    }

    @Override
    public void update(Account account) {
        if (account == null) {
            throw new IllegalArgumentException("Account to be updated is null");
        }
        if (account.getId() == null) {
            throw new IllegalArgumentException("Account to be updated has null id");
        }
        if (get(account.getId()) == null) {
            throw new IllegalArgumentException("Account to be updated doesn't exist in DB");
        }
        
        validateAccount(account);
        em.merge(account);
    }

    @Override
    public void delete(Account account) {
        if (account == null) {
            throw new IllegalArgumentException("Account to be deleted is null");
        }
        if (account.getId() == null) {
            throw new IllegalArgumentException("Account to be deleted has null id");
        }
        if (get(account.getId()) == null) {
            throw new IllegalArgumentException("Account to be deleted doesn't exist in DB");
        }

        Account remove = em.getReference(Account.class, account.getId());
        em.remove(remove);
    }

    @Override
    public List<Account> getAll() {
        List<Account> result = new ArrayList<>();
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Account.class));
        Query q = em.createQuery(cq);
        result = q.getResultList();
        return result;
    }
    
    private void validateAccount(Account account) {
        if (account.getName() == null) {
            throw new IllegalArgumentException("Account name must be set, it's null");
        }
        if (account.getName().isEmpty()) {
            throw new IllegalArgumentException("Account name must be set, it's empty");
        }
        if (account.getPassword() == null) {
            throw new IllegalArgumentException("Account password must be set, it's null");
        }
        if (account.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Account address must be set, it's null");
        }
        if (account.getCustomer() == null) {
            throw new IllegalArgumentException("Customer must be set, it's null");
        }
    }
    
}
