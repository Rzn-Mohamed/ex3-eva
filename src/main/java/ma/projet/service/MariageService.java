package ma.projet.service;

import ma.projet.beans.Mariage;
import ma.projet.dao.IDao;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class MariageService implements IDao<Mariage> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean create(Mariage o) {
        try {
            entityManager.persist(o);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean update(Mariage o) {
        try {
            entityManager.merge(o);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean delete(Mariage o) {
        try {
            entityManager.remove(entityManager.contains(o) ? o : entityManager.merge(o));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Mariage findById(int id) {
        return entityManager.find(Mariage.class, id);
    }

    @Override
    public List<Mariage> findAll() {
        return entityManager.createQuery("SELECT m FROM Mariage m", Mariage.class).getResultList();
    }
}
