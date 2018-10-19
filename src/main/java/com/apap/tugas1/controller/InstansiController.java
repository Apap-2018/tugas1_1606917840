package com.apap.tugas1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.service.InstansiService;
import com.apap.tugas1.service.ProvinsiService;

@Controller
public class InstansiController {
	@Autowired
	InstansiService instansiService;
	
	@Autowired
	ProvinsiService provinsiService;
	
	@RequestMapping(value="/instansi/listIntansiFromProvinsi", method=RequestMethod.GET)
	@ResponseBody
	private List<InstansiModel> getInstansiFromProvinsi(@RequestParam("idProvinsi") int idProvinsi, Model model) {
		ProvinsiModel provinsi = provinsiService.getProvinsi(idProvinsi);
		List<InstansiModel> listInstansi = instansiService.getInstansiByProvinsi(provinsi);
		return listInstansi;
	}
}
