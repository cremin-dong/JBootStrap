package com.dmm.module.controller;

import com.dmm.common.core.BackResult;
import com.dmm.common.core.ResponseCodeEnum;
import com.dmm.common.utils.TreeUtils;
import com.dmm.common.utils.UserUtils;
import com.dmm.module.domain.Resource;
import com.dmm.module.domain.User;
import com.dmm.module.service.ResourceService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * 用户控制器Mode
 */
@Controller
@RequestMapping("/resources")
public class ResourceController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceController.class);

    @Autowired
    ResourceService resourceService;

    @RequestMapping(value = {"list", ""},method = RequestMethod.GET)
    public String list(Model model){


        Resource resource = new Resource();
        resource.setDelFlag(Resource.DEL_FLAG_NO);

        List<Resource> resourceList = resourceService.selectBySelective(resource);


        TreeUtils treeUtils = new TreeUtils<>(resourceList);
        resourceList = treeUtils.buildTreeTableList();

        model.addAttribute("resourceList",resourceList);

        return "resource/resource_list";
    }


    @RequestMapping(value = {"ztreeList"},method = RequestMethod.GET)
    @ResponseBody
    public List<Resource> ajaxZtreeList(HttpServletRequest request,Model model){

        Resource resource = new Resource();
        resource.setDelFlag(Resource.DEL_FLAG_NO);

        String type = request.getParameter("type");
        if(StringUtils.isNotBlank(type)){
            resource.setType(type);
        }


        List<Resource> resourceList = resourceService.selectBySelective(resource);

        return resourceList;
    }

    @RequestMapping(value = "/del/{id}",method = RequestMethod.GET)
    @ResponseBody
    public BackResult<String> del(@PathVariable("id")String id){

        BackResult<String> backResult = new BackResult<>();

        try{

            resourceService.delResourceAndChilds(id);

            backResult.setCode(ResponseCodeEnum.BACK_CODE_SUCCESS.value);

        }catch (Exception ex){

            LOGGER.error("删除该资源及所有子资源项异常",ex);
            backResult.setCode(ResponseCodeEnum.BACK_CODE_FAIL.value);
        }

        return backResult;

    }


    @RequestMapping(value = "/form",method = RequestMethod.GET)
    public String add(HttpServletRequest request,Model model){

        String id = request.getParameter("id");
        Resource resource = new Resource();

        //编辑
        if(StringUtils.isNotBlank(id)){
            resource = resourceService.selectByPrimaryKey(id);
            model.addAttribute("isOperatorEdit",true);
        }else{

            resource.setType(Resource.TYPE_MENU);
            resource.setSort(Resource.DEFULAT_SORT_VALUE);

            String parentId = request.getParameter("parentId");
            if(StringUtils.isNotBlank(parentId)){
                resource.setParentId(parentId);
            }

            model.addAttribute("isOperatorEdit",false);
        }

        model.addAttribute("resource",resource);

        return "resource/resource_form";
    }


    @RequestMapping(value = "/save",method = RequestMethod.POST)
    @ResponseBody
    public  BackResult<String> save(@Validated Resource resource){

        BackResult<String> backResult = new BackResult<>();

        try{

            String id = resource.getId();

            //设置父节点串信息
            String parentId = resource.getParentId();
            if(StringUtils.isNotBlank(parentId)){

                Resource parentResource = resourceService.selectByPrimaryKey(parentId);
                if(parentResource != null){

                    //设定父节点信息
                    resource.setParentId(parentId);
                    resource.setParentIds(parentResource.getParentIds() == null? parentId
                            :String.join(",",parentResource.getParentIds(),parentId));
                }
            }else{
                resource.setParentId(null);
                resource.setParentIds(null);
            }


            if(StringUtils.isNotBlank(id)){ //编辑

                resource.setUpdateBy(UserUtils.getCurrUserId());
                resource.setUpdateDate(new Date());
                resourceService.updateByPrimaryKey(resource);

            }else{ //新增

                //设置默认排序值
                if(resource.getSort() == null){
                    resource.setSort(Resource.DEFULAT_SORT_VALUE);
                }

                
                resource.setCreateBy(UserUtils.getCurrUserId());
                resource.preInsert();
                resourceService.insert(resource);
            }

            backResult.setCode(ResponseCodeEnum.BACK_CODE_SUCCESS.value);

        }catch (Exception ex){

            LOGGER.error("保存员工信息异常",ex);
            backResult.setCode(ResponseCodeEnum.BACK_CODE_FAIL.value);
        }

        return backResult;

    }

    @RequestMapping(value = "/nameOnlyCheck",method = RequestMethod.GET)
    @ResponseBody
    public boolean nameOnlyCheck(@RequestParam("name")String name,@RequestParam("oldName")String oldName){


        try{

            if(StringUtils.isNotBlank(name)){

                if(StringUtils.isNotBlank(oldName) && name.equals(oldName)){
                    return  true;
                }

                Resource resource = new Resource();
                resource.setName(name);
                resource.setDelFlag(User.DEL_FLAG_NO);

                List<Resource> resources = resourceService.selectBySelective(resource);

                if(CollectionUtils.isNotEmpty(resources)){
                    return false;
                }

            }

        }catch (Exception ex){
            LOGGER.error("验证名称唯一性异常",ex);
        }

        return true;

    }

    @RequestMapping(value = "/permissionOnlyCheck",method = RequestMethod.GET)
    @ResponseBody
    public boolean permissionOnlyCheck(@RequestParam("permission")String permission,@RequestParam("oldPermission")String oldPermission){


        try{

            if(StringUtils.isNotBlank(permission)){

                if(StringUtils.isNotBlank(oldPermission) && permission.equals(oldPermission)){
                    return  true;
                }

                Resource resource = new Resource();
                resource.setPermission(permission);
                resource.setDelFlag(User.DEL_FLAG_NO);

                List<Resource> resources = resourceService.selectBySelective(resource);

                if(CollectionUtils.isNotEmpty(resources)){
                    return false;
                }

            }

        }catch (Exception ex){
            LOGGER.error("验证权限唯一性异常",ex);
        }

        return true;

    }

}