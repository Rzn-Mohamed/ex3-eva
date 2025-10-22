package ma.projet.service;

import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.dao.IDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class HommeService implements IDao<Homme> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean create(Homme o) {
        try {
            entityManager.persist(o);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean update(Homme o) {
        try {
            entityManager.merge(o);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean delete(Homme o) {
        try {
            entityManager.remove(entityManager.contains(o) ? o : entityManager.merge(o));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Homme findById(int id) {
        return entityManager.find(Homme.class, id);
    }

    @Override
    public List<Homme> findAll() {
        return entityManager.createQuery("SELECT h FROM Homme h", Homme.class).getResultList();
    }

    public List<Mariage> findEpousesBetweenDates(Homme homme, Date dateDebut, Date dateFin) {
        return entityManager.createQuery(
                "SELECT m FROM Mariage m WHERE m.homme = :homme AND m.dateDebut BETWEEN :dateDebut AND :dateFin",
                Mariage.class)
                .setParameter("homme", homme)
                .setParameter("dateDebut", dateDebut)
                .setParameter("dateFin", dateFin)
                .getResultList();
    }

    public List<Homme> findHommesMarriedToFourWomenBetweenDates(Date dateDebut, Date dateFin) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Homme> query = cb.createQuery(Homme.class);
        Root<Homme> homme = query.from(Homme.class);
        Join<Homme, Mariage> mariages = homme.join("mariages");

        Predicate datePredicate = cb.between(mariages.get("dateDebut"), dateDebut, dateFin);

        query.select(homme)
                .where(datePredicate)
                .groupBy(homme.get("id"))
                .having(cb.equal(cb.count(mariages), 4));

        return entityManager.createQuery(query).getResultList();
    }
}
