package com.dmm.common.utils;

import com.dmm.common.core.DataTablesPager;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by cremin on 2017/8/10.
 */
public class PageUtils {

    /**
     * 默认分页偏移量
     **/
    public static int DEFUALT_OFFSET = 0;

    /**
     * 默认分页每页数量
     **/
    public static int DEFUALT_LIMIT = 10;

    /**
     * 默认DRAW
     **/
    public static int DEFUALT_DRAW = 1;


    /**
     * 获取分页参数,组装给PageHelper使用
     *
     * @param request
     * @return
     */
    public static Page<Object> getPagerParams(HttpServletRequest request) {

        String start = request.getParameter("start");
        String length = request.getParameter("length");

        int offset = DEFUALT_OFFSET;
        int limit = DEFUALT_LIMIT;

        if (StringUtils.isNotBlank(start)) {
            offset = Integer.parseInt(start);
        }

        if (StringUtils.isNotBlank(length)) {
            limit = Integer.parseInt(length);
        }

        return PageHelper.offsetPage(offset, limit);
    }


    /**
     * 将PageHelper的数据转换为DataTables所需数据
     *
     * @param page
     * @param request
     * @return
     */
    public static DataTablesPager pageHelperToDataTablesPager(Page page, HttpServletRequest request) {

        String draw = request.getParameter("draw");

        DataTablesPager dataTablesPager = new DataTablesPager();

        dataTablesPager.setData(page.getResult());
        dataTablesPager.setRecordsTotal(page.getTotal());
        dataTablesPager.setRecordsFiltered(page.getTotal());
        dataTablesPager.setDraw(StringUtils.isNotBlank(draw) ? Integer.parseInt(draw) : DEFUALT_DRAW);

        return dataTablesPager;
    }
}
