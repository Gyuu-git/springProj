package kr.or.ddit.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CusDao {

	@Autowired
	SqlSessionTemplate sqlSessionTemplate;

	public String getCusNum() {
		return sqlSessionTemplate.selectOne("cus.getCusNum");
	}
}
