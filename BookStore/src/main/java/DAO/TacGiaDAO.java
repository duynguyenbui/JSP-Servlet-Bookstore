package DAO;

import util.HibernateUtil;
import model.TacGia;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;

public class TacGiaDAO implements DAOInterface<TacGia> {

    public static TacGiaDAO getInstance() {return new TacGiaDAO();}

    @Override
    public ArrayList<TacGia> selectAll() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        String queryString = "SELECT t FROM TacGia t";
        Query<TacGia> query = session.createQuery(queryString, TacGia.class);
        return (ArrayList<TacGia>) query.getResultList();
    }

    @Override
    public TacGia selectByID(TacGia tacGia) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        return (TacGia) session.load(TacGia.class, tacGia.getMaTacGia());
    }

    @Override
    public int insert(TacGia tacGia) {
        int flag = 0;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            // Do some work
            session.saveOrUpdate(tacGia);
            tx.commit();
            flag = 1;
        }
        catch (Exception e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return flag;
    }

    @Override
    public int insertAll(ArrayList<TacGia> arr) {
        arr.forEach(tacGia -> {
            insert(tacGia);
        });
        return 1;
    }

    @Override
    public int delete(TacGia tacGia) {
        int flag = 0;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            // Do some work
            session.delete(tacGia);
            tx.commit();
            flag = 1;
        }
        catch (Exception e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return flag;
    }

    @Override
    public int deleteAll(ArrayList<TacGia> arr) {
        arr.forEach(tacGia -> {
            delete(tacGia);
        });
        return 1;
    }

    @Override
    public int update(TacGia tacGia) {
        int flag = 0;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            // Do some work
            session.saveOrUpdate(tacGia);
            tx.commit();
            flag = 1;
        }
        catch (Exception e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return flag;
    }
}
