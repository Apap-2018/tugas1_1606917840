package com.apap.tugas1.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="jabatan")
public class JabatanModel {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private long id;

	@NotNull
	@Column(name="nama",nullable=false)
	private String nama;

	@NotNull
	@Column(name="deskripsi",nullable=false)
	private String deskripsi;
	
	@NotNull
	@Column(name="gaji_pokok",nullable=false)
	private double gajiPokok;
	
	@ManyToMany(mappedBy="jabatanList", fetch=FetchType.LAZY, cascade=CascadeType.PERSIST)
	@JsonIgnore
	private List<PegawaiModel> pegawaiList;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public String getDeskripsi() {
		return deskripsi;
	}

	public void setDeskripsi(String deskripsi) {
		this.deskripsi = deskripsi;
	}

	public double getGajiPokok() {
		return gajiPokok;
	}

	public void setGajiPokok(double gajiPokok) {
		this.gajiPokok = gajiPokok;
	}

	public List<PegawaiModel> getPegawaiList() {
		return pegawaiList;
	}

	public void setPegawaiList(List<PegawaiModel> pegawaiList) {
		this.pegawaiList = pegawaiList;
	}

	

	
	
	
}
