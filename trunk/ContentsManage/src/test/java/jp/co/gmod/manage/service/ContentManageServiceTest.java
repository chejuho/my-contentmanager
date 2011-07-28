package jp.co.gmod.manage.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.seasar.framework.unit.S2Assert.assertBeanEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jp.co.gmod.manage.entity.Content;
import jp.co.gmod.manage.entity.Destination;
import jp.co.gmod.manage.entity.DestinationDbInfo;
import jp.co.gmod.manage.form.DestinationForm;
import junit.framework.AssertionFailedError;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seasar.extension.dataset.ColumnType;
import org.seasar.extension.dataset.DataRow;
import org.seasar.extension.dataset.DataSet;
import org.seasar.extension.dataset.DataTable;
import org.seasar.extension.dataset.types.ColumnTypes;
import org.seasar.extension.unit.BeanReader;
import org.seasar.framework.unit.DataAccessor;
import org.seasar.framework.unit.PreparationType;
import org.seasar.framework.unit.S2Assert;
import org.seasar.framework.unit.Seasar2;
import org.seasar.framework.unit.TestContext;

@RunWith(Seasar2.class)
public class ContentManageServiceTest {

	private TestContext ctx;
	private ContentsManageService contentManageService;
	private DataAccessor accessor;

	public void before() {

		  ctx.setPreparationType(PreparationType.ALL_REPLACE);
	}


    @Test
    public void testGetContents() {
    	List<Content> listContent = contentManageService.getContents();
    	DataSet expected = ctx.getExpected();
    	assertBeanEquals("OUTPUT", expected, listContent);

    }

    @Test
    public void testGetContentsByIdList() {
    	String[] idList = {"1"};

    	List<Content> listContent = contentManageService.getContentsByIdList(idList);
    	DataSet expected = ctx.getExpected();
    	assertBeanEquals("OUTPUT", expected, listContent);

    }

    @Test
    public void testGetDestinationListByContentID() {

    	List<Destination> listContent = contentManageService.getDestinationListByContentID("all");

    	Content content1 = new Content();
    	content1.id = 1;
    	content1.content_name = "ログ分析";

    	Content content2 = new Content();
    	content2.id = 2;
    	content2.content_name = "破壊９９";

    	Destination expected1 = new Destination();

    	expected1.id = 1;
    	expected1.content_id = 1;
    	expected1.destination_name = "テスト1";
    	expected1.content = content1;

    	Destination expected2 = new Destination();

    	expected2.id = 2;
    	expected2.content_id = 1;
    	expected2.destination_name = "テスト2";
    	expected2.content = content1;

    	Destination expected3 = new Destination();

    	expected3.id = 3;
    	expected3.content_id = 1;
    	expected3.destination_name = "テスト3";
    	expected3.content = content1;

    	Destination expected4 = new Destination();

    	expected4.id = 4;
    	expected4.content_id = 2;
    	expected4.destination_name = "テスト4";
    	expected4.content = content2;

    	List<Destination> destinationList1 = new ArrayList<Destination>();
    	destinationList1.add(expected1);
    	destinationList1.add(expected2);
    	destinationList1.add(expected3);

    	List<Destination> destinationList2 = new ArrayList<Destination>();
    	destinationList2.add(expected4);

    	List<Destination> expectedList = new ArrayList<Destination>();
    	expectedList.add(expected1);
    	expectedList.add(expected2);
    	expectedList.add(expected3);
    	expectedList.add(expected4);

    	content1.destinationList = destinationList1;
    	content2.destinationList = destinationList2;

    	for (Destination actual : listContent) {
    		boolean find = false;
    		for (Destination expected : expectedList) {

    			if (actual.id.intValue() ==  expected.id.intValue()) {
    				find = true;
    				assertEqualsBean(expected, actual);
    				break;
    			}
        	}
    		assertTrue(find);
    	}
    }

    @Test
    public void testGetDestinationListByContentID02() {

    	List<Destination> listContent = contentManageService.getDestinationListByContentID("2");

    	Content content = new Content();
    	content.id = 2;
    	content.content_name = "破壊９９";

    	Destination expected = new Destination();

    	expected.id = 4;
    	expected.content_id = 2;
    	expected.destination_name = "テスト4";
    	expected.content = content;

    	List<Destination> destinationList = new ArrayList<Destination>();
    	destinationList.add(expected);

    	content.destinationList = destinationList;

    	List<Destination> expectedList = new ArrayList<Destination>();
    	expectedList.add(expected);

    	for (Destination actual : listContent) {
    		boolean find = false;
    		for (Destination expecteddata : expectedList) {

    			if (actual.id.intValue() ==  expecteddata.id.intValue()) {
    				find = true;
    				assertEqualsBean(actual, expecteddata);
    				break;
    			}
        	}
    		assertTrue(find);
    	}
    }

    @Test
    public void testGetContentById() {
    	Content content = contentManageService.getContentById("3");

    	Content expected = new Content();

    	expected.id = 3;
    	expected.content_name = "コンテンツ管理";

    	assertEqualsBean(content, expected);


    }

    @Test
    public void testGetDestinationById() {

    	Destination des = contentManageService.getDestinationById("4");

    	Destination expected = new Destination();

    	expected.id = 4;
    	expected.content_id = 2;
    	expected.destination_name = "テスト4";

    	DestinationDbInfo expecteddb1 = new DestinationDbInfo();
    	DestinationDbInfo expecteddb2 = new DestinationDbInfo();
    	DestinationDbInfo expecteddb3 = new DestinationDbInfo();

    	expecteddb1.id = 1;
    	expecteddb1.destination_id = 4;
    	expecteddb1.url = "tdb-master.local/99hakai_mbga_dev";
    	expecteddb1.db_id = "master";
    	expecteddb1.db_password = "master";
    	expecteddb1.environment_id = 1;
    	expecteddb1.destination = expected;

    	expecteddb2.id = 2;
    	expecteddb2.destination_id = 4;
    	expecteddb2.url = "tdb-master.local/99hakai_mbga";
    	expecteddb2.db_id = "master";
    	expecteddb2.db_password = "master";
    	expecteddb2.environment_id = 2;
    	expecteddb2.destination = expected;

    	expecteddb3.id = 3;
    	expecteddb3.destination_id = 4;
    	expecteddb3.url = "db-master.local/99hakai_mbga";
    	expecteddb3.db_id = "master";
    	expecteddb3.db_password = "master";
    	expecteddb3.environment_id = 3;
    	expecteddb3.destination = expected;

    	List<DestinationDbInfo> destinationDbInfolist = new ArrayList<DestinationDbInfo>();

    	destinationDbInfolist.add(expecteddb1);
    	destinationDbInfolist.add(expecteddb2);
    	destinationDbInfolist.add(expecteddb3);

    	expected.destinationDbInfoList = destinationDbInfolist;

    	assertEqualsBean(des, expected);

    	assertEqualsListBean(des.destinationDbInfoList, expected.destinationDbInfoList);
    }

    @Test
    public void testGetDestinationByIdFullJoin() {
    	Destination des = contentManageService.getDestinationByIdFullJoin("4");
    	Destination expected = new Destination();

    	expected.id = 4;
    	expected.content_id = 2;
    	expected.destination_name = "テスト4";

    	Content content = new Content();
    	content.id = 2;
    	content.content_name = "破壊９９";

    	DestinationDbInfo expecteddb1 = new DestinationDbInfo();
    	DestinationDbInfo expecteddb2 = new DestinationDbInfo();
    	DestinationDbInfo expecteddb3 = new DestinationDbInfo();

    	expecteddb1.id = 1;
    	expecteddb1.destination_id = 4;
    	expecteddb1.url = "tdb-master.local/99hakai_mbga_dev";
    	expecteddb1.db_id = "master";
    	expecteddb1.db_password = "master";
    	expecteddb1.environment_id = 1;
    	expecteddb1.destination = expected;

    	expecteddb2.id = 2;
    	expecteddb2.destination_id = 4;
    	expecteddb2.url = "tdb-master.local/99hakai_mbga";
    	expecteddb2.db_id = "master";
    	expecteddb2.db_password = "master";
    	expecteddb2.environment_id = 2;
    	expecteddb2.destination = expected;

    	expecteddb3.id = 3;
    	expecteddb3.destination_id = 4;
    	expecteddb3.url = "db-master.local/99hakai_mbga";
    	expecteddb3.db_id = "master";
    	expecteddb3.db_password = "master";
    	expecteddb3.environment_id = 3;
    	expecteddb3.destination = expected;

    	List<DestinationDbInfo> destinationDbInfolist = new ArrayList<DestinationDbInfo>();

    	destinationDbInfolist.add(expecteddb1);
    	destinationDbInfolist.add(expecteddb2);
    	destinationDbInfolist.add(expecteddb3);

    	List<Destination> destinationList = new ArrayList<Destination>();
    	destinationList.add(expected);

    	expected.content = content;
    	content.destinationList = destinationList;
    	expected.destinationDbInfoList = destinationDbInfolist;

    	assertEqualsBean(des, expected);
    	assertEqualsListBean(des.destinationDbInfoList, expected.destinationDbInfoList);
    }

    @Test
    public void testGetDestinationsByIdList() {
    	String[] idList = {"1","2","3","4"};
    	List<Destination> desList = contentManageService.getDestinationsByIdList(idList);

    	Destination expected1 = new Destination();

    	expected1.id = 1;
    	expected1.content_id = 1;
    	expected1.destination_name = "テスト1";

    	Destination expected2 = new Destination();

    	expected2.id = 2;
    	expected2.content_id = 1;
    	expected2.destination_name = "テスト2";

    	Destination expected3 = new Destination();

    	expected3.id = 3;
    	expected3.content_id = 1;
    	expected3.destination_name = "テスト3";

    	Destination expected4 = new Destination();

    	expected4.id = 4;
    	expected4.content_id = 2;
    	expected4.destination_name = "テスト4";

    	List<Destination> destinationList = new ArrayList<Destination>();
    	destinationList.add(expected1);
    	destinationList.add(expected2);
    	destinationList.add(expected3);
    	destinationList.add(expected4);

    	assertEqualsListBean(desList, destinationList);

    }

    @Test
    public void testDeleteDestination() {

    	boolean check = contentManageService.deleteDestinatio("4");

    	assertTrue(check);

    	DataSet dataSet = ctx.getExpected();

    	DataTable expected1 = accessor.readDbByTable("destination");
    	DataTable actual1 = dataSet.getTable("destination");

    	DataTable expected2 = accessor.readDbByTable("destination_db_info");
    	DataTable actual2 = dataSet.getTable("destination_db_info");
    	assertEquals(expected1, actual1);
    	assertEquals(expected2, actual2);

    }

    @Test
    public void testDeleteDestinatioByContentid() {
    	boolean check = contentManageService.deleteDestinatioByContentid("2");

    	assertTrue(check);
    	DataSet dataSet = ctx.getExpected();

    	DataTable expected1 = accessor.readDbByTable("destination");
    	DataTable actual1 = dataSet.getTable("destination");

    	DataTable expected2 = accessor.readDbByTable("destination_db_info");
    	DataTable actual2 = dataSet.getTable("destination_db_info");
    	assertEquals(expected1, actual1);
    	assertEquals(expected2, actual2);

    }



    @Test
    public void testDeleteContent() {
    	boolean check = contentManageService.deleteContent("2");

    	assertTrue(check);
    	DataSet dataSet = ctx.getExpected();

    	DataTable expected1 = accessor.readDbByTable("destination");
    	DataTable actual1 = dataSet.getTable("destination");

    	DataTable expected2 = accessor.readDbByTable("destination_db_info");
    	DataTable actual2 = dataSet.getTable("destination_db_info");

    	DataTable expected3 = accessor.readDbByTable("destination_db_info");
    	DataTable actual3 = dataSet.getTable("destination_db_info");
    	assertEquals(expected1, actual1);
    	assertEquals(expected2, actual2);
    	assertEquals(expected1, actual1);
    	assertEquals(expected3, actual3);

    }
    @Test
    public void testDeleteContentByEdit() {
    	boolean check = contentManageService.deleteContentByEdit("2");

    	assertTrue(check);
    	DataSet dataSet = ctx.getExpected();

    	DataTable expected1 = accessor.readDbByTable("contents");
    	DataTable actual1 = dataSet.getTable("contents");

    	DataTable expected2 = accessor.readDbByTable("columns");
    	DataTable actual2 = dataSet.getTable("columns");

    	DataTable expected3 = accessor.readDbByTable("table_info");
    	DataTable actual3 = dataSet.getTable("table_info");
    	assertEquals(expected1, actual1);
    	assertEquals(expected2, actual2);
    	assertEquals(expected1, actual1);
    	assertEquals(expected3, actual3);


    }
    @Test
    public void testInsertContentInfo1() {
    	//String ernFile = ResourceUtil.convertPath("src/main/resources/contentManage.erm", getClass());
    	File ernFile = new File("src/test/resources/jp/co/gmod/manage/service/contentManage.erm");
    	boolean check = contentManageService.insertContentInfo("test1", ernFile);

    	assertTrue(check);

    }

    @Test
    public void testInsertContentInfo2() {
    	//String ernFile = ResourceUtil.convertPath("src/main/resources/contentManage.erm", getClass());
    	File ernFile = new File("src/test/resources/jp/co/gmod/manage/service/test.erm");
    	boolean check = contentManageService.insertContentInfo("test1", ernFile);

    	DataSet dataSet = ctx.getExpected();
    	DataTable expected1 = accessor.readDbByTable("contents");
    	DataTable actual1 = dataSet.getTable("contents");

    	DataTable expected2 = accessor.readDbByTable("columns");
    	DataTable actual2 = dataSet.getTable("columns");

    	DataTable expected3 = accessor.readDbByTable("table_info");
    	DataTable actual3 = dataSet.getTable("table_info");

    	String[] notColumn = {"id"};

    	assertEqualsTableExceptColumn("", expected1, actual1, notColumn);
    	assertEqualsTableExceptColumn("", expected2, actual2, notColumn);
    	assertEqualsTableExceptColumn("", expected3, actual3, notColumn);

    	assertTrue(check);

    }

    @Test
    public void testInsertContentInfo3() {
    	//String ernFile = ResourceUtil.convertPath("src/main/resources/contentManage.erm", getClass());
    	File ernFile = new File("src/test/resources/jp/co/gmod/manage/service/skygalleon_db.erm");
    	boolean check = contentManageService.insertContentInfo("test3", ernFile);
    	assertTrue(check);

    }

    @Test
    public void testUpdateContentInfo() {
    	//String ernFile = ResourceUtil.convertPath("src/main/resources/contentManage.erm", getClass());
    	File inserternFile = new File("src/test/resources/jp/co/gmod/manage/service/test.erm");
    	File updateernFile = new File("src/test/resources/jp/co/gmod/manage/service/test_update.erm");
    	boolean check1 = contentManageService.insertContentInfo("test1", inserternFile);
    	boolean check2 = contentManageService.deleteContentByEdit("4");
    	boolean check3 = contentManageService.updateContentInfo("test1", updateernFile, "4");

    	assertTrue(check1);
    	assertTrue(check2);
    	assertTrue(check3);

    	DataSet dataSet = ctx.getExpected();
    	DataTable expected1 = accessor.readDbByTable("contents");
    	DataTable actual1 = dataSet.getTable("contents");

    	DataTable expected2 = accessor.readDbByTable("columns");
    	DataTable actual2 = dataSet.getTable("columns");

    	DataTable expected3 = accessor.readDbByTable("table_info");
    	DataTable actual3 = dataSet.getTable("table_info");

    	String[] notColumn = {"id"};

    	assertEqualsTableExceptColumn("", expected1, actual1, notColumn);
    	assertEqualsTableExceptColumn("", expected2, actual2, notColumn);
    	assertEqualsTableExceptColumn("", expected3, actual3, notColumn);

    }

    @Test
    public void testAddTable() {

    	File inserternFile = new File("src/test/resources/jp/co/gmod/manage/service/test.erm");
    	File addernFile = new File("src/test/resources/jp/co/gmod/manage/service/addtable.erm");
    	boolean check1 = contentManageService.insertContentInfo("test1", inserternFile);
    	boolean check2 = contentManageService.addTable(addernFile, "4");

    	assertTrue(check1);
    	assertTrue(check2);

    	DataSet dataSet = ctx.getExpected();
    	DataTable expected1 = accessor.readDbByTable("contents");
    	DataTable actual1 = dataSet.getTable("contents");

    	DataTable expected2 = accessor.readDbByTable("columns");
    	DataTable actual2 = dataSet.getTable("columns");

    	DataTable expected3 = accessor.readDbByTable("table_info");
    	DataTable actual3 = dataSet.getTable("table_info");

    	String[] notColumn = {"id"};

    	assertEqualsTableExceptColumn("", expected1, actual1, notColumn);
    	assertEqualsTableExceptColumn("", expected2, actual2, notColumn);
    	assertEqualsTableExceptColumn("", expected3, actual3, notColumn);
    }

    @Test
    public void testGroupColumn() {

    	File ernFile = new File("src/test/resources/jp/co/gmod/manage/service/grouptest.erm");
    	boolean check = contentManageService.insertContentInfo("test1", ernFile);

    	DataSet dataSet = ctx.getExpected();
    	DataTable expected1 = accessor.readDbByTable("contents");
    	DataTable actual1 = dataSet.getTable("contents");

    	DataTable expected2 = accessor.readDbByTable("columns");
    	DataTable actual2 = dataSet.getTable("columns");

    	DataTable expected3 = accessor.readDbByTable("table_info");
    	DataTable actual3 = dataSet.getTable("table_info");

    	String[] notColumn = {"id"};

    	assertEqualsTableExceptColumn("", expected1, actual1, notColumn);
    	assertEqualsTableExceptColumn("", expected2, actual2, notColumn);
    	assertEqualsTableExceptColumn("", expected3, actual3, notColumn);

    	assertTrue(check);
    }


    @Test
    public void testGroupColumn2() {

    	File ernFile = new File("src/test/resources/jp/co/gmod/manage/service/grouptest2.erm");
    	boolean check = contentManageService.insertContentInfo("test1", ernFile);

    	DataSet dataSet = ctx.getExpected();
    	DataTable expected1 = accessor.readDbByTable("contents");
    	DataTable actual1 = dataSet.getTable("contents");

    	DataTable expected2 = accessor.readDbByTable("columns");
    	DataTable actual2 = dataSet.getTable("columns");

    	DataTable expected3 = accessor.readDbByTable("table_info");
    	DataTable actual3 = dataSet.getTable("table_info");

    	String[] notColumn = {"id","seq_id"};

    	assertEqualsTableExceptColumn("", expected1, actual1, notColumn);
    	assertEqualsTableExceptColumn("", expected2, actual2, notColumn);
    	assertEqualsTableExceptColumn("", expected3, actual3, notColumn);

    	assertTrue(check);
    }

    @Test
    public void testInsertDestinationInfo() {

    	DestinationForm form = new DestinationForm();

    	form.contentid = "3";

    	form.contentname = "コンテンツ管理";

    	form.destinationName = "配布先１";
    	form.devUrl = "dev_test_url";
    	form.devId = "dev_test_id";
    	form.devPassword = "dev_test_pwd";

    	form.stgUrl = "stg_test_url";
    	form.stgId = "stg_test_id";
    	form.stgPassword = "stg_test_pwd";

    	form.pubUrl = "pub_test_url";
    	form.pubId = "pub_test_id";
    	form.pubPassword = "pub_test_pwd";


    	contentManageService.insertDestinationInfo(form);

    	DataSet dataSet = ctx.getExpected();
    	DataTable expected1 = accessor.readDbByTable("destination");
    	DataTable actual1 = dataSet.getTable("destination");

    	DataTable expected2 = accessor.readDbByTable("destination_db_info");
    	DataTable actual2 = dataSet.getTable("destination_db_info");

    	String[] notColumn = {"id"};
    	assertEqualsTableExceptColumn("", expected1, actual1, notColumn);
    	assertEqualsTableExceptColumn("", expected2, actual2, notColumn);

    }

    @Test
    public void testUpdateDestinationInfo() {

    	DestinationForm insertform = new DestinationForm();

    	insertform.contentid = "3";

    	insertform.contentname = "コンテンツ管理";

    	insertform.destinationName = "配布先１";
    	insertform.devUrl = "dev_test_url";
    	insertform.devId = "dev_test_id";
    	insertform.devPassword = "dev_test_pwd";

    	insertform.stgUrl = "stg_test_url";
    	insertform.stgId = "stg_test_id";
    	insertform.stgPassword = "stg_test_pwd";

    	insertform.pubUrl = "pub_test_url";
    	insertform.pubId = "pub_test_id";
    	insertform.pubPassword = "pub_test_pwd";


    	contentManageService.insertDestinationInfo(insertform);



    	Destination destination = contentManageService.getDestinationById("5");

    	DestinationForm form = new DestinationForm();

    	form.contentid = "3";

    	form.contentname = "コンテンツ管理";

    	form.destinationName = "配布先１";
    	form.devUrl = "update_dev_test_url";
    	form.devId = "update_dev_test_id";
    	form.devPassword = "update_dev_test_pwd";

    	form.stgUrl = "update_stg_test_url";
    	form.stgId = "update_stg_test_id";
    	form.stgPassword = "update_stg_test_pwd";

    	form.pubUrl = "update_pub_test_url";
    	form.pubId = "update_pub_test_id";
    	form.pubPassword = "update_pub_test_pwd";

    	contentManageService.updateDestinationInfo(destination, form);

    	DataSet dataSet = ctx.getExpected();
    	DataTable expected1 = accessor.readDbByTable("destination");
    	DataTable actual1 = dataSet.getTable("destination");

    	DataTable expected2 = accessor.readDbByTable("destination_db_info");
    	DataTable actual2 = dataSet.getTable("destination_db_info");

    	String[] notColumn = {"id"};
    	assertEqualsTableExceptColumn("", expected1, actual1, notColumn);
    	assertEqualsTableExceptColumn("", expected2, actual2, notColumn);

    }
    @Test
    public void testUpdateDestinationInfo2() {

    	DestinationForm insertform = new DestinationForm();

    	insertform.contentid = "3";

    	insertform.contentname = "コンテンツ管理";

    	insertform.destinationName = "配布先１";
    	insertform.devUrl = "";
    	insertform.devId = "";
    	insertform.devPassword = "";

    	insertform.stgUrl = "";
    	insertform.stgId = "";
    	insertform.stgPassword = "";

    	insertform.pubUrl = "";
    	insertform.pubId = "";
    	insertform.pubPassword = "";


    	contentManageService.insertDestinationInfo(insertform);



    	Destination destination = contentManageService.getDestinationById("5");

    	DestinationForm form = new DestinationForm();

    	form.contentid = "3";

    	form.contentname = "コンテンツ管理";

    	form.destinationName = "配布先１";
    	form.devUrl = "update_dev_test_url";
    	form.devId = "update_dev_test_id";
    	form.devPassword = "update_dev_test_pwd";

    	form.stgUrl = "update_stg_test_url";
    	form.stgId = "update_stg_test_id";
    	form.stgPassword = "update_stg_test_pwd";

    	form.pubUrl = "update_pub_test_url";
    	form.pubId = "update_pub_test_id";
    	form.pubPassword = "update_pub_test_pwd";

    	contentManageService.updateDestinationInfo(destination, form);

    	DataSet dataSet = ctx.getExpected();
    	DataTable expected1 = accessor.readDbByTable("destination");
    	DataTable actual1 = dataSet.getTable("destination");

    	DataTable expected2 = accessor.readDbByTable("destination_db_info");
    	DataTable actual2 = dataSet.getTable("destination_db_info");

    	String[] notColumn = {"id"};
    	assertEqualsTableExceptColumn("", expected1, actual1, notColumn);
    	assertEqualsTableExceptColumn("", expected2, actual2, notColumn);

    }


    private void assertEqualsListBean(List<?> list1, List<?> list2) {

    	 assertEquals("ListSize", list1.size(), list2.size());

    	int i = 0;
    	for (Object obj1 : list1) {

    		assertEqualsBean(obj1, list2.get(i));
    		i++;

    	}
    }

    public void assertEqualsTableExceptColumn(String message, DataTable actual, DataTable expected, String[] columnname)
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
