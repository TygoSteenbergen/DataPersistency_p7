import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;

public class ReizigerDAOHibernate implements ReizigerDAO {
    private Session session;
    private Transaction transaction;

    private static SessionFactory getSessionFactory() {
        return new Configuration().configure().buildSessionFactory();
    }

    public Session openSession() {
        session = getSessionFactory().openSession();
        return session;
    }

    public Session openTransactionSession() {
        session = getSessionFactory().openSession();
        transaction = session.beginTransaction();
        return session;
    }

    public void closeSession() {
        session.close();
    }

    public void closeTransactionSession() {
        transaction.commit();
        session.close();
    }

    @Override
    public void save(Reiziger reiziger) {
        getSession().save(reiziger);
    }

    @Override
    public void update(Reiziger reiziger) {
        getSession().update(reiziger);
    }

    @Override
    public void delete(Reiziger reiziger) {
        getSession().delete(reiziger);
    }

    @Override
    public Reiziger findById(int id) {
        return getSession().get(Reiziger.class, id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public ArrayList<Reiziger> findByGbdatum(String datum) {
        Criteria criteria = session.createCriteria(Adres.class);
        return (ArrayList<Reiziger>) criteria.add(Restrictions.eq("geboortedatum", datum)).list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public ArrayList<Reiziger> findAll() {
        return (ArrayList<Reiziger>) getSession().createQuery("from Reiziger").list();
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
}
