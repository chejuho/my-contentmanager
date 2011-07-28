package jp.co.gmod.manage.service;

import java.util.ArrayList;
import java.util.List;

import jp.co.gmod.manage.condition.PagerCondition;
import jp.co.gmod.manage.dto.TableDto;
import jp.co.gmod.manage.dto.TableInfoDto;
import jp.co.gmod.manage.entity.Column;
import jp.co.gmod.manage.entity.Content;
import jp.co.gmod.manage.entity.Destination;
import jp.co.gmod.manage.entity.TableInfo;
import junit.framework.AssertionFailedError;

import org.seasar.extension.dataset.ColumnType;
import org.seasar.extension.dataset.DataRow;
import org.seasar.extension.dataset.DataSet;
import org.seasar.extension.dataset.DataTable;
import org.seasar.extension.dataset.types.ColumnTypes;
import org.seasar.extension.dbcp.impl.XADataSourceImpl;
import org.seasar.extension.jdbc.JdbcManager;
import org.seasar.extension.unit.BeanReader;
import org.seasar.extension.unit.S2TestCase;
import org.seasar.framework.beans.util.BeanMap;
import org.seasar.framework.container.annotation.tiger.Binding;
import org.seasar.framework.unit.S2Assert;

public class TableInfoServiceTest extends S2TestCase{

	private TableInfoService tableInfoService;
	private ContentsManageService contentManageService;

	@Binding("managerJdbcManager")
	private JdbcManager managerJdbcManager;

	@Binding("dataJdbcManager")
	private JdbcManager jdbcManager;

	@Binding("publishJdbcManager")
	private JdbcManager publishJdbcManager;

	@Binding("dataDataSource")
	private XADataSourceImpl dataDataSource;



	protected void setUp() throws Exception  {
		 super.setUp();
		 include("app.dicon");
	}

    public void testGetContentsTx() {
    	readXlsAllReplaceDb("TableInfoServiceTest.xls", "manager");
    	//readXlsAllReplaceDb("TableInfoServiceTest.xls");
    	contentManageService.getContents();

    }

    public void testGetContentsByIdListTx() {
    	readXlsAllReplaceDb("TableInfoServiceTest.xls", "manager");
    	String[] contentIdList = {"2","3"};
    	List<Content> list = contentManageService.getContentsByIdList(contentIdList);
    	assertEquals(2, list.size());
    }

    public void testGetTableInfoTx() {

    	readXlsAllReplaceDb("TableInfoServiceTest_getTableInfo.xls", "manager");

    	Destination destinationInfo = contentManageService.getDestinationByIdFullJoin("5");

    	tableInfoService.setEvSetting(destinationInfo, 1);
    	List<TableInfoDto> tableList = tableInfoService.getTableInfo(destinationInfo);
    	//levels
    	//heads
    	//countries
    	//user_items
    	TableInfoDto dto1 = tableList.get(0);
    	TableInfoDto dto2 = tableList.get(1);
    	TableInfoDto dto3 = tableList.get(2);
    	TableInfoDto dto4 = tableList.get(3);

    	assertEquals("国マスタ", dto1.getTableLogicName());
    	assertEquals("頭マスタ", dto2.getTableLogicName());
    	assertEquals("レベルマスタ", dto3.getTableLogicName());
    	assertEquals("ユーザー所持アイテム", dto4.getTableLogicName());

    }

    public void testGetTableDataAllTx() {

    	readXlsAllReplaceDb("TableInfoServiceTest_getTableInfoByTableId_m.xls", "manager");
    	readXlsAllReplaceDb("TableInfoServiceTest_getTableInfoByTableId_d.xls", "dataData");

    	TableDto tabledto = tableInfoService.getTableDataAll("13");

    	List<Column> list = tabledto.getColumnList();

    	TableInfo tableInfo = tabledto.getTableInfo();

    	assertEquals("配布先DB情報", tableInfo.logical_name);
    	assertEquals("destination_db_info", tableInfo.physical_name);

    	assertEquals("id", list.get(0).logical_name);
    	assertEquals("id", list.get(0).physical_name);
    	assertEquals("配布先ID", list.get(1).logical_name);
    	assertEquals("destination_id", list.get(1).physical_name);
    	assertEquals("url", list.get(2).logical_name);
    	assertEquals("url", list.get(2).physical_name);
    	assertEquals("db_id", list.get(3).logical_name);
    	assertEquals("db_id", list.get(3).physical_name);
    	assertEquals("パスワード", list.get(4).logical_name);
    	assertEquals("db_password", list.get(4).physical_name);
    	assertEquals("環境区分ID", list.get(5).logical_name);
    	assertEquals("environment_id", list.get(5).physical_name);

    	for (BeanMap map : tabledto.getDataList()) {
    		System.out.println(map);
    	}
    }

    public void testGetTableDataByIdListTx() {

    	readXlsAllReplaceDb("TableInfoServiceTest_getTableInfoByTableId_m.xls", "manager");
    	readXlsAllReplaceDb("TableInfoServiceTest_getTableInfoByTableId_d.xls", "dataData");

    	String[] idlist = {"3"};

    	TableDto tabledto = tableInfoService.getTableDataByIdList("12", idlist);

    	//3	1	テスト3

    	TableInfo tableInfo = tabledto.getTableInfo();

    	assertEquals("新規テーブル", tableInfo.logical_name);
    	assertEquals("destination", tableInfo.physical_name);

    	for (BeanMap map : tabledto.getDataList()) {
    		assertEquals(3, map.get("id"));
        	assertEquals(1, map.get("contentId"));
        	assertEquals("テスト3", map.get("destinationName"));
    	}

    }

    public void testGetTableDataByIdTx() {

    	readXlsAllReplaceDb("TableInfoServiceTest_getTableInfoByTableId_m.xls", "manager");
    	readXlsAllReplaceDb("TableInfoServiceTest_getTableInfoByTableId_d.xls", "dataData");

    	TableDto tabledto = tableInfoService.getTableDataById("12", "3");

    	//3	1	テスト3

    	TableInfo tableInfo = tabledto.getTableInfo();

    	assertEquals("新規テーブル", tableInfo.logical_name);
    	assertEquals("destination", tableInfo.physical_name);

    	for (BeanMap map : tabledto.getDataList()) {
    		assertEquals(3, map.get("id"));
        	assertEquals(1, map.get("contentId"));
        	assertEquals("テスト3", map.get("destinationName"));
    	}

    }

    public void testGetTableDataTx() {

    	readXlsAllReplaceDb("TableInfoServiceTest_getTableInfoByTableId_m.xls", "manager");
    	readXlsAllReplaceDb("TableInfoServiceTest_getTableInfoByTableId_d.xls", "dataData");

    	TableDto tabledto = tableInfoService.getTableData("12");

    	//3	1	テスト3

    	TableInfo tableInfo = tabledto.getTableInfo();

    	assertEquals("新規テーブル", tableInfo.logical_name);
    	assertEquals("destination", tableInfo.physical_name);

    	assertNull(tabledto.getDataList());

    }

    public void testGetTableDataListTx() {

    	readXlsAllReplaceDb("TableInfoServiceTest_getTableInfoByTableId_m.xls", "manager");
    	readXlsAllReplaceDb("TableInfoServiceTest_getTableInfoByTableId_d.xls", "dataData");


    	int maxCnt = tableInfoService.getMaxCnt("10", null);
    	PagerCondition pagerCondition = new PagerCondition(10, 10, maxCnt);

    	TableDto tabledto = tableInfoService.getTableDataList("10", pagerCondition);

    	TableInfo tableInfo = tabledto.getTableInfo();

    	assertEquals("カラム", tableInfo.logical_name);
    	assertEquals("columns", tableInfo.physical_name);

    	assertEquals(10, tabledto.getDataList().size());

    	for (BeanMap map : tabledto.getDataList()) {
    		System.out.println(map);
    	}
    }

    public void testGetTableDataList2Tx() {

    	readXlsAllReplaceDb("TableInfoServiceTest_getTableInfoByTableId_m.xls", "manager");
    	readXlsAllReplaceDb("TableInfoServiceTest_getTableInfoByTableId_d.xls", "dataData");


    	int maxCnt = tableInfoService.getMaxCnt("10", null);
    	PagerCondition pagerCondition = new PagerCondition(10, 10, maxCnt);

    	pagerCondition.setNowPage(3);

    	TableDto tabledto = tableInfoService.getTableDataList("10", pagerCondition);

    	TableInfo tableInfo = tabledto.getTableInfo();

    	assertEquals("カラム", tableInfo.logical_name);
    	assertEquals("columns", tableInfo.physical_name);

    	assertEquals(10, tabledto.getDataList().size());

    	for (BeanMap map : tabledto.getDataList()) {
    		System.out.println(map);
    	}
    }


    public void testGetTableDataListByIndexSearchTx() {

    	readXlsAllReplaceDb("TableInfoServiceTest_getTableInfoByTableId_m.xls", "manager");
    	readXlsAllReplaceDb("TableInfoServiceTest_getTableInfoByTableId_d.xls", "dataData");

    	int maxCnt = tableInfoService.getMaxCntBySearch("10", null, "主キー", "all");
    	PagerCondition pagerCondition = new PagerCondition(10, 10, maxCnt);

    	TableDto tabledto = tableInfoService.getTableDataListByIndexSearch("10" ,"主キー", pagerCondition, "all");

    	TableInfo tableInfo = tabledto.getTableInfo();

    	assertEquals("カラム", tableInfo.logical_name);
    	assertEquals("columns", tableInfo.physical_name);

    	assertEquals(1, tabledto.getDataList().size());
    	for (BeanMap map : tabledto.getDataList()) {
    		System.out.println(map);
    	}


    }

    public void testPublishDataTx() {

    	readXlsAllReplaceDb("TableInfoServiceTest_getTableInfoByTableId_m.xls", "manager");
    	readXlsAllReplaceDb("TableInfoServiceTest_getTableInfoByTableId_d.xls", "dataData");
    	readXlsAllReplaceDb("TableInfoServiceTest_getTableInfoByTableId_p.xls", "publshData");

    	Destination destinationInfo = contentManageService.getDestinationById("5");

    	String[] publishidList = {"3"};

    	int cnt = tableInfoService.publishData("11", destinationInfo, 2, publishidList);
    	assertEquals(1, cnt);

    	String sql = "SELECT * FROM contents WHERE id = 3";
		BeanMap bean = publishJdbcManager.selectBySql(BeanMap.class, sql).getSingleResult();

		assertEquals(3, bean.get("id"));
		assertEquals("コンテンツ管理", bean.get("contentName"));
    }

    public void testPublishData2Tx() {

    	readXlsAllReplaceDb("TableInfoServiceTest_getTableInfoByTableId_m.xls", "manager");
    	readXlsAllReplaceDb("TableInfoServiceTest_getTableInfoByTableId_d.xls", "dataData");
    	readXlsAllReplaceDb("TableInfoServiceTest_getTableInfoByTableId_p.xls", "publshData");

    	Destination destinationInfo = contentManageService.getDestinationById("5");

    	String[] publishidList = {"2"};

    	int cnt = tableInfoService.publishData("11", destinationInfo, 2, publishidList);
    	assertEquals(1, cnt);

    	String sql = "SELECT * FROM contents WHERE id = 2";
		BeanMap bean = publishJdbcManager.selectBySql(BeanMap.class, sql).getSingleResult();

		assertEquals(2, bean.get("id"));
		assertEquals("破壊９９", bean.get("contentName"));
    }

    public void testDeleteTableTx() {

    	readXlsAllReplaceDb("TableInfoServiceTest_getTableInfoByTableId_m.xls", "manager");
    	readXlsAllReplaceDb("TableInfoServiceTest_getTableInfoByTableId_d.xls", "dataData");
    	TableDto before = tableInfoService.getTableDataAll("15");

    	assertEquals(13, before.getDataList().size());

    	String[] deleteList = {"10","11","12","13","14","15"};
    	tableInfoService.deleteTable("15", deleteList);

    	TableDto tableDto = tableInfoService.getTableDataAll("15");

    	assertEquals(7, tableDto.getDataList().size());
    }

    public void testInsertTableTx() {

    	readXlsAllReplaceDb("TableInfoServiceTest_getTableInfoByTableId_m.xls", "manager");
    	readXlsAllReplaceDb("TableInfoServiceTest_getTableInfoByTableId_d.xls", "dataData");
    	TableDto before = tableInfoService.getTableDataAll("15");
    	assertEquals(13, before.getDataList().size());

    	List<Column> columns = tableInfoService.getTableColumnListByTableId("15");

    	BeanMap requestMap = new BeanMap();
    	requestMap.put("id", "");
    	requestMap.put("physical_name", "new_physical_name");
    	requestMap.put("logical_name", "new_logical_name");
    	requestMap.put("content_id", 3);
    	requestMap.put("seq_id", 4);
    	tableInfoService.insertTable("15", columns, requestMap);

    	TableDto tableDto = tableInfoService.getTableDataAll("15");

    	assertEquals(14, tableDto.getDataList().size());

    	for (BeanMap map : tableDto.getDataList()) {
    		System.out.println(map);
    	}

    }

    public void testUpdateTableTx() {
    	readXlsAllReplaceDb("TableInfoServiceTest_getTableInfoByTableId_m.xls", "manager");
    	readXlsAllReplaceDb("TableInfoServiceTest_getTableInfoByTableId_d.xls", "dataData");

    	List<Column> columns = tableInfoService.getTableColumnListByTableId("15");


    	BeanMap requestMap = new BeanMap();
    	requestMap.put("id", "2");
    	requestMap.put("physical_name", "new_physical_name");
    	requestMap.put("logical_name", "new_logical_name");
    	requestMap.put("content_id", 3);
    	requestMap.put("seq_id", 4);

    	tableInfoService.updateTable("15", columns, requestMap, "2#");

    	TableDto tableDto = tableInfoService.getTableDataById("15", "2");

    	for (BeanMap map : tableDto.getDataList()) {
    		assertEquals(2, map.get("id"));
    		assertEquals("new_physical_name", map.get("physicalName"));
    		assertEquals("new_logical_name", map.get("logicalName"));
    		assertEquals(3, map.get("contentId"));
    		assertEquals(4, map.get("seqId"));
    	}
    }

    public void testImportCsvDataTx() {
    	readXlsAllReplaceDb("TableInfoServiceTest_getTableInfoByTableId_m.xls", "manager");
    	readXlsAllReplaceDb("TableInfoServiceTest_getTableInfoByTableId_d.xls", "dataData");

    	BeanMap requestMap1 = new BeanMap();
    	requestMap1.put("id", "17");
    	requestMap1.put("physical_name", "new_physical_name7");
    	requestMap1.put("logical_name", "new_logical_name7");
    	requestMap1.put("content_id", 1);
    	requestMap1.put("seq_id", 4);

    	BeanMap requestMap2 = new BeanMap();
    	requestMap2.put("id", "18");
    	requestMap2.put("physical_name", "new_physical_name8");
    	requestMap2.put("logical_name", "new_logical_name8");
    	requestMap2.put("content_id", 2);
    	requestMap2.put("seq_id", 4);

    	BeanMap requestMap3 = new BeanMap();
    	requestMap3.put("id", "19");
    	requestMap3.put("physical_name", "new_physical_name9");
    	requestMap3.put("logical_name", "new_logical_name9");
    	requestMap3.put("content_id", 3);
    	requestMap3.put("seq_id", 4);

    	List<BeanMap> mapList = new ArrayList<BeanMap>();

    	mapList.add(requestMap1);
    	mapList.add(requestMap2);
    	mapList.add(requestMap3);

    	TableDto before = tableInfoService.getTableDataAll("15");
    	assertEquals(13, before.getDataList().size());

    	tableInfoService.importCsvData(mapList, "15");


    	TableDto tableDto = tableInfoService.getTableDataAll("15");

    	assertEquals(16, tableDto.getDataList().size());

    	for (BeanMap map : tableDto.getDataList()) {
    		System.out.println(map);
    	}
    }




    private void assertEqualsListBean(List<?> list1, List<?> list2) {

    	 assertEquals("ListSize", list1.size(), list2.size());

    	int i = 0;
    	for (Object obj1 : list1) {

    		assertEqualsBean(obj1, list2.get(i));
    		i++;

    	}
    }

    public void readXlsAllReplaceDb(String path, String dataSourceName) {
    	 DataSet dataSet = readXls(path, true);
    	 JdbcManager jdbcM = null;
    	 if ("manager".equals(dataSourceName)) {
    		 jdbcM = managerJdbcManager;
    	 } else if ("dataData".equals(dataSourceName)) {
    		 dataDataSource.setURL("jdbc:mysql://localhost:3306/test");
    		 dataDataSource.setUser("root");
    		 dataDataSource.setPassword("3322");
    		 jdbcM = jdbcManager;
    	 } else if ("publshData".equals(dataSourceName)) {
    		 XADataSourceImpl dataSource = (XADataSourceImpl) getComponent("publishDataSource");
    		 dataSource.setURL("jdbc:mysql://localhost:3306/pubtest");
    		 dataSource.setUser("root");
    		 dataSource.setPassword("3322");
    		 jdbcM = publishJdbcManager;
    	 }
    	 for(int i = dataSet.getTableSize() - 1; i >= 0; i--) {
    		 String updateSql = "DELETE FROM " + dataSet.getTable(i).getTableName();
    		 jdbcM.updateBySql(updateSql).execute();
    	 }
    	 for(int i = dataSet.getTableSize() - 1; i >= 0; i--) {
    		 DataTable table = dataSet.getTable(i);

    		 for (int j = 0; j < table.getRowSize(); j++) {
    			 DataRow row = table.getRow(j);
    			 String sql = dataSetToInsertSql(table, row);
    			 jdbcM.updateBySql(sql).execute();
    		 }

    	 }
    }

    private String dataSetToInsertSql(DataTable table, DataRow row) {

    	StringBuilder sb = new StringBuilder();
    	//INSERT INTO table_info (id, physical_name, logical_name, content_id, seq_id) VALUES (9, 'test3_3', 'コンテンツ管理_3', 3, 3)
    	sb.append("INSERT INTO ");
    	sb.append(table.getTableName());
    	sb.append("(");
    	 for (int j = 0; j < table.getColumnSize(); j++) {
    		 String columnName = table.getColumnName(j);
			 sb.append(columnName);
			 sb.append(",");
		 }
    	 sb.deleteCharAt(sb.length() - 1);
    	 sb.append(")");
    	 sb.append(" VALUES (");

    	 for (int k = 0; k < table.getColumnSize(); k++) {
			 String columnName = table.getColumnName(k);
			 ColumnType columnType = table.getColumnType(k);
			 Object val = row.getValue(columnName);
			 String value = (val == null) ? "" :  val.toString();

			 if (ColumnTypes.BIGDECIMAL == columnType) {
				 sb.append(value);
			 } else if (ColumnTypes.STRING == columnType) {
				 sb.append("'");
				 sb.append(value);
				 sb.append("'");
			 } else if (ColumnTypes.TIMESTAMP == columnType) {
				 sb.append("'");
				 sb.append(value);
				 sb.append("'");
			 }
			 sb.append(",");
		 }
    	 sb.deleteCharAt(sb.length() - 1);
    	 sb.append(")");

    	return sb.toString();
    }

    private void assertEqualsTableExceptColumn(String message, DataTable actual, DataTable expected, String[] columnname)
    {
        message = message != null ? message : "";
        message = message + ":TableName=" + actual.getTableName();
        S2Assert.assertEquals(message + ":RowSize", actual.getRowSize(), expected.getRowSize());
        for(int i = 0; i < actual.getRowSize(); i++)
        {
            DataRow actualRow = actual.getRow(i);
            boolean find = false;
            for (int k = 0; k < expected.getRowSize(); k++) {
            	DataRow expectedRow = expected.getRow(k);

            	if (isEqualExceptColumn(actual, expectedRow, actualRow, columnname)) {
            		find = true;
            		break;
            	}
            }
            System.out.println(dataRowPrint(actual, actualRow));
            assertTrue(find);

        }

    }

    private String dataRowPrint(DataTable actual, DataRow actualRow) {

    	StringBuilder sb = new StringBuilder();
    	 for(int i = 0; i < actual.getColumnSize(); i++) {
    		 String columnName = actual.getColumnName(i);
    		 Object actualValue = actualRow.getValue(columnName);
    		 if (actualValue == null) {
    			 sb.append("columnName=" + columnName + ", Value=empty");
    		 } else {
    			 sb.append("columnName=" + columnName + ", Value=" + actualValue.toString());
    		 }

    	 }
    	 return sb.toString();
    }

    private boolean isEqual(DataTable expected ,DataRow expectedRow, DataRow actualRow) {
    	boolean equal = true;

    	 for(int i = 0; i < expected.getColumnSize(); i++) {
    		 String columnName = expected.getColumnName(i);

    		 Object expectedValue = expectedRow.getValue(columnName);
             ColumnType ct = ColumnTypes.getColumnType(expectedValue);
             Object actualValue = actualRow.getValue(columnName);
             if(!ct.equals(expectedValue, actualValue)) {
            	 equal = false;
            	 break;
             }
    	 }

    	return equal;
    }

    private boolean isEqualExceptColumn(DataTable expected ,DataRow expectedRow, DataRow actualRow, String[] columnname) {
    	boolean equal = true;

    	 for(int i = 0; i < expected.getColumnSize(); i++) {
    		 String columnName = expected.getColumnName(i);
    		 if (!isColumn(columnname, columnName)) {
    			 Object expectedValue = expectedRow.getValue(columnName);
                 ColumnType ct = ColumnTypes.getColumnType(expectedValue);
                 Object actualValue = actualRow.getValue(columnName);
                 if(!ct.equals(expectedValue, actualValue)) {
                	 equal = false;
                	 break;
                 }
    		 }

    	 }

    	return equal;
    }

    private boolean isColumn(String[] columnname, String columnName) {

    	for (String column : columnname) {
    		if (column.equals(columnName)) {
    			return true;
    		}
    	}

    	return false;
    }

    private void assertEqualsBean(Object read1, Object read2) {

		BeanReader acreader = new BeanReader(read1);
		BeanReader exreader = new BeanReader(read2);
		DataSet expected = exreader.read();
		DataSet actual = acreader.read();

		for (int i = 0; i < expected.getTableSize(); ++i) {
            DataTable expectedTable = expected.getTable(i);
            DataTable actualTable = actual.getTable(i);
            S2Assert.assertEquals(":RowSize", expectedTable.getRowSize(), actualTable.getRowSize());
            System.out.println(actualTable.getRowSize());


            for(int j = 0; j < expectedTable.getRowSize(); j++)
            {
                DataRow expectedRow = expectedTable.getRow(j);
                DataRow actualRow = actualTable.getRow(j);
                List errorMessages = new ArrayList();
                for(int k = 0; k < expectedTable.getColumnSize(); k++)
                    try
                    {
                        String columnName = expectedTable.getColumnName(k);
                        Object expectedValue = expectedRow.getValue(columnName);
                        ColumnType ct = ColumnTypes.getColumnType(expectedValue);
                        Object actualValue = actualRow.getValue(columnName);
                        if (actualValue == null) {
                        	assertNull(expectedValue);
                        } else if(!ct.equals(expectedValue, actualValue)) {
                        	  if (ct.equals(ColumnTypes.OBJECT)) {
                        		  assertEqualsBean(expectedValue, actualValue);
                        	  } else {
                        		  S2Assert.assertEquals(":Row=" + i + ":columnName=" + columnName, expectedValue, actualValue);
                        	  }

                        } else {
                        	 System.out.println("columnName=" + columnName + ", actualValue=" + actualValue.toString() + "," + "expectedValue=" + expectedValue.toString());
                        }

                    }
                    catch(AssertionFailedError e)
                    {
                        errorMessages.add(e.getMessage());
                    }

                if(!errorMessages.isEmpty())
                    fail("" + errorMessages);
            }
        }
	}

}
