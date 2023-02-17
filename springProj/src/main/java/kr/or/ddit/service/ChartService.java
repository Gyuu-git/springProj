package kr.or.ddit.service;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;

import kr.or.ddit.vo.AttachVO;
import kr.or.ddit.vo.LprodVO;

// 서비스 interface : 비즈니스 로직(비즈니스 레이어)
public interface ChartService {
	// 상품별 판매금액의 합계가 천만원이 넘는 데이터
	public JSONObject cartMoney();

	// 회원별 구매회수 구하기
	public JSONObject memberMoney();
	
}
