package jp.co.gmod.manage.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jp.co.gmod.manage.entity.Column;
import jp.co.gmod.manage.exception.AppException;
import junit.framework.AssertionFailedError;

import org.seasar.extension.dataset.ColumnType;
import org.seasar.extension.dataset.DataRow;
import org.seasar.extension.dataset.DataSet;
import org.seasar.extension.dataset.DataTable;
import org.seasar.extension.dataset.types.ColumnTypes;
import org.seasar.extension.unit.BeanReader;
import org.seasar.extension.unit.S2TestCase;
import org.seasar.framework.beans.util.BeanMap;
import org.seasar.framework.unit.S2Assert;



public class CSVFileUtilTest extends S2TestCase{


	 public void testFileToBeanMap() throws AppException {
		 File file = new File("src/test/resources/jp/co/gmod/manage/util/test.csv");

		 List<Column> columns = new ArrayList<Column>();

		 Column column1 = new Column();
		 Column column2 = new Column();
		 Column column3 = new Column();
		 Column column4 = new Column();
		 Column column5 = new Column();
		 Column column6 = new Column();

		 column1.physical_name = "id";
		 column2.physical_name = "cond_max";
		 column3.physical_name = "cond_min";
		 column4.physical_name = "trust_min";
		 column5.physical_name = "trust_max";
		 column6.physical_name = "party_flag";

		 columns.add(column1);
		 columns.add(column2);
		 columns.add(column3);
		 columns.add(column4);
		 columns.add(column5);
		 columns.add(column6);


		 List<BeanMap> list = CSVFileUtil.fileToBeanMap(columns, file);

		 /*
		  * "id","cond_min","cond_max","trust_min","trust_max","party_flag"
			"1","0","39","ﾊﾟｰﾃｨの招待来るかしら","100","0"
			"2","1","39","0","疲れた…お茶飲みたいな…","0"
			"3","2","39","0","100","0"
		  */
		 List<BeanMap> expectedList = new ArrayList<BeanMap>();
		 BeanMap map1 = new BeanMap();
		 BeanMap map2 = new BeanMap();
		 BeanMap map3 = new BeanMap();

		 map1.put("id", "1");
		 map1.put("cond_min", "0");
		 map1.put("cond_max", "39");
		 map1.put("trust_min", "ﾊﾟｰﾃｨの招待来るかしら");
		 map1.put("trust_max", "100");
		 map1.put("party_flag", "0");

		 map2.put("id", "2");
		 map2.put("cond_min", "1");
		 map2.put("cond_max", "39");
		 map2.put("trust_min", "0");
		 map2.put("trust_max", "疲れた…お茶飲みたいな…");
		 map2.put("party_flag", "0");

		 map3.put("id", "3");
		 map3.put("cond_min", "2");
		 map3.put("cond_max", "39");
		 map3.put("trust_min", "0");
		 map3.put("trust_max", "100");
		 map3.put("party_flag", "0");

		 expectedList.add(map1);
		 expectedList.add(map2);
		 expectedList.add(map3);

		 assertEqualsListBean(list, expectedList);





		 for (BeanMap map : list) {
			 int i = 0;
			 for (String key : map.keySet()) {
				 if (i == 0) {
					 assertEquals("id", key);
				 }
				 if (i == 1) {
					 assertEquals("cond_max", key);
				 }
				 if (i == 2) {
					 assertEquals("cond_min", key);
				 }
				 if (i == 3) {
					 assertEquals("trust_min", key);
				 }
				 if (i == 4) {
					 assertEquals("trust_max", key);
				 }
				 if (i == 5) {
					 assertEquals("party_flag", key);
				 }
				 i++;
			 }

		 }

	 }

	 public void testDataToString() {

		 List<BeanMap> list = new ArrayList<BeanMap>();
		 List<Column> columns =  new ArrayList<Column>();

		 BeanMap map1 = new BeanMap();
		 BeanMap map2 = new BeanMap();
		 BeanMap map3 = new BeanMap();

		 map1.put("id", "1");
		 map1.put("cond_min", "0");
		 map1.put("cond_max", "100");
		 map1.put("trust_min", "2");
		 map1.put("trust_max", "3");

		 map2.put("id", "2");
		 map2.put("cond_min", "0");
		 map2.put("cond_max", "1000");
		 map2.put("trust_min", "4");
		 map2.put("trust_max", "5");

		 map3.put("id", "3");
		 map3.put("cond_min", "0");
		 map3.put("cond_max", "10000");
		 map3.put("trust_min", "6");
		 map3.put("trust_max", "7");

		 list.add(map1);
		 list.add(map2);
		 list.add(map3);


		 Column column1 = new Column();
		 Column column2 = new Column();
		 Column column3 = new Column();
		 Column column4 = new Column();
		 Column column5 = new Column();

		 column1.physical_name = "id";
		 column2.physical_name = "cond_min";
		 column3.physical_name = "cond_max";
		 column4.physical_name = "trust_min";
		 column5.physical_name = "trust_max";

		 columns.add(column1);
		 columns.add(column2);
		 columns.add(column3);
		 columns.add(column4);
		 columns.add(column5);




		 String output = null;
		 try {
			output = CSVFileUtil.dataToString(list, columns);
		} catch (IOException e) {
			e.printStackTrace();
		}
		StringBuilder expected = new StringBuilder();
		expected.append("\"id\",\"cond_min\",\"cond_max\",\"trust_min\",\"trust_max\"\n");
		expected.append("\"1\",\"0\",\"100\",\"2\",\"3\"\n");
		expected.append("\"2\",\"0\",\"1000\",\"4\",\"5\"\n");
		expected.append("\"3\",\"0\",\"10000\",\"6\",\"7\"\n");
		assertEquals(expected.toString(), output);

	 }

	 private void assertEqualsListBean(List<BeanMap> list1, List<BeanMap> list2) {

    	 assertEquals("ListSize", list1.size(), list2.size());

    	 /**
    	  *  map1.put("id", "1");
		 map1.put("cond_min", "0");
		 map1.put("cond_max", "100");
		 map1.put("trust_min", "2");
		 map1.put("trust_max", "3");

    	  */
    	int i = 0;
    	for (BeanMap obj1 : list1) {
    		assertEquals(obj1.get("id"), obj1.get("id"));
    		assertEquals(obj1.get("cond_min"), obj1.get("cond_min"));
    		assertEquals(obj1.get("cond_max"), obj1.get("cond_max"));
    		assertEquals(obj1.get("trust_min"), obj1.get("trust_min"));
    		assertEquals(obj1.get("trust_max"), obj1.get("trust_max"));
    		i++;

    	}
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
