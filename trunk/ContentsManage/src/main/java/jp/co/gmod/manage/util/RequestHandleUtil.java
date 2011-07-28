package jp.co.gmod.manage.util;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.co.gmod.manage.entity.Column;

import org.seasar.framework.beans.util.BeanMap;

public class RequestHandleUtil {


	public static BeanMap requestToBeanMap(HttpServletRequest request, List<Column> columns) {

		BeanMap map = new BeanMap();

		for (Column column : columns) {
			String field = column.physical_name;
			String value = request.getParameter(field);
			map.put(field, value);
		}

		return map;

	}

}
