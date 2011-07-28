package jp.co.gmod.manage.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	public String id;

	public String user_id;

	public String password;

	public String username;

}
