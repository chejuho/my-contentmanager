package jp.co.gmod.manage.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jp.co.gmod.manage.constant.ErrorMessageConstant;
import jp.co.gmod.manage.entity.Column;
import jp.co.gmod.manage.exception.AppException;

import org.mozilla.universalchardet.UniversalDetector;
import org.seasar.framework.beans.util.BeanMap;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

public class CSVFileUtil {


	private final static Pattern pattern = Pattern.compile("\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}");

	public static String dataToString(List<BeanMap> list, List<Column> columns) throws IOException {

		StringWriter stringWriter = new StringWriter();
		CSVWriter writer = new CSVWriter(stringWriter);
		try {

			//ヘッダ出力
			String header[] = getHeader(columns);
			writer.writeNext(header);
			for (BeanMap map : list) {
				List<String> datalist = new ArrayList<String>();
				for (Object value : map.values()) {
					if (value == null) {
						datalist.add("");
					} else {
						datalist.add(getFilterDateType(value.toString()));
					}
				}
				writer.writeNext(datalist.toArray(new String[0]));
			}
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				throw e;
			}
		}
		return stringWriter.toString();

	}



	public static List<BeanMap> fileToBeanMap(List<Column> columns, File file) throws AppException {

		List<BeanMap> list = new ArrayList<BeanMap>();

		String encoding = CSVFileUtil.getEncoding(file);

		try {
			CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(file), encoding));
			String [] nextLine= null;
			Map<String, Integer> head = null;
			boolean first = true;
			while ((nextLine = reader.readNext()) != null) {
				if (first) {
					first = false;
					//if (nextLine.length != columns.size()) {
					//	throw new AppException(ErrorMessageConstant.IMPORT_NOT_MATCH);
					//}
					head = getHeaderInfoToMap(nextLine);
				} else {
					BeanMap beanMap = new BeanMap();
					for (Column column : columns) {
						beanMap.put(column.physical_name, nextLine[head.get(column.physical_name)]);
					}
					list.add(beanMap);
				}
			}

		} catch (IOException e) {
			throw new AppException(ErrorMessageConstant.IMPORT_NOT_MATCH, e);
		}

		return list;

	}


	public static String getEncoding(File file) {
		String encoding = null;
		UniversalDetector detector = new UniversalDetector(null);
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			int nread;
			byte[] buf = new byte[4096];
			while ((nread = fis.read(buf)) > 0 && !detector.isDone()) {
				detector.handleData(buf, 0, nread);
			}
			// (3)
			detector.dataEnd();
			// (4)
			encoding = detector.getDetectedCharset();
			// Log.d("getEncoding", encoding);
			// (5)
			detector.reset();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fis != null) {
					fis.close();
				}
			} catch (IOException e) {
				e.printStackTrace();

			}
		}
		if (encoding == null) {
			encoding = "UTF-8";
		}

		return encoding;
	}

	private static Map<String, Integer> getHeaderInfoToMap(String[] nextLine) {

		Map<String, Integer> map = new HashMap<String, Integer>();
		int index = 0;
		for (String next : nextLine) {
			map.put(next, Integer.valueOf(index));
			index++;
		}
		return map;
	}



	private static String[] getHeader(List<Column> columns) {

		List<String> headers = new ArrayList<String>();

		for (Column column : columns) {
			headers.add(column.physical_name);
		}

		return headers.toArray(new String[] {});
	}

	private static String getFilterDateType(String value) {


		Matcher matcher = pattern.matcher(value);

		if(matcher.find()){
			return matcher.group(0);
		}

		return value;


	}
}
