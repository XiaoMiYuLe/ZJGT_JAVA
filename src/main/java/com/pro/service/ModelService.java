package com.pro.service;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.pro.model.Model;

public class ModelService extends JdbcDaoSupport {
	protected JdbcTemplate jdbcTemplate;
	protected String tableName = "model";
	protected String className = "Model";
	protected String pagekName = "com.pro.model";
	protected HashMap<String, Object> modelHm = new HashMap<String, Object>();
	protected HashMap<String, String[]> fieldsHm = null;
	protected Model m = null;

	protected String getClassName() {
		return pagekName + "." + className;
	}

	protected HashMap<String, String[]> getFields() {
		return null;
	}

	protected HashMap<String, String[]> getFieldsHm() {
		if (fieldsHm == null) {
			fieldsHm = getFields();
		}
		return fieldsHm;
	}

	public void modelHmInit() {
		modelHm = new HashMap<String, Object>();
	}

	public void setModelHm(String key, Object value) {
		modelHm.put(key, value);
	}

	protected HashMap<String, Object> getModelHm() {
		return modelHm;
	}

	protected String getTableName() {
		return tableName;
	}

	protected Model getClassModel() {
		if (m == null) {
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
			Iterator<Entry<String, Object>> iter = modelHm.entrySet().iterator();
			while (iter.hasNext()) {
				Entry<String, Object> entry = iter.next();
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
			Iterator<Entry<String, Object>> iter = modelHm.entrySet().iterator();
			try {
				int index = 0;
				while (iter.hasNext()) {
					Entry<String, Object> entry = iter.next();
					objs[index] = entry.getValue();
					index++;
				}
			} catch (NumberFormatException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
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
			case "double":
				getModel.getClass().getMethod(getMethodName, new Class[] { double.class })
						.invoke(getModel, new Object[] { Double
								.parseDouble(String.valueOf(row.get(getFildName))) });
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

	public Map<String, Object> getOneMap(String sql, Object[] obj) {
		List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql, obj);
		for (Map<String, Object> row : rows) {
			return row;
		}
		return null;
	}

	public Model getOneModel(String filedsString) {
		String sql = "SELECT " + getFiledsString(filedsString) + " FROM " + getTableName() + getWhere();
		Model getModel = getClassModel();
		Object[] getObj = getObjs();
		Map<String, Object> row = getOneMap(sql, getObj);
		if (row != null) {
			setMethod(getModel, filedsString, row);
			return getModel;
		}
		return null;
	}

	public Model getOneModel() {
		return getOneModel("*");
	}

	public List<Model> getListModel(String filedsString, int page, int pageSize) {
		String sql = "SELECT " + getFiledsString(filedsString) + " FROM " + getTableName() + getWhere();
		if (pageSize > 0) {
			sql += " LIMIT " + page * pageSize + "," + pageSize;
		}
		Object[] getObj = getObjs();
		return getListModel(sql, filedsString, getObj);
	}

	public List<Model> getListModel(String sql, String filedsString, Object[] objs) {
		List<Model> mList = new ArrayList<Model>();
		List<Map<String, Object>> rows = getListMap(sql, objs);
		for (Map<String, Object> row : rows) {
			Model getModel = getClassModel();
			setMethod(getModel, filedsString, row);
			mList.add(getModel);
		}
		return mList;
	}

	public List<Map<String, Object>> getListMap(String sql, Object[] objs) {
		if (objs == null) {
			return getJdbcTemplate().queryForList(sql);
		} else {
			return getJdbcTemplate().queryForList(sql, objs);
		}
	}

	public List<Map<String, Object>> getListMap(String filedsString, int page, int pageSize) {
		String sql = "SELECT " + getFiledsString(filedsString) + " FROM " + getTableName() + getWhere();
		if (pageSize > 0) {
			sql += " LIMIT " + page * pageSize + "," + pageSize;
		}
		Object[] getObj = getObjs();
		return getListMap(sql, getObj);
	}

	public Map<String, Object> getListMapAndTotal(String sql, Object[] objs) {
		HashMap<String, Object> getResult = new HashMap<String, Object>();
		getResult.put("list", getJdbcTemplate().queryForList(sql, objs));
		getResult.put("total", getTotals(objs));
		return getResult;
	}

	public Map<String, Object> getListMapAndTotal(String filedsString, int page, int pageSize) {
		String sql = "SELECT " + getFiledsString(filedsString) + " FROM " + getTableName() + getWhere();
		if (pageSize > 0) {
			sql += " LIMIT " + page * pageSize + "," + pageSize;
		}
		Object[] getObj = getObjs();
		return getListMapAndTotal(sql, getObj);
	}

	public int getTotals(String sql, Object[] obj) {
		return getJdbcTemplate().queryForObject(sql, obj, Integer.class);
	}

	public int getTotals(Object[] obj) {
		String sql = "SELECT COUNT(*) FROM " + getTableName() + getWhere();
		return getTotals(sql, obj);
	}

	public int getTotals() {
		String sql = "SELECT COUNT(*) FROM " + getTableName() + getWhere();
		Object[] getObj = getObjs();
		return getTotals(sql, getObj);
	}

	protected String[] getInsertStrings() {
		String[] fieldsStr = new String[] { "", "" };
		Iterator<Entry<String, Object>> iter = modelHm.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, Object> entry = iter.next();
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
		Connection conn = null;
		int upResult = 0;
		try {
			conn = jdbcTemplate.getDataSource().getConnection();
			conn.setAutoCommit(false);
			upResult = getJdbcTemplate().update(sql, getObj);
			conn.commit();
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				conn.setAutoCommit(true);
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return upResult;
	}

	public int insert(String sql, Object obj) {
		Connection conn = null;
		int upResult = 0;
		try {
			conn = jdbcTemplate.getDataSource().getConnection();
			conn.setAutoCommit(false);
			upResult = getJdbcTemplate().update(sql, obj);
			conn.commit();
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				conn.setAutoCommit(true);
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return upResult;
	}

}
