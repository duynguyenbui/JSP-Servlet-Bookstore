package DAO;

import util.HibernateUtil;
import model.DonHang;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;

public class DonHangDAO implements DAOInterface<DonHang>{
    public static DonHangDAO getInstance() {return new DonHangDAO();}

    @Override
    public ArrayList<DonHang> selectAll() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        String queryString = "SELECT t FROM DonHang t";
        Query<DonHang> query = session.createQuery(queryString, DonHang.class);
        return (ArrayList<DonHang>) query.getResultList();
    }

    @Override
    public DonHang selectByID(DonHang donHang) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        return (DonHang) session.load(DonHang.class, donHang.getMaDonHang());
    }

    @Override
    public int insert(DonHang donHang) {
        int flag = 0;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            // Do some work
            session.saveOrUpdate(donHang);
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
    public int insertAll(ArrayList<DonHang> arr) {
        arr.forEach(donHang -> {
            insert(donHang);
        });
        return 1;
    }

    @Override
    public int delete(DonHang donHang) {
        int flag = 0;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            // Do some work
            session.delete(donHang);
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
    public int deleteAll(ArrayList<DonHang> arr) {
        arr.forEach(donHang -> {
            delete(donHang);
        });
        return 1;
    }

    @Override
    public int update(DonHang donHang) {
        int flag = 0;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            // Do some work
            session.saveOrUpdate(donHang);
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
