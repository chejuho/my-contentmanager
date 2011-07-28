package jp.co.gmod.manage.bean;


public class ColumnBean {

	private String word_id;
	private String seq_id;
	private String primary_key;
	private String unique_key;
	private String autoincrement_sign;

	private String logical_name;
	private String physical_name;
	private String type;
	/**
	 * word_idを取得します。
	 * @return word_id
	 */
	public String getWord_id() {
	    return word_id;
	}
	/**
	 * word_idを設定します。
	 * @param word_id word_id
	 */
	public void setWord_id(String word_id) {
	    this.word_id = word_id;
	}
	/**
	 * seq_idを取得します。
	 * @return seq_id
	 */
	public String getSeq_id() {
	    return seq_id;
	}
	/**
	 * seq_idを設定します。
	 * @param seq_id seq_id
	 */
	public void setSeq_id(String seq_id) {
	    this.seq_id = seq_id;
	}
	/**
	 * primary_keyを取得します。
	 * @return primary_key
	 */
	public Integer isPrimary_key() {
	    return "true".equals(primary_key) ? Integer.valueOf(1) : Integer.valueOf(0);
	}
	/**
	 * primary_keyを設定します。
	 * @param primary_key primary_key
	 */
	public void setPrimary_key(String primary_key) {
	    this.primary_key = primary_key;
	}
	/**
	 * unique_keyを取得します。
	 * @return unique_key
	 */
	public Integer isUnique_key() {
	    return "true".equals(unique_key) ? Integer.valueOf(1) : Integer.valueOf(0);
	}
	/**
	 * primary_keyを取得します。
	 * @return primary_key
	 */
	public String getPrimary_key() {
	    return primary_key;
	}
	/**
	 * unique_keyを設定します。
	 * @param unique_key unique_key
	 */
	public void setUnique_key(String unique_key) {
	    this.unique_key = unique_key;
	}
	/**
	 * unique_keyを取得します。
	 * @return unique_key
	 */
	public String getUnique_key() {
	    return unique_key;
	}
	/**
	 * autoincrement_signを取得します。
	 * @return autoincrement_sign
	 */
	public Integer isAutoincrement_sign() {
		return "true".equals(autoincrement_sign) ? Integer.valueOf(1) : Integer.valueOf(0);
	}
	/**
	 * autoincrement_signを設定します。
	 * @param autoincrement_sign autoincrement_sign
	 */
	public void setAutoincrement_sign(String autoincrement_sign) {
	    this.autoincrement_sign = autoincrement_sign;
	}
	/**
	 * logical_nameを取得します。
	 * @return logical_name
	 */
	public String getLogical_name() {
	    return logical_name;
	}
	/**
	 * logical_nameを設定します。
	 * @param logical_name logical_name
	 */
	public void setLogical_name(String logical_name) {
	    this.logical_name = logical_name;
	}
	/**
	 * physical_nameを取得します。
	 * @return physical_name
	 */
	public String getPhysical_name() {
	    return physical_name;
	}
	/**
	 * physical_nameを設定します。
	 * @param physical_name physical_name
	 */
	public void setPhysical_name(String physical_name) {
	    this.physical_name = physical_name;
	}
	/**
	 * typeを取得します。
	 * @return type
	 */
	public String getType() {
	    return type;
	}
	/**
	 * typeを設定します。
	 * @param type type
	 */
	public void setType(String type) {
	    this.type = type;
	}

}
