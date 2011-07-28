package jp.co.gmod.manage.customtag;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import jp.co.gmod.manage.config.EmojiStoreConfig;

import org.seasar.framework.container.SingletonS2Container;

public class EmojiTableTag extends SimpleTagSupport {

	private String configname = null;

	private static final int DISPLAYCOUNT = 20;



	public void doTag() throws JspException, IOException {

		JspWriter out = getJspContext().getOut();


		EmojiStoreConfig emojiStoreConfig = SingletonS2Container.getComponent(EmojiStoreConfig.class);

		List<String> emojiList = emojiStoreConfig.getList();

		int size = emojiList.size();
		int roof = size / DISPLAYCOUNT;

		if (roof % DISPLAYCOUNT != 0) {
			roof += 1;
		}
		StringBuilder tagWriter = new StringBuilder();

		int index = 0;

		tagWriter.append("<table>");

		for (int i = 0; i < roof; i++) {

			tagWriter.append("<tr>");
			for (int j = 0; index < size && j < DISPLAYCOUNT; j++) {
				tagWriter.append("<td id = '");
				tagWriter.append(index);
				tagWriter.append("'>");
				tagWriter.append(emojiList.get(index));
				tagWriter.append("</td>");
				index++;
			}
			tagWriter.append("\n");
			tagWriter.append("</tr>");
		}
		tagWriter.append("</table>");
		//System.out.println(tagWriter.toString());
		out.print(tagWriter.toString());
	}

	/**
	 * confignameを取得します。
	 *
	 * @return configname
	 */
	public String getConfigname() {
		return configname;
	}

	/**
	 * confignameを設定します。
	 *
	 * @param configname
	 *            configname
	 */
	public void setConfigname(String configname) {
		this.configname = configname;
	}
}
