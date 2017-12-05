package com.pro.service;

import java.util.HashMap;

public class UserService extends ModelService {
	protected String className = "User";
	protected String tableName = "user";

	protected String getClassName() {
		return pagekName + "." + className;
	}

	protected String getTableName() {
		return tableName;
	}

	protected HashMap<String, String[]> getFields() {
		String[] fieldsId = { "int", "id", "Id" };
		String[] fieldsUserName = { "string", "user_name", "UserName" };
		String[] fieldsPassword = { "string", "password", "Password" };
		String[] fieldsAge = { "double", "age", "Age" };
		HashMap<String, String[]> hm = new HashMap<String, String[]>();
		hm.put("id", fieldsId);
		hm.put("user_name", fieldsUserName);
		hm.put("password", fieldsPassword);
		hm.put("age", fieldsAge);
		return hm;
	}
}
