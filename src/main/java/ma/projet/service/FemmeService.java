package ma.projet.service;

import ma.projet.beans.Femme;
import ma.projet.dao.IDao;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class FemmeService implements IDao<Femme> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean create(Femme o) {
        try {
            entityManager.persist(o);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean update(Femme o) {
        try {
            entityManager.merge(o);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean delete(Femme o) {
        try {
            entityManager.remove(entityManager.contains(o) ? o : entityManager.merge(o));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Femme findById(int id) {
        return entityManager.find(Femme.class, id);
    }

    @Override
    public List<Femme> findAll() {
        return entityManager.createQuery("SELECT f FROM Femme f", Femme.class).getResultList();
    }

    public int countEnfantsBetweenDates(Femme femme, Date dateDebut, Date dateFin) {
        Object result = entityManager.createNamedQuery("Femme.countEnfantsBetweenDates")
                .setParameter(1, femme.getId())
                .setParameter(2, dateDebut)
                .setParameter(3, dateFin)
                .getSingleResult();
        
        if (result == null) {
            return 0;
        }
        if (result instanceof BigDecimal) {
            return ((BigDecimal) result).intValue();
        }
        return ((Number) result).intValue();
    }

    public List<Femme> findFemmesMarriedTwiceOrMore() {
        return entityManager.createNamedQuery("Femme.marriedTwiceOrMore", Femme.class).getResultList();
    }

    public Femme findOldestFemme() {
        List<Femme> femmes = entityManager.createQuery(
                "SELECT f FROM Femme f ORDER BY f.dateNaissance ASC", Femme.class)
                .setMaxResults(1)
                .getResultList();
        return femmes.isEmpty() ? null : femmes.get(0);
    }
}
