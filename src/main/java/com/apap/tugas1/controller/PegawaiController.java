package com.apap.tugas1.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.service.InstansiService;
import com.apap.tugas1.service.JabatanService;
import com.apap.tugas1.service.PegawaiService;
import com.apap.tugas1.service.ProvinsiService;

@Controller
public class PegawaiController {
	
	@Autowired
	PegawaiService pegawaiService;
	
	@Autowired
	JabatanService jabatanService;
	
	@Autowired
	InstansiService instansiService;
	
	@Autowired
	ProvinsiService provinsiService;
	
	@RequestMapping("/")
	private String home(Model model) {
		model.addAttribute("listJabatan", jabatanService.getAllJabatan());
		model.addAttribute("listInstansi", instansiService.getAllInstansi());
		return "home";
	}
	
	@RequestMapping(value="/pegawai/tambah", method=RequestMethod.GET)
	private String add(Model model) {
		PegawaiModel pegawai = new PegawaiModel();
		pegawai.setJabatanList(new ArrayList<JabatanModel>());
		pegawai.getJabatanList().add(new JabatanModel());
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("listProvinsi", provinsiService.getAllProvinsi());
		model.addAttribute("listJabatan", jabatanService.getAllJabatan());
		model.addAttribute("listInstansi", instansiService.getAllInstansi());
		return "addPegawai";
	}
	
	@RequestMapping(value="/pegawai/tambah", method=RequestMethod.POST, params= {"addRow"})
	private String addRow(@ModelAttribute PegawaiModel pegawai, Model model) {
		pegawai.getJabatanList().add(new JabatanModel());
		ProvinsiModel provinsi = provinsiService.getProvinsi(pegawai.getInstansi().getProvinsi().getId());
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("listProvinsi", provinsiService.getAllProvinsi());
		model.addAttribute("listJabatan", jabatanService.getAllJabatan());
		model.addAttribute("listInstansi", instansiService.getInstansiByProvinsi(provinsi));
		return "addPegawai";
	}
	
	@RequestMapping(value="/pegawai/tambah", method=RequestMethod.POST)
	private String addPegawaiSubmit(@ModelAttribute PegawaiModel pegawai, Model model) {
		if (pegawai.getNama() != "" || pegawai.getTahunMasuk() != "" || String.valueOf(pegawai.getTanggalLahir()) != "" ||				
				pegawai.getTempatLahir() != "" || pegawai.getInstansi() != null || pegawai.getJabatanList() != null) {
			Set<JabatanModel> setJabatan = new HashSet<JabatanModel>(pegawai.getJabatanList());
			for (JabatanModel jabatan : setJabatan) {
				jabatanService.tambahPegawai(jabatan.getId(), pegawai);
			}
			if (pegawai.getNama() != null) {
				pegawaiService.setNipPegawai(pegawai);
				pegawaiService.addPegawai(pegawai);
				model.addAttribute("notif", "Pegawai dengan NIP " + pegawai.getNip() + " Berhasil Ditambahkan");
				return "notif";
			}
		}
		model.addAttribute("notif", "Terjadi error, mohon semua data dimasukkan");
		return "notif";
	}
	
	
	@RequestMapping(value="/pegawai", method=RequestMethod.GET)
	private String view(@RequestParam("nip") String nip, Model model) {
		PegawaiModel pegawai = pegawaiService.getPegawaiDetailByNip(nip);
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("maxGaji", pegawaiService.maxGaji(pegawai));
		return "view-pegawai";
	}
	
	@RequestMapping(value="/pegawai/termuda-tertua", method=RequestMethod.GET)
	private String viewTertuaTermuda(@RequestParam("instansi") long idInstansi, Model model) {
		List<PegawaiModel> listPegawaiAsc = pegawaiService.getPegawaiOrderedAsc(instansiService.getInstansiById(idInstansi));
		PegawaiModel pegawaiTertua = listPegawaiAsc.get(0);
		PegawaiModel pegawaiTermuda = listPegawaiAsc.get(listPegawaiAsc.size()-1);
		model.addAttribute("pegawaiTertua", pegawaiTertua);
		model.addAttribute("tertuaGaji", pegawaiService.maxGaji(pegawaiTertua));
		model.addAttribute("pegawaiTermuda", pegawaiTermuda);
		model.addAttribute("termudaGaji", pegawaiService.maxGaji(pegawaiTermuda));
		return "view-pegawai-termuda-tertua";
	}
	
	@RequestMapping(value="/pegawai/ubah", method=RequestMethod.GET)
	private String update(@RequestParam("nip") String nip, Model model) {
		PegawaiModel pegawaiLama = pegawaiService.getPegawaiDetailByNip(nip);
		PegawaiModel pegawaiTemp = pegawaiLama;
		ProvinsiModel provinsi = provinsiService.getProvinsi(pegawaiTemp.getInstansi().getProvinsi().getId());
		model.addAttribute("pegawaiTemp", pegawaiTemp);
		model.addAttribute("listProvinsi", provinsiService.getAllProvinsi());
		model.addAttribute("listJabatan", jabatanService.getAllJabatan());
		model.addAttribute("listInstansi", instansiService.getInstansiByProvinsi(provinsi));
		return "update-pegawai";
	}
	
	@RequestMapping(value="/pegawai/ubah", method=RequestMethod.POST)
	private String updatePegawaiSubmit(@RequestParam("nip") String nip, @ModelAttribute PegawaiModel pegawaiTemp, Model model) {
		PegawaiModel pegawaiLama = pegawaiService.getPegawaiDetailByNip(pegawaiTemp.getNip());
		if (pegawaiTemp.getNama() != "" || pegawaiTemp.getTahunMasuk() != "" || String.valueOf(pegawaiTemp.getTanggalLahir()) != "" ||				
				pegawaiTemp.getTempatLahir() != "" || pegawaiTemp.getInstansi() != null || pegawaiTemp.getJabatanList() != null) {
			if (!pegawaiTemp.getTahunMasuk().equals(pegawaiLama.getTahunMasuk()) || !pegawaiTemp.getTanggalLahir().equals(pegawaiLama.getTanggalLahir()) || !pegawaiTemp.getInstansi().equals(pegawaiLama.getInstansi()))
				pegawaiService.setNipPegawai(pegawaiTemp);
			pegawaiService.ubahPegawai(pegawaiTemp, pegawaiLama);
			model.addAttribute("notif", "Pegawai dengan NIP " + pegawaiLama.getNip() + " Berhasil Diubah");
			return "notif";
		}
		model.addAttribute("notif", "Terjadi error ketika update, mohon masukkan semua data");
		return "notif";
	}
	
	@RequestMapping(value="/pegawai/ubah", method=RequestMethod.POST, params= {"addRowUpdate"})
	private String addRowUpdate(@ModelAttribute PegawaiModel pegawaiTemp, Model model) {
		pegawaiTemp.getJabatanList().add(new JabatanModel());
		ProvinsiModel provinsi = provinsiService.getProvinsi(pegawaiTemp.getInstansi().getProvinsi().getId());
		model.addAttribute("pegawaiTemp", pegawaiTemp);
		model.addAttribute("listProvinsi", provinsiService.getAllProvinsi());
		model.addAttribute("listJabatan", jabatanService.getAllJabatan());
		model.addAttribute("listInstansi", instansiService.getInstansiByProvinsi(provinsi));
		return "update-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/cari", method = RequestMethod.GET)
	public String cariPegawai (@RequestParam(value="idProvinsi", required = false) Optional<Integer> idProvinsi, 
								@RequestParam(value="idInstansi", required = false) Optional<Long> idInstansi, 
								@RequestParam(value="idJabatan", required = false) Optional<Long> idJabatan, Model model) {
		
		List<PegawaiModel> queryResult = null;
		if (idProvinsi.isPresent()) {
			if (idInstansi.isPresent()) {	
				if (idJabatan.isPresent()) {
					queryResult = pegawaiService.getPegawaiByProvinsiAndJabatanOrInstansiOrBoth(idJabatan.get(), idInstansi.get());
				}
				else {
					queryResult = pegawaiService.getPegawaiByProvinsiAndJabatanOrInstansiOrBoth(0, idInstansi.get());
				}
			}
			else if (idJabatan.isPresent()) {
				queryResult = pegawaiService.getPegawaiByProvinsiAndJabatanOrInstansiOrBoth(idJabatan.get(), 0);
			}
			else {
				queryResult = pegawaiService.getPegawaiByProvinsi(idProvinsi.get());
			}
		} 
		else if (idJabatan.isPresent()){
			queryResult = pegawaiService.getPegawaiByJabatan(idJabatan.get());
		}
		if (idJabatan.isPresent()) model.addAttribute("jabatan", jabatanService.getDetailJabatanById(idJabatan.get()));
		if (idInstansi.isPresent()) model.addAttribute("instansi", instansiService.getInstansiById(idInstansi.get()));
		if (idProvinsi.isPresent()) model.addAttribute("provinsi", provinsiService.getProvinsi(idProvinsi.get()));
		model.addAttribute("listJabatan", jabatanService.getAllJabatan());
		model.addAttribute("listInstansi", instansiService.getAllInstansi());
		model.addAttribute("listProvinsi", provinsiService.getAllProvinsi());
		model.addAttribute("queryResult", queryResult);
		return "cariPegawai";
	}
	

}
