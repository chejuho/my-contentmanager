package jp.co.gmod.manage.service;

import java.security.MessageDigest;

import javax.annotation.Resource;

import jp.co.gmod.manage.entity.User;

import org.seasar.extension.jdbc.JdbcManager;

public class UserInfoService {

	private JdbcManager jdbcManager;

	@Resource(name = "managerJdbcManager")
	public void setJdbcManager(JdbcManager jdbcManager) {
		this.jdbcManager = jdbcManager;
	}

	public User login(String id, String pwd) {
		User user = jdbcManager.from(User.class).where(
				"user_id = ? and password = ?", id, md5(pwd)).getSingleResult();

		return user;

	}

	public User firstlogin(String id, String pwd) {
		User user = jdbcManager.from(User.class).where(
				"user_id = ? and password = ?", id, pwd).getSingleResult();

		return user;

	}

	public boolean changePassword(String id, String pwd) {


		User user = jdbcManager.from(User.class).where(
				"user_id = ?", id).getSingleResult();

		user.password = md5(pwd);

		jdbcManager.update(user).execute();

		return true;

	}


	private String md5(String source) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] bytes = md.digest(source.getBytes("UTF-8"));
			return getString(bytes);
		} catch (Exception e) {
			return null;
		}
	}

	private String getString(byte[] bytes) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			byte b = bytes[i];
			String hex = Integer.toHexString((int) 0x00FF & b);
			if (hex.length() == 1) {
				sb.append("0");
			}
			sb.append(hex);
		}
		return sb.toString();
	}
}
