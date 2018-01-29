package com.wm.controller.base;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.context.SecurityContextHolder;

import com.github.pagehelper.Page;
import com.wm.model.LoginUserDetails;

/**
 * 
 * @version 1.0
 * @author WangMingM
 * @date 2017.11.13
 * @param <T>
 */
public abstract class BaseController<T> {

	public Map<String, Object> getPageInfo(Page<T> page) {
		Map<String, Object> pageInfo = new HashMap<String, Object>();
		pageInfo.put("totalRows", page.getTotal());
		pageInfo.put("totalPage", page.getPages());
		pageInfo.put("pageSize", page.getPageSize());
		pageInfo.put("curPage", page.getPageNum());
		return pageInfo;
	}

	public Map<String, Object> getPageInfo(List<?> list) {
		Page<?> page = (Page<?>) list;
		Map<String, Object> pageInfo = new HashMap<String, Object>();
		pageInfo.put("totalRows", page.getTotal());
		pageInfo.put("totalPage", page.getPages());
		pageInfo.put("pageSize", page.getPageSize());
		pageInfo.put("curPage", page.getPageNum());
		return pageInfo;
	}

	public Map<String, Object> getFormParam(HttpServletRequest request) {
		String str = request.getParameter("formParam");
		Map<String, Object> param = new HashMap<String, Object>();
		if (str != null) {
			String[] arr = str.split("&");
			for (int i = 0; i < arr.length; i++) {
				String[] kv = arr[i].split("=");
				if (kv.length == 2) {
					try {
						param.put(kv[0], URLDecoder.decode(kv[1], "utf-8"));
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return param;
	}

	public LoginUserDetails getLoginUser() {
		LoginUserDetails userDetails = null;
		try {
			userDetails = (LoginUserDetails) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userDetails;
	}

	public String getLoginUserId() {
		String id = null;
		try {
			LoginUserDetails userDetails = (LoginUserDetails) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
			id = userDetails.getId();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}

	public boolean checkLogin(String uId) {
		boolean flag = false;

		LoginUserDetails ld = this.getLoginUser();
		if (ld == null || ld.getId() == null)
			return flag;

		if (ld.getId().equals(uId))
			flag = true;

		return flag;
	}
}
