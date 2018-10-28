package com.apap.tugas1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.repository.InstansiDB;

@Service
public class InstansiServiceImpl implements InstansiService {

	@Autowired
	InstansiDB instansiDB;
	
	public List<InstansiModel> getAllInstansi() {
		return instansiDB.findAll();
	}

	public InstansiModel getInstansiById(long id) {
		return instansiDB.findById(id);
	}

	@Override
	public List<InstansiModel> getInstansiByProvinsi(ProvinsiModel provinsi) {
		return instansiDB.findByProvinsi(provinsi);
	}

}
