package jp.co.gmod.manage.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "contents_color")
public class ContentsColor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer id;

	public Integer content_id;

	public String color;

}
