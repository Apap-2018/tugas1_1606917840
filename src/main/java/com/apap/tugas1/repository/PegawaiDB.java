package com.apap.tugas1.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;

@Repository
public interface PegawaiDB extends JpaRepository<PegawaiModel, Long>{
	List<PegawaiModel> findAll();
	PegawaiModel findByNip(String nip);
	List<PegawaiModel> findByIdInstansiOrderByTanggalLahirAsc(InstansiModel instansi);
	List<PegawaiModel> findByIdInstansiAndTanggalLahirAndTahunMasuk(InstansiModel instansi, Date tanggalLahir, String tahunMasuk);
//	List<PegawaiModel> findByIdJabatanAndIdInstansi(JabatanModel idJabatan, InstansiModel idInstansi);
}