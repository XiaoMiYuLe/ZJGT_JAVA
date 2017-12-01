package com.pro.service;

public class UserService extends ModelService {
	protected String className = "User";
	protected String tableName = "user";

	protected String getClassName() {
		return pagekName + "." + className;
	}
	
	protected String getTableName() {
		return tableName;
	}
}
