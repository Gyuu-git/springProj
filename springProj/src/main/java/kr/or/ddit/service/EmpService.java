package kr.or.ddit.service;

import java.util.List;
import java.util.Map;

import kr.or.ddit.vo.EmpVO;

public interface EmpService {
	// 다음 직원번호를 가져옴
	public String getEmpNum();
	
	// 신규 직원 등록
	public int createPost(EmpVO empVO);
	
	// 모든 직원 정보 가져오기
	public List<EmpVO> getEmpAll();
	
	// 직원 상세 보기(관리자가 있으면 관리자 정보도 포함)
	public List<EmpVO> detail(EmpVO empVO);

	// 직원 삭제
	public int deletePost(EmpVO empVO);

	// 직원 목록 / 검색
	public List<EmpVO> list(Map<String, String> map);

	// 매니저 상세
	public EmpVO showMj(EmpVO empVO);

	// 총 직원 수
	public int getTotal(Map<String, String> map);
}
