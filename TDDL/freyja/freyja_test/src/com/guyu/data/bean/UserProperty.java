package com.guyu.data.bean;

import org.freyja.jdbc.annotation.Id;
import org.freyja.jdbc.annotation.SubColumn;
import org.freyja.jdbc.annotation.Table;
import org.freyja.jdbc.annotation.Transient;

@Table(name = "t_user_prop", isSubTable = false)
public class UserProperty {

	@Id
	private Integer id;

	@SubColumn(isSubColumn = true)
	private Integer uid;

	private Integer pid;

	private Integer num;

	@Transient
	private int version;

	public UserProperty() {
	}

	public UserProperty(int uid, int num, int pid) {

		this.uid = uid;
		this.num = num;
		this.pid = pid;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

}
