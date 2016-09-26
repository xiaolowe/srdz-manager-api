package cn.org.citycloud.srdz.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * desc the file.
 *
 * @author demon
 * @Date 2016/6/3 16:35
 */
@Entity
@Table(name="supplier")
public class SupplierOther implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="supplier_id")
    private int supplierId;

    @Column(name="supplier_name")
    private String supplierName;

    private int status;

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
