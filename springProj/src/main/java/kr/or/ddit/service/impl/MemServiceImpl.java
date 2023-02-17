package kr.or.ddit.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.ddit.mapper.MemMapper;
import kr.or.ddit.service.MemService;
import kr.or.ddit.vo.MemVO;

@Service
public class MemServiceImpl implements MemService {
	
	@Autowired
	MemMapper memMapper;

	@Override
	public MemVO memLogin(MemVO memVO) {
		return this.memMapper.memLogin(memVO);
	}
	
}