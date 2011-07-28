package jp.co.gmod.manage.dto;

import java.util.List;

import org.seasar.framework.beans.util.BeanMap;

import jp.co.gmod.manage.entity.Column;
import jp.co.gmod.manage.entity.TableInfo;

public class TableDto {

	//テーブル情報
	private TableInfo tableInfo = null;
	//カラム情報
	private List<Column> columnList = null;
	//データリスト
	private List<BeanMap> dataList = null;


	/**
	 * tableInfoを取得します。
	 * @return tableInfo
	 */
	public TableInfo getTableInfo() {
	    return tableInfo;
	}

	/**
	 * tableInfoを設定します。
	 * @param tableInfo tableInfo
	 */
	public void setTableInfo(TableInfo tableInfo) {
	    this.tableInfo = tableInfo;
	}

	/**
	 * columnListを取得します。
	 * @return columnList
	 */
	public List<Column> getColumnList() {
	    return columnList;
	}

	/**
	 * columnListを設定します。
	 * @param columnList columnList
	 */
	public void setColumnList(List<Column> columnList) {
	    this.columnList = columnList;
	}

	/**
	 * dataListを取得します。
	 * @return dataList
	 */
	public List<BeanMap> getDataList() {
	    return dataList;
	}

	/**
	 * dataListを設定します。
	 * @param dataList dataList
	 */
	public void setDataList(List<BeanMap> dataList) {
	    this.dataList = dataList;
	}

}
