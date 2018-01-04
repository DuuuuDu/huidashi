package com.cn.huidashi.controller;

import com.cn.huidashi.Utils.JsonUtil;
import com.cn.huidashi.Utils.ParamUtil;
import com.cn.huidashi.entity.MeetingRoom;
import com.cn.huidashi.entity.Site;
import com.cn.huidashi.entity.page.Pager;
import com.cn.huidashi.entity.pagedto.SitePageParam;
import com.cn.huidashi.entity.paramDto.ResultDto;
import com.cn.huidashi.service.ICommonService;
import com.cn.huidashi.service.IMeetingRoomService;
import com.cn.huidashi.service.ISiteService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Maps;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
     *
     * @return
     */
    @GetMapping({"/site/detail/{id}"})
    public String fetch_site_detail_html(Model m, @PathVariable("id") Integer id) {
        try {
            Site site = iSiteService.getSiteById(id);

            if (ObjectUtils.isEmpty(site)) {
                return "404";
            } else {
                List<MeetingRoom> rooms = iMeetingRoomService.selectBySiteId(site.getId());

                m.addAttribute("site", site);
                m.addAttribute("rooms", rooms);
            }
        } catch (Exception e) {
            e.printStackTrace();
            //TODO 跳转至错误页面
        }
        return "site_detail";
    }

    /**
     * 返回场地明细数据（html）
     *
     * @return
     */
    @GetMapping(value = {"/site/detail/api/{id}"})
    @ResponseBody
    public JsonNode fetch_site_detail_json(Model m, @PathVariable("id") Integer id) throws IOException {
        ResultDto resultDto = new ResultDto();
        Site site = null;
        try {
            site = iSiteService.getSiteById(id);

            if (ObjectUtils.isEmpty(site)) {
                resultDto.setFlag(0);
                JsonUtil.toJson(resultDto);
            } else {
                List<MeetingRoom> rooms = iMeetingRoomService.selectBySiteId(site.getId());

                HashMap<String, Object> mapData = Maps.newHashMap();
                mapData.put("site", site);
                mapData.put("rooms", rooms);
                resultDto.setMapData(mapData);
            }
        } catch (Exception e) {
            resultDto.setFlag(0);
        }
        return JsonUtil.toJson(resultDto);
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

    @PostMapping({"/systemManage/site/saveOrUpdate"})
    @ResponseBody
    public JsonNode upload_site_file(@RequestParam(value = "previewImg", required = false) MultipartFile previewImg,
                                     @RequestParam(value = "pdfBrief", required = false) MultipartFile pdfBrief,
                                     MultipartHttpServletRequest request) throws IOException {

        //配置文件中获取文件保存路径
        String sitePath = env.getProperty("image.site.path", String.class);
        String siteBriefFile = env.getProperty("image.siteBriefFile.path", String.class);

        //配置文件中获取静态资源的路由
        String siteRoute = env.getProperty("image.site.reqRoute", String.class);
        String siteBriefFileRoute = env.getProperty("image.siteBriefFile.reqRoute", String.class);

        //操作标识
        ResultDto resultDto = new ResultDto();

        try {

            Site site = JsonUtil.toPOJO(request.getParameter("site"), new TypeReference<Site>() {});

            //id不为空白，意味着要更新
            if (!StringUtils.isEmpty(site.getId())) {

                int id = site.getId();

                Site siteDb = iSiteService.getSiteById(id);

                //文件是否重复上传了，如果上传了，需要删除源文件
                if (!ObjectUtils.isEmpty(previewImg)
                        &&
                        !StringUtils.isEmpty(previewImg.getOriginalFilename())) {

                    String previewImageName = siteDb.getPreviewImageUrl().substring(siteDb.getPreviewImageUrl().lastIndexOf("/"));
                    //删除原文件
                    FileUtils.forceDelete(new File(sitePath + File.separator + previewImageName));
                    //生成文件名
                    String previewFileName = UUID.randomUUID().toString();
                    //保存新文件
                    FileUtils.copyToFile(previewImg.getInputStream(), new File(sitePath + File.separator + previewFileName));
                    //保存请求路由
                    site.setPreviewImageUrl(siteRoute + File.separator + previewFileName);

                }
                if (!ObjectUtils.isEmpty(pdfBrief)
                        &&
                        !StringUtils.isEmpty(pdfBrief.getOriginalFilename())) {

                    String getPdfBriefName = siteDb.getPdfBriefUrl().substring(siteDb.getPdfBriefUrl().lastIndexOf("/"));
                    //删除源文件
                    FileUtils.forceDelete(new File(siteBriefFile + File.separator + getPdfBriefName));
                    //生成文件名
                    String pdfFileName = UUID.randomUUID().toString();
                    //保存新文件
                    FileUtils.copyToFile(pdfBrief.getInputStream(), new File(siteBriefFile + File.separator + pdfFileName));
                    //保存请求路由
                    site.setPdfBriefUrl(siteBriefFileRoute + File.separator + pdfFileName);
                }
                //更新会场信息
                iSiteService.saveOrUpdateSite(site);

                //避免校验更新时房间信息和数据库房间信息两个对象数组之间的差异，直接删除所有旧的房间信息
                iMeetingRoomService.deleteBySiteId(site.getId());
                List<MeetingRoom> rooms = JsonUtil.toPOJO(request.getParameter("meetingRooms"), new TypeReference<List<MeetingRoom>>() {
                });
                rooms.forEach(m -> {
                    m.setSiteId(site.getId());
                });
                iMeetingRoomService.batchInsert(rooms);

                resultDto.setMessage("会场信息更新成功");

                return JsonUtil.toJson(resultDto);

            } else {
                File sitePathDir = new File(sitePath);
                if (!sitePathDir.exists()) {
                    sitePathDir.mkdirs();
                }
                File siteBriefPathDir = new File(siteBriefFile);
                if (!siteBriefPathDir.exists()) {
                    siteBriefPathDir.mkdirs();
                }

                //预览图必须上传
                if (!StringUtils.isEmpty(previewImg.getOriginalFilename())) {

                    //文件名为UUID
                    String previewImgName = UUID.randomUUID().toString();
                    String pdfBriefName = "";

                    //配置中获取项目路径，保存
                    FileUtils.copyToFile(previewImg.getInputStream(), new File(sitePath + File.separator + previewImgName));

                    //PDF描述文件为上传可选项
                    if (!ObjectUtils.isEmpty(pdfBrief) && !StringUtils.isEmpty(pdfBrief.getOriginalFilename())) {
                        pdfBriefName = UUID.randomUUID().toString();
                        FileUtils.copyToFile(pdfBrief.getInputStream(), new File(siteBriefFile + File.separator + pdfBriefName));
                    }

                    //保存图片请求url
                    site.setPreviewImageUrl(siteRoute + File.separator + previewImgName);
                    site.setPdfBriefUrl(siteBriefFileRoute + File.separator + pdfBriefName);

                    site.setCreateTime(new Date());
                    site.setUpdateTime(new Date());

                    iSiteService.saveOrUpdateSite(site);

                    List<MeetingRoom> rooms = JsonUtil.toPOJO(request.getParameter("meetingRooms"), new TypeReference<List<MeetingRoom>>() {
                    });
                    rooms.forEach(m -> {
                        m.setSiteId(site.getId());
                    });

                    iMeetingRoomService.batchInsert(rooms);

                    resultDto.setMessage("会场信息保存成功");

                    return JsonUtil.toJson(resultDto);


                } else {
                    //预览图必传
                    resultDto.setFlag(0);
                    resultDto.setMessage("预览图必须上传");
                }

                return JsonUtil.toJson(resultDto);
            }

        } catch (Exception e) {
            e.printStackTrace();
            resultDto.setFlag(0);
            resultDto.setMessage("场地信息保存异常，请重试，或联系系统管理员");
            return JsonUtil.toJson(resultDto);
        }


    }

    /**
     * 保存/更新场地信息
     * @return
     */
//    @PostMapping({"/systemManage/site/update"})
//    @ResponseBody
//    public JsonNode upds_site(@RequestBody String param) throws IOException {
//
//
//    }

    /**
     * 移除会场信息
     *
     * @param id
     * @return
     */
    @GetMapping({"/systemManage/site/delete/{id}"})
    public String del_site(@PathVariable("id") int id) {
        try {

            iSiteService.deleteSite(id);

        } catch (Exception e) {
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
            m.addAttribute("page_header", "场地管理");//标题
            m.addAttribute("current_menu", "site_list");//当前菜单高亮
            m.addAttribute("curUrl", "/systemManage/site/list");//分页片段url
            m.addAttribute("pathParam", pathParam);

        } catch (Exception e) {
            e.printStackTrace();
            //TODO 跳转至错误页面

        }
        return "systemManage/site_list";
    }
}
