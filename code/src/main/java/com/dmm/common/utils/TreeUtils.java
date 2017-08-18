package com.dmm.common.utils;

import com.dmm.common.core.TreeDomain;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by cremin on 2017/8/15.
 */
public class TreeUtils<T extends TreeDomain> {

    private List<T> sourtList = new ArrayList<T>();//排序后的list
    private List<T> originalList; //原始list
    private Iterator<T> iterator;

    public TreeUtils(List<T> originalList) {
        this.originalList = originalList;
    }

    /**
     * 构建treeTable树形结构list
     * @return 返回树形结构List
     */
    public List<T> buildTreeTableList() {


        iterator = originalList.iterator();

        while(iterator.hasNext()){

            T node = iterator.next();

            //获取一级节点
            if(StringUtils.isBlank(node.getParentId())){

                sourtList.add(node);

                //移除已经添加到新列表中的数据
                iterator.remove();

                //递归获取子节点
                build(node);
            }
        }

        return sourtList;
    }


    /**
     * 递归获取子节点
     * @param node 当前节点
     */
    private void build(T node) {

        List<T> children = getChildren(node);

        //如果存在子节点
        if (CollectionUtils.isNotEmpty(children)) {

            //将子节点遍历加入返回值中
            for (T child : children) {

                sourtList.add(child);

                //递归获取子节点
                build(child);
            }
        }

        //元素移除后重新迭代一次
        iterator = originalList.iterator();

    }


    /**
     * 获取直接子节点
     * @param node
     * @return 返回
     */
    private List<T> getChildren(T node) {

        List<T> childrenList = new ArrayList<T>();

        iterator = originalList.iterator();

        while(iterator.hasNext()){

            T child = iterator.next();

            //获取一级节点
            if(node.getId().equals(child.getParentId())){

                //将该节点加入循环列表中
                childrenList.add(child);

                //移除已经添加到新列表中的数据
                iterator.remove();

            }
        }

        return childrenList;
    }
}
