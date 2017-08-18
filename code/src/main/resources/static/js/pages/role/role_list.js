jbootstrap.roleList = (function (jbootstrap, window, $) {


    var table;
    var zNodes = [];

    var readyFunc = function () {

        initTableData();

        initPageOperate();

        initParentTree();

        initControlStyle();
    };


    /**
     * 初始化table数据
     */
    function initTableData() {

        table = $('#tab-rolelist').DataTable({
            ajax: {
                //指定数据源
                url: baseContextPath + "roles/pagerData",
                dataSrc: "data",
                data: function (d) {
                    var param = {};
                    param.draw = d.draw;
                    param.start = d.start;
                    param.length = d.length;

                    var formData = $("#queryForm").serializeArray();//把form里面的数据序列化成数组
                    formData.forEach(function (e) {
                        param[e.name] = e.value;
                    });
                    return param;//自定义需要传递的参数。
                },
            },
            fnDrawCallback: function () {
                var api = this.api();
                var startIndex = api.context[0]._iDisplayStart;//获取到本页开始的条数
                api.column(1).nodes().each(function (cell, i) {
                    cell.innerHTML = startIndex + i + 1;
                });
            },
            columns: [
                {
                    "data": "id"
                }, {
                    "data": null //此列不绑定数据源，用来显示序号
                }, {
                    "data": "name"
                }, {
                    "data": "description"
                }, {
                    "data": null,
                    "render": function (data, type, row, meta) {

                        return row.isSys == "1" ? "是" : "否";
                    }
                }, {
                    "data": "updateDate"
                }, {
                    "data": null,
                    "render": function (data, type, row, meta) {

                        var source = $("#tpl").html();
                        var template = Handlebars.compile(source);

                        var context = {
                            show: row.isSys == "1" ? false : true,
                            editUrl: baseContextPath + "roles/form?id=" + row.id,
                            obj: row
                        };
                        var html = template(context);

                        return html;
                    }
                }],
            columnDefs: [
                {
                    "visible": false,
                    "targets": 0
                }]

        });


    }

    /**
     * 初始化页面操作
     */
    function initPageOperate() {

        //查询按钮
        $("#btn-query").on("click", function () {
            table.draw();
        });

        //刷新
        $("#btn-re").on("click", function () {
            table.draw(false);//刷新保持分页状态
        });
    }

    /**
     * 初始化页面样式
     */
    function initControlStyle() {
        $("#authorizationModal .modal-body").css({
            "overflow-y": "auto",
            "min-height": "400px",
            "border-bottom": "1px solid #ddd",
            "border-top": "1px solid #ddd",
            "max-height": 600
        });
    }

    /**
     * 删除
     * @param id
     */
    var delRowFunc = function (id) {


        swal({
            title: "您确定要删除该条数据吗？",
            type: "warning",
            showCancelButton: true,
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            closeOnConfirm: false
        }, function () {

            $.ajax({
                url: baseContextPath + 'roles/del/' + id,
                type: 'get',
                success: function (msg) {
                    if (msg.code === "6000") {

                        swal({
                            title: "删除成功！",
                            type: "success",
                            timer: 1000,
                            showConfirmButton: false
                        });

                        table.draw();

                    } else {
                        swal({
                            title: "删除失败！",
                            type: "error",
                            timer: 2000,
                            showConfirmButton: false
                        });
                    }
                },
                error: function () {
                    showAjaxErrorMsg();
                }
            });
        });


    }


    /**
     * 弹出授权模态框
     * @param id
     * @param description
     */
    var authorizeModalFunc = function (id, description) {

        //设置当前修改角色的ID
        $("#currRoleId").val(id);

        //设置标题
        $("#authorizationModal .modal-title").html("授权：" + description);

        //获取当前角色拥有的资源
        var resourceIds = [];
        $.ajax({
            async: false,
            url: baseContextPath + "roles/resourceIds",
            dataType: 'json',
            data: {
                roleId: id
            },
            success: function (data) {

                resourceIds = data;
            }
        });

        var treeObj = $.fn.zTree.getZTreeObj("parentIdTreeWrap");

        //设置全不选中并且展开
        treeObj.checkAllNodes(false);
        treeObj.expandAll(true);

        //获取树的所有节点
        var node = treeObj.getNodes();
        var nodes = treeObj.transformToArray(node);

        //设置选中项
        if (resourceIds != null && resourceIds.length > 0) {

            $.each(nodes, function (index, treeNode) {

                //当前节点ID包含在resourceIds中，则设置选中
                if ($.inArray(treeNode.id, resourceIds) > -1) {

                    treeObj.checkNode(treeNode, true);
                }
            })

        }

        $('#authorizationModal').modal('toggle');
    }


    /**
     * 初始资源树
     */
    function initParentTree() {

        //获取所有资源
        $.ajax({
            async: false,
            url: baseContextPath + "resources/ztreeList",
            dataType: 'json',
            success: function (data) {
                zNodes = data;
            }
        });


        var setting = {
            view: {
                dblClickExpand: false
            },
            check: {
                enable: true,
                chkboxType: { "Y" : "s", "N" : "s" }
            },
            data: {
                simpleData: {
                    enable: true,
                    pIdKey: "parentId"
                }
            }
        };

        $.fn.zTree.init($("#parentIdTreeWrap"), setting, zNodes);


        //展开树节点
        var treeObj = $.fn.zTree.getZTreeObj("parentIdTreeWrap");
        treeObj.expandAll(true);
    }


    /**
     * 保存授权信息
     */
    var authorizeSaveFunc = function () {

        var roleId = $("#currRoleId").val();

        var treeObj = $.fn.zTree.getZTreeObj("parentIdTreeWrap");
        var nodes = treeObj.getCheckedNodes(true);
        var resourceIdList= []; //选中的资源ID列表

        $.each(nodes,function (index,node) {
            resourceIdList.push(node.id);
        })
        

        $.ajax({
            type: "POST",
            url: baseContextPath + "roles/saveAuthorize",
            data:{
                roleId:roleId,
                resourceIdList:resourceIdList
            },
            async: false,
            success: function(data) {

                if (data.code === "6000") {

                    swal({
                        title: "保存成功！",
                        type: "success",
                        timer: 2000,
                        showConfirmButton: false
                    });

                } else {
                    swal({
                        title: "保存失败！",
                        type: "error",
                        timer: 2000,
                        showConfirmButton: false
                    });
                }
            },
            error: function(request) {
                showAjaxErrorMsg();
            }
        });
    }


    return {
        ready: readyFunc,
        delRow: delRowFunc,
        authorizeModal: authorizeModalFunc,
        authorizeSave: authorizeSaveFunc
    };
})(jbootstrap, window, jQuery);

jbootstrap.roleList.ready();