package kr.or.ddit.vo;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;


// 자바빈 클래스
// 1)멤버변수  2)기본생성자  3)getter/setter메소드
public class BookVO {
	// 멤버변수
	private int rnum;
	private int bookId;
	private String title;
	private String category;
	private int price;
	private Date insertDate;
	private String content;
	// <textarea name="cont" rows="5" cols="30"></textarea>
	private String cont;
	// <input type="file" id="input_imgs" name="uploadfile" multiple />
	private MultipartFile[] uploadfile;
	// BookVO : AttachVO = 1 : N
	private List<AttachVO> attachVOList;

	// 기본생성자. 생략가능
	public BookVO() {}
	
	// getter/setter 메서드
	public int getRnum() {
		return rnum;
	}
	public void setRnum(int rnum) {
		this.rnum = rnum;
	}
	public int getBookId() {
		return bookId;
	}
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public Date getInsertDate() {
		return insertDate;
	}
	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCont() {
		return cont;
	}
	public void setCont(String cont) {
		this.cont = cont;
		this.content = cont;
	}
	public MultipartFile[] getUploadfile() {
		return this.uploadfile;
	}
	public void setUploadfile(MultipartFile[] uploadfile) {
		this.uploadfile = uploadfile;
	}
	public List<AttachVO> getAttachVOList() {
		return attachVOList;
	}
	public void setAttachVOList(List<AttachVO> attachVOList) {
		this.attachVOList = attachVOList;
	}

	@Override
	public String toString() {
		return "BookVO [rnum=" + rnum + ", bookId=" + bookId + ", title=" + title + ", category=" + category
				+ ", price=" + price + ", insertDate=" + insertDate + ", content=" + content + ", cont=" + cont
				+ ", uploadfile=" + Arrays.toString(uploadfile) + ", attachVOList=" + attachVOList + "]";
	}
}
