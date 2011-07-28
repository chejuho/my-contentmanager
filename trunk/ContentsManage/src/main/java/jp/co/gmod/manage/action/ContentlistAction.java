package jp.co.gmod.manage.action;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import jp.co.gmod.manage.constant.ErrorMessageConstant;
import jp.co.gmod.manage.entity.Content;
import jp.co.gmod.manage.entity.Destination;
import jp.co.gmod.manage.entity.DestinationDbInfo;
import jp.co.gmod.manage.form.DestinationForm;
import jp.co.gmod.manage.service.ContentsManageService;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;
import org.seasar.framework.container.annotation.tiger.Binding;
import org.seasar.framework.container.annotation.tiger.BindingType;
import org.seasar.struts.annotation.Execute;
import org.seasar.struts.annotation.Required;
import org.seasar.struts.exception.ActionMessagesException;

public class ContentlistAction {

	private static final String MAIN_KEY = "main";

	private final static int DEV_ID = 1;
	private final static int STG_ID = 2;
	private final static int PUB_ID = 3;

	@Resource
	protected HttpSession session;

	public List<Content> contents;

	@Required
	@Binding(bindingType = BindingType.NONE)
	public FormFile ermFile;

	@Required
	public String contentsname;

	public HttpServletRequest request;

	public ServletContext application;

	//コンテンツリスト
	public List<Content> conents;
	public List<Destination> destinations;

	public String selected;

	//削除リスト
	public String[] deleteContents;
	//編集するコンテンツID
	public String editContentId;
	//編集するコンテンツ
	public Content editContent;

	//削除リスト
	public String[] deleteDestinations;
	//編集する配布先ID
	public String editDestinationId;

	public String detailDestinationId;

	public DestinationForm form;
	@Resource
	protected ContentsManageService contentsManageService;

	@Execute(validator = false)
	public String index() {

		conents = contentsManageService.getContents();
		destinations = contentsManageService.getDestinationListByContentID("all");
		selected = MAIN_KEY;
		return "index.jsp";
	}

	@Execute(validator = false)
	public String contentSelect() {

		conents = contentsManageService.getContents();
		destinations = contentsManageService.getDestinationListByContentID(selected);
		return "index.jsp";
	}

	@Execute(validator = false, urlPattern="selectContent/{selected}")
	public String selectSideMenu() {

		return "index.jsp";
	}

	@Execute(validator = false)
	public String contentAddOpen() {

		editContent = contentsManageService.getContentById(editContentId);
		contentsname = editContent.content_name;
		editContentId = String.valueOf(editContent.id);
		return "contentaddopen.jsp";
	}

	@Execute(validator = false)
	public String contentRegistOpen() {

		return "contentregistopen.jsp";
	}

	@Execute(validator = false)
	public String contentDeleteOpen() {

		conents = contentsManageService.getContentsByIdList(deleteContents);
		return "contentdeleteopen.jsp";
	}

	@Execute(validator = false)
	public String contentDelete() {

		for (String id : deleteContents) {
			contentsManageService.deleteContent(id);
		}
		return "contentManageOpen?redirect=true";
	}

	@Execute(validator = false)
	public String contentEditOpen() {

		editContent = contentsManageService.getContentById(editContentId);
		contentsname = editContent.content_name;
		editContentId = String.valueOf(editContent.id);
		return "contenteditopen.jsp";
	}

	@Execute(validator = false)
	public String contentManageOpen() {
		conents = contentsManageService.getContents();
		return "contentmanageopen.jsp";
	}


	@Execute(input = "contentregistopen.jsp")
	public String contentRegist() {
		String uploadFilePath = upload(ermFile);

		File file = new File(uploadFilePath);
		boolean check = contentsManageService.insertContentInfo(contentsname, file);
		file.delete();
		if (!check) {
			throw new ActionMessagesException(ErrorMessageConstant.ERMFILEUPLOAD);
		}
		return "contentManageOpen?redirect=true";
	}

	@Execute(input = "contenteditopen.jsp")
	public String contentEdit() throws Exception {
		// ActionMessages messages = new ActionMessages();
		String uploadFilePath = upload(ermFile);

		File file = new File(uploadFilePath);

		boolean delcheck = contentsManageService.deleteContentByEdit(editContentId);

		if (!delcheck) {
			file.delete();
			throw  new ActionMessagesException(ErrorMessageConstant.DB_ERROR);
		}

		boolean check = contentsManageService.updateContentInfo(contentsname, file, editContentId);

		if (!check) {
			file.delete();
			throw  new ActionMessagesException("errors.ermfileupload");
		}
		return "contentManageOpen?redirect=true";
	}
	@Execute(input = "contentaddopen.jsp")
	public String contentAdd() {

		String uploadFilePath = upload(ermFile);

		File file = new File(uploadFilePath);

		boolean check = contentsManageService.addTable(file, editContentId);
		file.delete();
		if (!check) {
			// エラーメッセージをセットします。
			throw  new ActionMessagesException(ErrorMessageConstant.ERMFILEUPLOAD);
		}
		return "contentManageOpen?redirect=true";
	}

	@Execute(validator = false)
	public String destinationRegistOpen() {

		conents = contentsManageService.getContents();
		return "destinationregistopen.jsp";
	}

	@Execute(input = "destinationregistopen.jsp", validator = false, validate= "destinationRegistCheck")
	public String destinationRegistConfirm() {

		form.contentname = contentsManageService.getContentById(form.contentid).content_name;

		return "destinationregistconfirm.jsp";
	}

	@Execute(validator = false)
	public String destinationRegist() {

		contentsManageService.insertDestinationInfo(form);
		return "index?redirect=true";
	}

	@Execute(validator = false)
	public String destinationDeleteOpen() {
		destinations = contentsManageService.getDestinationsByIdList(deleteDestinations);
		return "destinationdeleteopen.jsp";
	}

	@Execute(validator = false)
	public String destinationDelete() {

		for (String id : deleteDestinations) {
			contentsManageService.deleteDestinatio(id);
		}

		return "index?redirect=true";
	}
	@Execute(validator = false)
	public String destinationEditOpen() {

		Destination destination = contentsManageService.getDestinationById(editDestinationId);
		conents = contentsManageService.getContents();
		selected = String.valueOf(destination.content_id);

		form = new DestinationForm();

		form.contentid = String.valueOf(destination.content_id);
		form.destinationName = destination.destination_name;

		for (DestinationDbInfo dbinfo : destination.destinationDbInfoList) {
			if (DEV_ID == dbinfo.environment_id) {
				form.devUrl =  dbinfo.url;
				form.devId = dbinfo.db_id;
				form.devPassword = dbinfo.db_password;
			}
			if (STG_ID == dbinfo.environment_id) {
				form.stgUrl =  dbinfo.url;
				form.stgId = dbinfo.db_id;
				form.stgPassword = dbinfo.db_password;
			}
			if (PUB_ID == dbinfo.environment_id) {
				form.pubUrl =  dbinfo.url;
				form.pubId = dbinfo.db_id;
				form.pubPassword = dbinfo.db_password;
			}
		}

		return "destinationeditopen.jsp";
	}

	@Execute(input = "destinationeditopen.jsp", validator = false, validate= "destinationRegistCheck")
	public String destinationEditConfirm() {

		form.contentname = contentsManageService.getContentById(form.contentid).content_name;

		return "destinationeditconfirm.jsp";
	}

	@Execute(validator = false)
	public String destinationEdit() {

		Destination destination = contentsManageService.getDestinationById(editDestinationId);
		contentsManageService.updateDestinationInfo(destination, form);

		return "index?redirect=true";
	}

	@Execute(validator = false)
	public String destinationDetail() {

		Destination destination = contentsManageService.getDestinationById(detailDestinationId);
		conents = contentsManageService.getContents();
		selected = String.valueOf(destination.content_id);

		form = new DestinationForm();

		form.contentid = String.valueOf(destination.content_id);
		form.destinationName = destination.destination_name;

		for (DestinationDbInfo dbinfo : destination.destinationDbInfoList) {
			if (DEV_ID == dbinfo.environment_id) {
				form.devUrl =  dbinfo.url;
				form.devId = dbinfo.db_id;
				form.devPassword = dbinfo.db_password;
			}
			if (STG_ID == dbinfo.environment_id) {
				form.stgUrl =  dbinfo.url;
				form.stgId = dbinfo.db_id;
				form.stgPassword = dbinfo.db_password;
			}
			if (PUB_ID == dbinfo.environment_id) {
				form.pubUrl =  dbinfo.url;
				form.pubId = dbinfo.db_id;
				form.pubPassword = dbinfo.db_password;
			}
		}

		return "destinationdetail.jsp";
	}

	@Execute(validator = false)
	public String goTableManage() {
		return "/tablelist?id=" + detailDestinationId + "&redirect=true";
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

	/**
	 * チェックを行う
	 */
	public ActionMessages destinationRegistCheck() {
		ActionMessages error = new ActionMessages();
		// エラーメッセージをセットします。
		if ("main".equals(form.contentid)) {
			error.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					ErrorMessageConstant.NOTSELECTED_CONTENT,
					// メッセージリソースファイルに定義してあるキーです。
					new Object[] {}));
		}
		if (form.destinationName.trim().length() == 0) {
			error.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					ErrorMessageConstant.NOT_INPUT_DESTINATIONNAME,
					// メッセージリソースファイルに定義してあるキーです。
					new Object[] {}));
		}

		if (!error.isEmpty()) {
			conents = contentsManageService.getContents();
		}

		return error;
	}

}
