/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package jp.co.gmod.manage.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import jp.co.gmod.manage.constant.ErrorMessageConstant;
import jp.co.gmod.manage.dto.UserDto;
import jp.co.gmod.manage.entity.User;
import jp.co.gmod.manage.form.LoginForm;
import jp.co.gmod.manage.service.UserInfoService;
import jp.co.gmod.manage.util.LoginUtil;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.seasar.framework.beans.util.Beans;
import org.seasar.struts.annotation.ActionForm;
import org.seasar.struts.annotation.Execute;
import org.seasar.struts.util.ActionMessagesUtil;
import org.seasar.struts.util.RequestUtil;

public class IndexAction {


	@Resource
	protected UserInfoService userInfoService;
	@Resource
	protected HttpSession session;

	@ActionForm
	@Resource
	protected LoginForm loginForm;


	 @Execute(validator = false)
	 public String index() {
		 if (LoginUtil.isLogin(session)) {
			 return "/contentlist?redirect=true";
		 }
		 return "index.jsp";
	 }

	 @Execute(input = "index.jsp")
	 public String login() {

		 User user = userInfoService.login(loginForm.id, loginForm.password);

		 if (user == null) {
			 user = userInfoService.firstlogin(loginForm.id, loginForm.password);

			 if (user != null) {
				 saveError(ErrorMessageConstant.CHANGE_PWD);
				 return "passchange.jsp";
			 }

			 //エラーメッセージをセットします。
			 saveError(ErrorMessageConstant.LOGIN);
			 return "index.jsp";

		 } else {
			 UserDto dto = Beans.createAndCopy(UserDto.class, user).execute();
			 session.setAttribute("userInfo", dto);
		 }

	     return "/contentlist?redirect=true";
	 }

	 @Execute(input = "passchange.jsp")
	 public String passchange() {

		 if (!loginForm.password.equals(loginForm.repassword)) {
			//エラーメッセージをセットします。
			 saveError(ErrorMessageConstant.PASSWORD_CONFIRM);
			 return "passchange.jsp";
		 }
		 userInfoService.changePassword(loginForm.id, loginForm.password);

	     return "/?redirect=true";
	 }

	 @Execute(validator = false)
	 public String logout() {
		 session.invalidate();
		 return "index.jsp";
	 }

	 private void saveError(String errorMsgKey) {
		 ActionMessages errors = new ActionMessages();
		 errors.add(ActionMessages.GLOBAL_MESSAGE,
				 new ActionMessage(errorMsgKey,
				//メッセージリソースファイルに定義してあるキーです。
				new Object[] {}));
		 ActionMessagesUtil.addErrors(RequestUtil.getRequest(), errors);
		}


}
