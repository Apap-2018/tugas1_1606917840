package com.apap.tugas1.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.repository.InstansiDB;
import com.apap.tugas1.repository.JabatanDB;
import com.apap.tugas1.repository.PegawaiDB;

@Service
public class PegawaiServiceImpl implements PegawaiService {

	@Autowired
	PegawaiDB pegawaiDB;
	
	@Autowired
	InstansiDB instansiDB;
	
	@Autowired
	JabatanDB jabatanDB;
	
	public PegawaiModel getPegawaiDetailByNip(String nip) {
		return pegawaiDB.findByNip(nip);
	}

	public void addPegawai(PegawaiModel pegawai) {
		pegawaiDB.save(pegawai);
		
	}

	public void ubahPegawai(PegawaiModel pegawaiTemp, PegawaiModel pegawaiLama) {
		pegawaiLama.setInstansi(pegawaiTemp.getInstansi());
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
		ProvinsiModel provinsi = pegawaiDB.findByNip(pegawai.getNip()).getInstansi().getProvinsi();
		double tunjangan = provinsi.getPresentase_tunjangan() / 100 * max;
		
		return max + tunjangan;
	}

	public List<PegawaiModel> getPegawaiOrderedAsc(InstansiModel instansi) {
		return pegawaiDB.findByInstansiOrderByTanggalLahirAsc(instansi);
	}

	public void setNipPegawai(PegawaiModel pegawai) {

		String nipTglLahir = "";
		
		Date tglLahir = pegawai.getTanggalLahir();
		String[] tanggalLahir = (String.valueOf(tglLahir).split("-"));
		for (int i = 0; i < tanggalLahir.length; i++) {
			nipTglLahir = tanggalLahir[i].substring(tanggalLahir[i].length()-2, tanggalLahir[i].length()) + nipTglLahir;
		}
		
		List<PegawaiModel> listPegawai = pegawaiDB.findByInstansiAndTanggalLahirAndTahunMasukOrderByNipAsc(pegawai.getInstansi(), pegawai.getTanggalLahir(), pegawai.getTahunMasuk());
		int nomorPegawaiTemp = 0;
		if (listPegawai.isEmpty()) {
			nomorPegawaiTemp = 1;
		} else {
			PegawaiModel lastPegawai = listPegawai.get(listPegawai.size()-1);
			nomorPegawaiTemp = Integer.valueOf(lastPegawai.getNip().substring(lastPegawai.getNip().length()-2)) + 1;
		}
		String nomorPegawai = (nomorPegawaiTemp < 10 ? "0" : "") + nomorPegawaiTemp;
		
		String nip = pegawai.getInstansi().getId() + nipTglLahir + pegawai.getTahunMasuk() + nomorPegawai;
		
		pegawai.setNip(nip);
	}

	public List<PegawaiModel> getPegawaiByProvinsiAndJabatanOrInstansiOrBoth(long idJabatan,
			long idInstansi) {
		
		if (idInstansi != 0) {
			List<PegawaiModel> listPegawaiByProvinsiAndInstansi = pegawaiDB.findByInstansi(instansiDB.findById(idInstansi));
			List<PegawaiModel> listPegawaiByProvinsiAndInstansiAndJabatan = new ArrayList<PegawaiModel>();
			if (idJabatan != 0) {
				JabatanModel jabatan = jabatanDB.findById(idJabatan);
				for (PegawaiModel pegawai : listPegawaiByProvinsiAndInstansi) {
					if (pegawai.getJabatanList().contains(jabatan))
						listPegawaiByProvinsiAndInstansiAndJabatan.add(pegawai);
				}
				return listPegawaiByProvinsiAndInstansiAndJabatan;
			}
			return listPegawaiByProvinsiAndInstansi;
		}
		else {
			String provinsi = String.valueOf(idInstansi).substring(0,2);
			JabatanModel jabatan = jabatanDB.findById(idJabatan);
			List<PegawaiModel> listPegawaiByProvinsi = getPegawaiByProvinsi(Long.valueOf(provinsi));
			List<PegawaiModel> listPegawaiByProvinsiAndJabatan = new ArrayList<PegawaiModel>();
			for (PegawaiModel pegawai : listPegawaiByProvinsi) {
				if (pegawai.getJabatanList().contains(jabatan))
					listPegawaiByProvinsiAndJabatan.add(pegawai);
			}
			return listPegawaiByProvinsiAndJabatan; 
		}
	}

	public List<PegawaiModel> getPegawaiByProvinsi(long idProvinsi) {
		List<PegawaiModel> listPegawai = new ArrayList<PegawaiModel>();
		String provinsi = String.valueOf(idProvinsi);
		String instansi2[] = {"01", "02", "03", "04", "05"};
		for (String instansi: instansi2) {
			long idInstansi = Long.valueOf(provinsi + instansi);
			listPegawai.addAll(pegawaiDB.findByInstansi(instansiDB.findById(idInstansi)));
		}
		return listPegawai;
	}
	
	public List<PegawaiModel> getPegawaiByJabatan(long idJabatan) {
		JabatanModel jabatan = jabatanDB.findById(idJabatan);
		List<JabatanModel> jabatanToList = new ArrayList<JabatanModel>();
		jabatanToList.add(jabatan);
		return pegawaiDB.findByJabatanList(jabatanToList);
	}
}
