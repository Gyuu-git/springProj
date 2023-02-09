package kr.or.ddit.service.impl;

import java.util.List;
import java.util.Map;

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
	
	// 직원 상세 보기(관리자가 있으면 관리자 정보도 포함)
	@Override
	public List<EmpVO> detail(EmpVO empVO){
		return this.empMapper.detail(empVO);
	}

	// 직원 삭제
	@Override
	public int deletePost(EmpVO empVO) {
		return this.empMapper.deletePost(empVO);
	}
	
	// 직원 목록
	@Override
	public List<EmpVO> list(Map<String, String> map){
		return this.empMapper.list(map);
	}
	
	// 매니저 상세
	@Override
	public EmpVO showMj(EmpVO empVO) {
		return this.empMapper.showMj(empVO);
	}

	// 총 직원 수
	@Override
	public int getTotal(Map<String, String> map) {
		return this.empMapper.getTotal(map);
	}
}