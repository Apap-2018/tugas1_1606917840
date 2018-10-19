package com.apap.tugas1.service;


import java.util.List;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;

public interface PegawaiService {
	PegawaiModel getPegawaiDetailByNip(String nip);
	void addPegawai(PegawaiModel pegawai);
	void ubahPegawai(PegawaiModel pegawaiTemp, PegawaiModel pegawaiLama);
	double maxGaji(PegawaiModel pegawai);
	List<PegawaiModel> getPegawaiOrderedAsc(InstansiModel instansi);
	void setNipPegawai(PegawaiModel pegawai);
//	List<PegawaiModel> getPegawaiByIdJabatanAndIdInstansi(JabatanModel jabatan, InstansiModel instansi);
}
