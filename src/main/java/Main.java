import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Testklasse - deze klasse test alle andere klassen in deze package.
 *
 * System.out.println() is alleen in deze klasse toegestaan (behalve voor exceptions).
 *
 * @author tijmen.muller@hu.nl
 */
public class Main {
    // Creëer een factory voor Hibernate sessions.
    private static final SessionFactory factory;

    static {
        try {
            // Create a Hibernate session factory
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Retouneer een Hibernate session.
     *
     * @return Hibernate session
     * @throws HibernateException
     */
    private static Session getSession() throws HibernateException {
        return factory.openSession();
    }

    public static void main(String[] args) throws SQLException {
//        testFetchAll();
        testHibernate();
    }

    /**
     * P6. Haal alle (geannoteerde) entiteiten uit de database.
     */
    private static void testFetchAll() {
        Session session = getSession();
        try {
            Metamodel metamodel = session.getSessionFactory().getMetamodel();
            for (EntityType<?> entityType : metamodel.getEntities()) {
                Query query = session.createQuery("from " + entityType.getName());

                System.out.println("[Test] Alle objecten van type " + entityType.getName() + " uit database:");
                for (Object o : query.list()) {
                    System.out.println("  " + o);
                }
                System.out.println();
            }
        } finally {
            session.close();
        }
    }

    private static void testHibernate() {
        Reiziger reiziger = new Reiziger(6, "G.J.", "", "Mak",
                LocalDate.of(2000, 10, 8));
        Adres adres = new Adres(6, "3451XK", "12", "Secr. Verstgln.",
                "Vleuten", reiziger);
        OVChipkaart ovChipkaart = new OVChipkaart(83956,
                Date.valueOf(LocalDate.of(2022, 10, 12)), 2, 2.5, reiziger);
        Product product = new Product(7, "Seniorenkorting",
                "Goedkoper reizen omdat je van de tijd die je nog hebt het beste moet maken.", 12.5);

        ReizigerDAOHibernate RDAO = new ReizigerDAOHibernate();
        AdresDAOHibernate ADAO = new AdresDAOHibernate();
        OVChipkaartDAOHibernate ODAO = new OVChipkaartDAOHibernate();
        ProductDAOHibernate PDAO = new ProductDAOHibernate();

        reset(RDAO, reiziger, ADAO, adres, ODAO, ovChipkaart, PDAO, product);

        ovChipkaart.addToProducten(product); // Koppel het product aan de OV Kaart
        product.addToOVKaarten(ovChipkaart); // Koppel de OV Kaart aan het product

        System.out.println("Alle reizigers:");
        RDAO.openSession();
        RDAO.findAll().forEach(System.out::println);
        RDAO.closeSession();

        System.out.println("\nNieuwe reiziger toevoegen...");
        RDAO.openTransactionSession();
        RDAO.save(reiziger);
        RDAO.closeTransactionSession();

        System.out.println("Alle reizigers met nieuwe reiziger:");
        RDAO.openSession();
        RDAO.findAll().forEach(System.out::println);
        RDAO.closeSession();

        System.out.println("\nAlle adressen:");
        ADAO.openSession();
        ADAO.findAll().forEach(System.out::println);
        ADAO.closeSession();

        System.out.println("\nNieuw adres toevoegen...");
        ADAO.openTransactionSession();
        ADAO.save(adres);
        ADAO.closeTransactionSession();

        System.out.println("\nAlle adressen met nieuw Adres:");
        ADAO.openSession();
        ADAO.findAll().forEach(System.out::println);
        ADAO.closeSession();

        System.out.println("\nAlle OV kaarten:");
        ODAO.openSession();
        ODAO.findAll().forEach(System.out::println);
        ODAO.closeSession();

        System.out.println("\nNieuwe OV kaart toevoegen...");
        ODAO.openTransactionSession();
        ODAO.save(ovChipkaart);
        ODAO.closeTransactionSession();

        System.out.println("\nAlle OV kaarten met nieuwe OV kaart:");
        ODAO.openSession();
        ODAO.findAll().forEach(System.out::println);
        ODAO.closeSession();

        System.out.println("\nAlle producten bevat nu ook meteen het gekoppelde product:");
        PDAO.openSession();
        PDAO.findAll().forEach(System.out::println);
        PDAO.closeSession();
    }

    private static void reset(ReizigerDAOHibernate RDAO, Reiziger reiziger,
                              AdresDAOHibernate ADAO, Adres adres,
                              OVChipkaartDAOHibernate ODAO, OVChipkaart ovChipkaart,
                              ProductDAOHibernate PDAO, Product product) {
        ADAO.openTransactionSession();
        ADAO.delete(adres);
        ADAO.closeTransactionSession();

        ODAO.openTransactionSession();
        ODAO.delete(ovChipkaart);
        ODAO.closeTransactionSession();

        PDAO.openTransactionSession();
        PDAO.delete(product);
        PDAO.closeSession();

        RDAO.openTransactionSession();
        RDAO.delete(reiziger);
        RDAO.closeTransactionSession();
    }
}