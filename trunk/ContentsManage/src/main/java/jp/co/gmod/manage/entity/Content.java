package jp.co.gmod.manage.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name = "contents")
public class Content implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	public Integer id;

	public String content_name;

	@OneToMany(mappedBy = "content")
	public List<Destination> destinationList;

	@Transient
	public String getContent_name() {
		return content_name;
	}

}
