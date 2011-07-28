package jp.co.gmod.manage.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name = "destination")
public class Destination implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	public Integer id;

	public Integer content_id;

	public String destination_name;

	@ManyToOne
	@JoinColumn(name = "content_id")
	public Content content;

	@OneToMany(mappedBy = "destination")
	public List<DestinationDbInfo> destinationDbInfoList;

	@Transient
	public Content getContent() {
		return content;
	}

	@Transient
	public String getDestination_name() {
		return destination_name;
	}
}
