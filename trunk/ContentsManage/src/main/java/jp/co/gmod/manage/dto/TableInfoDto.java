package jp.co.gmod.manage.dto;

public class TableInfoDto {

	private Integer tableid;
	private String tablename ="";
	private String tableLogicName ="";
	private String tableCnt = "";
	/**
	 * tableidを取得します。
	 * @return tableid
	 */
	public Integer getTableid() {
	    return tableid;
	}
	/**
	 * tableidを設定します。
	 * @param tableid tableid
	 */
	public void setTableid(Integer tableid) {
	    this.tableid = tableid;
	}
	/**
	 * tablenameを取得します。
	 * @return tablename
	 */
	public String getTablename() {
	    return tablename;
	}
	/**
	 * tablenameを設定します。
	 * @param tablename tablename
	 */
	public void setTablename(String tablename) {
	    this.tablename = tablename;
	}
	/**
	 * tableLogicNameを取得します。
	 * @return tableLogicName
	 */
	public String getTableLogicName() {
	    return tableLogicName;
	}
	/**
	 * tableLogicNameを設定します。
	 * @param tableLogicName tableLogicName
	 */
	public void setTableLogicName(String tableLogicName) {
	    this.tableLogicName = tableLogicName;
	}
	/**
	 * tableCntを取得します。
	 * @return tableCnt
	 */
	public String getTableCnt() {
	    return tableCnt;
	}
	/**
	 * tableCntを設定します。
	 * @param tableCnt tableCnt
	 */
	public void setTableCnt(String tableCnt) {
	    this.tableCnt = tableCnt;
	}



}
