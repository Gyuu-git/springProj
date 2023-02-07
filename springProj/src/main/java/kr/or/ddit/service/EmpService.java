package kr.or.ddit.service;

import java.util.List;

import kr.or.ddit.vo.EmpVO;

public interface EmpService {
	// 다음 직원번호를 가져옴
	public String getEmpNum();
	
	// 신규 직원 등록
	public int createPost(EmpVO empVO);
	
	// 모든 직원 정보 가져오기
	public List<EmpVO> getEmpAll();
}
