package kr.or.ddit.vo;

import java.util.Date;

import lombok.Data;

// 아몰랑
// Pojo에 위반..
@Data
public class AttachVO {
	private int seq;
	private String filename;
	private int filesize;
	private String thumbnail;
	private Date regdate;
	// 전사적 아이디
	private String etpId;
}
