package jp.co.gmod.manage.util;

import javax.servlet.http.HttpSession;

import jp.co.gmod.manage.dto.UserDto;

public class LoginUtil {

	public static boolean isLogin(HttpSession session) {


		UserDto userDto = (UserDto) session.getAttribute("userInfo");

		if (userDto == null) {
			return false;
		}
		return true;

	}
}
