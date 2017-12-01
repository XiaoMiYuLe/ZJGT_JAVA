package com.pro.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.pro.model.Model;

public class ModelService extends JdbcDaoSupport {
	protected DataSource dataSource;
	protected JdbcTemplate jdbcTemplate;
	protected String tableName = "model";
	protected String className = "Model";
	protected String pagekName = "com.pro.model";
	protected HashMap<String, String> modelHm = new HashMap<String, String>();
	protected HashMap<String, String[]> fieldsHm = null;

	// public void setDataSource(DataSource dataSource) {
	// this.dataSource = dataSource;
	// }

	protected String getClassName() {
		return pagekName + "." + className;
	}

	protected HashMap<String, String[]> getFieldsHm() {
		if (fieldsHm == null) {
			Model m = getClassModel();
			fieldsHm = m.getFields();
		}
		return fieldsHm;
	}

	public void modelHmInit() {
		modelHm = null;
		modelHm = new HashMap<String, String>();
	}

	public void setModelHm(String key, String value) {
		modelHm.put(key, value);
	}

	protected HashMap<String, String> getModelHm() {
		return modelHm;
	}

	protected String getTableName() {
		return tableName;
	}

	protected Model getClassModel() {
		Model m = null;
		try {
			m = (Model) Class.forName(getClassName()).newInstance();
		} catch (InstantiationException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return m;
	}

	protected String getFiledsString(String filedsString) {
		String fieldsStr = "";
		if (filedsString == "*") {
			HashMap<String, String[]> getFields = getFieldsHm();
			Iterator<Entry<String, String[]>> iter = getFields.entrySet().iterator();
			while (iter.hasNext()) {
				Entry<String, String[]> entry = iter.next();
				if (fieldsStr != "") {
					fieldsStr += ",";
				}
				fieldsStr += entry.getKey();
			}
		} else {
			fieldsStr = filedsString;
		}
		return fieldsStr;
	}

	protected String getWhere() {
		String whereStr = "";
		if (modelHm != null) {
			Iterator<Entry<String, String>> iter = modelHm.entrySet().iterator();
			while (iter.hasNext()) {
				Entry<String, String> entry = iter.next();
				if (whereStr != "") {
					whereStr += " and ";
				} else {
					whereStr += " where ";
				}
				whereStr += entry.getKey() + " = ? ";
			}
		}
		return whereStr;
	}

	protected Object[] getObjs() {
		Object[] objs = null;
		if (modelHm != null) {
			objs = new Object[modelHm.size()];
			Iterator<Entry<String, String>> iter = modelHm.entrySet().iterator();
			try {
				int index = 0;
				while (iter.hasNext()) {
					Entry<String, String> entry = iter.next();
					objs[index] = entry.getValue();
					index++;
				}
			} catch (NumberFormatException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		modelHmInit();
		return objs;
	}

	protected void setMethodMain(Model getModel, String[] value, Map<String, Object> row) {
		try {
			String getTypeStr = value[0];
			String getFildName = value[1];
			String getMethodName = "set" + value[2];
			switch (getTypeStr) {
			case "int":
				getModel.getClass().getMethod(getMethodName, new Class[] { int.class }).invoke(getModel,
						new Object[] { (int) row.get(getFildName) });
				break;
			case "float":
				getModel.getClass().getMethod(getMethodName, new Class[] { float.class })
						.invoke(getModel, new Object[] { (float) row.get(getFildName) });
				break;
			default:
				getModel.getClass().getMethod(getMethodName, new Class[] { String.class })
						.invoke(getModel, new Object[] { (String) row.get(getFildName) });
				break;
			}
		} catch (IllegalAccessException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

	protected void setMethod(Model getModel, String filedsString, Map<String, Object> row) {
		HashMap<String, String[]> getFields = getFieldsHm();
		if (filedsString == "*") {
			Iterator<Entry<String, String[]>> iter = getFields.entrySet().iterator();
			while (iter.hasNext()) {
				Entry<String, String[]> entry = iter.next();
				String[] value = entry.getValue();
				setMethodMain(getModel, value, row);
			}

		} else {
			String[] filedsStrings = filedsString.split(",");
			for (String filed : filedsStrings) {
				String[] value = getFields.get(filed);
				setMethodMain(getModel, value, row);
			}
		}
	}

	public Map<String, Object> getOne(String sql, Object[] obj) {
		List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql, obj);
		for (Map<String, Object> row : rows) {
			return row;
		}
		return null;
	}

	public Model getOne(String filedsString) {
		String sql = "SELECT " + getFiledsString(filedsString) + " FROM " + getTableName() + getWhere();
		Model getModel = getClassModel();
		Object[] getObj = getObjs();
		Map<String, Object> row = getOne(sql, getObj);
		if (row != null) {
			setMethod(getModel, filedsString, row);
			return getModel;
		}
		return null;
	}

	public Model getOne() {
		return getOne("*");
	}

	public List<Model> getList(String filedsString, int page, int pageSize) {
		String sql = "SELECT " + getFiledsString(filedsString) + " FROM " + getTableName() + getWhere();
		if (pageSize > 0) {
			sql += " LIMIT " + page * pageSize + "," + pageSize;
		}
		Object[] getObj = getObjs();
		return getList(sql, filedsString, getObj);
	}

	public List<Model> getList(String sql, String filedsString, Object[] objs) {
		List<Model> mList = new ArrayList<Model>();
		List<Map<String, Object>> rows = getList(sql, objs);
		for (Map<String, Object> row : rows) {
			Model getModel = getClassModel();
			setMethod(getModel, filedsString, row);
			mList.add(getModel);
		}
		return mList;
	}

	public List<Map<String, Object>> getList(String sql, Object[] objs) {
		return getJdbcTemplate().queryForList(sql, objs);
	}

	public int getTotals(String sql, Object[] obj) {
		return getJdbcTemplate().queryForObject(sql, obj, Integer.class);
	}

	public int getTotals() {
		String sql = "SELECT COUNT(*) FROM " + getTableName() + getWhere();
		Object[] getObj = getObjs();
		return getTotals(sql, getObj);
	}

	protected String[] getInsertStrings() {
		String[] fieldsStr = new String[] { "", "" };
		HashMap<String, String> getFields = getModelHm();
		Iterator<Entry<String, String>> iter = getFields.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, String> entry = iter.next();
			if (fieldsStr[0] != "") {
				fieldsStr[0] += ",";
				fieldsStr[1] += ",";
			}
			fieldsStr[0] += entry.getKey();
			fieldsStr[1] += "?";
		}
		return fieldsStr;
	}

	public int insert() {
		String[] getInsertStrings = getInsertStrings();
		String sql = "INSERT INTO " + getTableName() + " (" + getInsertStrings[0] + ") VALUES ("
				+ getInsertStrings[1] + ")";
		Object[] getObj = getObjs();
		return getJdbcTemplate().update(sql, getObj);
	}

}
