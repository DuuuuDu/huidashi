package com.cn.huidashi.controller;

import com.cn.huidashi.Utils.ParamUtil;
import com.cn.huidashi.entity.Apply;
import com.cn.huidashi.entity.page.Pager;
import com.cn.huidashi.entity.pagedto.ApplyPageParam;
import com.cn.huidashi.entity.pagedto.SitePageParam;
import com.cn.huidashi.service.IApplyService;
import com.cn.huidashi.service.ICommonService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.BeanUtil;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

/**
 * 用户场地需求申请
 */
@Controller
@RequestMapping("/")
public class ApplyController {

	@Autowired
	private IApplyService iApplyService;

	/**
	 * 用户场地需求前台录入
	 * @param apply
	 * @param request
	 * @return
	 */
	@PostMapping("/apply/post")
	public String index(Apply apply, HttpServletRequest request) {

		apply.setCreateTime(new Date());
        if(apply.getBeginDate() == null){
            apply.setBeginDate(new Date());
        }

		iApplyService.save(apply);

		return "redirect:/";
	}


    /**
     * 标记申请为已读
     * @param id
     * @return
     */
    @PostMapping("systemManage/apply/markReaded")
    @ResponseBody
    public String index(Integer id) {
        return String.valueOf(iApplyService.markReaded(id));
    }

	/**
	 * 用户场地后台管理页面列表
	 * @return
	 */
	@GetMapping("/systemManage/apply/list")
	public String to_manage_dashboard(Model m,ApplyPageParam applyPageParam){
        try {
            ObjectMapper mapper = new ObjectMapper();

            Pager<Apply> pager = iApplyService.getApplyPaged(applyPageParam);

            m.addAttribute("readedCount",iApplyService.getReadedCount());
            m.addAttribute("unReadedCount",iApplyService.getUnReadedCount());

            m.addAttribute("current_menu","apply_list");//当前菜单高亮
            m.addAttribute("page_header","需求汇总");//标题
            m.addAttribute("pager",pager);

            String pathParam = ParamUtil.parseBeanToPathParam(applyPageParam);
            m.addAttribute("curUrl","/systemManage/apply/list");//分页片段url
            m.addAttribute("pathParam", pathParam);
            m.addAttribute("applyPageParam", applyPageParam);


        } catch (Exception e) {
            e.printStackTrace();
            //TODO 跳转至错误页面

        }

        return "systemManage/apply_list";
	}
	
}
