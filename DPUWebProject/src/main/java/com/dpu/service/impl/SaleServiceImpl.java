package com.dpu.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dpu.dao.SaleDao;
import com.dpu.entity.Sale;
import com.dpu.model.SaleReq;
import com.dpu.service.SaleService;

@Service
public class SaleServiceImpl implements SaleService {

	@Autowired
	private SaleDao saleDao;

	@Override
	public List<SaleReq> getAll() {
		List<Sale> saleList = saleDao.findAll();
		List<SaleReq> saleReqList = new ArrayList<SaleReq>();

		if (saleList != null && !saleList.isEmpty()) {
			for (Sale sale : saleList) {
				SaleReq saleReq = new SaleReq();
				saleReq.setName(sale.getName());
				saleReq.setSaleId(saleReq.getSaleId());
				saleReq.setStatus(sale.getStatus().getStatus());
				saleReqList.add(saleReq);
			}
		}
		return saleReqList;
	}

}
