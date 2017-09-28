package up.light.web.entity;

import java.util.Date;

public class TestDO {
	private int id;
	private int pid;
	private boolean finish;
	private String name;
	private Date createTime;
	private UserDO creator;
	private int dsid;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public boolean isFinish() {
		return finish;
	}

	public void setFinish(boolean finish) {
		this.finish = finish;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public UserDO getCreator() {
		return creator;
	}

	public void setCreator(UserDO creator) {
		this.creator = creator;
	}

	public int getDsid() {
		return dsid;
	}

	public void setDsid(int dsid) {
		this.dsid = dsid;
	}

}
