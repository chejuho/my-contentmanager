package jp.co.gmod.manage.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.seasar.struts.annotation.Execute;

public class ErrorAction {
	@Resource
	protected HttpServletResponse response;


	@Execute(validator = false)
	public String index() {

		return "error.jsp";

	}
}
