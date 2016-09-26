package cn.org.citycloud.srdz.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;


/**
 * The persistent class for the region_info database table.
 * 
 */
@Entity
@Table(name="region_info")
@NamedQuery(name="RegionInfo.findAll", query="SELECT r FROM RegionInfo r")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class RegionInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="region_code")
	private int regionCode;

	@JsonIgnore
	@Column(name="region_level")
	private int regionLevel;

	@Column(name="region_name")
	private String regionName;

	public RegionInfo() {
	}

	public int getRegionCode() {
		return this.regionCode;
	}

	public void setRegionCode(int regionCode) {
		this.regionCode = regionCode;
	}

	public int getRegionLevel() {
		return this.regionLevel;
	}

	public void setRegionLevel(int regionLevel) {
		this.regionLevel = regionLevel;
	}

	public String getRegionName() {
		return this.regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

}