package util;

import util.BaseException;

public class DBException extends BaseException {
	public DBException(java.lang.Throwable ex){
		super("Êý¾Ý¿â²Ù×÷´íÎó£º"+ex.getMessage());
	}
}
