package jp.co.gmod.manage.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TableInfoBean {


	private String physical_name;
	private String logical_name;
	private String seq_id;
	private Map<String, ColumnBean> columnList = new HashMap<String, ColumnBean>();

	private List<ColumnGroupIndexBean> columnGroupList = new ArrayList<ColumnGroupIndexBean>();
	private List<IndexBean> indexList = new ArrayList<IndexBean>();
	/**
	 * physical_nameを取得します。
	 * @return physical_name
	 */
	public String getPhysical_name() {
	    return physical_name;
	}
	/**
	 * physical_nameを設定します。
	 * @param physical_name physical_name
	 */
	public void setPhysical_name(String physical_name) {
	    this.physical_name = physical_name;
	}
	/**
	 * logical_nameを取得します。
	 * @return logical_name
	 */
	public String getLogical_name() {
	    return logical_name;
	}
	/**
	 * logical_nameを設定します。
	 * @param logical_name logical_name
	 */
	public void setLogical_name(String logical_name) {
	    this.logical_name = logical_name;
	}

	public void addColumn(ColumnBean column){
		columnList.put(column.getSeq_id(), column);
    }

	public Map<String, ColumnBean> getColumns(){
		return columnList;
    }

	public ColumnBean getColumn(String key){
		return columnList.get(key);
    }

	public void addIndex(IndexBean index){
		indexList.add(index);
    }


	public List<IndexBean> getIndexList() {
		return indexList;
	}

	public void addColumnGroup(ColumnGroupIndexBean columnGroupIndexBean){
		columnGroupList.add(columnGroupIndexBean);
    }
	public List<ColumnGroupIndexBean> getColumnGroupList() {
		return columnGroupList;
	}

	/**
	 * seq_idを取得します。
	 * @return seq_id
	 */
	public String getSeq_id() {
	    return seq_id;
	}
	/**
	 * seq_idを設定します。
	 * @param seq_id seq_id
	 */
	public void setSeq_id(String seq_id) {
	    this.seq_id = seq_id;
	}

}
