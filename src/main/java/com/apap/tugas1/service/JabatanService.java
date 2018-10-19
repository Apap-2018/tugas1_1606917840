package com.apap.tugas1.service;

import java.util.List;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;

public interface JabatanService {
	JabatanModel getDetailJabatanById(long id);
	void addJabatan(JabatanModel jabatan);
	void ubahJabatan(JabatanModel jabatanTemp);
	List<JabatanModel> getAllJabatan();
	void hapusJabatan(long id);
	void tambahPegawai(long idJabatan, PegawaiModel pegawai);
}
