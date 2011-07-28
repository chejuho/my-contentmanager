package jp.co.gmod.manage.service;

import java.util.ArrayList;
import java.util.List;

import jp.co.gmod.manage.condition.PagerCondition;
import jp.co.gmod.manage.dto.TableDto;
import jp.co.gmod.manage.dto.TableInfoDto;
import jp.co.gmod.manage.entity.Column;
import jp.co.gmod.manage.entity.Destination;
import jp.co.gmod.manage.entity.DestinationDbInfo;
import jp.co.gmod.manage.entity.TableInfo;
import jp.co.gmod.manage.util.CustomFunction;
import jp.co.gmod.manage.util.DateUtil;

import org.seasar.extension.dbcp.impl.XADataSourceImpl;
import org.seasar.extension.jdbc.JdbcManager;
import org.seasar.framework.beans.util.BeanMap;
import org.seasar.framework.container.annotation.tiger.Binding;
import org.seasar.framework.util.StringUtil;


public class TableInfoService {

	@Binding("dataJdbcManager")
	private JdbcManager jdbcManager;

	@Binding("managerJdbcManager")
	private JdbcManager managerJdbcManager;

	@Binding("publishJdbcManager")
	private JdbcManager publishJdbcManager;

	@Binding("dataDataSource")
	private XADataSourceImpl dataDataSource;

	@Binding("publishDataSource")
	private XADataSourceImpl publshDataSource;

	private final static String MYSQL_HEAD_URL = "jdbc:mysql://";
	private final static String TABLE_SORT_KEY = "physical_name asc";

	public boolean setEvSetting(Destination destinationInfo, int environment_id) {

		//DB環境設定
		return evSetting(destinationInfo, environment_id);
	}

	/**
	 *
	 * @param dataSourceFactory
	 * @return
	 */
	public List<TableInfoDto> getTableInfo(Destination destinationInfo) {

		List<TableInfoDto> tableInfoDtoList = new ArrayList<TableInfoDto>();

		List<TableInfo> tableInfoList = managerJdbcManager.from(TableInfo.class).
			where("content_id = ?", Integer.valueOf(destinationInfo.content_id)).orderBy(TABLE_SORT_KEY).getResultList();


		for (TableInfo tableInfo : tableInfoList) {

			TableInfoDto info = new TableInfoDto();
			info.setTableid(tableInfo.id);
			info.setTablename(tableInfo.physical_name);
			info.setTableLogicName(tableInfo.logical_name);

			tableInfoDtoList.add(info);
		}

		return tableInfoDtoList;

	}

	/**
	 *
	 * @param tableid
	 * @return
	 */
	public TableInfo getTableInfoByTableId(String tableid) {

		TableInfo tableInfo = managerJdbcManager.from(TableInfo.class).id(Integer.valueOf(tableid)).getSingleResult();
		return tableInfo;
	}

	/**
	 *
	 * @param tablename
	 * @return
	 */
	public TableDto getTableDataAll(String tableid) {

		TableInfo tableInfo = getTableInfoByTableId(tableid);

		List<Column> columnList = getTableColumnListByTableId(tableid);

		List<BeanMap> tableDataList = getTableDataListAll(columnList, tableInfo.physical_name);

		TableDto tableDto = new TableDto();

		tableDto.setTableInfo(tableInfo);

		tableDto.setColumnList(columnList);

		tableDto.setDataList(tableDataList);

		return tableDto;
	}

	/**
	 *
	 * @param tablename
	 * @return
	 */
	public TableDto getTableDataByIdList(String tableid, String[] idList) {

		TableInfo tableInfo = getTableInfoByTableId(tableid);

		List<Column> columnList = getTableColumnListByTableId(tableid);

		List<BeanMap> tableDataList = getTableDataByIdList(columnList, tableInfo.physical_name, idList);

		TableDto tableDto = new TableDto();

		tableDto.setTableInfo(tableInfo);

		tableDto.setColumnList(columnList);

		tableDto.setDataList(tableDataList);

		return tableDto;
	}

	/**
	 *
	 * @param tablename
	 * @return
	 */
	public TableDto getTableDataById(String tableid, String id) {

		TableInfo tableInfo = getTableInfoByTableId(tableid);

		List<Column> columnList = getTableColumnListByTableId(tableid);

		BeanMap tableData = getTableDataById(columnList, tableInfo.physical_name, id);

		TableDto tableDto = new TableDto();

		tableDto.setTableInfo(tableInfo);

		tableDto.setColumnList(columnList);

		List<BeanMap> tableDataList = new ArrayList<BeanMap>();
		tableDataList.add(tableData);

		tableDto.setDataList(tableDataList);

		return tableDto;
	}

	/**
	 *
	 * @param tablename
	 * @return
	 */
	public TableDto getTableData(String tableid) {

		TableInfo tableInfo = getTableInfoByTableId(tableid);

		List<Column> columnList = getTableColumnListByTableId(tableid);

		TableDto tableDto = new TableDto();

		tableDto.setTableInfo(tableInfo);

		tableDto.setColumnList(columnList);

		return tableDto;
	}

	/**
	 *
	 * @param tableid
	 * @param pagerCondition
	 * @return
	 */
	public int getMaxCnt(String tableid, PagerCondition pagerCondition) {

		//件数取得
		int maxCnt = 0;

		//テーブル情報取得
		TableInfo tableInfo = getTableInfoByTableId(tableid);

		//nullではない場合
		if (pagerCondition != null) {
			maxCnt = pagerCondition.getTotalCount();
		} else {
			maxCnt = getTableDataCount(tableInfo.physical_name);
		}

		return maxCnt;
	}

	/**
	 *
	 * @param tableid
	 * @param pagerCondition
	 * @return
	 */
	public int getMaxCntBySearch(String tableid, PagerCondition pagerCondition, String search, String searchoption) {

		//件数取得
		int maxCnt = 0;

		//テーブル情報取得
		TableInfo tableInfo = getTableInfoByTableId(tableid);
		//カラム情報取得
		List<Column> columnList = getTableColumnListByTableId(tableid);
		List<Column> searchColumns = getSearchColumnList(columnList, searchoption);

		//nullではない場合
		if (pagerCondition != null) {
			maxCnt = pagerCondition.getTotalCount();
		} else {
			String maxCntSql = makeTableMaxCountSQLBySearch(searchColumns, tableInfo.physical_name, search);
			BeanMap countInfo = jdbcManager.selectBySql(BeanMap.class, maxCntSql).getSingleResult();
			maxCnt = Integer.valueOf(countInfo.get("count").toString()).intValue();
		}

		return maxCnt;
	}

	/**
	 *
	 * @param tableid
	 * @param session
	 * @param pageKey
	 * @param pageNum
	 * @param displaykensu
	 * @return
	 */
	public TableDto getTableDataList(
			String tableid,
			PagerCondition page) {

		//テーブル情報取得
		TableInfo tableInfo = getTableInfoByTableId(tableid);
		//カラム情報取得
		List<Column> columnList = getTableColumnListByTableId(tableid);

		if (columnList.size() == 0) {
			return null;
		}
		List<BeanMap> tableDataList = getTableDataList(columnList, tableInfo.physical_name, page);

		TableDto tableDto = new TableDto();

		tableDto.setTableInfo(tableInfo);

		tableDto.setColumnList(columnList);

		tableDto.setDataList(tableDataList);

		return tableDto;

	}

	/**
	 *
	 * @param tableid
	 * @param search
	 * @param session
	 * @param pageKey
	 * @param pageNum
	 * @param displaykensu
	 * @return
	 */
	public TableDto getTableDataListByIndexSearch(
			String tableid,
			String search,
			PagerCondition page,
			String searchoption) {

		//テーブル情報取得
		TableInfo tableInfo = getTableInfoByTableId(tableid);
		//カラム情報取得
		List<Column> columnList = getTableColumnListByTableId(tableid);

		if (columnList.size() == 0) {
			return null;
		}
		List<Column> searchColumns = getSearchColumnList(columnList, searchoption);
		//
		String sql = makeTableDataSQLBySearch(columnList, searchColumns, tableInfo.physical_name, search) + makePrimaryKeyOrderList(columnList);
		List<BeanMap> tableDataList = jdbcManager.selectBySql(BeanMap.class, sql).limit(page.getLimit()).offset(page.getOffset()).getResultList();

		TableDto tableDto = new TableDto();

		tableDto.setTableInfo(tableInfo);

		tableDto.setColumnList(columnList);

		tableDto.setDataList(tableDataList);

		return tableDto;

	}

	/**
	 *
	 * @param tablename
	 * @return
	 */
	public List<BeanMap> getTableDataListAll(List<Column> columnList, String tablename) {

		String sql = makeTableDataSQL(columnList, tablename);
		List<BeanMap> tableDataList = jdbcManager.selectBySql(BeanMap.class, sql).getResultList();

		return tableDataList;
	}

	/**
	 *
	 * @param tablename
	 * @return
	 */
	public List<BeanMap> getTableDataByIdList(List<Column> columnList, String tablename, String[] idList) {

		List<BeanMap> tableDataList = new ArrayList<BeanMap>();

		for (String id : idList) {
			BeanMap tableData = getTableDataById(columnList, tablename, id);

			tableDataList.add(tableData);

		}

		return tableDataList;
	}

	/**
	 *
	 * @param tablename
	 * @return
	 */
	public BeanMap getTableDataById(List<Column> columnList, String tablename, String id) {

		String sql = makeGetTableDataSQLByPrimaryKey(columnList, tablename, id);
		BeanMap tableData = jdbcManager.selectBySql(BeanMap.class, sql).getSingleResult();

		return tableData;
	}

	/**
	 *
	 * @param tablename
	 * @param destinationInfo
	 * @param publishTargetEv
	 * @param publishidList
	 * @return
	 */
	public int publishData(
			String tableid,
			Destination destinationInfo,
			Integer publishTargetEv,
			String[] publishidList) {

		TableInfo tableInfo = getTableInfoByTableId(tableid);
		List<Column> columnList = getTableColumnListByTableId(tableid);
		List<BeanMap> publishsrc = getTableDataByIdList(columnList, tableInfo.physical_name, publishidList);

		//環境変数設定
		DestinationDbInfo dbInfo = null;
		for (DestinationDbInfo destinationDbInfo : destinationInfo.destinationDbInfoList) {
			if (destinationDbInfo.environment_id.intValue() == publishTargetEv.intValue()) {
				dbInfo = destinationDbInfo;
				break;
			}
		}
		publshDataSource.setURL(MYSQL_HEAD_URL + dbInfo.url);
		publshDataSource.setUser(dbInfo.db_id);
		publshDataSource.setPassword(dbInfo.db_password);

		int count = 0;
		int idIndex = 0;
		for (BeanMap data : publishsrc) {

			//String isExistSql = "SELECT * FROM " + tableInfo.physical_name + " WHERE id = ?";
			String isExistSql = makeGetTableDataSQLByPrimaryKey(columnList, tableInfo.physical_name, publishidList[idIndex]);
			BeanMap isExist = publishJdbcManager.selectBySql(BeanMap.class, isExistSql).getSingleResult();

			//存在する場合
			if (isExist != null) {
				String updateSql = makeUpdateSql(tableInfo.physical_name, columnList, data, publishidList[idIndex]);
				count += publishJdbcManager.updateBySql(updateSql).execute();
			} else {
				String insertSql = makeInsertSql(tableInfo.physical_name, columnList, data, null);
				count += publishJdbcManager.updateBySql(insertSql).execute();
			}

			idIndex++;
		}

		return count;
	}


	public int deleteTable(String tableid, String[] deleteList) {
		TableInfo tableInfo = getTableInfoByTableId(tableid);
		List<Column> columnList = getTableColumnListByTableId(tableid);
		int count = 0;

		for (String id : deleteList) {
			String sql = "delete FROM " + tableInfo.physical_name + getPrimaryKeyWhere(columnList, id);
			count += jdbcManager.updateBySql(sql).execute();
		}

		return count;
	}
	public int insertTable(
			String tableid,
			List<Column> columns,
			BeanMap requestMap) {

		TableInfo tableInfo = getTableInfoByTableId(tableid);
		String[] exceptColumns = getExceptColumn(columns);
		String insertSql = makeInsertSql(tableInfo.physical_name, columns, requestMap, exceptColumns);
		int count =jdbcManager.updateBySql(insertSql).execute();
		return count;
	}

	public int updateTable(
			String tableid,
			List<Column> columns,
			BeanMap requestMap,
			String id) {

		TableInfo tableInfo = getTableInfoByTableId(tableid);
		String updateSql = makeUpdateSql(tableInfo.physical_name, columns, requestMap, id);
		int count =jdbcManager.updateBySql(updateSql).execute();
		return count;
	}


	public boolean evSetting(Destination destinationInfo, int environment_id) {
		boolean settingSuccess = false;
		DestinationDbInfo dbInfo = null;
		for (DestinationDbInfo destinationDbInfo : destinationInfo.destinationDbInfoList) {
			if (destinationDbInfo.environment_id == environment_id) {
				dbInfo = destinationDbInfo;
				settingSuccess = true;
				break;
			}
		}
		if (settingSuccess) {
			dataDataSource.setURL(MYSQL_HEAD_URL + dbInfo.url);
			dataDataSource.setUser(dbInfo.db_id);
			dataDataSource.setPassword(dbInfo.db_password);
		}
		return settingSuccess;

	}

	/**
	 * @param csvFile
	 * @param tableid
	 */
	public int importCsvData(List<BeanMap> list, String tableid) {

		TableInfo tableInfo = getTableInfoByTableId(tableid);
		List<Column>columnList = getTableColumnListByTableId(tableid);
		int count = 0;

		for (BeanMap data : list) {

			String insertSql = makeInsertSql(tableInfo.physical_name, columnList, data, null);
			count += jdbcManager.updateBySql(insertSql).execute();

		}

		return count;
	}

	/**
	 *
	 * @param tablename
	 * @return
	 */
	private List<BeanMap> getTableDataList(List<Column> columnList, String tablename, PagerCondition page) {

		//String sql = "select * from " + tablename + ";";
		String sql = makeTableDataSQL(columnList, tablename) + makePrimaryKeyOrderList(columnList);;
		List<BeanMap> tableDataList = jdbcManager.selectBySql(BeanMap.class, sql).limit(page.getLimit()).offset(page.getOffset()).getResultList();

		return tableDataList;
	}


	/**
	 *
	 * @param tablename
	 * @return
	 */
	public List<Column> getTableColumnListByTableId(String tableid) {


		List<Column> columnInfolist = managerJdbcManager.from(Column.class)
			.where("table_id = ?", Integer.valueOf(tableid)).orderBy("seq_id asc").getResultList();

		return columnInfolist;
	}

	/**
	 *
	 * @param tablename
	 * @return
	 */
	private int getTableDataCount(String tablename) {

		String count = "select count(*) as count from " + tablename + ";";
		BeanMap countInfo = jdbcManager.selectBySql(BeanMap.class, count).getSingleResult();

		return Integer.valueOf(countInfo.get("count").toString()).intValue();
	}

	private String makeUpdateSql(
			String tablename,
			List<Column> columnList,
			BeanMap data,
			String id) {

		StringBuilder sql = new StringBuilder();

		sql.append("update ");
		sql.append(tablename);
		sql.append(" set ");

		int i = 0;
		for (Object obj : data.values()) {
			String type = columnList.get(i).type;
			String field = columnList.get(i).physical_name;
			String value = "";
			if (obj != null) {
				value = obj.toString();
			}
			sql.append(field);
			sql.append(" = ");

			if ("updated_at".equals(field)) {
				sql.append("'" + DateUtil.getTodayDate() + "'");
			} else {
				sql.append(getValue(type, value));
			}


			sql.append(" ,");

			i++;
		}
		sql.deleteCharAt(sql.length() - 1);
		sql.append(getPrimaryKeyWhere(columnList, id));

		return sql.toString();
	}

	private String getPrimaryKeyWhere(List<Column> columnList, String id) {

		StringBuilder where = new StringBuilder();

		where.append(" WHERE ");

		String[] idInfolist = id.split(CustomFunction.KBN_MOZI);

		int primaryKeyIndex = 0;
		for (Column column : columnList) {

			if (column.primary_key_sign.intValue()== 1){
				where.append(column.physical_name);
				where.append("=");
				where.append(getValue(column.type, idInfolist[primaryKeyIndex]));

				where.append(" and ");
				primaryKeyIndex++;
			}
		}

		where.delete(where.length() - 4, where.length());

		return where.toString();
	}


	/**
	 *
	 * @param tablename
	 * @param columnList
	 * @param data
	 * @return
	 */
	private String makeInsertSql(String tablename, List<Column> columnList, BeanMap data, String[] exceptColumns) {

		StringBuilder sql = new StringBuilder();

		sql.append("insert into ");
		sql.append(tablename);
		sql.append(" ( ");

		for (Column column : columnList) {

			if (exceptColumns != null && exceptColumns.length != 0) {
				for(String exceptColumn :  exceptColumns) {
					if (!exceptColumn.equals(column.physical_name)) {
						appendInsertColumn(sql, column.physical_name);
					}
				}
			} else {
				appendInsertColumn(sql, column.physical_name);
			}
		}
		sql.deleteCharAt(sql.length() - 1);
		sql.append(" ) values ( ");

		int i = 0;
		for (Object obj : data.values()) {
			String columnname = columnList.get(i).physical_name;
			String type = columnList.get(i).type;
			if (exceptColumns != null && exceptColumns.length != 0) {
				for(String exceptColumn :  exceptColumns) {
					if (!exceptColumn.equals(columnname)) {
						appendInsertValue(sql, type, obj);
					}
				}
			} else {
				appendInsertValue(sql, type, obj);
			}
			i++;
		}
		sql.deleteCharAt(sql.length() - 1);
		sql.append(" )");

		return sql.toString();

	}

	private void appendInsertColumn(StringBuilder sql, String physical_name) {
		sql.append(physical_name);
		sql.append(" ,");
	}

	private void appendInsertValue(StringBuilder sql, String type, Object obj) {

		String value = "";
		if (obj != null) {
			value = obj.toString();
		}
		sql.append(getValue(type, value));
		sql.append(" ,");
	}

	private List<Column> getSearchColumnList(List<Column> columnList, String searchoption) {

		List<Column> searchColumnList = new ArrayList<Column>();

		for (Column column : columnList) {
			if ("all".equals(searchoption)) {
				if (column.index_sign.intValue() == 1) {
					searchColumnList.add(column);
				}
				if (column.unique_key_sign.intValue() == 1) {
					searchColumnList.add(column);
				}
				if (column.primary_key_sign.intValue() == 1) {
					searchColumnList.add(column);
				}
			} else {
				if (column.physical_name.equals(searchoption)) {
					searchColumnList.add(column);
				}
			}

		}

		return searchColumnList;
	}

	private String makeTableMaxCountSQLBySearch(
			List<Column> searchColumns,
			String tablename,
			String search) {

		StringBuilder sb = new StringBuilder();
		sb.append("select count(*) as count from " + tablename + " where ");

		for (Column column : searchColumns) {

			sb.append(column.physical_name);
			sb.append(" like ");
			sb.append("'%");
			sb.append(search);
			sb.append("%'");
			sb.append(" or ");
		}
		sb.delete(sb.length() - 3, sb.length() - 1);

		return sb.toString();
	}

	private String makeTableDataSQLBySearch(
			List<Column> columnList,
			List<Column> searchColumns,
			String tablename,
			String search) {

		StringBuilder sb = new StringBuilder();
		sb.append("select ");

		for (Column column : columnList) {
			sb.append(column.physical_name);
			sb.append(" as ");
			sb.append(column.physical_name);
			sb.append(",");
		}
		sb.deleteCharAt(sb.length() - 1);

		sb.append(" from " + tablename);

		sb.append(" where ");

		for (Column searchColumn : searchColumns) {
			sb.append(searchColumn.physical_name);
			sb.append(" like ");
			sb.append("'%");
			sb.append(search);
			sb.append("%'");
			sb.append(" or ");
		}
		sb.delete(sb.length() - 3, sb.length() - 1);


		return sb.toString();
	}

	private String makeGetTableDataSQLByPrimaryKey(List<Column> columnList, String tablename, String id) {

		StringBuilder sb = new StringBuilder();

		sb.append("select ");

		for (Column column : columnList) {
			sb.append(column.physical_name);
			sb.append(" as ");
			sb.append(column.physical_name);
			sb.append(",");
		}
		sb.deleteCharAt(sb.length() - 1);

		sb.append(" from " + tablename);

		sb.append(" where ");

		String[] idInfolist = id.split(CustomFunction.KBN_MOZI);

		int primaryKeyIndex = 0;
		for (Column column : columnList) {

			if (column.primary_key_sign.intValue()== 1){
				sb.append(column.physical_name);
				sb.append("=");
				sb.append(getValue(column.type, idInfolist[primaryKeyIndex]));

				sb.append(" and ");
				primaryKeyIndex++;
			}
		}

		sb.delete(sb.length() - 4, sb.length());

		return sb.toString();
	}

	/**
	 *
	 * @param columnList
	 * @param tablename
	 * @return
	 */
	private String makeTableDataSQL(List<Column> columnList, String tablename) {

		StringBuilder sb = new StringBuilder();

		sb.append("select ");

		for (Column column : columnList) {
			sb.append(column.physical_name);
			sb.append(" as ");
			sb.append(column.physical_name);
			sb.append(",");
		}
		sb.deleteCharAt(sb.length() - 1);

		sb.append(" from " + tablename);


		return sb.toString();
	}

	private String makePrimaryKeyOrderList(List<Column> columnList) {

		StringBuilder sb = new StringBuilder();
		sb.append(" order by ");

		for (Column column : columnList) {
			if (column.primary_key_sign.intValue() == 1) {
				sb.append(column.physical_name);
				sb.append(" DESC");
				sb.append(" ,");
			}
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();

	}

	private String getValue(String type, String value) {

		String transformValue = null;
		if (type.indexOf("serial") >= 0 || type.indexOf("bigint") >= 0) {
			if (value.length() == 0) {
				transformValue = "0";
			} else {
				transformValue = value;
			}
		} else if (type.indexOf("nvarchar") >= 0 || type.indexOf("char") >= 0) {
			transformValue = "'" + value + "'";
		} else if (type.indexOf("datetime") >= 0) {
			if (value.length() == 0) {
				transformValue = "NULL";
			} else {
				transformValue = "'" + value + "'";
			}
		} else if (type.indexOf("tinyint") >= 0) {
			if (value.length() == 0) {
				transformValue = "0";
			} else {
				transformValue = value;
			}
		} else {
			transformValue = value;
		}

		return transformValue;

	}

	private String[] getExceptColumn(List<Column> columns) {

		List<String> exceptColumns = new ArrayList<String>();

		for (Column column : columns) {

			if (column.autoincrement_sign == 1) {
				exceptColumns.add(column.physical_name);
			}
		}
		return exceptColumns.toArray(new String[]{});
	}

}
