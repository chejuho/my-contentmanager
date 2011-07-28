package jp.co.gmod.manage.bean;

import java.util.ArrayList;
import java.util.List;


public class TableParseBean {

	List<TableInfoBean> tableInfoList = new ArrayList<TableInfoBean>();


	public void addTable(TableInfoBean info){
		tableInfoList.add(info);
    }

	public List<TableInfoBean> getTableInfoList() {
		return tableInfoList;
	}


}
