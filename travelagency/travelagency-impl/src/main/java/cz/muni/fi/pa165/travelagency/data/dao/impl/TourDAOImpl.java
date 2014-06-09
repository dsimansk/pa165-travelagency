package cz.muni.fi.pa165.travelagency.data.dao.impl;

import cz.muni.fi.pa165.travelagency.data.dao.TourDAO;
import cz.muni.fi.pa165.travelagency.data.entity.Tour;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Michal Jurc
 */
@Repository(value = "tourDAO")
public class TourDAOImpl implements TourDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Tour tour) {
        if (tour == null) {
            throw new IllegalArgumentException("Tour to be created is null");
        }
        if (tour.getId() != null) {
            throw new IllegalArgumentException("Tour to be created has already assigned id");
        }
        
        validateTour(tour);
        em.persist(tour);
    }

    @Override
    public Tour get(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Tour id to be retrieved is null");
        }

        Tour result = null;
        result = em.find(Tour.class, id);
        return result;
    }

    @Override
    public void update(Tour tour) {
        if (tour == null) {
            throw new IllegalArgumentException("Tour to be updated is null");
        }
        if (tour.getId() == null) {
            throw new IllegalArgumentException("Tour to be updated has null id");
        }
        if (get(tour.getId()) == null) {
            throw new IllegalArgumentException("Tour to be updated doesn't exist in DB");
        }
        
        validateTour(tour);
        em.merge(tour);
    }

    @Override
    public void delete(Tour tour) {
        if (tour == null) {
            throw new IllegalArgumentException("Tour to be deleted is null");
        }
        if (tour.getId() == null) {
            throw new IllegalArgumentException("Tour to be deleted has null id");
        }
        if (get(tour.getId()) == null) {
            throw new IllegalArgumentException("Tour to be deleted doesn't exist in DB");
        }

        Tour remove = em.getReference(Tour.class, tour.getId());
        em.remove(remove);
    }

    @Override
    public List<Tour> getAll() {
        List<Tour> result = new ArrayList<>();
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Tour.class));
        Query q = em.createQuery(cq);
        result = q.getResultList();
        return result;
    }

    private void validateTour(Tour tour) {
        if (tour.getDestination() == null) {
            throw new IllegalArgumentException("Tour destination must be set, it's null");
        }
        if (tour.getDestination().isEmpty()) {
            throw new IllegalArgumentException("Tour destination must be set, it's empty");
        }
        if (tour.getStartDate() == null) {
            throw new IllegalArgumentException("Tour start date must be set, it's null");
        }
        if (tour.getDurationInHours() < 1) {
            throw new IllegalArgumentException("Tour duration must be set, it's less than 1");
        }
    }
}
