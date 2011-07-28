package jp.co.gmod.manage.service;

import java.util.ArrayList;
import java.util.List;

import jp.co.gmod.manage.entity.ContentsColor;

import org.seasar.extension.jdbc.JdbcManager;
import org.seasar.framework.container.annotation.tiger.Binding;

public class CotentsColorService {

	@Binding("managerJdbcManager")
	private JdbcManager managerJdbcManager;

	public List<String> getColors(int contentsid) {

		List<String> colorList = new ArrayList<String>();
		List<ContentsColor> colors = managerJdbcManager.from(ContentsColor.class).where("content_id = ?", contentsid).getResultList();

		for (ContentsColor contentsColor : colors) {
			colorList.add(contentsColor.color);
		}

		return colorList;
	}

}
