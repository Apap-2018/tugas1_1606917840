package com.apap.tugas1.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="instansi")
public class InstansiModel {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private long id;

	@NotNull
	@Column(name="nama",nullable=false)
	private String nama;

	@NotNull
	@Column(name="deskripsi",nullable=false)
	private String deskripsi;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_provinsi", referencedColumnName="id", nullable=false)
	@OnDelete(action=OnDeleteAction.CASCADE)
	@JsonIgnore
	private ProvinsiModel idProvinsi;
	
	@OneToMany(mappedBy="idInstansi", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private List<PegawaiModel> listPegawai;

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

	public ProvinsiModel getIdProvinsi() {
		return idProvinsi;
	}

	public void setIdProvinsi(ProvinsiModel idProvinsi) {
		this.idProvinsi = idProvinsi;
	}

	
	
	
}
