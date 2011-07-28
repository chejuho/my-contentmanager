package jp.co.gmod.manage.dto;

import java.io.Serializable;


public class UserDto implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String user_id;

	private String password;

	private String username;


	/**
	 * user_idを取得します。
	 * @return user_id
	 */
	public String getUser_id() {
	    return user_id;
	}

	/**
	 * user_idを設定します。
	 * @param user_id user_id
	 */
	public void setUser_id(String user_id) {
	    this.user_id = user_id;
	}

	/**
	 * passwordを取得します。
	 * @return password
	 */
	public String getPassword() {
	    return password;
	}

	/**
	 * passwordを設定します。
	 * @param password password
	 */
	public void setPassword(String password) {
	    this.password = password;
	}

	/**
	 * usernameを取得します。
	 * @return username
	 */
	public String getUsername() {
	    return username;
	}

	/**
	 * usernameを設定します。
	 * @param username username
	 */
	public void setUsername(String username) {
	    this.username = username;
	}
}
