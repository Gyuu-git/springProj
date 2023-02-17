package kr.or.ddit.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.or.ddit.vo.AttachVO;
import kr.or.ddit.vo.LprodVO;
import lombok.extern.slf4j.Slf4j;

/*
 매퍼XML(book_SQL.xml)을 실행시키는 DAO(Data Access Object) 클래스
 Repository 어노테이션 : 데이터에 접근하는 클래스
 => 스프링이 데이터를 관리하는 클래스라고 인지하여 자바빈 객체로 등록하여 관리함
*/
@Slf4j
@Repository
public class ChartDao {
	@Autowired
	SqlSessionTemplate sqlSessionTemplate;
	
	// 상품별 판매금액의 합계가 천만원이 넘는 데이터
	//<select id="cartMoney" resultType="hashMap">
	public List<Map<String, Object>> cartMoney(){
		return this.sqlSessionTemplate.selectList("chart.cartMoney");
	}
	
	// 회원별 구매회수 구하기
	public List<Map<String, Object>> memberMoney(){
		return this.sqlSessionTemplate.selectList("chart.memberMoney");
	}
}
