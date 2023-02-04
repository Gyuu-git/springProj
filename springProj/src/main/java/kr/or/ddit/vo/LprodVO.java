package kr.or.ddit.vo;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class LprodVO {
	private int rnum;
	private int lprodId;
	private String lprodGu;
	private String lprodNm;
	private MultipartFile[] uploadFile;		// <input type="file" name="uploadFile" multiple />
	// 상품분류 : 첨부파일 = 1 : N
	private List<AttachVO> attachVOList;
	
	public LprodVO() {}

	public int getRnum() {
		return rnum;
	}
	public void setRnum(int rnum) {
		this.rnum = rnum;
	}
	public int getLprodId() {
		return lprodId;
	}
	public void setLprodId(int lprodId) {
		this.lprodId = lprodId;
	}
	public String getLprodGu() {
		return lprodGu;
	}
	public void setLprodGu(String lprodGu) {
		this.lprodGu = lprodGu;
	}
	public String getLprodNm() {
		return lprodNm;
	}
	public void setLprodNm(String lprodNm) {
		this.lprodNm = lprodNm;
	}
	public MultipartFile[] getUploadFile() {
		return uploadFile;
	}
	public void setUploadFile(MultipartFile[] uploadFile) {
		this.uploadFile = uploadFile;
	}
	public List<AttachVO> getAttachVOList() {
		return attachVOList;
	}
	public void setAttachVOList(List<AttachVO> attachVOList) {
		this.attachVOList = attachVOList;
	}

	@Override
	public String toString() {
		return "LprodVO [lprodId=" + lprodId + ", lprodGu=" + lprodGu + ", lprodNm=" + lprodNm + ", uploadFile="
				+ Arrays.toString(uploadFile) + ", attachVOList=" + attachVOList + "]";
	}
}
