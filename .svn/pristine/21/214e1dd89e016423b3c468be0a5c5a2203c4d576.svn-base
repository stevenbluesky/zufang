package com.ant.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ant.util.ResultUtil;

import com.ant.constant.CommonConstant;

public class SystemFilter implements Filter {
	public void init(FilterConfig config) throws ServletException {
	}
	
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		String path = request.getServletPath();
		
		if(CommonConstant.webPathMap.containsKey(path)){
			chain.doFilter(request, response);
			return;
		}else{
			if(request.getSession().getAttribute("user") == null){
				//跳转到登录页面
	            printJson(request, response, ResultUtil.getSessionFailureInfo());
	            return;
			}else{
				chain.doFilter(request, response);
			}
		}
	
	}
	
	public void destroy() {
		
	}

	public void printJson(HttpServletRequest request,
			HttpServletResponse response,String text) throws IOException{
		PrintWriter writer;    
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		writer = response.getWriter();
		writer.write(text);
			writer.close();
        
	}

	
}
