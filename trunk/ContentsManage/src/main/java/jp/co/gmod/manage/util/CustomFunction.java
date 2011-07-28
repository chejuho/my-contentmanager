package jp.co.gmod.manage.util;

import org.seasar.framework.util.StringUtil;
import org.seasar.struts.action.BeanWrapper;
import org.seasar.struts.action.ListWrapper;
import org.seasar.struts.action.MapWrapper;

public class CustomFunction {


	public final static String KBN_MOZI = "#";
	public final static String KBN_EQUAL = "=";

	public static final String changeRnToN(String str) {

		if (str != null) {
			str = str.replaceAll("\r\n", "%0D%0A");
		}
		return str;

	}

	public static final String makePrimaryKeyList(MapWrapper beans, ListWrapper columnList) {

		StringBuilder sb = new StringBuilder();

		//sb.append(b);
		int size = columnList.size();
		for (int i = 0; i < size; i++) {
			BeanWrapper column = (BeanWrapper) columnList.get(i);
			Integer sign = (Integer) column.get("primary_key_sign");
			if (sign.intValue() == 1) {

				String physicalName = StringUtil.decapitalize(StringUtil.camelize((String) column.get("physical_name")));
				sb.append(beans.get(physicalName));
				sb.append(KBN_MOZI);
			}
		}

		return sb.toString();
	}
}
