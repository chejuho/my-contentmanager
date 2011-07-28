package jp.co.gmod.manage.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.seasar.struts.annotation.Execute;

public class CommonAction {

	public String fieldname;

	@Resource
	protected HttpServletRequest request;

	@Execute(validator = false)
	public String index() {

		fieldname = request.getParameter("filed");

		return "calendar.jsp";
	}



}
