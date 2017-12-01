package com.pro.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.gson.Gson;
import com.pro.action.ModelAction;
import com.pro.action.v1.UserAction;
import com.pro.model.Model;
import com.pro.model.User;
import com.pro.service.UserService;

/**
 * Hello world!
 *
 */
public class Index extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1882393179947731520L;

	// 当前url：http://localhost:8080/CarsiLogCenter_new/idpstat.jsp?action=idp.sptopn
	// request.getRequestURL() http://localhost:8080/CarsiLogCenter_new/idpstat.jsp
	// request.getRequestURI() /CarsiLogCenter_new/idpstat.jsp
	// request.getContextPath()/CarsiLogCenter_new
	// request.getServletPath() /idpstat.jsp
	// request.getQueryString()action=idp.sptopn
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		String getV = req.getParameter("v") == null ? "1" : req.getParameter("v");
		String[] reqURI = req.getRequestURI().split("/");
		try {
			// Class c = Class.forName("com.pro.action.v" + getV + "." + reqURI[2]);
			Class getCLass = Class.forName("com.pro.action.v" + getV + "." + reqURI[2] + "Action");
			/* 以下调用带参的、私有构造函数 */
			Constructor newClass = getCLass.getDeclaredConstructor(
					new Class[] { HttpServletRequest.class, HttpServletResponse.class });
			ModelAction ma = (ModelAction) newClass.newInstance(new Object[] { req, resp });
			ma.getClass().getMethod(reqURI[3]).invoke(ma);
		} catch (NoSuchMethodException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

		// UserAction ua = new UserAction(req, resp);
		// ma.getUserList();
		// @SuppressWarnings("resource")
		// ApplicationContext context = new
		// ClassPathXmlApplicationContext("applicationContext.xml");
		// UserService userS = (UserService) context.getBean("userService");
		// // 添加查询条件
		// userS.setModelHm("id", "2");
		// User getUser = (User) userS.getOne("user_name");
		// if (getUser != null) {
		// System.out.println(getUser.getUserName());
		// }
		//
		// userS.setModelHm("id", "5");
		// List<Model> getModels = userS.getList("user_name,password", 0, 2);
		// for (Model getMOne : getModels) {
		// getUser = (User) getMOne;
		// System.out.println(getUser.getUserName());
		// }
		// System.out.println(userS.getTotals());
		//
		// Object[] obj = new Object[] { "%e%" };
		// List<Model> getModels2 = userS.getList("SELECT id,user_name,password FROM
		// user WHERE user_name like ? ",
		// "user_name,password", obj);
		// for (Model getMOne : getModels2) {
		// getUser = (User) getMOne;
		// System.out.println(getUser.getUserName());
		// }
		//
		//
		// String getV = req.getParameter("v") == null ? "1" : req.getParameter("v");
		// System.out.println(req.getQueryString());
		// System.out.println(getV);

		// try {
		// req.getRequestDispatcher("/servlet/HomeServler") .forward(req, resp);
		// } catch (ServletException e) {
		// // TODO 自动生成的 catch 块
		// e.printStackTrace();
		// }
		// userS.setModelHm("user_name", "ergergeref");
		// userS.setModelHm("password", "84684481");
		// userS.setModelHm("age", "6698");
		// userS.insert();
		//
		// System.out.println(userS.getTotals(null));

	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		doGet(req, resp);
	}
}
