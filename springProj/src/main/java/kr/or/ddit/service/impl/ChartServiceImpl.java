package kr.or.ddit.service.impl;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.ddit.dao.ChartDao;
import kr.or.ddit.service.ChartService;
import lombok.extern.slf4j.Slf4j;

// 서비스 클래스 : 비즈니스 로직
// 스프링 MVC 구조에서 Controller와 DAO를 연결하는 역할
/*
 스프링 프레임워크(디자인패턴 + 라이브러리)는 직접 클래스를 생성하는 것을 지양하고,
 인터페이스를 통해 접근하는 것을 권장하고 있기 때문(확장성)
 * 프링이는 인터페이스를 좋아해.
 그래서 서비스 레이어는 인터페이스(BookService)와 클래스(BookServiceImpl)를 함께 사용함
 
 Impl : implement의 약자
*/
// Service 어노테이션 : "프링아 이 클래스는 서비스 클래스야"라고 알려줌.
//			프링이가 자바빈으로 등록해줌
@Slf4j
@Service
public class ChartServiceImpl implements ChartService {
	
	@Autowired
	ChartDao chartDao;

	// 상품별 판매금액의 합계가 천만원이 넘는 데이터
	@Override
	public JSONObject cartMoney(){
		List<Map<String, Object>> list = this.chartDao.cartMoney();

		// sampleData.json 파일 참고
		// 0. 리턴할 json객체--------------------------
		JSONObject data = new JSONObject(); // {}

		// 1.cols 배열에 넣기
		// JSON 컬럼 객체---------------------------------------------
		JSONObject col1 = new JSONObject();
		JSONObject col2 = new JSONObject();
		// JSON 배열 객체
		JSONArray title = new JSONArray();
		col1.put("label", "상품명");
		col1.put("type", "string");
		col2.put("label", "금액");
		col2.put("type", "number");
		// 타이틀행에 컬럼 추가
		title.add(col1);
		title.add(col2);

		// json객체에 타이틀행 추가
		data.put("cols", title);
		// {"cols":[{"label":"상품명","type":"string"},{"label":"금액","type":"number"}]}

		// 2.rows 배열에 넣기
		JSONArray body = new JSONArray(); // rows
		for (Map<String, Object> map : list) {
			JSONObject prodName = new JSONObject();
			prodName.put("v", map.get("PRODNAME")); // 상품명

			JSONObject money = new JSONObject();
			money.put("v", map.get("MONEY")); // 금액

			JSONArray row = new JSONArray();
			row.add(prodName);
			row.add(money);

			JSONObject cell = new JSONObject();
			cell.put("c", row);
			body.add(cell); // 레코드 1개 추가
		}

		data.put("rows", body);

		return data;
	}
	
	// 회원별 구매회수 구하기
	@Override
	public JSONObject memberMoney(){
		List<Map<String, Object>> list = this.chartDao.memberMoney();
		
		// sampleData.json 파일 참고
		// 0. 리턴할 json객체--------------------------
		JSONObject data = new JSONObject(); // {}
		
		// 1.cols 배열에 넣기
		// JSON 컬럼 객체---------------------------------------------
		JSONObject col1 = new JSONObject();
		JSONObject col2 = new JSONObject();
		// JSON 배열 객체
		JSONArray title = new JSONArray();
		col1.put("label", "회원");
		col1.put("type", "string");
		col2.put("label", "구매회수");
		col2.put("type", "number");
		// 타이틀행에 컬럼 추가
		title.add(col1);
		title.add(col2);
		
		// json객체에 타이틀행 추가
		data.put("cols", title);
		// {"cols":[{"label":"상품명","type":"string"},{"label":"금액","type":"number"}]}
		
		// 2.rows 배열에 넣기
		JSONArray body = new JSONArray(); // rows
		for (Map<String, Object> map : list) {
			JSONObject prodName = new JSONObject();
			prodName.put("v", map.get("MEMID")); // 상품명
			
			JSONObject money = new JSONObject();
			money.put("v", map.get("CARTCNT")); // 금액
			
			JSONArray row = new JSONArray();
			row.add(prodName);
			row.add(money);
			
			JSONObject cell = new JSONObject();
			cell.put("c", row);
			body.add(cell); // 레코드 1개 추가
		}
		
		data.put("rows", body);
		
		return data;
	}
}
