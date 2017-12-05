package com.pro.action.v1;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pro.action.ModelAction;
import com.pro.service.UserService;

public class UserAction extends ModelAction{
	public UserAction(HttpServletRequest req, HttpServletResponse resp) {
		super(req, resp);
	}

	public void getUserList() {
		UserService userS = (UserService) context.getBean("userService");
		Object[] obj = new Object[] { "%e%" };
		List<Map<String, Object>> getListMap =  userS.getListMap("SELECT id,user_name,password FROM user WHERE user_name like ? ", obj);
		showResult(0,"",getListMap);
	}
	
	public void getUser() {
		UserService userS = (UserService) context.getBean("userService");
		Object[] obj = new Object[] {req.getParameter("id") };
		Map<String, Object> getMap =  userS.getOneMap("SELECT id as ID,user_name as userName,password FROM user WHERE id = ? ",obj);
		showResult(0,"",getMap);
	}	
}
