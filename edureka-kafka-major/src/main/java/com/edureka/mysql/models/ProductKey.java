package com.edureka.mysql.models;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class ProductKey implements Serializable {

	private static final long serialVersionUID = -4272600205578165796L;

	private long pogId;

	private String supc;

	public long getPogId() {
		return pogId;
	}

	public void setPogId(long pogId) {
		this.pogId = pogId;
	}

	public String getSupc() {
		return supc;
	}

	public void setSupc(String supc) {
		this.supc = supc;
	}

	public ProductKey() {

	}

	public ProductKey(long pogId, String supc) {
		super();
		this.pogId = pogId;
		this.supc = supc;
	}

}
