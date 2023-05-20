package controller;

import DAO.KhachHangDAO;
import model.KhachHang;
import util.Email;
import util.MaHoa;
import util.SoNgauNhien;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;
import java.util.Random;

@WebServlet(name = "KhachHang", urlPatterns = {"/khach-hang"})
public class KhachHangController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String hanhDong = request.getParameter("hanhDong");
        if(hanhDong.equals("dang-nhap")) {
            dangNhap(request, response);
        } else if(hanhDong.equals("dang-xuat")) {
            dangXuat(request, response);
        } else if (hanhDong.equals("doi-mat-khau")) {
            doiMatKhau(request, response);
        } else if (hanhDong.equals("dang-ky")) {
            dangKy(request, response);
        } else if (hanhDong.equals("thay-doi-thong-tin")) {
            thayDoiThongTin(request, response);
        } else if (hanhDong.equals("xac-thuc")) {
            xacThuc(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    private void dangNhap(HttpServletRequest request, HttpServletResponse response) {
        String tenDangNhap = request.getParameter("tenDangNhap");
        String matKhau = request.getParameter("matKhau");

        model.KhachHang kh = new model.KhachHang();
        kh.setTenDangNhap(tenDangNhap);
        kh.setMatKhau(MaHoa.toSHA1(matKhau));
        model.KhachHang khachHang = KhachHangDAO.getInstance().selectByUser(kh);
        String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                + request.getContextPath();
        if(khachHang != null && khachHang.isTrangThaiXacThuc()) {
            HttpSession session = request.getSession();
            session.setAttribute("khachHang", khachHang);
            url += "/index.jsp";
        } else{
            request.setAttribute("baoLoi", "Tên đăng nhập hoặc mật khẩu không chính xác hoặc chưa xác thực");
            url += "/dangnhap.jsp";
        }
        RequestDispatcher rd = getServletContext().getRequestDispatcher(url);
        try {
            rd.forward(request, response);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void dangXuat(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.invalidate();
        String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                + request.getContextPath();
        try {
            response.sendRedirect(url+"/index.jsp");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void dangKy(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tenDangNhap = request.getParameter("tenDangNhap");
        String matKhau = request.getParameter("matKhau");
        String matKhauNhapLai = request.getParameter("matKhauNhapLai");
        String hoVaTen = request.getParameter("hoVaTen");
        String gioiTinh = request.getParameter("gioiTinh");
        String ngaySinh = request.getParameter("ngaySinh");
        String diaChiKhachHang = request.getParameter("diaChiKhachHang");
        String diaChiMuaHang = request.getParameter("diaChiMuaHang");
        String diaChiNhanHang = request.getParameter("diaChiNhanHang");
        String dienThoai = request.getParameter("dienThoai");
        String email = request.getParameter("email");
        String dongYNhanMail = request.getParameter("dongYNhanMail");
        request.setAttribute("tenDangNhap", tenDangNhap);
        request.setAttribute("hoVaTen", hoVaTen);
        request.setAttribute("gioiTinh", gioiTinh);
        request.setAttribute("ngaySinh", ngaySinh);
        request.setAttribute("diaChiKhachHang", diaChiKhachHang);
        request.setAttribute("diaChiMuaHang", diaChiMuaHang);
        request.setAttribute("diaChiNhanHang", diaChiNhanHang);
        request.setAttribute("dienThoai", dienThoai);
        request.setAttribute("dongYNhanMail", dongYNhanMail);

        String baoLoi = "";
        KhachHangDAO khachHangDAO = new KhachHangDAO();
        String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                + request.getContextPath();
        if(khachHangDAO.kiemTraTenDangNhap(tenDangNhap)) {
            baoLoi +="Tên đăng nhập đã tồn tại, vui lòng chọn tên đăng nhập khác.<br/>";
        }

        if(!matKhau.equals(matKhauNhapLai)) {
            baoLoi +="Mẫu khẩu không khớp.<br/>";
        } else {
            matKhau = MaHoa.toSHA1(matKhau);
        }

        request.setAttribute("baoLoi", baoLoi);


        if(baoLoi.length()>0) {
            url += "/khachhang/dangky.jsp";
        }else {
            Random rd = new Random();
            String maKhachHang = System.currentTimeMillis() + rd.nextInt(1000) +"";
            KhachHang kh = new KhachHang(maKhachHang, tenDangNhap, matKhau, hoVaTen, gioiTinh, diaChiKhachHang, diaChiNhanHang, diaChiMuaHang, Date.valueOf(ngaySinh), dienThoai, email, dongYNhanMail!=null);
            if(khachHangDAO.insert(kh) > 0) {
                String maXacThuc = SoNgauNhien.getSoNgauNhien();

                Date todaysDate = new Date(new java.util.Date().getTime());
                Calendar c = Calendar.getInstance();
                c.setTime(todaysDate);
                c.add(Calendar.DATE, 1);
                Date thoiGianHieuLucCuaMaXacThuc = new Date(c.getTimeInMillis());

                // Trang thai xac thuc = false
                boolean trangThaiXacThuc = false;

                kh.setMaXacThuc(maXacThuc);
                kh.setThoiGianHieuLucCuaMaXacThuc(thoiGianHieuLucCuaMaXacThuc);
                kh.setTrangThaiXacThuc(trangThaiXacThuc);
                if(khachHangDAO.updateVerifyInformation(kh) > 0) {
                    String pathCon = "/khach-hang?hanhDong=xac-thuc&maKhachHang="+kh.getMaKhachHang()+"&maXacThuc="+kh.getMaXacThuc();
                    Email.sendEmail(kh.getEmail(), "Xác thực tài khoản tại DuyNguyenBui.vn", getNoiDung(kh, url+pathCon));
                }
            }
            url += "/khachhang/thanhcong.jsp";
        }
        RequestDispatcher rd = getServletContext().getRequestDispatcher(url);
        rd.forward(request, response);
    }

    private void doiMatKhau(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String matKhauHienTai = request.getParameter("matKhauHienTai");
        String matKhauMoi = request.getParameter("matKhauMoi");
        String matKhauMoiNhapLai = request.getParameter("matKhauMoiNhapLai");

        String matKhauHienTai_Sha1 = MaHoa.toSHA1(matKhauHienTai);

        String baoLoi = "";
        String url = "/khachhang/doimatkhau.jsp";

        // Kiem tra mat khau cu co giong hay khong
        HttpSession session = request.getSession();
        Object obj = session.getAttribute("khachHang");
        KhachHang khachHang = null;
        if (obj!=null)
            khachHang = (KhachHang)obj;
        if(khachHang==null) {
            baoLoi = "Bạn chưa đăng nhập vào hệ thống!";
        }else {
            // Neu khach hang da dang nhap
            if(!matKhauHienTai_Sha1.equals(khachHang.getMatKhau())){
                baoLoi = "Mật khẩu hiện tại không chính xác!";
            }else {
                if(!matKhauMoi.equals(matKhauMoiNhapLai)) {
                    baoLoi = "Mật khẩu nhập lại không khớp!";
                }else {
                    String matKhauMoi_Sha1 = MaHoa.toSHA1(matKhauMoi);
                    khachHang.setMatKhau(matKhauMoi_Sha1);
                    KhachHangDAO khd = new KhachHangDAO();
                    if(khd.changePassword(khachHang)) {
                        baoLoi = "Mật khẩu đã thay đổi thành công!";
                    }else {
                        baoLoi = "Quá trình thay đổi mật khẩu không thành công!";
                    }
                }
            }
        }

        request.setAttribute("baoLoi", baoLoi);
        RequestDispatcher rd = getServletContext().getRequestDispatcher(url);
        rd.forward(request, response);
    }

    private void thayDoiThongTin(HttpServletRequest request, HttpServletResponse response) throws  ServletException, IOException {
        String hoVaTen = request.getParameter("hoVaTen");
        String gioiTinh = request.getParameter("gioiTinh");
        String ngaySinh = request.getParameter("ngaySinh");
        String diaChiKhachHang = request.getParameter("diaChiKhachHang");
        String diaChiMuaHang = request.getParameter("diaChiMuaHang");
        String diaChiNhanHang = request.getParameter("diaChiNhanHang");
        String dienThoai = request.getParameter("dienThoai");
        String email = request.getParameter("email");
        String dongYNhanMail = request.getParameter("dongYNhanMail");
        request.setAttribute("hoVaTen", hoVaTen);
        request.setAttribute("gioiTinh", gioiTinh);
        request.setAttribute("ngaySinh", ngaySinh);
        request.setAttribute("diaChiKhachHang", diaChiKhachHang);
        request.setAttribute("diaChiMuaHang", diaChiMuaHang);
        request.setAttribute("diaChiNhanHang", diaChiNhanHang);
        request.setAttribute("dienThoai", dienThoai);
        request.setAttribute("dongYNhanMail", dongYNhanMail);


        String baoLoi = "";
        KhachHangDAO khachHangDAO = new KhachHangDAO();

        request.setAttribute("baoLoi", baoLoi);
        String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                + request.getContextPath();

        if(baoLoi.length()>0) {
            url += "/dangky.jsp";
        }else {
            Object obj = request.getSession().getAttribute("khachHang");
            KhachHang khachHang = null;
            if (obj!=null)
                khachHang = (KhachHang)obj;
            if(khachHang!=null){
                String maKhachHang = khachHang.getMaKhachHang();
                KhachHang kh = new KhachHang(maKhachHang, "", "", hoVaTen, gioiTinh, diaChiKhachHang, diaChiNhanHang, diaChiMuaHang, Date.valueOf(ngaySinh), dienThoai, email, dongYNhanMail!=null);
                khachHangDAO.updateInfo(kh);
                KhachHang kh2 = khachHangDAO.selectByID(kh);
                request.getSession().setAttribute("khachHang", kh2);
                url += "/thanhcong.jsp";
            }

        }
        RequestDispatcher rd = getServletContext().getRequestDispatcher(url);
        rd.forward(request, response);
    }


    private void xacThuc(HttpServletRequest request, HttpServletResponse response) {
        try {
            String maKhachHang = request.getParameter("maKhachHang");
            String maXacThuc = request.getParameter("maXacThuc");
            String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                    + request.getContextPath();
            KhachHangDAO khachHangDAO = new KhachHangDAO();

            KhachHang kh = new KhachHang();
            kh.setMaKhachHang(maKhachHang);
            KhachHang khachHang = khachHangDAO.selectByID(kh);

            String msg = "";
            if (khachHang != null) {
                // Kiem tra ma xac thuc co giong nhau hay khong? // Kiem tra xem ma xac thuc con hieu luc hay khong?
                if(khachHang.getMaXacThuc().equals(maXacThuc)) {
                    // Thanh Cong
                    khachHang.setTrangThaiXacThuc(true);
                    khachHangDAO.updateVerifyInformation(khachHang);
                    msg ="Xác thực thành công!";
                }else {
                    // That Bai
                    msg ="Xác thực không thành công!";
                }
            }else {
                msg ="Tài khoản không tồn tại!";
            }
            url += "/thongbao.jsp";
            request.setAttribute("baoLoi", msg);
            RequestDispatcher rd = getServletContext().getRequestDispatcher(url);
            rd.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static String getNoiDung(KhachHang kh, String link) {
        String noiDung = "<p>duynguyenbui.vn xin ch&agrave;o bạn <strong>"+kh.getHoVaTen()+"</strong>,</p>\r\n"
                + "<p>Vui l&ograve;ng x&aacute;c thực t&agrave;i khoản của bạn bằng c&aacute;ch nhập m&atilde; <strong>"+kh.getMaXacThuc()+"</strong>, hoặc click trực tiếp v&agrave;o đường link sau đ&acirc;y:</p>\r\n"
                + "<p><a href=\""+link+"\">"+link+"</a></p>\r\n"
                + "<p>Đ&acirc;y l&agrave; email tự động, vui l&ograve;ng kh&ocirc;ng phản hồi email n&agrave;y.</p>\r\n"
                + "<p>Tr&acirc;n trọng cảm ơn.</p>";
        return noiDung;
    }
}
