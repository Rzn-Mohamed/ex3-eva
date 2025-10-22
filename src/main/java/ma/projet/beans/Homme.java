package ma.projet.beans;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Homme extends Personne {
    
    @OneToMany(mappedBy = "homme", fetch = FetchType.EAGER)
    private List<Mariage> mariages;

    public Homme() {
        this.mariages = new ArrayList<>();
    }

    public Homme(String nom, String prenom, String telephone, String adresse, Date dateNaissance) {
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
