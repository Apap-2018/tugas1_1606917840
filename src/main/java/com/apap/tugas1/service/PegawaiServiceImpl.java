package com.apap.tugas1.service;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.repository.PegawaiDB;

@Service
public class PegawaiServiceImpl implements PegawaiService {

	@Autowired
	PegawaiDB pegawaiDB;
	
	public PegawaiModel getPegawaiDetailByNip(String nip) {
		return pegawaiDB.findByNip(nip);
	}

	public void addPegawai(PegawaiModel pegawai) {
		pegawaiDB.save(pegawai);
		
	}

	public void ubahPegawai(PegawaiModel pegawaiTemp, PegawaiModel pegawaiLama) {
		pegawaiLama.setIdInstansi(pegawaiTemp.getIdInstansi());
		pegawaiLama.setNama(pegawaiTemp.getNama());
		pegawaiLama.setNip(pegawaiTemp.getNip());
		pegawaiLama.setTahunMasuk(pegawaiTemp.getTahunMasuk());
		pegawaiLama.setTanggalLahir(pegawaiTemp.getTanggalLahir());
		pegawaiLama.setTempatLahir(pegawaiTemp.getTempatLahir());
		
		List<JabatanModel> listJabatanTemp = pegawaiTemp.getJabatanList();
		int counter = listJabatanTemp.size();
		for (int i = 0; i < counter; i++) {
			pegawaiLama.getJabatanList().set(i, pegawaiTemp.getJabatanList().get(i));
		}
		pegawaiDB.save(pegawaiLama);
	}

	public double maxGaji(PegawaiModel pegawai) {
		List<JabatanModel> jabatan2 = pegawai.getJabatanList();
		double max = 0;
		for (JabatanModel jabatan : jabatan2) {
			if (max < jabatan.getGajiPokok()) max = jabatan.getGajiPokok();
		}
		ProvinsiModel provinsi = pegawaiDB.findByNip(pegawai.getNip()).getIdInstansi().getIdProvinsi();
		double tunjangan = provinsi.getPresentase_tunjangan() / 100 * max;
		
		return max + tunjangan;
	}

	public List<PegawaiModel> getPegawaiOrderedAsc(InstansiModel instansi) {
		return pegawaiDB.findByIdInstansiOrderByTanggalLahirAsc(instansi);
	}

	public void setNipPegawai(PegawaiModel pegawai) {

		String nipTglLahir = "";
		
		Date tglLahir = pegawai.getTanggalLahir();
		String[] tanggalLahir = (""+tglLahir).split("-");
		for (int i = 0; i < tanggalLahir.length; i++) {
			nipTglLahir = tanggalLahir[i].substring(tanggalLahir[i].length()-2, tanggalLahir[i].length()) + nipTglLahir;
		}
		
		List<PegawaiModel> listPegawai = pegawaiDB.findByIdInstansiAndTanggalLahirAndTahunMasuk(pegawai.getIdInstansi(), pegawai.getTanggalLahir(), pegawai.getTahunMasuk());
		int nomorPegawaiTemp = listPegawai.size() + 1;
		String nomorPegawai = String.valueOf(nomorPegawaiTemp);
		if (nomorPegawaiTemp < 10) 
			nomorPegawai = (nomorPegawaiTemp < 10 ? "0" : "") + nomorPegawaiTemp;
		
		String nip = pegawai.getIdInstansi().getId() + nipTglLahir + pegawai.getTahunMasuk() + nomorPegawai; 
		pegawai.setNip(nip);
	}

//	public List<PegawaiModel> getPegawaiByIdJabatanAndIdInstansi(JabatanModel jabatan, InstansiModel instansi) {
//		return pegawaiDB.findByIdJabatanAndIdInstansi(jabatan, instansi);
//	}
	
	

}
