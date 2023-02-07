package kr.or.ddit.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.ddit.mapper.EmpMapper;
import kr.or.ddit.service.EmpService;
import kr.or.ddit.vo.EmpVO;

@Service
public class EmpServiceImpl implements EmpService {
	
	@Autowired
	EmpMapper empMapper;
	
	// 다음 직원번호를 가져옴
	@Override
	public String getEmpNum() {
		return this.empMapper.getEmpNum();
	}
	
	// 신규 직원 등록
	@Override
	public int createPost(EmpVO empVO) {
		return this.empMapper.createPost(empVO);
	}
	
	// 모든 직원 정보 가져오기
	@Override
	public List<EmpVO> getEmpAll(){
		return this.empMapper.getEmpAll();
	}
}