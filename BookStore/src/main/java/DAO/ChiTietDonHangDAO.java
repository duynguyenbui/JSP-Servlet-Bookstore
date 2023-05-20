package DAO;

import util.HibernateUtil;
import model.ChiTietDonHang;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;

public class ChiTietDonHangDAO implements DAOInterface<ChiTietDonHang>  {
    public static ChiTietDonHangDAO getInstance() {return new ChiTietDonHangDAO();}


    @Override
    public ArrayList<ChiTietDonHang> selectAll() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        String queryString = "SELECT t FROM ChiTietDonHang t";
        Query<ChiTietDonHang> query = session.createQuery(queryString, ChiTietDonHang.class);
        return (ArrayList<ChiTietDonHang>) query.getResultList();
    }

    @Override
    public ChiTietDonHang selectByID(ChiTietDonHang chiTietDonHang) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        return (ChiTietDonHang) session.load(ChiTietDonHang.class, chiTietDonHang.getMaChiTietDonHang());
    }

    @Override
    public int insert(ChiTietDonHang chiTietDonHang) {
        int flag = 0;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            // Do some work
            session.saveOrUpdate(chiTietDonHang);
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
    public int insertAll(ArrayList<ChiTietDonHang> arr) {
        arr.forEach(chiTietDonHang -> {
            insert(chiTietDonHang);
        });
        return 1;
    }

    @Override
    public int delete(ChiTietDonHang chiTietDonHang) {
        int flag = 0;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            // Do some work
            session.delete(chiTietDonHang);
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
    public int deleteAll(ArrayList<ChiTietDonHang> arr) {
        arr.forEach(chiTietDonHang -> {
            delete(chiTietDonHang);
        });
        return 1;
    }

    @Override
    public int update(ChiTietDonHang chiTietDonHang) {
        int flag = 0;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            // Do some work
            session.saveOrUpdate(chiTietDonHang);
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
