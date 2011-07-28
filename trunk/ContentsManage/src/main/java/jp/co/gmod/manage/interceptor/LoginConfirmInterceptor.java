package jp.co.gmod.manage.interceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import jp.co.gmod.manage.util.LoginUtil;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.seasar.framework.aop.interceptors.AbstractInterceptor;
import org.seasar.struts.annotation.Execute;
import org.seasar.struts.util.ActionMessagesUtil;
import org.seasar.struts.util.RequestUtil;

public class LoginConfirmInterceptor  extends AbstractInterceptor {
	private static final long serialVersionUID = 1L;

	@Resource
	protected HttpSession session;
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		// ここの条件がtrueであればログイン済みと判断
		// ここの条件がfalseであればログインページへ移動
		return (!isExecuteMethod(invocation) || isLoggedIn()) ? invocation
				.proceed() : "/index?redirect=true";
	}

	private boolean isExecuteMethod(MethodInvocation invocation) {
		return invocation.getMethod().isAnnotationPresent(Execute.class);
	}

	private boolean isLoggedIn() {

		if (!LoginUtil.isLogin(session)) {
			 //エラーメッセージをセットします。
			 ActionMessages errors = new ActionMessages();
			 errors.add(ActionMessages.GLOBAL_MESSAGE,
					 new ActionMessage("errors.auth",
					//メッセージリソースファイルに定義してあるキーです。
					new Object[] {}));
			 ActionMessagesUtil.addErrors(RequestUtil.getRequest(), errors);
			 return false;
		}


		return true;
	}


}
