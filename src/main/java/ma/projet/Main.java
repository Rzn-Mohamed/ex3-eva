package ma.projet;

import ma.projet.beans.Femme;
import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.service.FemmeService;
import ma.projet.service.HommeService;
import ma.projet.service.MariageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public CommandLineRunner demo(HommeService hommeService, FemmeService femmeService, MariageService mariageService) {
        return (args) -> {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            System.out.println("\n=== Création de 10 femmes ===");
            Femme f1 = new Femme("Benjelloun", "Fatima Zahra", "0612345678", "Casablanca", sdf.parse("15/03/1975"));
            Femme f2 = new Femme("Alami", "Karima", "0623456789", "Rabat", sdf.parse("20/05/1980"));
            Femme f3 = new Femme("El Fassi", "Khadija", "0634567890", "Fes", sdf.parse("10/08/1985"));
            Femme f4 = new Femme("Bennani", "Samira", "0645678901", "Marrakech", sdf.parse("25/11/1978"));
            Femme f5 = new Femme("Tazi", "Amina", "0656789012", "Tanger", sdf.parse("05/01/1982"));
            Femme f6 = new Femme("Rami", "Salima", "0667890123", "Meknes", sdf.parse("18/07/1976"));
            Femme f7 = new Femme("Ali", "Amal", "0678901234", "Oujda", sdf.parse("30/09/1988"));
            Femme f8 = new Femme("Alaoui", "Wafa", "0689012345", "Agadir", sdf.parse("12/12/1990"));
            Femme f9 = new Femme("Idrissi", "Nadia", "0690123456", "Kenitra", sdf.parse("22/04/1983"));
            Femme f10 = new Femme("Berrada", "Laila", "0601234567", "Tetouan", sdf.parse("08/06/1979"));

            femmeService.create(f1);
            femmeService.create(f2);
            femmeService.create(f3);
            femmeService.create(f4);
            femmeService.create(f5);
            femmeService.create(f6);
            femmeService.create(f7);
            femmeService.create(f8);
            femmeService.create(f9);
            femmeService.create(f10);

            System.out.println("\n=== Création de 5 hommes ===");
            Homme h1 = new Homme("Safi", "Said", "0612340001", "Casablanca", sdf.parse("10/01/1970"));
            Homme h2 = new Homme("Tazi", "Youssef", "0623450002", "Rabat", sdf.parse("15/02/1972"));
            Homme h3 = new Homme("El Amrani", "Hassan", "0634560003", "Fes", sdf.parse("20/03/1968"));
            Homme h4 = new Homme("Bennis", "Karim", "0645670004", "Marrakech", sdf.parse("25/04/1975"));
            Homme h5 = new Homme("Chraibi", "Omar", "0656780005", "Tanger", sdf.parse("30/05/1977"));

            hommeService.create(h1);
            hommeService.create(h2);
            hommeService.create(h3);
            hommeService.create(h4);
            hommeService.create(h5);

            System.out.println("\n=== Création des mariages ===");
            Mariage m1 = new Mariage(sdf.parse("03/09/1989"), sdf.parse("03/09/1990"), 0, h1, f2);
            Mariage m2 = new Mariage(sdf.parse("03/09/1990"), null, 4, h1, f6);
            Mariage m3 = new Mariage(sdf.parse("03/09/1995"), null, 2, h1, f7);
            Mariage m4 = new Mariage(sdf.parse("04/11/2000"), null, 3, h1, f8);

            Mariage m5 = new Mariage(sdf.parse("15/06/1995"), null, 2, h2, f3);
            Mariage m6 = new Mariage(sdf.parse("20/08/2000"), null, 3, h2, f9);

            Mariage m7 = new Mariage(sdf.parse("10/05/1990"), sdf.parse("10/05/2005"), 1, h3, f1);
            Mariage m8 = new Mariage(sdf.parse("15/06/2006"), null, 2, h3, f4);

            Mariage m9 = new Mariage(sdf.parse("01/01/2000"), sdf.parse("01/01/2010"), 1, h4, f5);
            Mariage m10 = new Mariage(sdf.parse("05/05/2011"), null, 2, h4, f10);

            Mariage m11 = new Mariage(sdf.parse("20/03/2005"), null, 3, h5, f1);

            mariageService.create(m1);
            mariageService.create(m2);
            mariageService.create(m3);
            mariageService.create(m4);
            mariageService.create(m5);
            mariageService.create(m6);
            mariageService.create(m7);
            mariageService.create(m8);
            mariageService.create(m9);
            mariageService.create(m10);
            mariageService.create(m11);

            System.out.println("\n=== Test 1: Afficher la liste des femmes ===");
            List<Femme> femmes = femmeService.findAll();
            for (Femme f : femmes) {
                System.out.println("ID: " + f.getId() + " - " + f.getPrenom() + " " + f.getNom() + 
                        " - Né(e) le: " + sdf.format(f.getDateNaissance()));
            }

            System.out.println("\n=== Test 2: Afficher la femme la plus âgée ===");
            Femme oldest = femmeService.findOldestFemme();
            if (oldest != null) {
                System.out.println("Femme la plus âgée: " + oldest.getPrenom() + " " + oldest.getNom() + 
                        " - Né(e) le: " + sdf.format(oldest.getDateNaissance()));
            }

            System.out.println("\n=== Test 3: Afficher les épouses de SAFI SAID entre 01/01/1990 et 31/12/2000 ===");
            Date dateDebut = sdf.parse("01/01/1990");
            Date dateFin = sdf.parse("31/12/2000");
            List<Mariage> epouses = hommeService.findEpousesBetweenDates(h1, dateDebut, dateFin);
            for (Mariage m : epouses) {
                System.out.println("Épouse: " + m.getFemme().getPrenom() + " " + m.getFemme().getNom() + 
                        " - Date mariage: " + sdf.format(m.getDateDebut()));
            }

            System.out.println("\n=== Test 4: Nombre d'enfants de Fatima Zahra Benjelloun entre 01/01/1990 et 31/12/2010 ===");
            int nbrEnfants = femmeService.countEnfantsBetweenDates(f1, sdf.parse("01/01/1990"), sdf.parse("31/12/2010"));
            System.out.println("Nombre d'enfants: " + nbrEnfants);

            System.out.println("\n=== Test 5: Afficher les femmes mariées deux fois ou plus ===");
            List<Femme> femmesMarriedTwice = femmeService.findFemmesMarriedTwiceOrMore();
            for (Femme f : femmesMarriedTwice) {
                System.out.println("Femme: " + f.getPrenom() + " " + f.getNom() + 
                        " - Nombre de mariages: " + f.getMariages().size());
            }

            System.out.println("\n=== Test 6: Hommes mariés à quatre femmes entre 01/01/1989 et 31/12/2005 ===");
            List<Homme> hommesQuatreFemmes = hommeService.findHommesMarriedToFourWomenBetweenDates(
                    sdf.parse("01/01/1989"), sdf.parse("31/12/2005"));
            for (Homme h : hommesQuatreFemmes) {
                System.out.println("Homme: " + h.getPrenom() + " " + h.getNom());
            }

            System.out.println("\n=== Test 7: Afficher les mariages de SAFI SAID avec tous les détails ===");
            Homme safiSaid = hommeService.findById(h1.getId());
            System.out.println("Nom : " + safiSaid.getNom().toUpperCase() + " " + safiSaid.getPrenom().toUpperCase());
            
            System.out.println("\nMariages En Cours :");
            int count = 1;
            for (Mariage m : safiSaid.getMariages()) {
                if (m.getDateFin() == null) {
                    System.out.println(count + ". Femme : " + m.getFemme().getPrenom().toUpperCase() + " " + 
                            m.getFemme().getNom().toUpperCase() + "    Date Début : " + 
                            sdf.format(m.getDateDebut()) + "    Nbr Enfants : " + m.getNbrEnfant());
                    count++;
                }
            }
            
            System.out.println("\nMariages échoués :");
            count = 1;
            for (Mariage m : safiSaid.getMariages()) {
                if (m.getDateFin() != null) {
                    System.out.println(count + ". Femme : " + m.getFemme().getPrenom().toUpperCase() + " " + 
                            m.getFemme().getNom().toUpperCase() + "   Date Début : " + 
                            sdf.format(m.getDateDebut()));
                    System.out.println("   Date Fin : " + sdf.format(m.getDateFin()) + 
                            "   Nbr Enfants : " + m.getNbrEnfant());
                    count++;
                }
            }

            System.out.println("\n=== Fin des tests ===");
        };
    }
}
