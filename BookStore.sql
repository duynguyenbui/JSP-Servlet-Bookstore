CREATE DATABASE `bookstore` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;

-- bookstore.ChiTietDonHang definition

CREATE TABLE `ChiTietDonHang` (
	`maChiTietDonHang` varchar(255) NOT NULL,
	`giaBan` double NOT NULL,
	`giaGoc` double NOT NULL,
	`giamGia` double NOT NULL,
	`soLuong` double NOT NULL,
	`thueVAT` double NOT NULL,
	`tongTien` double NOT NULL,
	`donHang_maDonHang` varchar(255) DEFAULT NULL,
	`sanPham_maSanPham` varchar(255) DEFAULT NULL,
	PRIMARY KEY (`maChiTietDonHang`),
	KEY `FKkm9hbi6j33e30xoe6jwb4a9pd` (`donHang_maDonHang`),
	KEY `FKqba63phmqyxxvq27g2ehlm30d` (`sanPham_maSanPham`),
	CONSTRAINT `FKkm9hbi6j33e30xoe6jwb4a9pd` FOREIGN KEY (`donHang_maDonHang`) REFERENCES `DonHang` (`maDonHang`),
	CONSTRAINT `FKqba63phmqyxxvq27g2ehlm30d` FOREIGN KEY (`sanPham_maSanPham`) REFERENCES `SanPham` (`maSanPham`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- bookstore.DonHang definition

CREATE TABLE `DonHang` (
	`maDonHang` varchar(255) NOT NULL,
	`diaChiMuaHang` varchar(255) DEFAULT NULL,
	`diaChiNhanHang` varchar(255) DEFAULT NULL,
	`hinhThucThanhToan` varchar(255) DEFAULT NULL,
	`ngayDatHang` date DEFAULT NULL,
	`ngayGiaoHang` date DEFAULT NULL,
	`soTienConThieu` double NOT NULL,
	`soTienDaThanhToan` double NOT NULL,
	`trangThai` varchar(255) DEFAULT NULL,
	`trangThaiThanhToan` varchar(255) DEFAULT NULL,
	`khachHang_maKhachHang` varchar(255) DEFAULT NULL,
	PRIMARY KEY (`maDonHang`),
	KEY `FKoj1981mv9e776uemq9np7stsw` (`khachHang_maKhachHang`),
	CONSTRAINT `FKoj1981mv9e776uemq9np7stsw` FOREIGN KEY (`khachHang_maKhachHang`) REFERENCES `KhachHang` (`maKhachHang`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- bookstore.KhachHang definition

CREATE TABLE `KhachHang` (
	`maKhachHang` varchar(255) NOT NULL,
	`dangKyNhanBangTin` bit(1) NOT NULL,
	`diaChi` varchar(255) DEFAULT NULL,
	`diaChiMuaHang` varchar(255) DEFAULT NULL,
	`diaChiNhanHang` varchar(255) DEFAULT NULL,
	`email` varchar(255) DEFAULT NULL,
	`gioiTinh` varchar(255) DEFAULT NULL,
	`hoVaTen` varchar(255) DEFAULT NULL,
	`maXacThuc` varchar(255) DEFAULT NULL,
	`matKhau` varchar(255) DEFAULT NULL,
	`ngaySinh` date DEFAULT NULL,
	`soDienThoai` varchar(255) DEFAULT NULL,
	`tenDangNhap` varchar(255) DEFAULT NULL,
	`thoiGianHieuLucCuaMaXacThuc` date DEFAULT NULL,
	`trangThaiXacThuc` bit(1) DEFAULT NULL,
	PRIMARY KEY (`maKhachHang`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- bookstore.SanPham definition

CREATE TABLE `SanPham` (
	`maSanPham` varchar(255) NOT NULL,
	`giaBan` double NOT NULL,
	`giaGoc` double NOT NULL,
	`giaNhap` double NOT NULL,
	`moTa` varchar(255) DEFAULT NULL,
	`namXuatBan` int(11) NOT NULL,
	`ngonNgu` varchar(255) DEFAULT NULL,
	`soLuong` int(11) NOT NULL,
	`tenSanPham` varchar(255) DEFAULT NULL,
	`tacGia_maTacGia` varchar(255) DEFAULT NULL,
	`theLoai_maTheLoai` varchar(255) DEFAULT NULL,
	PRIMARY KEY (`maSanPham`),
	KEY `FKg736pf4teqkoqelim6fq1lxx7` (`tacGia_maTacGia`),
	KEY `FKcp21y3wai8t7ifx7u7x4r4tkj` (`theLoai_maTheLoai`),
	CONSTRAINT `FKcp21y3wai8t7ifx7u7x4r4tkj` FOREIGN KEY (`theLoai_maTheLoai`) REFERENCES `TheLoai` (`maTheLoai`),
	CONSTRAINT `FKg736pf4teqkoqelim6fq1lxx7` FOREIGN KEY (`tacGia_maTacGia`) REFERENCES `TacGia` (`maTacGia`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- bookstore.TacGia definition

CREATE TABLE `TacGia` (
	`maTacGia` varchar(255) NOT NULL,
	`hoVaTen` varchar(255) DEFAULT NULL,
	`ngaySinh` date DEFAULT NULL,
	`tieuSu` varchar(255) DEFAULT NULL,
	PRIMARY KEY (`maTacGia`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- bookstore.TheLoai definition

CREATE TABLE `TheLoai` (
	`maTheLoai` varchar(255) NOT NULL,
	`tenTheLoai` varchar(255) DEFAULT NULL,
	PRIMARY KEY (`maTheLoai`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;