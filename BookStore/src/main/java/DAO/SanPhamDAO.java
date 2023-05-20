package DAO;

import util.HibernateUtil;
import model.SanPham;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;

public class SanPhamDAO implements DAOInterface<SanPham> {
    public static SanPhamDAO getInstance() {return new SanPhamDAO();}

    @Override
    public ArrayList<SanPham> selectAll() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        String queryString = "SELECT t FROM SanPham t";
        Query<SanPham> query = session.createQuery(queryString, SanPham.class);
        return (ArrayList<SanPham>) query.getResultList();
    }

    @Override
    public SanPham selectByID(SanPham sanPham) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        return (SanPham) session.load(SanPham.class, sanPham.getMaSanPham());
    }

    @Override
    public int insert(SanPham sanPham) {
        int flag = 0;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            // Do some work
            session.saveOrUpdate(sanPham);
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
    public int insertAll(ArrayList<SanPham> arr) {
        arr.forEach(sanPham -> {
            insert(sanPham);
        });
        return 1;
    }

    @Override
    public int delete(SanPham sanPham) {
        int flag = 0;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            // Do some work
            session.delete(sanPham);
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
    public int deleteAll(ArrayList<SanPham> arr) {
        arr.forEach(sanPham -> {
            delete(sanPham);
        });
        return 1;
    }

    @Override
    public int update(SanPham sanPham) {
        int flag = 0;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            // Do some work
            session.saveOrUpdate(sanPham);
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
