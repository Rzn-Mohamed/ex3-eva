package ma.projet.util;

import org.springframework.stereotype.Component;

@Component
public class HibernateUtil {
    
    public HibernateUtil() {
        System.out.println("HibernateUtil initialized - Configuration managed by Spring Boot");
    }
}
