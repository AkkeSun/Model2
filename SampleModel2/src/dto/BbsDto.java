package dto;

import java.io.Serializable;

public class BbsDto implements Serializable{
	private int seq;
	private String id;       //작성자
	
	private int ref;         // 그룹번호
	private int step;        // 행번호
	private int depth;       // 댓글 깊이 
	
	private String title;    // 제목
	private String contents; // 내용
	private	String wdate;	 // 작성날짜
	private int del;         // 삭제
	private int readcount;   // 조회수
	
	
	
	public BbsDto(int seq, String id, int ref, int step, int depth, String title,
				   String contents, String wdate, int del, int readcount) {
		this.seq = seq;
		this.id = id;
		this.ref = ref;
		this.step = step;
		this.depth = depth;
		this.title = title;
		this.contents = contents;
		this.wdate = wdate;
		this.del = del;
		this.readcount = readcount;
	}

	//유저가 입력받는 용도 
	public BbsDto(String id, String title, String contents) {
		this.id = id;
		this.title = title;
		this.contents = contents;
	}

	
	
	
	
	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getRef() {
		return ref;
	}

	public void setRef(int ref) {
		this.ref = ref;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getWdate() {
		return wdate;
	}

	public void setWdate(String wdate) {
		this.wdate = wdate;
	}

	public int getDel() {
		return del;
	}

	public void setDel(int del) {
		this.del = del;
	}

	public int getReadcount() {
		return readcount;
	}

	public void setReadcount(int readcount) {
		this.readcount = readcount;
	}
	
	
	
	
	
	
	
	
	
}
