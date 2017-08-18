package com.dmm.module.controller;

import com.dmm.common.core.BackResult;
import com.dmm.common.core.DataTablesPager;
import com.dmm.common.core.ResponseCodeEnum;
import com.dmm.common.utils.PageUtils;
import com.dmm.common.utils.UserUtils;
import com.dmm.module.domain.Resource;
import com.dmm.module.domain.Role;
import com.dmm.module.domain.User;
import com.dmm.module.service.ResourceService;
import com.dmm.module.service.RoleService;
import com.dmm.module.service.UserService;
import com.github.pagehelper.Page;
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
import java.util.*;

/**
 * 角色控制器Mode
 */
@Controller
@RequestMapping("/roles")
public class RoleController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    RoleService roleService;

    @Autowired
    ResourceService resourceService;

    @RequestMapping(value = {"list", ""},method = RequestMethod.GET)
    public String list(Model model){
        return "role/role_list";
    }

    @RequestMapping(value = {"pagerData", ""},method = RequestMethod.GET)
    @ResponseBody
    public DataTablesPager<List<User>> ajaxPager(HttpServletRequest request){

        //设定分页参数
        Page<Object> pagerParams = PageUtils.getPagerParams(request);

        //组装查询参数
        Map<String,Object> params = new HashMap<>();
        params.put("delFlag",Role.DEL_FLAG_NO);

        String searchText = request.getParameter("searchText");

        if(StringUtils.isNotBlank(searchText)){
            params.put("searchText",searchText);
        }


        //获取查询数据
        Page<List<Role>> listPage = pagerParams.doSelectPage(() -> roleService.selectByMap(params));

        //转换为DataTables数据
        DataTablesPager dataTablesPager = PageUtils.pageHelperToDataTablesPager(listPage, request);

        return dataTablesPager;

    }


    @RequestMapping(value = "/form",method = RequestMethod.GET)
    public String add(HttpServletRequest request,Model model){

        String id = request.getParameter("id");
        Role role = new Role();

        //编辑
        if(StringUtils.isNotBlank(id)){
            role = roleService.selectByPrimaryKey(id);
            model.addAttribute("isOperatorEdit",true);
        }else{
            model.addAttribute("isOperatorEdit",false);
        }

        model.addAttribute("role",role);

        return "role/role_form";
    }


    @RequestMapping(value = "/save",method = RequestMethod.POST)
    @ResponseBody
    public  BackResult<String> save(@Validated Role role){

        BackResult<String> backResult = new BackResult<>();

        try{

            String id = role.getId();

            if(StringUtils.isNotBlank(id)){ //编辑

                role.setUpdateBy(UserUtils.getCurrUserId());
                role.setUpdateDate(new Date());
                roleService.updateByPrimaryKey(role);

            }else{ //新增
                role.setCreateBy(UserUtils.getCurrUserId());
                role.setIsSys(Role.IS_SYS_FLASE);
                role.preInsert();
                roleService.insert(role);
            }

            backResult.setCode(ResponseCodeEnum.BACK_CODE_SUCCESS.value);

        }catch (Exception ex){

            LOGGER.error("保存角色信息异常",ex);
            backResult.setCode(ResponseCodeEnum.BACK_CODE_FAIL.value);
        }

        return backResult;

    }

    @RequestMapping(value = "/del/{id}",method = RequestMethod.GET)
    @ResponseBody
    public BackResult<String> del(@PathVariable("id")String id){

        BackResult<String> backResult = new BackResult<>();

        try{

            Role role = new Role();
            role.setId(id);
            role.setDelFlag(User.DEL_FLAG_YES);
            roleService.updateByPrimaryKeySelective(role);
            backResult.setCode(ResponseCodeEnum.BACK_CODE_SUCCESS.value);

        }catch (Exception ex){

            LOGGER.error("删除角色信息异常",ex);
            backResult.setCode(ResponseCodeEnum.BACK_CODE_FAIL.value);
        }

        return backResult;

    }

    @RequestMapping(value = "/nameOnlyCheck",method = RequestMethod.GET)
    @ResponseBody
    public boolean noOnlyCheck(@RequestParam("name")String name,@RequestParam("oldName")String oldName){


        try{

            if(StringUtils.isNotBlank(name)){

                if(StringUtils.isNotBlank(oldName) && name.equals(oldName)){
                    return  true;
                }

                Role role = new Role();
                role.setDelFlag(Role.DEL_FLAG_NO);
                role.setName(name);

                List<Role> roles = roleService.selectBySelective(role);

                if(CollectionUtils.isNotEmpty(roles)){
                    return false;
                }
            }


        }catch (Exception ex){
            LOGGER.error("验证名称唯一性异常",ex);
        }

        return true;

    }

    @RequestMapping(value = "/descriptionOnlyCheck",method = RequestMethod.GET)
    @ResponseBody
    public boolean descriptionOnlyCheck(@RequestParam("description")String description,@RequestParam("oldDescription")String oldDescription){


        try{

            if(StringUtils.isNotBlank(description)){

                if(StringUtils.isNotBlank(oldDescription) && description.equals(oldDescription)){
                    return  true;
                }

                Role role = new Role();
                role.setDelFlag(Role.DEL_FLAG_NO);
                role.setDescription(description);

                List<Role> roles = roleService.selectBySelective(role);

                if(CollectionUtils.isNotEmpty(roles)){
                    return false;
                }
            }


        }catch (Exception ex){
            LOGGER.error("验证描述唯一性异常",ex);
        }

        return true;

    }


    /**
     * 根据角色ID获取角色拥有的资源
     * @param roleId
     * @return
     */
    @RequestMapping(value = "/resources",method = RequestMethod.GET)
    @ResponseBody
    public List<Resource> getRoleResources(@RequestParam("roleId")String roleId){


        try{

            return  resourceService.selectByRoleId(roleId);

        }catch (Exception ex){
            LOGGER.error("根据角色ID获取角色拥有的资源",ex);
        }

        return new ArrayList<>();

    }


    /**
     * 根据角色ID获取角色拥有的资源Id列表
     * @param roleId
     * @return
     */
    @RequestMapping(value = "/resourceIds",method = RequestMethod.GET)
    @ResponseBody
    public List<String> getRoleResourceIds(@RequestParam("roleId")String roleId){

        List<String> resourceIdList = new ArrayList<>();

        try{

            List<Resource> resourceList = resourceService.selectByRoleId(roleId);

            if(CollectionUtils.isNotEmpty(resourceList)){
                resourceList.forEach(item ->{
                    resourceIdList.add(item.getId());
                });
            }

        }catch (Exception ex){
            LOGGER.error("根据角色ID获取角色拥有的资源",ex);
        }

        return resourceIdList;

    }


    /**
     * 保存授权信息
     * @param roleId 角色ID
     * @param resourceIdList 资源ID列表
     * @return
     */

    @RequestMapping(value = "/saveAuthorize",method = RequestMethod.POST)
    @ResponseBody
    public  BackResult<String> saveAuthorize(@RequestParam("roleId") String roleId,@RequestParam("resourceIdList[]") String[] resourceIdList){

        BackResult<String> backResult = new BackResult<>();

        try{

            resourceService.saveAuthorize(roleId,resourceIdList);
            backResult.setCode(ResponseCodeEnum.BACK_CODE_SUCCESS.value);

        }catch (Exception ex){

            LOGGER.error("保存角色信息异常",ex);
            backResult.setCode(ResponseCodeEnum.BACK_CODE_FAIL.value);
        }

        return backResult;

    }

}