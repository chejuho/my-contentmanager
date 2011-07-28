package jp.co.gmod.manage.config;

import java.util.ArrayList;
import java.util.List;

import jp.co.gmod.manage.service.EmojiInfoService;

import org.seasar.framework.beans.util.BeanMap;
import org.seasar.framework.container.SingletonS2Container;
import org.seasar.framework.log.Logger;

public class EmojiStoreConfig {


	List<String> emojiInfoList = null;
	private Logger logger = Logger.getLogger(getClass());

	public void init() {

		EmojiInfoService emojiInfoService = SingletonS2Container.getComponent(EmojiInfoService.class);

		emojiInfoList = new ArrayList<String>();
		List<BeanMap> emojiInfoBeanList = emojiInfoService.getEmojiInfoList();
		for (BeanMap info : emojiInfoBeanList) {
			logger.debug(info.get("target"));
			emojiInfoList.add((String) info.get("target"));
		}
	}

	public List<String> getList() {
		return emojiInfoList;
	}
}
