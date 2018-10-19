package com.apap.tugas1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.repository.ProvinsiDB;

@Service
public class ProvinsiServiceImpl implements ProvinsiService {
	
	@Autowired
	ProvinsiDB provinsiDB;
	
	public List<ProvinsiModel> getAllProvinsi() {
		return provinsiDB.findAll();
	}

	@Override
	public ProvinsiModel getProvinsi(int id) {
		return provinsiDB.findById(id);
	}

}
