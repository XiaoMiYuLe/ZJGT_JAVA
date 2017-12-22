package com.pro.api;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pro.action.ModelAction;

/**
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
		if (reqURI.length >= 3) {
			try {
				// reqURI[2] 获取类名
				Class<?> getCLass = Class
						.forName("com.pro.action.v" + getV + "." + reqURI[2] + "Action");
				/* 以下调用带参的、私有构造函数 */
				Constructor<?> newClass = getCLass.getDeclaredConstructor(
						new Class[] { HttpServletRequest.class, HttpServletResponse.class });
				ModelAction ma = (ModelAction) newClass.newInstance(new Object[] { req, resp });
				// reqURI[3] 获取方法名
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
		}
		System.out.println("end");
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
		doGet(req, resp);
	}
}
