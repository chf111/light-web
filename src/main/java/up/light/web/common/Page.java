package up.light.web.common;

/**
 * 分页信息
 */
public class Page {
	// 页码，默认是第一页
	private int pageNo = 1;
	// 每页显示的记录数，默认是20
	private int pageSize = 20;
	// 总记录数
	private int totalRecord;
	// 总页数
	private int totalPage;

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo <= 0 ? 1 : pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize <= 0 ? 20 : pageSize;
	}

	public int getTotalRecord() {
		return totalRecord;
	}

	void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
		// 在设置总页数的时候计算出对应的总页数
		int totalPage = totalRecord % pageSize == 0 ? totalRecord / pageSize : totalRecord / pageSize + 1;
		this.setTotalPage(totalPage);
	}

	public int getTotalPage() {
		return totalPage;
	}

	void setTotalPage(int totalPage) {
		this.totalPage = totalPage <= 0 ? 1 : totalPage;
		if (this.totalPage < this.pageNo) {
			this.pageNo = this.totalPage;
		}
	}

	public int getStart() {
		return (pageNo - 1) * pageSize;
	}

	@Override
	public String toString() {
		return "Page [pageNo=" + pageNo + ", pageSize=" + pageSize + ", totalRecord=" + totalRecord + ", totalPage="
				+ totalPage + "]";
	}

}
