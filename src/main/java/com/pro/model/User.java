package com.pro.model;

import java.util.HashMap;

public class User implements Model {
	private int id;
	private String userName;
	private String password;
	private float age;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public float getAge() {
		return age;
	}

	public void setAge(float age) {
		this.age = age;
	}

	public HashMap<String,String[]> getFields() {
		String[] fieldsId = { "int", "id", "Id","false" };
		String[] fieldsUserName = { "string", "user_name","UserName", "true" };
		String[] fieldsPassword = { "string", "password", "Password","true" };
		String[] fieldsAge = { "float", "age", "Age","true" };
		HashMap<String, String[]> hm = new HashMap<String, String[]>();
		hm.put("id", fieldsId);
		hm.put("user_name", fieldsUserName);
		hm.put("password", fieldsPassword);	
		hm.put("age", fieldsAge);		
//		//hashmap的遍历
//		Iterator<Entry<String, String[]>> iter = hm.entrySet().iterator();
//		while (iter.hasNext()) {
//			Entry<String, String[]> entry = iter.next();
//			Object key = entry.getKey();
//			Object val = entry.getValue();
//		}
		return hm;
	}	
}
