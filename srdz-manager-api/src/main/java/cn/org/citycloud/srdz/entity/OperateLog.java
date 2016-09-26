package cn.org.citycloud.srdz.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * The persistent class for the operate_log database table.
 * 
 */
@Entity
@Table(name="operate_log")
@NamedQuery(name="OperateLog.findAll", query="SELECT o FROM OperateLog o")
public class OperateLog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "operate_log_id")
	private int operateLogId;

	@Column(name="bus_code")
	private int busCode;

	@Column(name="bus_id")
	private int busId;

	private String operator;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	private String operation;

	public OperateLog() {
	}

	public int getOperateLogId() {
		return operateLogId;
	}

	public void setOperateLogId(int operateLogId) {
		this.operateLogId = operateLogId;
	}

	public int getBusCode() {
		return this.busCode;
	}

	public void setBusCode(int busCode) {
		this.busCode = busCode;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getOperation() {
		return this.operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public int getBusId() {
		return busId;
	}

	public void setBusId(int busId) {
		this.busId = busId;
	}
}