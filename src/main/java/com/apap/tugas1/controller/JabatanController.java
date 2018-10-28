package com.apap.tugas1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.service.JabatanService;

@Controller
public class JabatanController {
	
	@Autowired
	JabatanService jabatanService;
	
	@RequestMapping(value="/jabatan/tambah", method=RequestMethod.GET)
	private String add(Model model) {
		JabatanModel jabatan = new JabatanModel();
		model.addAttribute("jabatan", jabatan);
		return "addJabatan";
	}
	
	@RequestMapping(value="/jabatan/tambah", method=RequestMethod.POST)
	private String addJabatanSubmit(@ModelAttribute JabatanModel jabatan, Model model) {
		jabatanService.addJabatan(jabatan);
		model.addAttribute("notif", "Jabatan " + jabatan.getNama() + " berhasil ditambahkan");
		model.addAttribute("title", "Tambah Jabatan");
		return "notif";
	}
	
	@RequestMapping(value="/jabatan/view", method=RequestMethod.GET)
	private String view(@RequestParam("idJabatan") long idJabatan, Model model) {
		JabatanModel jabatan = jabatanService.getDetailJabatanById(idJabatan);
		model.addAttribute("jmlPegawai", jabatan.getPegawaiList().size());
		model.addAttribute("jabatan", jabatan);
		return "view-jabatan";
	}
	
	@RequestMapping(value="/jabatan/hapus", method=RequestMethod.POST)
	private String hapus(@ModelAttribute JabatanModel jabatan, Model model) {
		try {
			jabatanService.hapusJabatan(jabatan.getId());
			model.addAttribute("notif", "Jabatan " + " telah dihapus");
			model.addAttribute("title", "Hapus Jabatan");
			return "notif";
		} 
		catch(Exception e) {
			JabatanModel jabatanLama = jabatanService.getDetailJabatanById(jabatan.getId());
			model.addAttribute("notif", "Jabatan " + jabatanLama.getNama() + " tidak bisa dihapus karena masih ada pegawai");
			model.addAttribute("jabatan", jabatanLama);
			model.addAttribute("jmlPegawai", jabatanLama.getPegawaiList().size());
			return "view-jabatan";
		}
		
	}
	
	@RequestMapping(value="/jabatan/ubah", method=RequestMethod.GET)
	private String update(@RequestParam("idJabatan") long idJabatan, Model model) {
		JabatanModel jabatanTemp = new JabatanModel();
		JabatanModel jabatan = jabatanService.getDetailJabatanById(idJabatan);
		model.addAttribute("jabatanTemp", jabatanTemp);
		model.addAttribute("jabatan", jabatan);
		return "update-jabatan";
	}
	
	@RequestMapping(value="/jabatan/ubah", method=RequestMethod.POST)
	private String updateJabatanSubmit(@ModelAttribute JabatanModel jabatanTemp, Model model) {
		jabatanService.ubahJabatan(jabatanTemp);
		model.addAttribute("notif", "Jabatan berhasil diubah");
		model.addAttribute("title", "Ubah Jabatan");
		return "notif";
	}
	
	@RequestMapping(value="/jabatan/viewall", method=RequestMethod.GET)
	private String viewall(Model model) {
		model.addAttribute("jabatanList", jabatanService.getAllJabatan());
		return "viewall-jabatan";
	}
	
}
