package com.apap.tugas1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.repository.JabatanDB;

@Service
public class JabatanServiceImpl implements JabatanService {

	@Autowired
	JabatanDB jabatanDB;
	
	public JabatanModel getDetailJabatanById(long id) {
		return jabatanDB.findById(id);
	}
	
	public void addJabatan(JabatanModel jabatan) {
		jabatanDB.save(jabatan);
	}

	public void ubahJabatan(JabatanModel jabatanTemp) {
		JabatanModel jabatan = jabatanDB.findById(jabatanTemp.getId());
		jabatan.setNama(jabatanTemp.getNama());
		jabatan.setDeskripsi(jabatanTemp.getDeskripsi());
		jabatan.setGajiPokok(jabatanTemp.getGajiPokok());
		jabatanDB.save(jabatan);
	}

	public List<JabatanModel> getAllJabatan() {
		return jabatanDB.findAll();
	}

	public void hapusJabatan(long id) {
		jabatanDB.deleteById(id);
	}

	public void tambahPegawai(long idJabatan, PegawaiModel pegawai) {
		JabatanModel jabatan = getDetailJabatanById(idJabatan);
		jabatan.getPegawaiList().add(pegawai);
	}

}
