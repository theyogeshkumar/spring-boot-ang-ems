package com.ems.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="DepartmentDetails")
public class Department {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="DepartmentId")
	private int departmentId;
	@Column(name="DeprtmentName",nullable=false,length=40)
	private String deprtmentName;
	public int getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}
	public String getDeprtmentName() {
		return deprtmentName;
	}
	public void setDeprtmentName(String deprtmentName) {
		this.deprtmentName = deprtmentName;
	}	
}
