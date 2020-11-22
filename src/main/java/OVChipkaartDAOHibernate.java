import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.List;

public class OVChipkaartDAOHibernate implements OVChipkaartDAO {
    private Session session;
    private Transaction transaction;

    public OVChipkaartDAOHibernate() {}

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
    public void save(OVChipkaart ovChipkaart) {
        getSession().save(ovChipkaart);
    }

    @Override
    public void update(OVChipkaart ovChipkaart) {
        getSession().update(ovChipkaart);
    }

    @Override
    public void delete(OVChipkaart ovChipkaart) {
        getSession().delete(ovChipkaart);
    }

    @Override
    public OVChipkaart findByKaartnummer(int kaart_nummer) {
        return getSession().get(OVChipkaart.class, kaart_nummer);
    }

    @SuppressWarnings("unchecked")
    @Override
    public ArrayList<OVChipkaart> findByReiziger(Reiziger reiziger) {
        Criteria criteria = session.createCriteria(OVChipkaart.class);
        return (ArrayList<OVChipkaart>) criteria.add(Restrictions.eq("reiziger_id", reiziger.getId())).list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<OVChipkaart> findAll() {
        return (List<OVChipkaart>) getSession().createQuery("from OVChipkaart").list();
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
