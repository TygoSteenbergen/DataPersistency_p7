import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class AdresDAOHibernate implements AdresDAO {
    private Session session;
    private Transaction transaction;

    public AdresDAOHibernate() {}

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
    public void save(Adres adres) {
        getSession().save(adres);
    }

    @Override
    public void update(Adres adres) {
        getSession().update(adres);
    }

    @Override
    public void delete(Adres adres) {
        getSession().delete(adres);
    }

    @Override
    public Adres findByReiziger(Reiziger reiziger) {
        Criteria criteria = session.createCriteria(Adres.class);
        return (Adres) criteria.add(Restrictions.eq("reiziger_id", reiziger.getId())).uniqueResult();
    }


    @SuppressWarnings("unchecked")
    @Override
    public List<Adres> findAll() {
        return (List<Adres>) getSession().createQuery("from Adres").list();
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
