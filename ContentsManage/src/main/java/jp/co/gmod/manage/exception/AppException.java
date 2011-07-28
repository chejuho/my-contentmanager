package jp.co.gmod.manage.exception;

public class AppException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public AppException(String error) {
		super(error);
	}
	public AppException(String error, Throwable throwable) {
		super(error, throwable);
	}
}
