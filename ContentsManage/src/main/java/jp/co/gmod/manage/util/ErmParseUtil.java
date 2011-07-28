package jp.co.gmod.manage.util;

import java.io.File;

import jp.co.gmod.manage.bean.ColumnBean;
import jp.co.gmod.manage.bean.ColumnGroupBean;
import jp.co.gmod.manage.bean.ColumnGroupIndexBean;
import jp.co.gmod.manage.bean.ColumnGroupsBean;
import jp.co.gmod.manage.bean.IndexBean;
import jp.co.gmod.manage.bean.TableInfoBean;
import jp.co.gmod.manage.bean.TableParseBean;
import jp.co.gmod.manage.bean.WordBean;
import jp.co.gmod.manage.bean.WordsBean;

import org.apache.commons.digester.BeanPropertySetterRule;
import org.apache.commons.digester.Digester;
import org.apache.commons.digester.ObjectCreateRule;

public class ErmParseUtil {

	public static TableParseBean parseTable(File ernFile) {

		Digester digester = new Digester();

		digester.addRule("*/contents", new ObjectCreateRule(TableParseBean.class));
		digester.addRule("*/contents/table", new ObjectCreateRule(TableInfoBean.class));

		digester.addRule("*/contents/table/id", new BeanPropertySetterRule("seq_id"));
		digester.addRule("*/contents/table/physical_name", new BeanPropertySetterRule("physical_name"));
		digester.addRule("*/contents/table/logical_name", new BeanPropertySetterRule("logical_name"));

		digester.addRule("*/contents/table/columns/normal_column", new ObjectCreateRule(ColumnBean.class));
		digester.addRule("*/contents/table/columns/normal_column/word_id", new BeanPropertySetterRule("word_id"));
		digester.addRule("*/contents/table/columns/normal_column/primary_key", new BeanPropertySetterRule("primary_key"));
		digester.addRule("*/contents/table/columns/normal_column/unique_key", new BeanPropertySetterRule("unique_key"));
		digester.addRule("*/contents/table/columns/normal_column/id", new BeanPropertySetterRule("seq_id"));
		digester.addRule("*/contents/table/columns/normal_column/auto_increment", new BeanPropertySetterRule("autoincrement_sign"));

		digester.addRule("*/contents/table/columns/normal_column/logical_name", new BeanPropertySetterRule("logical_name"));
		digester.addRule("*/contents/table/columns/normal_column/physical_name", new BeanPropertySetterRule("physical_name"));
		digester.addRule("*/contents/table/columns/normal_column/type", new BeanPropertySetterRule("type"));

		digester.addSetNext("*/contents/table/columns/normal_column", "addColumn");


		digester.addRule("*/contents/table/columns/column_group", new ObjectCreateRule(ColumnGroupIndexBean.class));
		digester.addRule("*/contents/table/columns/column_group", new BeanPropertySetterRule("indexId"));
		digester.addSetNext("*/contents/table/columns/column_group", "addColumnGroup");

		digester.addRule("*/contents/table/indexes/inidex", new ObjectCreateRule(IndexBean.class));
		digester.addRule("*/contents/table/indexes/inidex/columns/column/id", new BeanPropertySetterRule("indexId"));
		digester.addSetNext("*/contents/table/indexes/inidex", "addIndex");

		digester.addSetNext("*/contents/table", "addTable");

		TableParseBean bean = null;
		try {
			bean = (TableParseBean)digester.parse(ernFile);
		} catch (Exception e) {
			return null;
		}
		return bean;
	}

	public static ColumnGroupsBean parseColumnGroups(File ernFile) {

		Digester digester = new Digester();

		digester.addRule("*/column_groups/", new ObjectCreateRule(ColumnGroupsBean.class));
		digester.addRule("*/column_groups/column_group", new ObjectCreateRule(ColumnGroupBean.class));

		digester.addRule("*/column_groups/column_group/id", new BeanPropertySetterRule("groupid"));
		digester.addRule("*/column_groups/column_group/columns/normal_column", new ObjectCreateRule(ColumnBean.class));
		digester.addRule("*/column_groups/column_group/columns/normal_column/word_id", new BeanPropertySetterRule("word_id"));
		digester.addRule("*/column_groups/column_group/columns/normal_column/primary_key", new BeanPropertySetterRule("primary_key"));
		digester.addRule("*/column_groups/column_group/columns/normal_column/unique_key", new BeanPropertySetterRule("unique_key"));
		digester.addRule("*/column_groups/column_group/columns/normal_column/id", new BeanPropertySetterRule("seq_id"));

		digester.addSetNext("*/column_groups/column_group/columns/normal_column", "addColumn");
		digester.addSetNext("*/column_groups/column_group", "addColumnGroup");

		ColumnGroupsBean bean = null;
		try {
			bean = (ColumnGroupsBean)digester.parse(ernFile);
		} catch (Exception e) {
			return null;
		}
		return bean;


	}

	public static WordsBean parseWord(File ernFile) {

		Digester digester = new Digester();

		digester.addRule("*/dictionary", new ObjectCreateRule(WordsBean.class));
		digester.addRule("*/dictionary/word/", new ObjectCreateRule(WordBean.class));
		digester.addRule("*/dictionary/word/id", new BeanPropertySetterRule("id"));
		digester.addRule("*/dictionary/word/logical_name", new BeanPropertySetterRule("logical_name"));
		digester.addRule("*/dictionary/word/physical_name", new BeanPropertySetterRule("physical_name"));
		digester.addRule("*/dictionary/word/description", new BeanPropertySetterRule("description"));
		digester.addRule("*/dictionary/word/type", new BeanPropertySetterRule("type"));
		digester.addSetNext("*/dictionary/word", "addWord");

		WordsBean bean = null;
		try {
			bean = (WordsBean)digester.parse(ernFile);
		} catch (Exception e) {
			return null;
		}
		return bean;
	}
}
