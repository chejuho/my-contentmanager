package jp.co.gmod.manage.service;

import java.util.List;

import org.seasar.extension.jdbc.JdbcManager;
import org.seasar.framework.beans.util.BeanMap;
import org.seasar.framework.container.annotation.tiger.Binding;


public class EmojiInfoService {

	@Binding("emojiJdbcManager")
	private JdbcManager jdbcManager;


	public List<BeanMap> getEmojiInfoList() {


		String sql = "SELECT * FROM emoji_targets";
		List<BeanMap> emojiInfoList = jdbcManager.selectBySql(BeanMap.class, sql).getResultList();


		return emojiInfoList;
	}


}
