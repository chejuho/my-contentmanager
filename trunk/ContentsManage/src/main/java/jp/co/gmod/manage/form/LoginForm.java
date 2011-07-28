package jp.co.gmod.manage.form;

import org.seasar.struts.annotation.Minlength;
import org.seasar.struts.annotation.Required;

public class LoginForm {

	@Required
    public String id;

    @Required
    @Minlength(minlength = 4)
    public String password;

    public String repassword;

//    @Required
//    public String database;

    public void initialize() {
    	//database = "2";
    }
}
