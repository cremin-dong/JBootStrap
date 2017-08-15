package com.dmm.service;

import com.dmm.JbootstrapApplication;
import com.dmm.module.domain.Resource;
import com.dmm.module.service.ResourceService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by cremin on 2017/8/2.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = JbootstrapApplication.class)
public class ResourceServiceTest {


    @Autowired
    private ResourceService resourceService;

    @Test
    @Transactional
    public void testAdd(){


        String parentId = "a91fc95f-7be8-4483-b504-2706ef8d9eec";

        Resource resource = new Resource();
        resource.setHref("");
        resource.setIcon("fa fa-user");
        resource.setName("用户删除");

        Resource parentResource = resourceService.selectByPrimaryKey(parentId);
        if(parentResource != null){

            //设定父节点信息
            resource.setParentId(parentId);
            resource.setParentIds(parentResource.getParentIds() == null? parentId
                    :String.join(",",parentResource.getParentIds(),parentId));
        }

        resource.setPermission("sys:users:delete");
        resource.setSort(0L);
        resource.setType(Resource.TYPE_MENU);
        resource.setCreateBy("YYYYYYYYYY");
        resource.preInsert();

        int insert = resourceService.insert(resource);
        Assert.assertEquals(1,insert);
    }

    /**
     * 根据用户ID获取用户拥有的权限
     */
    @Test
    public void testSelectByUserId(){

        String userId = "8f0fb42d-8bf7-4844-afda-c15c8a78b605";
        List<Resource> resources = resourceService.selectByUserId(userId);

        Assert.assertNotNull(resources);
    }
}
