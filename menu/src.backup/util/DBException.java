package util;

import util.BaseException;

public class DBException extends BaseException {
	public DBException(java.lang.Throwable ex){
		super("���ݿ��������"+ex.getMessage());
	}
}
