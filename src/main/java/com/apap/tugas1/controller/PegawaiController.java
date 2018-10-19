package com.apap.tugas1.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugas1.model.InstansiModel;
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
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("listProvinsi", provinsiService.getAllProvinsi());
		model.addAttribute("listJabatan", jabatanService.getAllJabatan());
		model.addAttribute("listInstansi", instansiService.getAllInstansi());
		return "addPegawai";
	}
	
	@RequestMapping(value="/pegawai/tambah", method=RequestMethod.POST)
	private String addPegawaiSubmit(@ModelAttribute PegawaiModel pegawai, Model model) {
		for (JabatanModel jabatan : pegawai.getJabatanList()) {
			jabatanService.tambahPegawai(jabatan.getId(), pegawai);
		}
		pegawaiService.setNipPegawai(pegawai);
		pegawaiService.addPegawai(pegawai);
		model.addAttribute("notif", "Pegawai dengan NIP " + pegawai.getNip() + " Berhasil Ditambahkan");
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
	private String viewTertuaTermuda(@RequestParam("idInstansi") long idInstansi, Model model) {
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
		model.addAttribute("pegawaiTemp", pegawaiTemp);
		model.addAttribute("listProvinsi", provinsiService.getAllProvinsi());
		model.addAttribute("listJabatan", jabatanService.getAllJabatan());
		model.addAttribute("listInstansi", instansiService.getAllInstansi());
		return "update-pegawai";
	}
	
	@RequestMapping(value="/pegawai/ubah", method=RequestMethod.POST)
	private String updatePegawaiSubmit(@RequestParam("nip") String nip, @ModelAttribute PegawaiModel pegawaiTemp, Model model) {
		PegawaiModel pegawaiLama = pegawaiService.getPegawaiDetailByNip(pegawaiTemp.getNip());
		pegawaiService.setNipPegawai(pegawaiTemp);
		pegawaiService.ubahPegawai(pegawaiTemp, pegawaiLama);
		model.addAttribute("notif", "Pegawai berhasil diubah");
		return "notif";
	}
	
	@RequestMapping(value="/pegawai/ubah", method=RequestMethod.POST, params= {"addRowUpdate"})
	private String addRowUpdate(@ModelAttribute PegawaiModel pegawaiTemp, Model model) {
		pegawaiTemp.getJabatanList().add(new JabatanModel());
		model.addAttribute("pegawaiTemp", pegawaiTemp);
		model.addAttribute("listProvinsi", provinsiService.getAllProvinsi());
		model.addAttribute("listJabatan", jabatanService.getAllJabatan());
		model.addAttribute("listInstansi", instansiService.getAllInstansi());
		return "update-pegawai";
	}
	

	
}
