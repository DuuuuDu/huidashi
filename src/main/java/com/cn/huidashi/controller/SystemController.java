package com.cn.huidashi.controller;

import com.cn.huidashi.Utils.MD5Util;
import com.cn.huidashi.component.AuthorityFilter;
import com.cn.huidashi.entity.User;
import com.cn.huidashi.entity.pagedto.SitePageParam;
import com.cn.huidashi.service.ICommonService;
import com.cn.huidashi.service.ISiteService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.BeanUtilsBean2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 用户登陆管理
 */
@Controller
@RequestMapping("/manage")
public class SystemController {

	@Autowired
	private ICommonService commonService;

	@PostMapping("/dologin")
	public String dologin(HttpServletRequest request, User user, Model m) {

		//TODO 验证码通过
		if(true){

			User dbuser = commonService.validateUser(user);
			//用户名密码校验通过,保存会话信息，登录到管理首页
			if(dbuser != null){
				request.getSession().setAttribute(AuthorityFilter.LOGIN_USER,dbuser);
				return "redirect:/systemManage/apply/list?hasRead=false";
			}else{
				//用户名或者密码不通过
				m.addAttribute("msg","用户名或密码不正确");
				return "manage/login";
			}

		}else{
			//验证码不通过
			m.addAttribute("msg","验证码不正确");
			return "manage/login";
		}
	}

	@GetMapping("/dologout")
	public String dologout(HttpServletRequest request, User user, Model m) {

		request.getSession().invalidate();
		return "manage/login";
		//验证码通过
//		if(true){
//
//			SystemAdmin temp = CacheService.SYSTEM_ADMIN.get(systemAdmin.getUsername());
//
//			//用户名密码校验通过,保存会话信息，登录到管理首页
//			if(temp != null && MD5Util.MD5Encode(systemAdmin.getUsername() + systemAdmin.getPassword(),"UTF-8").equals(temp.getPassword())){
//
//				request.getSession().setAttribute(AuthorityFilter.LOGIN_USER,temp);
//
//				return "redirect:/systemManage/apply/list?hasRead=false";
//			}else{
//				//用户名或者密码不通过
//				m.addAttribute("msg","用户名或密码不正确");
//				return "/manage/login";
//			}
//
//		}else{
//			//验证码不通过
//			m.addAttribute("msg","验证码不正确");
//			return "/manage/login";
//		}
	}
	
}
