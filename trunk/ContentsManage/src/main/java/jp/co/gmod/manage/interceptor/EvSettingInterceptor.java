package jp.co.gmod.manage.interceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import jp.co.gmod.manage.constant.ErrorMessageConstant;
import jp.co.gmod.manage.entity.Destination;
import jp.co.gmod.manage.service.ContentsManageService;
import jp.co.gmod.manage.service.TableInfoService;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.seasar.framework.aop.interceptors.AbstractInterceptor;
import org.seasar.struts.util.ActionMessagesUtil;

public class EvSettingInterceptor  extends AbstractInterceptor {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private final static int DEV_ID = 1;
	private final static int STG_ID = 2;
	private final static int PUB_ID = 3;

	private final static String STG_KEY = "stg";
	private final static String PUB_KEY = "pub";

	@Resource
	protected HttpSession session;
	@Resource
	protected HttpServletRequest request;
	@Resource
	protected ContentsManageService contentsManageService;
	@Resource
	protected TableInfoService tableInfoService;

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {

		String selectDestinationId = request.getParameter("selectDestinationId");
		String selectedEv = request.getParameter("selectedEv");
		if (selectDestinationId != null && selectedEv != null) {
			if (!setEvSetting(selectDestinationId, selectedEv)) {
				saveError(session, ErrorMessageConstant.NOTDBINFO);
				return "index?id=" + selectDestinationId + "&redirect=true";
			}
		}

		return invocation.proceed();
	}

	private boolean setEvSetting(String destinationId, String ev) {
		//DB環境設定
		Destination destinationInfo = contentsManageService.getDestinationByIdFullJoin(destinationId);
		request.setAttribute("content_sns_info", destinationInfo);
		return tableInfoService.setEvSetting(destinationInfo, getEvId(ev));
	}

	private int getEvId(String evKey) {
		if (STG_KEY.equals(evKey)) {
			return STG_ID;
		} else if (PUB_KEY.equals(evKey)) {
			return PUB_ID;
		} else {
			return DEV_ID;
		}
	}

	private void saveError(HttpSession session, String errorMsgKey) {
		ActionMessages errors = new ActionMessages();
		errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
				errorMsgKey,
				// メッセージリソースファイルに定義してあるキーです。
				new Object[] {}));
		ActionMessagesUtil.saveErrors(session, errors);
	}

}
