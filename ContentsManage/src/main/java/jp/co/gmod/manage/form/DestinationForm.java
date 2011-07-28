package jp.co.gmod.manage.form;

import org.seasar.struts.annotation.Required;

public class DestinationForm {

	@Required
	public String contentid;

	public String contentname;

	@Required
	public String destinationName;
	public String devUrl;
	public String devId;
	public String devPassword;

	public String stgUrl;
	public String stgId;
	public String stgPassword;

	public String pubUrl;
	public String pubId;
	public String pubPassword;
}
