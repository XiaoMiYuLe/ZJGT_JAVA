package com.pro.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.gson.Gson;

public class ModelAction {
	protected HttpServletRequest req;
	protected HttpServletResponse resp;
	protected static final ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

	ModelAction() {

	}

	protected ModelAction(HttpServletRequest req, HttpServletResponse resp) {
		this.req = req;
		this.resp = resp;
	}

	public void showResult(int errorNo, String errorMesg, Object data) {
		LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("errorNo", errorNo);
		result.put("errorMesg", errorMesg);
		if(data == null) {
			data = "";
		}
		result.put("data", data);
		// 调用GSON jar工具包封装好的toJson方法，可直接生成JSON字符串
		Gson gson = new Gson();
		String json = gson.toJson(result);
		// 输出到界面
		resp.setContentType("text/plain");
		resp.setCharacterEncoding("utf-8");
		PrintWriter out;
		try {
			out = new PrintWriter(resp.getOutputStream());
			out.print(json);
			out.flush();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
}
