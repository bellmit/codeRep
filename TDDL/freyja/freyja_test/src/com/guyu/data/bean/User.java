package com.guyu.data.bean;

import java.io.Serializable;

import org.freyja.jdbc.annotation.Async;
import org.freyja.jdbc.annotation.Column;
import org.freyja.jdbc.annotation.Id;
import org.freyja.jdbc.annotation.SubColumn;
import org.freyja.jdbc.annotation.Table;

@Table(name = "t_user", isSubTable = true)
@Async(saveAsync = false, updateAsync = false)
public class User implements Serializable {

	@Id
	@Column(name = "uid")
	private Integer id;

	@Column(name = "open_id")
	@SubColumn(isSubColumn = true)
	private String openId;

	@Column(name = "nickname")
	private String nickName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

}
