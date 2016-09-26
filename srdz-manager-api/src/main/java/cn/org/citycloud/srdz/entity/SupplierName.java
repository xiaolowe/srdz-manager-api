package cn.org.citycloud.srdz.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * desc the file.
 *
 * @author demon
 * @Date 2016/6/7 15:37
 */
@Entity
@Table(name = "supplier")
public class SupplierName implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="supplier_id")
    private int supplierId;

    @Column(name = "supplier_name")
    private String supplierName;

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }
}
