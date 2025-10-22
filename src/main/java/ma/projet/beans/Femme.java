package ma.projet.beans;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@NamedNativeQuery(
    name = "Femme.countEnfantsBetweenDates",
    query = "SELECT SUM(m.nbr_enfant) FROM mariage m WHERE m.femme_id = ?1 AND m.date_debut BETWEEN ?2 AND ?3"
)
@NamedQuery(
    name = "Femme.marriedTwiceOrMore",
    query = "SELECT f FROM Femme f WHERE (SELECT COUNT(m) FROM Mariage m WHERE m.femme = f) >= 2"
)
public class Femme extends Personne {
    
    @OneToMany(mappedBy = "femme", fetch = FetchType.EAGER)
    private List<Mariage> mariages;

    public Femme() {
        this.mariages = new ArrayList<>();
    }

    public Femme(String nom, String prenom, String telephone, String adresse, Date dateNaissance) {
        super(nom, prenom, telephone, adresse, dateNaissance);
        this.mariages = new ArrayList<>();
    }

    public List<Mariage> getMariages() {
        return mariages;
    }

    public void setMariages(List<Mariage> mariages) {
        this.mariages = mariages;
    }
}
