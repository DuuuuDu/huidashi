package com.cn.huidashi.controller;

import com.cn.huidashi.Utils.JsonUtil;
import com.cn.huidashi.Utils.ParamUtil;
import com.cn.huidashi.entity.MeetingRoom;
import com.cn.huidashi.entity.Site;
import com.cn.huidashi.entity.page.Pager;
import com.cn.huidashi.entity.pagedto.SitePageParam;
import com.cn.huidashi.entity.paramDto.ResultDto;
import com.cn.huidashi.entity.paramDto.SiteDto;
import com.cn.huidashi.service.ICommonService;
import com.cn.huidashi.service.IMeetingRoomService;
import com.cn.huidashi.service.ISiteService;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.boot.json.JsonJsonParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Controller
@RequestMapping("/")
public class SiteController {

    @Autowired
    private ISiteService iSiteService;

    @Autowired
    private IMeetingRoomService iMeetingRoomService;

    @Autowired
    private ICommonService iCommonService;

    @Autowired
    private Environment env;

    /**
     * 返回场地明细数据（html）
     * @return
     */
    @GetMapping({"/site/detail/{id}"})
    public String to_site_detail_html(Model m,@PathVariable("id") Integer id) {
        try {
            Site site = iSiteService.getSiteById(id);

            if(ObjectUtils.isEmpty(site)){
                return "404";
            }else{
                List<MeetingRoom> rooms = iMeetingRoomService.selectBySiteId(site.getId());

                m.addAttribute("site", site);
                m.addAttribute("rooms", rooms);
            }
        } catch (Exception e) {
            e.printStackTrace();
            //TODO 跳转至错误页面
        }
        return "site_detail_v2";
    }

    /**
     * 返回场地明细数据（html）
     * @return
     */

    //注释部分为调试部分
//    @RequestMapping(
//            method = RequestMethod.GET,
//            value = {"/site/api/detail/{id}"},
//            produces={"text/html;charset=UTF-8;","application/json;"})

    @GetMapping(value = {"/systemManage/site/update/{id}"})
    public String manage_to_site_detail_html(Model m,@PathVariable("id") Integer id) {
        Site site = null;
        try {
            site = iSiteService.getSiteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            site = new Site();
        }
        m.addAttribute("site", site);
        return "systemManage/site_update";
    }



    /**
     * 导航到场地列表界面
     *
     * @return
     */
    @GetMapping({"/site/list"})
    public String to_site_list(Model m, SitePageParam sitePageParam) {

        try {
            m.addAttribute("menus", iCommonService.getAllMenu());
            Pager<Site> pager = iSiteService.getSitePaged(sitePageParam);

            m.addAttribute("pager", pager);

            String pathParam = ParamUtil.parseBeanToPathParam(sitePageParam);
            m.addAttribute("curUrl", "/site/list");//分页片段url
            m.addAttribute("pathParam", pathParam);

        } catch (Exception e) {
            e.printStackTrace();
            //TODO 跳转至错误页面

        }


        return "site_list";
    }


    @PostMapping({"/systemManage/site/file"})
    @ResponseBody
    public JsonNode upload_site_file(@RequestParam("previewImg")MultipartFile previewImg,
                                     @RequestParam(value = "pdfBrief",required = false)MultipartFile pdfBrief) throws IOException {

        //配置文件中获取文件保存路径
        String sitePath = env.getProperty("image.site.path", String.class);
        String siteBriefFile = env.getProperty("image.siteBriefFile.path", String.class);

        File sitePathDir = new File(sitePath);
        if(!sitePathDir.exists()){
            sitePathDir.mkdirs();
        }
        File siteBriefPathDir = new File(siteBriefFile);
        if(!siteBriefPathDir.exists()){
            siteBriefPathDir.mkdirs();
        }

        //操作标识
        ResultDto resultDto = new ResultDto();
        //文件非空时一定会保存
        if(! StringUtils.isEmpty(previewImg.getOriginalFilename()) ){
            try {

                String previewImgName = UUID.randomUUID().toString();
                //配置中获取项目路径，保存
                FileUtils.copyToFile(previewImg.getInputStream(),new File(sitePath + File.separator + previewImgName));
                resultDto.setMessage("文件上传成功");
                Map datas =  Maps.newHashMap();
                datas.put("previewImg",previewImgName);

                if(!ObjectUtils.isEmpty(pdfBrief) && !StringUtils.isEmpty(pdfBrief.getOriginalFilename()) ) {
                    String pdfBriefName = UUID.randomUUID().toString();
                    FileUtils.copyToFile(pdfBrief.getInputStream(), new File(siteBriefFile + File.separator + pdfBriefName));
                    datas.put("pdfBrief",pdfBriefName);
                }

                resultDto.setMapData(datas);

            } catch (IOException e) {
                e.printStackTrace();
                resultDto.setFlag(0);
            }
        }else{
            //预览图必传
            resultDto.setFlag(0);
        }

        return JsonUtil.toJson(resultDto);
    }

    /**
     * 保存/更新场地信息
     * @return
     */
    @PostMapping({"/systemManage/site/update"})
    @ResponseBody
    public JsonNode upds_site(@RequestBody String param) throws IOException {

        //操作标识
        ResultDto resultDto = new ResultDto();

        try {
            Site site = JsonUtil.toPOJO(param,"site",new TypeReference<Site>(){});
            String siteRoute = env.getProperty("image.site.reqRoute", String.class);
            String siteBriefFileRoute = env.getProperty("image.siteBriefFile.reqRoute", String.class);

            site.setPreviewImageUrl(siteRoute + File.separator + JsonUtil.getStringValue(param,"previewImg"));
            site.setPdfBriefUrl(siteBriefFileRoute + File.separator + JsonUtil.getStringValue(param,"pdfBrief"));

            site.setCreateTime(new Date());
            site.setUpdateTime(new Date());

            iSiteService.saveOrUpdateSite(site);

            List<MeetingRoom> rooms = JsonUtil.toPOJO(param,"meetingRooms",new TypeReference<List<MeetingRoom>>(){});
            rooms.forEach(m -> {m.setSiteId(site.getId());});

            iMeetingRoomService.batchInsert(rooms);

        } catch (Exception e) {
            e.printStackTrace();
            resultDto.setFlag(0);
            resultDto.setMessage("会场信息保存失败");
        }

        resultDto.setMessage("会场信息保存成功");

        return JsonUtil.toJson(resultDto);
    }

    /**
     * 移除会场信息
     * @param id
     * @return
     */
    @GetMapping({"/systemManage/site/delete/{id}"})
    public String del_site(@PathVariable("id") int id){
        try{

            iSiteService.deleteSite(id);

        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/systemManage/site/list";
    }

    /**
     * 管理后台场地列表界面
     *
     * @return
     */
    @GetMapping({"/systemManage/site/list"})
    public String to_bgsite_list(Model m, SitePageParam sitePageParam) {

        try {
            Pager<Site> pager = iSiteService.getSitePaged(sitePageParam);

            m.addAttribute("pager", pager);

            String pathParam = ParamUtil.parseBeanToPathParam(sitePageParam);
            m.addAttribute("page_header","场地管理");//标题
            m.addAttribute("current_menu","site_list");//当前菜单高亮
            m.addAttribute("curUrl","/systemManage/site/list");//分页片段url
            m.addAttribute("pathParam", pathParam);

        } catch (Exception e) {
            e.printStackTrace();
            //TODO 跳转至错误页面

        }


        return "systemManage/site_list";
    }

}
