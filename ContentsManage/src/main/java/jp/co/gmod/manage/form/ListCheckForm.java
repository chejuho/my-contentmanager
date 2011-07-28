package jp.co.gmod.manage.form;

import java.io.Serializable;
import java.util.List;

import org.seasar.framework.container.annotation.tiger.Component;
import org.seasar.framework.container.annotation.tiger.InstanceType;

@Component(instance = InstanceType.SESSION)
public class ListCheckForm implements Serializable {

    /**
	 *
	 */
	private static final long serialVersionUID = 1L;
	//一覧の更新対象チェックボックス用
	public boolean check;
	//更新データ用
	public List<ListCheckForm> checkItems;

}
