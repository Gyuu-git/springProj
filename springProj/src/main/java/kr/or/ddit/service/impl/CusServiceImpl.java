package kr.or.ddit.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.ddit.dao.CusDao;
import kr.or.ddit.service.CusService;

@Service
public class CusServiceImpl implements CusService {
	
	@Autowired
	CusDao cusDao;

	@Override
	public String makeCusNum() {
		return cusDao.makeCusNum();
	}

}
