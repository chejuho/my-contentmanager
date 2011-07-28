package jp.co.gmod.manage.exception;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ExceptionHandler;
import org.apache.struts.config.ExceptionConfig;
import org.seasar.framework.log.Logger;
import org.seasar.struts.config.S2ActionMapping;

public class DBExceptionHandler extends ExceptionHandler {

	private Logger logger = Logger.getLogger(getClass());


	 @Override
	  public ActionForward execute(Exception ex,
	      ExceptionConfig ae,
	      ActionMapping mapping,
	      ActionForm formInstance,
	      HttpServletRequest request,
	      HttpServletResponse response) throws ServletException {


	    super.execute(ex, ae, mapping, formInstance, request, response);
	    logger.error(ex.getMessage());
	    return (ae.getPath() != null ? ((S2ActionMapping) mapping).createForward(ae.getPath()) : mapping.getInputForward());
	 }
}
