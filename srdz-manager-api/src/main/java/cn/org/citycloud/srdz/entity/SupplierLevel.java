package cn.org.citycloud.srdz.entity;

import cn.org.citycloud.srdz.annotation.FieldName;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;


/**
 * The persistent class for the supplier_level database table.
 * 
 */
@Entity
@Table(name="supplier_level")
@NamedQuery(name="SupplierLevel.findAll", query="SELECT s FROM SupplierLevel s")
public class SupplierLevel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="supplier_level_id")
	private int supplierLevelId;

	@FieldName(name = "平台分成比例")
	@Column(name="platform_rates")
	private BigDecimal platformRates;

	@FieldName(name = "等级名称")
	@Column(name="supplier_level_name")
	private String supplierLevelName;

	public int getSupplierLevelId() {
		return this.supplierLevelId;
	}

	public void setSupplierLevelId(int supplierLevelId) {
		this.supplierLevelId = supplierLevelId;
	}

	public BigDecimal getPlatformRates() {
		return this.platformRates;
	}

	public void setPlatformRates(BigDecimal platformRates) {
		this.platformRates = platformRates;
	}

	public String getSupplierLevelName() {
		return this.supplierLevelName;
	}

	public void setSupplierLevelName(String supplierLevelName) {
		this.supplierLevelName = supplierLevelName;
	}
}