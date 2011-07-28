package jp.co.gmod.manage.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "table_info")
public class TableInfo {

	@Id
	public Integer id;

	public Integer content_id;

	public String physical_name;

	public String logical_name;

	public String seq_id;
}
