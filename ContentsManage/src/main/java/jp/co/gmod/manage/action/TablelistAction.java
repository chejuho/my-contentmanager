package jp.co.gmod.manage.action;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import jp.co.gmod.manage.condition.PagerCondition;
import jp.co.gmod.manage.constant.ErrorMessageConstant;
import jp.co.gmod.manage.dto.TableDto;
import jp.co.gmod.manage.dto.TableInfoDto;
import jp.co.gmod.manage.entity.Column;
import jp.co.gmod.manage.entity.Destination;
import jp.co.gmod.manage.entity.TableInfo;
import jp.co.gmod.manage.exception.AppException;
import jp.co.gmod.manage.service.ContentsManageService;
import jp.co.gmod.manage.service.CotentsColorService;
import jp.co.gmod.manage.service.TableInfoService;
import jp.co.gmod.manage.util.CSVFileUtil;
import jp.co.gmod.manage.util.PageHelper;
import jp.co.gmod.manage.util.RequestHandleUtil;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;
import org.apache.struts.util.TokenProcessor;
import org.seasar.extension.jdbc.exception.SEntityExistsException;
import org.seasar.framework.beans.util.BeanMap;
import org.seasar.framework.container.annotation.tiger.Binding;
import org.seasar.framework.container.annotation.tiger.BindingType;
import org.seasar.framework.util.StringUtil;
import org.seasar.struts.annotation.Execute;
import org.seasar.struts.annotation.Required;
import org.seasar.struts.exception.ActionMessagesException;
import org.seasar.struts.util.ActionMessagesUtil;
import org.seasar.struts.util.ResponseUtil;


public class TablelistAction {

	public ServletContext application;

	private final static String ENCODING = "Shift_JIS";

	private final static String INIT_PAGE = "init";

	//1ページ当たりのレコード数
	private final static int PER_PAGE = 10;

    private final static String SEARCH_KEY = "search";
    private final static String SEARCH_OPTION_KEY = "searchoption";

    private final static int PER_PAGE_GROUP = 20;

    private final static int DEV_ID = 1;
	private final static String DEV_KEY = "dev";

	public String selectDestinationId;
	public String selectedEv;

    //テーブル情報 テーブルリスト画面で使う
	public List<TableInfoDto> tableInfoList;
	//カラムリスト テーブル詳細画面で使う
	public List<Column> columnList = null;
	//データリスト  テーブル詳細画面で使う
	public List<BeanMap> dataList = null;
	//確認画面で使う
	public Map<String, String> dataMap;
	//ページリンク文字列
    public String pageLink;
    //テーブル情報
    public TableInfo tableInfo_display;
    //コンテンツ別色
    public String colors;

    /**Form データ **/
    //編集するID
    public String primaryid;
    //テーブル名
    public String tableid;
    //詳細画面でチェックしたID
    public String[] checks;
    //削除するリスト
    public String[] deleteList;
    //パブリッシュするリスト
    public String[] publishidList;
    //パブリッシュする環境
    public String publishEv;
    //詳細画面で表示する件数
    public String displayKensu;
    //page
	public String pageNum;
	 //エクスポートテーブル名
	public String exporttableid;
	 //サーチ
	public String search;
	 //サーチOption
	public String searchoption;

	 //ページキー
	public String pageInfoKey;

	@Required
	@Binding(bindingType = BindingType.NONE)
	public FormFile csvFormFile;

	@Resource
	protected HttpSession session;
	@Resource
	protected HttpServletRequest request;
	@Resource
	protected TableInfoService tableInfoService;
	@Resource
	protected ContentsManageService contentsManageService;
	@Resource
	protected CotentsColorService cotentsColorService;


	@Execute(validator = false)
	public String index() {

		selectDestinationId = request.getParameter("id");
		selectedEv = DEV_KEY;

		Destination destinationInfo = contentsManageService.getDestinationByIdFullJoin(selectDestinationId);
		//DB環境設定
		tableInfoService.setEvSetting(destinationInfo, DEV_ID);
		tableInfoList = tableInfoService.getTableInfo(destinationInfo);

		return "index.jsp";
	 }

	@Execute(validator = false)
	public String allexport() {

		TableDto tableDto = tableInfoService.getTableDataAll(exporttableid);

		TableInfo tableInfo = tableDto.getTableInfo();
		List<Column> columns = tableDto.getColumnList();
		List<BeanMap> csvdataList = tableDto.getDataList();

		String output = null;
		try {
			output = CSVFileUtil.dataToString(csvdataList, columns);
			ResponseUtil.download(new String(tableInfo.physical_name + ".csv"), output.getBytes(ENCODING));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return null;
	 }

	@Execute(validator = false, urlPattern="{selectDestinationId}/{selectedEv}/exportopen")
	public String exportopen() {

		TableDto tableDto = tableInfoService.getTableDataByIdList(tableid, checks);
		TableInfo tableInfo = tableDto.getTableInfo();
		List<Column> columns = tableDto.getColumnList();
		List<BeanMap> csvdataList = tableDto.getDataList();

		String output = null;
		try {
			output = CSVFileUtil.dataToString(csvdataList, columns);
			ResponseUtil.download(new String(tableInfo.physical_name + ".csv"), output.getBytes(ENCODING));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return null;
	}

	@Execute(validator = false, urlPattern="{selectDestinationId}/{selectedEv}/importopen")
	public String importopen() {

		//Tokenの生成
        TokenProcessor.getInstance().saveToken(request);
		tableInfo_display = tableInfoService.getTableInfoByTableId(tableid);
		return "importopen.jsp";
	}

	@Execute(input = "{selectDestinationId}/{selectedEv}/importopen", urlPattern="{selectDestinationId}/{selectedEv}/importcsvdata")
	public String importcsvdata() {

		String csvFilePath = upload(csvFormFile);

		File csvFile = new File(csvFilePath);
		List<BeanMap> list = null;
		List<Column> columnList = tableInfoService.getTableColumnListByTableId(tableid);
		try {
			list = CSVFileUtil.fileToBeanMap(columnList, csvFile);
			tableInfoService.importCsvData(list, tableid);
		} catch (AppException e) {
			throw new ActionMessagesException(e.getMessage());
		} catch (SEntityExistsException se) {
			throw new ActionMessagesException(ErrorMessageConstant.ENTITY_EXISTS_EXCEPTION);
		}

		return getDetailPage(selectDestinationId, selectedEv, tableid, "init", true);
	}

	@Execute(validator = false, urlPattern="list/{selectDestinationId}/{selectedEv}")
	public String list() {

		Destination destinationInfo = contentsManageService.getDestinationByIdFullJoin(selectDestinationId);
		tableInfoList = tableInfoService.getTableInfo(destinationInfo);
		return "index.jsp";
	 }

	@Execute(validator = false, urlPattern="changeEv/{selectDestinationId}/{selectedEv}")
	public String changeEvFromList() {

		Destination destinationInfo = contentsManageService.getDestinationByIdFullJoin(selectDestinationId);
		tableInfoList = tableInfoService.getTableInfo(destinationInfo);
		return "index.jsp";
	 }

	@Execute(validator = false, urlPattern="changeEv/{selectDestinationId}/{selectedEv}/{tableid}")
	public String changeEvFromDetail() {

		return getDetailPage(selectDestinationId, selectedEv, tableid, "init", true);
	 }

	@Execute(validator = false, urlPattern="{selectDestinationId}/{selectedEv}/displayKensuChange")
	 public String displayKensuChange() {
		session.setAttribute("displaykensu", Integer.valueOf(displayKensu));
		return getDetailPage(selectDestinationId, selectedEv, tableid, "init", true);
	}

	@Execute(validator = false, urlPattern="{selectDestinationId}/{selectedEv}/searchdetail/{tableid}/{pageNum}")
	 public String searchdetail() {


		if (search == null) {
			search = (String) session.getAttribute(SEARCH_KEY);
			searchoption = (String) session.getAttribute(SEARCH_OPTION_KEY);

		} else {
			search = StringUtil.rtrim(search);
			pageNum = INIT_PAGE;
		}

		session.setAttribute(SEARCH_KEY, search);
		session.setAttribute(SEARCH_OPTION_KEY, searchoption);
		//表示件数
		Integer displaykensu = setDisplaykensu();

		pageInfoKey = tableid + selectedEv;

		//ページ情報取得
		PagerCondition pagerCondition = PageHelper.getPager(session, pageInfoKey, pageNum);
		//MAX取得
		int maxCnt = tableInfoService.getMaxCntBySearch(tableid, pagerCondition, search, searchoption);
		PagerCondition page = PageHelper.pageProcess(session, pageInfoKey, pageNum, displaykensu, PER_PAGE_GROUP, maxCnt);

		TableDto tableDto = tableInfoService.getTableDataListByIndexSearch(tableid, search, page, searchoption);
		//ページリンク文字列作成
		pageLink = PageHelper.getView(session, pageInfoKey);
		tableInfo_display = tableDto.getTableInfo();
		columnList = tableDto.getColumnList();
		dataList = tableDto.getDataList();

		return "detail.jsp";
	}

	@Execute(validator = false, urlPattern="{selectDestinationId}/{selectedEv}/detail/{tableid}/{pageNum}")
	 public String detail() {

		session.removeAttribute(SEARCH_KEY);
		session.removeAttribute(SEARCH_OPTION_KEY);

		//表示件数
		Integer displaykensu = setDisplaykensu();

		pageInfoKey = tableid + selectedEv;

		//ページ情報取得
		PagerCondition pagerCondition = PageHelper.getPager(session, pageInfoKey, pageNum);
		//MAX取得
		int maxCnt = tableInfoService.getMaxCnt(tableid, pagerCondition);
		PagerCondition page = PageHelper.pageProcess(session, pageInfoKey, pageNum, displaykensu, PER_PAGE_GROUP, maxCnt);

		TableDto tableDto = tableInfoService.getTableDataList(tableid, page);

		if (tableDto == null) {
			saveError(session, ErrorMessageConstant.TABLE_NOT_EXIST);
			return "index?id=" + selectDestinationId + "&redirect=true";
		}

		pageLink = PageHelper.getView(session, pageInfoKey);
		tableInfo_display = tableDto.getTableInfo();
		columnList = tableDto.getColumnList();
		dataList = tableDto.getDataList();

		return "detail.jsp";
	 }



	@Execute(validator = false, urlPattern="{selectDestinationId}/{selectedEv}/publishopen")
	 public String publishopen() {
		publishidList = checks;
		TableDto tableDto = tableInfoService.getTableDataByIdList(tableid, publishidList);
		columnList = tableDto.getColumnList();
		dataList = tableDto.getDataList();

		return "publishopen.jsp";
	}

	@Execute(validator = false, urlPattern="{selectDestinationId}/{selectedEv}/publishconfirm")
	 public String publishconfirm() {
		//Tokenの生成
        TokenProcessor.getInstance().saveToken(request);
		TableDto tableDto = tableInfoService.getTableDataByIdList(tableid, publishidList);
		columnList = tableDto.getColumnList();
		dataList = tableDto.getDataList();

		return "publishconfirm.jsp";
	}

	@Execute(validator = false, urlPattern="{selectDestinationId}/{selectedEv}/publishmodoru")
	 public String publishmodoru() {
		TableDto tableDto = tableInfoService.getTableDataByIdList(tableid, publishidList);
		columnList = tableDto.getColumnList();
		dataList = tableDto.getDataList();

		return "publishopen.jsp";
	}
	@Execute(validator = false, validate= "tokenValidate", input="{selectDestinationId}/{selectedEv}/publishconfirm", urlPattern="{selectDestinationId}/{selectedEv}/publish")
	 public String publish() {

		Destination destinationInfo = contentsManageService.getDestinationById(selectDestinationId);
		String publishTargetEv = publishEv;
		try {
			tableInfoService.publishData(tableid, destinationInfo, Integer.valueOf(publishTargetEv), publishidList);
		} catch (SEntityExistsException se) {
			// エラーメッセージをセットします。
			throw new ActionMessagesException(ErrorMessageConstant.ENTITY_EXISTS_EXCEPTION);
		}

		return getDetailPage(selectDestinationId, selectedEv, tableid, "init", true);
	}

	@Execute(validator = false, urlPattern="{selectDestinationId}/{selectedEv}/deleteopen")
	 public String deleteopen() {

		deleteList = checks;
		TableDto tableDto = tableInfoService.getTableDataByIdList(tableid, checks);
		columnList = tableDto.getColumnList();
		dataList = tableDto.getDataList();

		return "deleteopen.jsp";
	}


	@Execute(validator = false, urlPattern="{selectDestinationId}/{selectedEv}/delete")
	 public String delete() {
		tableInfoService.deleteTable(tableid, deleteList);
		return getDetailPage(selectDestinationId, selectedEv, tableid, "init", true);
	}

	@Execute(validator = false, urlPattern="{selectDestinationId}/{selectedEv}/editopen")
	 public String editopen() {

		colors = getColors(selectDestinationId);

		TableDto tableDto = tableInfoService.getTableDataById(tableid, primaryid);

		tableInfo_display = tableDto.getTableInfo();
		columnList = tableDto.getColumnList();
		dataList = tableDto.getDataList();

		BeanMap tableData = dataList.get(0);

		if (dataMap == null) {
			dataMap = new HashMap<String, String>();
		} else {
			dataMap.clear();
		}
		int i = 0;
		for (Object data : tableData.values()) {
			Column column = columnList.get(i);

			if (data != null) {
				dataMap.put(column.physical_name, data.toString());
			} else {
				dataMap.put(column.physical_name, "");
			}
			i++;
		}
		return "editopen.jsp";
	}

	@Execute(validator = false, urlPattern="{selectDestinationId}/{selectedEv}/editconfirm")
	 public String editconfirm() {
		//Tokenの生成
        TokenProcessor.getInstance().saveToken(request);

		TableDto tableDto = tableInfoService.getTableData(tableid);

		tableInfo_display = tableDto.getTableInfo();
		columnList = tableDto.getColumnList();

		dataMap = requestToDataMap(columnList, request);

		return "editconfirm.jsp";
	}
	@Execute(validator = false, urlPattern="{selectDestinationId}/{selectedEv}/editmodoru")
	 public String editmodoru() {

		colors = getColors(selectDestinationId);

		TableDto tableDto = tableInfoService.getTableData(tableid);

		tableInfo_display = tableDto.getTableInfo();
		columnList = tableDto.getColumnList();

		dataMap = requestToDataMap(columnList, request);

		return "editopen.jsp";
	}
	@Execute(validator = false, redirect=true, urlPattern="{selectDestinationId}/{selectedEv}/edit")
	 public String edit() {

		if (TokenProcessor.getInstance().isTokenValid(request, true)) {
			List<Column> columns = tableInfoService.getTableColumnListByTableId(tableid);
			BeanMap requestMap = RequestHandleUtil.requestToBeanMap(request, columns);
			tableInfoService.updateTable(tableid, columns, requestMap, primaryid);
		}
		return getDetailPage(selectDestinationId, selectedEv, tableid, "init", true);
	}

	@Execute(validator = false, urlPattern="{selectDestinationId}/{selectedEv}/insertopen")
	 public String insertopen() {

		colors = getColors(selectDestinationId);

		TableDto tableDto = tableInfoService.getTableData(tableid);

		tableInfo_display = tableDto.getTableInfo();
		columnList = tableDto.getColumnList();

		return "insertopen.jsp";
	}
	@Execute(validator = false, urlPattern="{selectDestinationId}/{selectedEv}/insertconfirm")
	 public String insertconfirm() {
		//Tokenの生成
        TokenProcessor.getInstance().saveToken(request);

		TableDto tableDto = tableInfoService.getTableData(tableid);

		tableInfo_display = tableDto.getTableInfo();
		columnList = tableDto.getColumnList();

		dataMap = requestToDataMap(columnList, request);

		return "insertconfirm.jsp";
	}
	@Execute(validator = false, urlPattern="{selectDestinationId}/{selectedEv}/insertmodoru")
	 public String insertmodoru() {

		colors = getColors(selectDestinationId);

		TableDto tableDto = tableInfoService.getTableData(tableid);

		tableInfo_display = tableDto.getTableInfo();
		columnList = tableDto.getColumnList();

		dataMap = requestToDataMap(columnList, request);

		return "insertopen.jsp";
	}
	@Execute(validator = false , validate= "tokenValidate", input="{selectDestinationId}/{selectedEv}/insertopen", urlPattern="{selectDestinationId}/{selectedEv}/insert")
	 public String insert() {
		try {
			List<Column> columns = tableInfoService.getTableColumnListByTableId(tableid);
			BeanMap requestMap = RequestHandleUtil.requestToBeanMap(request, columns);
			tableInfoService.insertTable(tableid, columns, requestMap);
			PageHelper.deletePageInfo(session, tableid);
		} catch (SEntityExistsException se) {
			// エラーメッセージをセットします。
			throw new ActionMessagesException(ErrorMessageConstant.ENTITY_EXISTS_EXCEPTION);
		}
		return getDetailPage(selectDestinationId, selectedEv, tableid, "init", true);
	}



	public ActionMessages tokenValidate() {
		ActionMessages error = new ActionMessages();
		if (!TokenProcessor.getInstance().isTokenValid(request, true)) {
			error.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"errors.tokeninvalid",
					// メッセージリソースファイルに定義してあるキーです。
					new Object[] {}));
		}
		return error;
	}

	private String getColors(String selectDestinationId) {

		Destination destinationInfo = contentsManageService.getDestinationByIdFullJoin(selectDestinationId);
		List<String> colorList = cotentsColorService.getColors(destinationInfo.content_id);

		if (colorList.size() == 0) {

			colorList.add("#000000");
			colorList.add("#FFDEAD");
			colorList.add("#1E90FF");
			colorList.add("#DC143C");

		}
		StringBuilder sb = new StringBuilder();
		sb.append("[");



		for (String color : colorList) {
			sb.append("\"");
			sb.append(color);
			sb.append("\"");
			sb.append(",");
		}

		sb.deleteCharAt(sb.length() - 1);

		sb.append("]");
		return sb.toString();
	}

	private Integer setDisplaykensu() {
		Integer displaykensu = (Integer) session.getAttribute("displaykensu");
		if (displaykensu == null) {
			displaykensu = PER_PAGE;
			session.setAttribute("displaykensu", displaykensu);
		}
		return displaykensu;

	}

	private String getBackPath() {

		if (session.getAttribute(SEARCH_KEY) == null) {
			return "detail/";
		} else {
			return "searchdetail/";
		}
	}

	private String getDetailPage(
			String destinationId,
			String selectedEv,
			String tableid,
			String pageNum,
			boolean redirect) {

		StringBuilder sb = new StringBuilder();
//		/getBackPath() + selectDestinationId + "/" + selectedEv  + "/" + tableid + "/init?redirect=true";
		sb.append(destinationId);
		sb.append("/");
		sb.append(selectedEv);
		sb.append("/");
		sb.append(getBackPath());
		sb.append(tableid);
		sb.append("/");
		sb.append(pageNum);

		if (redirect) {
			sb.append("?redirect=true");
		}

		return sb.toString();
	}

	private Map<String, String> requestToDataMap(List<Column> columnList, HttpServletRequest request) {
		Map<String, String> dataMap = new HashMap<String, String>();

		for (Column column : columnList) {
			String field = column.physical_name;
			String value = request.getParameter(field);
			dataMap.put(field, value);
		}
		return dataMap;
	}

	private void saveError(HttpSession session, String errorMsgKey) {
		ActionMessages errors = new ActionMessages();
		errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
				errorMsgKey,
				// メッセージリソースファイルに定義してあるキーです。
				new Object[] {}));
		ActionMessagesUtil.saveErrors(session, errors);
	}

	protected String upload(FormFile file) {
		if (file.getFileSize() == 0) {
			return null;
		}
		String path = application.getRealPath("/WEB-INF/temp/" + file.getFileName());
		try {
			OutputStream out = new BufferedOutputStream(new FileOutputStream(
					path));
			try {
				out.write(file.getFileData(), 0, file.getFileSize());
			} finally {
				out.close();
			}
		} catch (IOException e) {
			path = null;

		}
		return path;
	}


}
