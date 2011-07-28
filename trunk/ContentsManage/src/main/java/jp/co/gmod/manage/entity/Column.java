package jp.co.gmod.manage.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "columns")
public class Column {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer id;

	public Integer table_id;

	public Integer seq_id;

	public String physical_name;

	public String logical_name;

	public String description;

	public String type;

	public Integer index_sign;

	public Integer primary_key_sign;

	public Integer unique_key_sign;

	public Integer autoincrement_sign;
	//public Integer text_sign;
}
