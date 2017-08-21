package com.dmm.module.controller;

import com.dmm.common.core.BackResult;
import com.dmm.common.core.DataTablesPager;
import com.dmm.common.constants.ResponseCodeEnum;
import com.dmm.common.utils.PageUtils;
import com.dmm.common.utils.UserUtils;
import com.dmm.module.domain.Role;
import com.dmm.module.domain.User;
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
 * 用户控制器Mode
 */
@Controller
@RequestMapping("/users")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @RequestMapping(value = {"list", ""}, method = RequestMethod.GET)
    public String list(Model model) {
        return "user/user_list";
    }

    @RequestMapping(value = {"pagerData", ""}, method = RequestMethod.GET)
    @ResponseBody
    public DataTablesPager<List<User>> ajaxPager(HttpServletRequest request) throws Exception {


        //设定分页参数
        Page<Object> pagerParams = PageUtils.getPagerParams(request);

        //组装查询参数
        Map<String, Object> params = new HashMap<>();
        params.put("delFlag", User.DEL_FLAG_NO);

        String usernameLike = request.getParameter("usernameLike");
        String no = request.getParameter("no");

        if (StringUtils.isNotBlank(usernameLike)) {
            params.put("usernameLike", usernameLike);
        }

        if (StringUtils.isNotBlank(no)) {
            params.put("no", no);
        }


        //获取查询数据
        Page<List<User>> listPage = pagerParams.doSelectPage(()
                -> userService.selectByMap(params));

        //转换为DataTables数据
        DataTablesPager dataTablesPager = PageUtils.pageHelperToDataTablesPager(listPage, request);

        return dataTablesPager;
    }


    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String add(HttpServletRequest request, Model model) {

        String id = request.getParameter("id");
        User user = new User();

        //编辑
        if (StringUtils.isNotBlank(id)) {
            user = userService.selectByPrimaryKey(id);
            model.addAttribute("isOperatorEdit", true);
        } else {
            user.setSex(User.SEX_MAN);
            model.addAttribute("isOperatorEdit", false);
        }

        model.addAttribute("user", user);

        return "user/user_form";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public BackResult<String> save(@Validated User user) {

        BackResult<String> backResult = new BackResult<>();
        backResult.setCode(ResponseCodeEnum.BACK_CODE_FAIL.value);

        String id = user.getId();

        if (StringUtils.isNotBlank(id)) { //编辑

            user.setUpdateBy(UserUtils.getCurrUserId());
            user.setUpdateDate(new Date());
            userService.updateByPrimaryKey(user);

        } else { //新增
            user.setPassword(UserUtils.getUserDefaultPassword(user.getUsername()));
            user.setCreateBy(UserUtils.getCurrUserId());
            user.preInsert();
            userService.insert(user);
        }

        backResult.setCode(ResponseCodeEnum.BACK_CODE_SUCCESS.value);

        return backResult;

    }

    @RequestMapping(value = "/del/{id}", method = RequestMethod.GET)
    @ResponseBody
    public BackResult<String> del(@PathVariable("id") String id) {

        BackResult<String> backResult = new BackResult<>();
        backResult.setCode(ResponseCodeEnum.BACK_CODE_FAIL.value);

        User user = new User();
        user.setId(id);
        user.setDelFlag(User.DEL_FLAG_YES);
        userService.updateByPrimaryKeySelective(user);
        backResult.setCode(ResponseCodeEnum.BACK_CODE_SUCCESS.value);

        return backResult;

    }

    @RequestMapping(value = "/usernameOnlyCheck", method = RequestMethod.GET)
    @ResponseBody
    public boolean usernameOnlyCheck(@RequestParam("username") String username, @RequestParam("oldUsername") String oldUsername) {


        if (StringUtils.isNotBlank(username)) {

            if (StringUtils.isNotBlank(oldUsername) && username.equals(oldUsername)) {
                return true;
            }

            User user = new User();
            user.setUsername(username);
            user.setDelFlag(User.DEL_FLAG_NO);

            List<User> users = userService.selectBySelective(user);

            if (CollectionUtils.isNotEmpty(users)) {
                return false;
            }

        }

        return true;

    }

    @RequestMapping(value = "/noOnlyCheck", method = RequestMethod.GET)
    @ResponseBody
    public boolean noOnlyCheck(@RequestParam("no") String no, @RequestParam("oldNo") String oldNo) {

        if (StringUtils.isNotBlank(no)) {

            if (StringUtils.isNotBlank(oldNo) && no.equals(oldNo)) {
                return true;
            }

            User user = new User();
            user.setNo(no);
            user.setDelFlag(User.DEL_FLAG_NO);

            List<User> users = userService.selectBySelective(user);

            if (CollectionUtils.isNotEmpty(users)) {
                return false;
            }
        }

        return true;

    }


    /**
     * 根据用户ID获取拥有的角色Id列表
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "/roleIds", method = RequestMethod.GET)
    @ResponseBody
    public List<String> getRoleIds(@RequestParam("userId") String userId) {

        List<String> roleIdList = new ArrayList<>();


        List<Role> resourceList = roleService.selectByUserId(userId);

        if (CollectionUtils.isNotEmpty(resourceList)) {
            resourceList.forEach(item -> {
                roleIdList.add(item.getId());
            });
        }

        return roleIdList;

    }


    /**
     * 保存分配角色信息
     *
     * @param userId     用戶ID
     * @param roleIdList 角色ID列表
     * @return
     */

    @RequestMapping(value = "/saveAssignRoles", method = RequestMethod.POST)
    @ResponseBody
    public BackResult<String> saveAssignRoles(@RequestParam("userId") String userId, @RequestParam(value = "roleIdList[]", required = false) String[] roleIdList) {

        BackResult<String> backResult = new BackResult<>();
        backResult.setCode(ResponseCodeEnum.BACK_CODE_FAIL.value);

        roleService.saveAssignRoles(userId, roleIdList);

        backResult.setCode(ResponseCodeEnum.BACK_CODE_SUCCESS.value);

        return backResult;

    }

}