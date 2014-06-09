package cz.muni.fi.pa165.travelagency.data.dao.impl;

import cz.muni.fi.pa165.travelagency.data.dao.VacationOrderDAO;
import cz.muni.fi.pa165.travelagency.data.entity.Customer;
import cz.muni.fi.pa165.travelagency.data.entity.VacationOrder;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Martin Gerlasinsky
 */
@Repository(value="vacationOrderDAO")
public class VacationOrderDAOImpl implements VacationOrderDAO {
    
    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(VacationOrder order) {
        if (order == null) {
            throw new IllegalArgumentException("Order to be created is null.");
        }
        if (order.getId() != null) {
            throw new IllegalArgumentException("Order to be created already "
                    + "has a set primary key.");
        }
        
        validateOrder(order);
        em.persist(order);
    }

    @Override
    public VacationOrder get(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id of order to be retrieved "
                    + "is null.");
        }

        VacationOrder result = null;
        result = em.find(VacationOrder.class, id);
        return result;
    }

    @Override
    public void update(VacationOrder order) {
        if (order == null) {
            throw new IllegalArgumentException("Order to be updated is null.");
        }
        if (order.getId() == null) {
            throw new IllegalArgumentException("Order to be updated has no "
                    + "primary key set.");
        }
        if (get(order.getId()) == null) {
            throw new IllegalArgumentException("Order to be updated does not "
                    + "exist in database.");
        }
        
        validateOrder(order);
        em.merge(order);
    }

    @Override
    public void delete(VacationOrder order) {
        if (order == null) {
            throw new IllegalArgumentException("Order to be deleted is null.");
        }
        if (order.getId() == null) {
            throw new IllegalArgumentException("Order to be deleted has no "
                    + "primary key set.");
        }
        if (get(order.getId()) == null) {
            throw new IllegalArgumentException("Order to be deleted does not "
                    + "exist in database.");
        }
        
        VacationOrder removed = em.getReference(VacationOrder.class, order.getId());
        em.remove(removed);
    }

    @Override
    public List<VacationOrder> getAll() {
        List<VacationOrder> results = new ArrayList<>();
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(VacationOrder.class));
        Query q = em.createQuery(cq);
        results = q.getResultList();
        return results;
    }

    @Override
    public List<VacationOrder> getOrdersByCustomer(Customer customer) {
        List<VacationOrder> results = new ArrayList<>();
        Long id = customer.getId();
        Query q = em.createQuery(
                    "SELECT c "
                    + "FROM cz.muni.fi.pa165.travelagency.data.entity.VacationOrder c "
                    + "WHERE c.customer = :customer");
        q.setParameter("customer", customer);
        results = q.getResultList();
        return results;
    }
    
    private void validateOrder(VacationOrder order) {
        if (order.getCustomer() == null) {
            throw new IllegalArgumentException("Order's customer must be set, "
                    + "it is currently null.");
        }
        if (order.getVacation() == null) {
            throw new IllegalArgumentException("Order's vacation must be set, "
                    + "it is currently null.");
        }
        if (order.getReservations() < 1) {
            throw new IllegalArgumentException("Order's reservations must be"
                    + "positive.");
        }
    }
    
}
