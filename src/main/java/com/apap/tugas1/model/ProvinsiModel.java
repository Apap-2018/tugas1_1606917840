package com.apap.tugas1.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="provinsi")
public class ProvinsiModel {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;

	@NotNull
	@Column(name="nama",nullable=false)
	private String nama;

	@NotNull
	@Column(name="presentase_tunjangan",nullable=false)
	private double presentase_tunjangan;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public double getPresentase_tunjangan() {
		return presentase_tunjangan;
	}

	public void setPresentase_tunjangan(double presentase_tunjangan) {
		this.presentase_tunjangan = presentase_tunjangan;
	}
	
	
}
