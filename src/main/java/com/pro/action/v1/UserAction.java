package com.pro.action.v1;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pro.action.ModelAction;
import com.pro.service.UserService;

public class UserAction extends ModelAction {
	public UserAction(HttpServletRequest req, HttpServletResponse resp) {
		super(req, resp);
	}

	public void getUserList() {
		UserService userS = (UserService) context.getBean("userService");
		int page = req.getParameter("page") == null ? 0 : Integer.parseInt(req.getParameter("page"));
		int pageSize = req.getParameter("pageSize") == null ? 10
				: Integer.parseInt(req.getParameter("pageSize"));
//		userS.setModelHm("id", 5);
		Map<String, Object> getListMapAndTotal = userS
				.getListMapAndTotal("id,user_name,password", page , pageSize);
		showResult(0, "", getListMapAndTotal);
	}

	public void getUser() {
		UserService userS = (UserService) context.getBean("userService");
		Object[] obj = new Object[] { req.getParameter("id") };
		Map<String, Object> getMap = userS.getOneMap(
				"SELECT id as ID,user_name as userName,password FROM user WHERE id = ? ", obj);
		showResult(0, "", getMap);
	}
}
