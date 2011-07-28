package jp.co.gmod.manage.bean;

import java.util.HashMap;
import java.util.Map;

public class ColumnGroupsBean {

	private Map<String, ColumnGroupBean> columnGroups = new HashMap<String, ColumnGroupBean>();

	public void addColumnGroup(ColumnGroupBean columnGroupBean) {
		columnGroups.put(columnGroupBean.getGroupid(), columnGroupBean);
	}


	public ColumnGroupBean getColumnGroup(String key) {
		return columnGroups.get(key);
	}
}
