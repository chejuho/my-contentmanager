package jp.co.gmod.manage.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "destination_db_info")
public class DestinationDbInfo  implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer id;

	public Integer destination_id;

	public String url;

	public String db_id;

	public String db_password;

	public Integer environment_id;

	@ManyToOne
	@JoinColumn(name = "destination_id")
	public Destination destination;

}
