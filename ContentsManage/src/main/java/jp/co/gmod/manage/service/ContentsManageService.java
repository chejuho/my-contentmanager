package jp.co.gmod.manage.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jp.co.gmod.manage.bean.ColumnBean;
import jp.co.gmod.manage.bean.ColumnGroupBean;
import jp.co.gmod.manage.bean.ColumnGroupIndexBean;
import jp.co.gmod.manage.bean.ColumnGroupsBean;
import jp.co.gmod.manage.bean.IndexBean;
import jp.co.gmod.manage.bean.TableInfoBean;
import jp.co.gmod.manage.bean.TableParseBean;
import jp.co.gmod.manage.bean.WordBean;
import jp.co.gmod.manage.bean.WordsBean;
import jp.co.gmod.manage.entity.Column;
import jp.co.gmod.manage.entity.Content;
import jp.co.gmod.manage.entity.Destination;
import jp.co.gmod.manage.entity.DestinationDbInfo;
import jp.co.gmod.manage.entity.TableInfo;
import jp.co.gmod.manage.form.DestinationForm;
import jp.co.gmod.manage.util.ErmParseUtil;

import org.seasar.extension.jdbc.JdbcManager;
import org.seasar.framework.beans.util.BeanMap;
import org.seasar.framework.beans.util.Beans;
import org.seasar.framework.container.annotation.tiger.Binding;

public class ContentsManageService {

	private final static int DEV_ID = 1;
	private final static int STG_ID = 2;
	private final static int PUB_ID = 3;

	private final static int GROUP_START = 500;

	@Binding("managerJdbcManager")
	private JdbcManager managerJdbcManager;
	/**
	 * 全てコンテンツを検索する
	 * @param tablename
	 * @return
	 */
	public List<Content> getContents() {

		return managerJdbcManager.from(Content.class).getResultList();
	}

	/**
	 *
	 * @param contentIdList
	 * @return
	 */
	public List<Content> getContentsByIdList(String[] contentIdList) {

		List<Content> conents = new ArrayList<Content>();

		for (String contentid : contentIdList) {
			Content content = managerJdbcManager.from(Content.class).id(Integer.valueOf(contentid)).getSingleResult();
			conents.add(content);
		}

		return conents;
	}

	/**
	 * コンテンツIDのキーで配布先を検索する
	 * @param contentId
	 * @return
	 */
	public List<Destination> getDestinationListByContentID(String contentId) {
		List<Destination> destinationList = null;
		if ("all".equals(contentId)) {
			destinationList = managerJdbcManager.from(Destination.class).innerJoin("content").orderBy("content_name asc").getResultList();
		} else {
			destinationList = managerJdbcManager.from(Destination.class).innerJoin("content").where("content_id = ?", Integer.valueOf(contentId)).getResultList();
		}
		return destinationList;
	}



	public Content getContentById(String id) {

		Content conent = managerJdbcManager.from(Content.class).id(Integer.valueOf(id)).getSingleResult();
		return conent;
	}

	public Destination getDestinationById(String id) {

		Destination destination = managerJdbcManager.from(Destination.class).id(Integer.valueOf(id)).
			leftOuterJoin("destinationDbInfoList").getSingleResult();

		return destination;
	}

	public Destination getDestinationByIdFullJoin(String id) {

		Destination destination = managerJdbcManager.from(Destination.class).id(Integer.valueOf(id)).
			innerJoin("destinationDbInfoList").innerJoin("content").getSingleResult();

		return destination;
	}


	public List<Destination> getDestinationsByIdList(String[] idList) {

		List<Destination> destinations = new ArrayList<Destination>();

		for (String id : idList) {
			Destination destination = managerJdbcManager.from(Destination.class).id(Integer.valueOf(id)).getSingleResult();
			destinations.add(destination);
		}

		return destinations;
	}
	public boolean deleteDestinatio(String id) {

		Destination destination = managerJdbcManager.from(Destination.class).id(Integer.valueOf(id)).getSingleResult();
		int result = managerJdbcManager.delete(destination).execute();
		if (result == 1) {
			String sql = "delete FROM destination_db_info WHERE destination_id=?;";
			managerJdbcManager.updateBySql(sql, Integer.class).params(Integer.valueOf(id)).execute();
		}
		return result == 1 ? true : false;
	}

	public boolean deleteDestinatioByContentid(String contentId) {

		List<Destination> destinations = managerJdbcManager.from(Destination.class).where("content_id = ?", Integer.valueOf(contentId)).getResultList();

		for (Destination destination : destinations) {
			int result = managerJdbcManager.delete(destination).execute();
			if (result == 1) {
				String sql = "delete FROM destination_db_info WHERE destination_id=?;";
				managerJdbcManager.updateBySql(sql, Integer.class).params(Integer.valueOf(destination.id)).execute();
			}
		}
		return true;
	}

	/**
	 *
	 * @param contentid
	 * @return
	 */
	public boolean deleteContent(String contentid) {

		Content content = managerJdbcManager.from(Content.class).id(Integer.valueOf(contentid)).getSingleResult();
		int result = managerJdbcManager.delete(content).execute();
		if (result == 1) {
			deleteDestinatioByContentid(contentid);
			String delColumns = "delete columns FROM columns INNER JOIN table_info ON columns.table_id = table_info.id WHERE table_info.content_id=?;";
			String sql = "delete FROM table_info WHERE content_id=?;";
			managerJdbcManager.updateBySql(delColumns, Integer.class).params(Integer.valueOf(contentid)).execute();
			managerJdbcManager.updateBySql(sql, Integer.class).params(Integer.valueOf(contentid)).execute();
		}

		return result == 1 ? true : false;
	}

	/**
	 *
	 * @param contentid
	 * @return
	 */
	public boolean deleteContentByEdit(String contentid) {

		Content content = managerJdbcManager.from(Content.class).id(Integer.valueOf(contentid)).getSingleResult();
		int result = managerJdbcManager.delete(content).execute();
		if (result == 1) {
			String delColumns = "delete columns FROM columns INNER JOIN table_info ON columns.table_id = table_info.id WHERE table_info.content_id=?;";
			String sql = "delete FROM table_info WHERE content_id=?;";
			managerJdbcManager.updateBySql(delColumns, Integer.class).params(Integer.valueOf(contentid)).execute();
			managerJdbcManager.updateBySql(sql, Integer.class).params(Integer.valueOf(contentid)).execute();
		}

		return result == 1 ? true : false;
	}


	public boolean insertContentInfo(String contentName, File ernFile) {

		TableParseBean bean = ErmParseUtil.parseTable(ernFile);
		WordsBean words = ErmParseUtil.parseWord(ernFile);
		ColumnGroupsBean columnGroupsBean = ErmParseUtil.parseColumnGroups(ernFile);

		if (bean == null) {
			return false;
		}

		String sql = "select max(id) as max from contents;";
		BeanMap countInfo = managerJdbcManager.selectBySql(BeanMap.class, sql).getSingleResult();
		Integer maxId = (Integer) countInfo.get("max");

		Content content = new Content();
		if (maxId == null) {
			maxId = 0;
		}
		content.id = Integer.valueOf(maxId) + 1;
		content.content_name = contentName;
		managerJdbcManager.insert(content).execute();


		for (TableInfoBean table : bean.getTableInfoList()) {
			TableInfo info = Beans.createAndCopy(TableInfo.class, table).execute();

			int tableId = nextVal();
			info.id = tableId;
			info.content_id = content.id;

			insertColumn(tableId, table, words);

			insertGroupColumn(tableId, table, columnGroupsBean, words);

			managerJdbcManager.insert(info).execute();
		}
		return true;

	}

	public boolean updateContentInfo(String contentName, File ernFile, String contentid) {

		TableParseBean bean = ErmParseUtil.parseTable(ernFile);
		WordsBean words = ErmParseUtil.parseWord(ernFile);
		ColumnGroupsBean columnGroupsBean = ErmParseUtil.parseColumnGroups(ernFile);

		if (bean == null) {
			return false;
		}

		Content content = new Content();
		content.id = Integer.valueOf(contentid);
		content.content_name = contentName;
		managerJdbcManager.insert(content).execute();


		for (TableInfoBean table : bean.getTableInfoList()) {
			TableInfo info = Beans.createAndCopy(TableInfo.class, table).execute();

			int tableId = nextVal();
			info.id = tableId;
			info.content_id = content.id;

			insertColumn(tableId, table, words);

			insertGroupColumn(tableId, table, columnGroupsBean, words);

			managerJdbcManager.insert(info).execute();
		}
		return true;

	}

	public boolean addTable(File ernFile, String contentid) {

		TableParseBean bean = ErmParseUtil.parseTable(ernFile);
		WordsBean words = ErmParseUtil.parseWord(ernFile);
		ColumnGroupsBean columnGroupsBean = ErmParseUtil.parseColumnGroups(ernFile);

		if (bean == null) {
			return false;
		}

		for (TableInfoBean table : bean.getTableInfoList()) {
			TableInfo info = Beans.createAndCopy(TableInfo.class, table).execute();

			int tableId = nextVal();
			info.id = tableId;
			info.content_id = Integer.valueOf(contentid);

			insertColumn(tableId, table, words);

			insertGroupColumn(tableId, table, columnGroupsBean, words);

			managerJdbcManager.insert(info).execute();
		}
		return true;

	}

	public boolean insertDestinationInfo(DestinationForm form) {

		int cnt = 0;
		String sql = "select max(id) as max from destination;";
		BeanMap countInfo = managerJdbcManager.selectBySql(BeanMap.class, sql).getSingleResult();
		Integer maxId = (Integer) countInfo.get("max");
		if (maxId == null) {
			maxId = 0;
		}
		Destination destination= new Destination();

		destination.id = Integer.valueOf(maxId) + 1;
		destination.content_id = Integer.valueOf(form.contentid);
		destination.destination_name = form.destinationName;
		cnt = managerJdbcManager.insert(destination).execute();

		//dev
		if (form.devUrl.trim().length() != 0
				&& form.devId.trim().length() != 0
				&& form.devPassword.trim().length() != 0) {

			DestinationDbInfo devDestinationDbInfo = new DestinationDbInfo();

			devDestinationDbInfo.destination_id = destination.id;
			devDestinationDbInfo.url = form.devUrl;
			devDestinationDbInfo.db_id = form.devId;
			devDestinationDbInfo.db_password = form.devPassword;
			devDestinationDbInfo.environment_id = DEV_ID;

			cnt += managerJdbcManager.insert(devDestinationDbInfo).execute();


		}
		//stg
		if (form.stgUrl.trim().length() != 0
				&& form.stgId.trim().length() != 0
				&& form.stgPassword.trim().length() != 0) {

			DestinationDbInfo stgDestinationDbInfo = new DestinationDbInfo();
			stgDestinationDbInfo.destination_id = destination.id;
			stgDestinationDbInfo.url = form.stgUrl;
			stgDestinationDbInfo.db_id = form.stgId;
			stgDestinationDbInfo.db_password = form.stgPassword;
			stgDestinationDbInfo.environment_id = STG_ID;

			cnt += managerJdbcManager.insert(stgDestinationDbInfo).execute();
		}
		//pub
		if (form.pubUrl.trim().length() != 0
				&& form.pubId.trim().length() != 0
				&& form.pubPassword.trim().length() != 0) {

			DestinationDbInfo pubDestinationDbInfo = new DestinationDbInfo();
			pubDestinationDbInfo.destination_id = destination.id;
			pubDestinationDbInfo.url = form.pubUrl;
			pubDestinationDbInfo.db_id = form.pubId;
			pubDestinationDbInfo.db_password = form.pubPassword;
			pubDestinationDbInfo.environment_id = PUB_ID;

			cnt += managerJdbcManager.insert(pubDestinationDbInfo).execute();

		}

		return cnt > 0 ? true : false;


	}

	public boolean updateDestinationInfo(Destination destination, DestinationForm form) {

		destination.content_id = Integer.valueOf(form.contentid);
		destination.destination_name = form.destinationName;

		managerJdbcManager.update(destination).execute();

		//dev
		if (form.devUrl.trim().length() != 0
				&& form.devId.trim().length() != 0
				&& form.devPassword.trim().length() != 0) {

			boolean devExist = false;

			for (DestinationDbInfo destinationDbInfo : destination.destinationDbInfoList) {

				if (DEV_ID == destinationDbInfo.environment_id) {
					devExist = true;
					destinationDbInfo.url = form.devUrl;
					destinationDbInfo.db_id = form.devId;
					destinationDbInfo.db_password = form.devPassword;

					managerJdbcManager.update(destinationDbInfo).execute();

					break;
				}
			}

			if (!devExist) {
				DestinationDbInfo newDestinationDbInfo = new DestinationDbInfo();
				newDestinationDbInfo.destination_id = destination.id;
				newDestinationDbInfo.url = form.devUrl;
				newDestinationDbInfo.db_id = form.devId;
				newDestinationDbInfo.db_password = form.devPassword;
				newDestinationDbInfo.environment_id = DEV_ID;

				managerJdbcManager.insert(newDestinationDbInfo).execute();
			}
		}

		//stg
		if (form.stgUrl.trim().length() != 0
				&& form.stgId.trim().length() != 0
				&& form.stgPassword.trim().length() != 0) {

			boolean stgExist = false;

			for (DestinationDbInfo destinationDbInfo : destination.destinationDbInfoList) {

				if (STG_ID == destinationDbInfo.environment_id) {
					stgExist = true;
					destinationDbInfo.url = form.stgUrl;
					destinationDbInfo.db_id = form.stgId;
					destinationDbInfo.db_password = form.stgPassword;

					managerJdbcManager.update(destinationDbInfo).execute();

					break;
				}
			}

			if (!stgExist) {
				DestinationDbInfo newDestinationDbInfo = new DestinationDbInfo();
				newDestinationDbInfo.destination_id = destination.id;
				newDestinationDbInfo.url = form.stgUrl;
				newDestinationDbInfo.db_id = form.stgId;
				newDestinationDbInfo.db_password = form.stgPassword;
				newDestinationDbInfo.environment_id = STG_ID;

				managerJdbcManager.insert(newDestinationDbInfo).execute();
			}
		}

		//pub
		if (form.pubUrl.trim().length() != 0
				&& form.pubId.trim().length() != 0
				&& form.pubPassword.trim().length() != 0) {

			boolean pubExist = false;

			for (DestinationDbInfo destinationDbInfo : destination.destinationDbInfoList) {

				if (PUB_ID == destinationDbInfo.environment_id) {
					pubExist = true;
					destinationDbInfo.url = form.pubUrl;
					destinationDbInfo.db_id = form.pubId;
					destinationDbInfo.db_password = form.pubPassword;

					managerJdbcManager.update(destinationDbInfo).execute();

					break;
				}
			}

			if (!pubExist) {
				DestinationDbInfo newDestinationDbInfo = new DestinationDbInfo();
				newDestinationDbInfo.destination_id = destination.id;
				newDestinationDbInfo.url = form.pubUrl;
				newDestinationDbInfo.db_id = form.pubId;
				newDestinationDbInfo.db_password = form.pubPassword;
				newDestinationDbInfo.environment_id = PUB_ID;

				managerJdbcManager.insert(newDestinationDbInfo).execute();
			}
		}

		return true;
	}




	private int nextVal() {

		String sql_update = "UPDATE sequence SET id=LAST_INSERT_ID(id+1);";
		String sql = "SELECT LAST_INSERT_ID();";
		managerJdbcManager.updateBySql(sql_update).execute();

		BeanMap countInfo = managerJdbcManager.selectBySql(BeanMap.class, sql).getSingleResult();
		Long id = (Long) countInfo.get("lastInsertId()");
		return id.intValue();

	}

	/**
	 *
	 * @param tableId
	 * @param table
	 * @param words
	 * @return
	 */
	private boolean insertColumn(int tableId, TableInfoBean table, WordsBean words) {

		for (ColumnBean columnBean : table.getColumns().values()) {
			WordBean wordBean = words.getWord(columnBean.getWord_id());

			Column column = new Column();
			column.table_id = tableId;

			//外部キーの場合
			if (wordBean == null) {
				column.logical_name = columnBean.getLogical_name();
				column.physical_name = columnBean.getPhysical_name();
				column.description = "";
				column.type = columnBean.getType();
			} else {
				column.logical_name = wordBean.getLogical_name();
				column.physical_name = wordBean.getPhysical_name();
				column.description = wordBean.getDescription();
				column.type = wordBean.getType();
			}

			column.seq_id = Integer.valueOf(columnBean.getSeq_id());
			column.primary_key_sign = columnBean.isPrimary_key();
			column.unique_key_sign = columnBean.isUnique_key();
			column.autoincrement_sign = columnBean.isAutoincrement_sign();
			//column.text_sign = 0;

			setIndexSign(table, column, columnBean.getSeq_id());

			managerJdbcManager.insert(column).execute();
		}

		return true;
	}

	private void setIndexSign(TableInfoBean table, Column column, String seqId) {

		boolean indexcheck = false;
		for (IndexBean index : table.getIndexList()) {
			if (index.getIndexId().equals(seqId)) {
				indexcheck = true;
				break;
			}
		}

		if (indexcheck) {
			column.index_sign = 1;
		} else {
			column.index_sign = 0;
		}
	}

	/**
	 *
	 * @param tableId
	 * @param table
	 * @param columnGroupsBean
	 * @param words
	 */
	private void insertGroupColumn(
			int tableId,
			TableInfoBean table,
			ColumnGroupsBean columnGroupsBean,
			WordsBean words) {

		int seqindex = GROUP_START;

		for (ColumnGroupIndexBean index : table.getColumnGroupList()) {
			ColumnGroupBean columnGroupBean = columnGroupsBean.getColumnGroup(index.getIndexId());

			if (columnGroupBean == null) {
				continue;
			}
			for (ColumnBean columnBean : columnGroupBean.getColumnList()) {
				WordBean wordBean = words.getWord(columnBean.getWord_id());
				Column column = new Column();
				column.table_id = tableId;
				column.logical_name = wordBean.getLogical_name();
				column.physical_name = wordBean.getPhysical_name();
				column.description = wordBean.getDescription();
				column.type = wordBean.getType();
				column.seq_id = Integer.valueOf(columnBean.getSeq_id()) + seqindex;
				column.index_sign = 0;
				column.primary_key_sign = columnBean.isPrimary_key();
				column.unique_key_sign = columnBean.isUnique_key();
				column.autoincrement_sign = columnBean.isAutoincrement_sign();
				managerJdbcManager.insert(column).execute();
			}
			seqindex += 100;
		}

	}


}
