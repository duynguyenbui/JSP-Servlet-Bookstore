package DAO;

import util.HibernateUtil;
import model.TheLoai;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;

public class TheLoaiDAO implements DAOInterface<TheLoai> {

    public static TheLoaiDAO getInstance() {return new TheLoaiDAO();}


    @Override
    public ArrayList<TheLoai> selectAll() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        String queryString = "SELECT t FROM TheLoai t";
        Query<TheLoai> query = session.createQuery(queryString, TheLoai.class);
        return (ArrayList<TheLoai>) query.getResultList();
    }

    @Override
    public TheLoai selectByID(TheLoai theLoai) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        return (TheLoai) session.load(TheLoai.class, theLoai.getMaTheLoai());
    }

    @Override
    public int insert(TheLoai theLoai) {
        int flag = 0;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            // Do some work
            session.saveOrUpdate(theLoai);
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
    public int insertAll(ArrayList<TheLoai> arr) {
        arr.forEach(theLoai -> {
            insert(theLoai);
        });
        return 1;
    }

    @Override
    public int delete(TheLoai theLoai) {
        int flag = 0;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            // Do some work
            session.delete(theLoai);
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
    public int deleteAll(ArrayList<TheLoai> arr) {
        arr.forEach(theLoai -> {
            delete(theLoai);
        });
        return 1;
    }

    @Override
    public int update(TheLoai theLoai) {
        int flag = 0;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            // Do some work
            session.saveOrUpdate(theLoai);
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
