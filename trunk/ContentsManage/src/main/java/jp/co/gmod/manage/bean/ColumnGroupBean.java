package jp.co.gmod.manage.bean;

import java.util.ArrayList;
import java.util.List;

public class ColumnGroupBean {


	private String groupid;

	private List<ColumnBean> columnList = new ArrayList<ColumnBean>();

	/**
	 * groupidを取得します。
	 * @return groupid
	 */
	public String getGroupid() {
	    return groupid;
	}

	/**
	 * groupidを設定します。
	 * @param groupid groupid
	 */
	public void setGroupid(String groupid) {
	    this.groupid = groupid;
	}

	/**
	 * columnListを取得します。
	 * @return columnList
	 */
	public List<ColumnBean> getColumnList() {
	    return columnList;
	}

	/**
	 *
	 * @param dolumn
	 */
	public void addColumn(ColumnBean dolumn){
		columnList.add(dolumn);
    }


}
