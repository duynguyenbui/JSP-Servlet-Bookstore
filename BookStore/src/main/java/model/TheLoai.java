package model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class TheLoai {
    @Id
    private String maTheLoai;
    private String tenTheLoai;

    @OneToMany(mappedBy = "theLoai", cascade = CascadeType.ALL)
    private List<SanPham> sanPhams = new ArrayList<>();
    public TheLoai() {
    }

    public TheLoai(String maTheLoai, String tenTheLoai) {
        this.maTheLoai = maTheLoai;
        this.tenTheLoai = tenTheLoai;
    }

    public String getMaTheLoai() {
        return maTheLoai;
    }

    public void setMaTheLoai(String maTheLoai) {
        this.maTheLoai = maTheLoai;
    }

    public String getTenTheLoai() {
        return tenTheLoai;
    }

    public void setTenTheLoai(String tenTheLoai) {
        this.tenTheLoai = tenTheLoai;
    }

    @Override
    public String toString() {
        return "TheLoai{" +
                "maTheLoai='" + maTheLoai + '\'' +
                ", tenTheLoai='" + tenTheLoai + '\'' +
                '}';
    }
}
