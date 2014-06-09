package cz.muni.fi.pa165.travelagency.data.dao.impl;

import cz.muni.fi.pa165.travelagency.data.dao.VacationDAO;
import cz.muni.fi.pa165.travelagency.data.entity.Vacation;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import org.springframework.stereotype.Repository;

/**
 *
 * @author David Simansky
 */
@Repository(value="vacationDAO")
public class VacationDAOImpl implements VacationDAO {
    
    
    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Vacation vacation) {
        if (vacation == null) {
            throw new IllegalArgumentException("Vacation to be created is null.");
        }
        if (vacation.getId() != null) {
            throw new IllegalArgumentException("Vacation to be created already "
                    + "has a set primary key.");
        }
        
        validateVacation(vacation);
        em.persist(vacation);
    }

    @Override
    public Vacation get(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id of vacation to be retrieved "
                    + "is null.");
        }
        
        Vacation result = null;
        result = em.find(Vacation.class, id);
        return result;
    }

    @Override
    public void update(Vacation vacation) {
        if (vacation == null) {
            throw new IllegalArgumentException("Vacation to be updated is null.");
        }
        if (vacation.getId() == null) {
            throw new IllegalArgumentException("Vacation to be updated has no "
                    + "primary key set.");
        }
        if (get(vacation.getId()) == null) {
            throw new IllegalArgumentException("Vacation to be updated does not "
                    + "exist in database.");
        }
        
        validateVacation(vacation);
        em.merge(vacation);
    }

    @Override
    public void delete(Vacation vacation) {
        if (vacation == null) {
            throw new IllegalArgumentException("Vacation to be deleted is null.");
        }
        if (vacation.getId() == null) {
            throw new IllegalArgumentException("Vacation to be deleted has no "
                    + "primary key set.");
        }
        if (get(vacation.getId()) == null) {
            throw new IllegalArgumentException("Vacation to be deleted does not "
                    + "exist in database.");
        }

        Vacation removed = em.getReference(Vacation.class, vacation.getId());
        em.remove(removed);
    }

    @Override
    public List<Vacation> getAll() {
        List<Vacation> results = new ArrayList<>();
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Vacation.class));
        Query q = em.createQuery(cq);
        results = q.getResultList();
        return results;
    }
    
    private void validateVacation(Vacation vacation) {
        if (vacation.getDestination() == null) {
            throw new IllegalArgumentException("Vacation's destination must be "
                    + "set, it is currently null.");
        }
        if (vacation.getDestination().isEmpty()) {
            throw new IllegalArgumentException("Vacation's destination must be "
                    + "set, it is currently empty.");
        }
        if (vacation.getStartDate() == null) {
            throw new IllegalArgumentException("Vacation's start date must be "
                    + "set, it is currently null.");
        }
        if (vacation.getEndDate() == null) {
            throw new IllegalArgumentException("Vacation's end date must be "
                    + "set, it is currently null.");
        }
        if (vacation.getEndDate().compareTo(vacation.getStartDate()) < 0) {
            throw new IllegalArgumentException("Vacation's start date is after "
                    + "its end date.");
        }
        if (vacation.getPrice() == null) {
            throw new IllegalArgumentException("Vacation's price must be set, "
                    + "it is currently null.");
        }
        if (vacation.getMaxCapacity() < 1) {
            throw new IllegalArgumentException("Vacation's capacity must be "
                    + "positive.");
        }
    }

    @Override
    public List<Vacation> getByDestination(String destination) {
        List<Vacation> result = null;
        final String qstring = "SELECT v FROM Vacation v WHERE v.destination = :destination";
        TypedQuery<Vacation> query = em.createQuery(qstring, Vacation.class);
        query.setParameter("destination", destination);
        result = query.getResultList();
        return result;
    }
    
    @Override
    public List<String> getAllDestinations() {
        List<String> result = null;
        final String qstring = "SELECT DISTINCT v.destination FROM Vacation v";
        TypedQuery<String> query = em.createQuery(qstring, String.class);
        result = query.getResultList();
        return result;
    }
}
