package DAO;

import util.HibernateUtil;
import model.KhachHang;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;

public class KhachHangDAO implements DAOInterface<KhachHang> {

    public static KhachHangDAO getInstance() {return new KhachHangDAO();}

    @Override
    public ArrayList<KhachHang> selectAll() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        String queryString = "SELECT t FROM KhachHang t";
        Query<KhachHang> query = session.createQuery(queryString, KhachHang.class);
        return (ArrayList<KhachHang>) query.getResultList();
    }

    @Override
    public KhachHang selectByID(KhachHang khachHang) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        return (KhachHang) session.load(KhachHang.class, khachHang.getMaKhachHang());
    }

    @Override
    public int insert(KhachHang khachHang) {
        int flag = 0;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            // Do some work
            session.saveOrUpdate(khachHang);
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
    public int insertAll(ArrayList<KhachHang> arr) {
        arr.forEach(khachHang -> {
            insert(khachHang);
        });
        return 1;
    }

    @Override
    public int delete(KhachHang khachHang) {
        int flag = 0;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            // Do some work
            session.delete(khachHang);
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
    public int deleteAll(ArrayList<KhachHang> arr) {
        arr.forEach(khachHang -> {
            delete(khachHang);
        });
        return 1;
    }

    @Override
    public int update(KhachHang khachHang) {
        int flag = 0;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            // Do some work
            session.saveOrUpdate(khachHang);
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

    public boolean kiemTraTenDangNhap(String tenDangNhap) {
        boolean result = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            // do some work
            Query query = session.createQuery("SELECT COUNT(*) FROM KhachHang WHERE tenDangNhap = :tenDangNhap");
            query.setParameter("tenDangNhap", tenDangNhap);
            Long count = (Long) query.uniqueResult();

            result = count > 0;
            tx.commit();
        }
        catch (Exception e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return result;
    }

    public KhachHang selectByUser(KhachHang t) {
        KhachHang result = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            // do some work
            String queryString = "FROM KhachHang kh WHERE kh.tenDangNhap = :tenDangNhap AND kh.matKhau = :matKhau";
            Query query = session.createQuery(queryString);
            query.setParameter("tenDangNhap", t.getTenDangNhap());
            query.setParameter("matKhau", t.getMatKhau());

            result = (KhachHang) query.uniqueResult();
            tx.commit();
        }
        catch (Exception e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return result;
    }

    public boolean changePassword(KhachHang khachHang) {
        boolean flag = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();

            String queryString = "UPDATE KhachHang SET matKhau = :matKhau WHERE maKhachHang = :maKhachHang";
            Query query = session.createQuery(queryString);
            query.setParameter("matKhau", khachHang.getMatKhau());
            query.setParameter("maKhachHang", khachHang.getMaKhachHang());

            int rowCount = query.executeUpdate();

            if (rowCount > 0) {
                tx.commit();
                System.out.println("Cập nhật mật khẩu thành công.");
                flag = true;
            } else {
                tx.rollback();
                System.out.println("Khách hàng không tồn tại hoặc không thể cập nhật mật khẩu.");
            }
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return flag;
    }

    public boolean updateInfo(KhachHang khachHang) {
        boolean flag = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();

            String queryString = "UPDATE KhachHang SET hoVaTen = :hoVaTen, gioiTinh =:gioiTinh, diaChi =:diaChi" +
                    ", diaChiNhanHang =:diaChiNhanHang, diaChiMuaHang =:diaChiMuaHang, ngaySinh =:ngaySinh, soDienThoai =:soDienThoai, email =:email, dangKyNhanBangTin =:dangKyNhanBangTin WHERE maKhachHang = :maKhachHang";
            Query query = session.createQuery(queryString);
            query.setParameter("hoVaTen", khachHang.getHoVaTen());
            query.setParameter("gioiTinh", khachHang.getGioiTinh());
            query.setParameter("diaChi", khachHang.getDiaChi());
            query.setParameter("diaChiNhanHang", khachHang.getDiaChiNhanHang());
            query.setParameter("diaChiMuaHang", khachHang.getDiaChiMuaHang());
            query.setParameter("ngaySinh", khachHang.getNgaySinh());
            query.setParameter("soDienThoai", khachHang.getSoDienThoai());
            query.setParameter("email", khachHang.getEmail());
            query.setParameter("dangKyNhanBangTin", khachHang.isDangKyNhanBangTin());
            query.setParameter("maKhachHang", khachHang.getMaKhachHang());
            int rowCount = query.executeUpdate();

            if (rowCount > 0) {
                tx.commit();
                System.out.println("Cập nhật thành công.");
                flag = true;
            } else {
                tx.rollback();
                System.out.println("Khách hàng không tồn tại hoặc không thể cập nhật.");
            }
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return flag;
    }

    public int updateVerifyInformation(KhachHang t) {
        int result = 0;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();

            String sql = "UPDATE KhachHang " + " SET " + " maXacThuc =:maXacThuc"
                    + ", thoiGianHieuLucCuaMaXacThuc =:thoiGianHieuLucCuaMaXacThuc"
                    + ", trangThaiXacThuc =:trangThaiXacThuc" +  " WHERE maKhachHang =:maKhachHang";
            Query query = session.createQuery(sql);
            query.setParameter("maXacThuc", t.getMaXacThuc());
            query.setParameter("thoiGianHieuLucCuaMaXacThuc", t.getThoiGianHieuLucCuaMaXacThuc());
            query.setParameter("trangThaiXacThuc", t.isTrangThaiXacThuc());
            query.setParameter("maKhachHang", t.getMaKhachHang());

            result = query.executeUpdate();

            tx.commit();
        }
        catch (Exception e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return result;
    }
}
