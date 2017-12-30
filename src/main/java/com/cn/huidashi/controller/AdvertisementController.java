package com.cn.huidashi.controller;

import com.cn.huidashi.Utils.ParamUtil;
import com.cn.huidashi.entity.Advertisment;
import com.cn.huidashi.entity.page.Pager;
import com.cn.huidashi.entity.pagedto.AdPageParam;
import com.cn.huidashi.service.IAdService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;


@Controller
@RequestMapping("/")
public class AdvertisementController {

    @Autowired
    private IAdService iAdService;

    @Autowired
    private Environment env;

    /**
     * 保存/更新广告信息
     * @param adImageUrl
     * @param advertisment
     * @return
     */
    @PostMapping({"/systemManage/ad/update"})
    public String upd_ad(@RequestParam("adImageUrl") MultipartFile adImageUrl, Advertisment advertisment, BindingResult bindingResult){


        //文件非空时一定会保存
        if(adImageUrl.getSize() != 0){
            //TODO 从配置文件中获取


            String path = env.getProperty("image.ad.path");
            try {
                //TODO 从配置中获取项目路径，保存
                FileUtils.copyToFile(adImageUrl.getInputStream(),new File(path + File.separator + adImageUrl.getOriginalFilename()));
                advertisment.setAdImageUrl(File.separator + "static" +File.separator+ "images" + File.separator+ "ad" + File.separator + adImageUrl.getOriginalFilename());

            } catch (IOException e) {
                e.printStackTrace();
                //TODO 跳转至错误页面
            }
        }

        iAdService.saveOrUpdateAd(advertisment);

        return "redirect:/systemManage/ad/list";
    }

    /**
     * 管理后台场地列表界面
     *
     * @return
     */
    @GetMapping({"/systemManage/ad/list"})
    public String to_bgad_list(Model m, AdPageParam adPageParam) {

        try {
            Pager<Advertisment> pager = iAdService.getAdPaged(adPageParam);

            m.addAttribute("pager", pager);

            String pathParam = ParamUtil.parseBeanToPathParam(adPageParam);
            m.addAttribute("page_header","广告管理");//标题
            m.addAttribute("current_menu","ad_list");//当前菜单高亮
            m.addAttribute("curUrl","/systemManage/ad/list");//分页片段url
            m.addAttribute("pathParam", pathParam);

        } catch (Exception e) {
            e.printStackTrace();
            //TODO 跳转至错误页面

        }


        return "systemManage/ad_list";
    }

}
