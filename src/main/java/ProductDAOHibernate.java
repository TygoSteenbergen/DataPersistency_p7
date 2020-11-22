import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class ProductDAOHibernate implements ProductDAO{
    private Session session;
    private Transaction transaction;

    public ProductDAOHibernate() {}

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
    public void save(Product product) {
        getSession().save(product);
    }

    @Override
    public void update(Product product) {
        getSession().update(product);
    }

    @Override
    public void delete(Product product) {
        getSession().delete(product);
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

    @SuppressWarnings("unchecked")
    @Override
    public List<Product> findAll() {
        return (List<Product>) getSession().createQuery("from Product").list();
    }
}
