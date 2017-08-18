jbootstrap.resourceAdd = (function (jbootstrap, window, $) {

    var zNodes = [];


    var readyFunc = function () {

        initPageOperate();

        initFormValidate();

        initParentTree();

    }

    /**
     * 初始化页面操作
     */
    function initPageOperate() {

        //保存按钮
        $(".btn-save").on("click", function () {

            if (!$("#editForm").valid()) {
                return;
            }

            $.ajax({
                type: "POST",
                url: baseContextPath + "resources/save",
                data: $("#editForm").serialize(),
                async: false,
                success: function (data) {
                    if (data.code === "6000") {

                        swal({
                            title: "保存成功！",
                            type: "success",
                            timer: 2000,
                            showConfirmButton: false
                        });

                        $.pjax.reload('#pjax-container', {
                            url: baseContextPath + "resources/list",
                            fragment: '#pjax-container',
                            timeout: 5000
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
                error: function (request) {
                    showAjaxErrorMsg();
                }
            });

        });


        //类型选择，类型为操作的时候隐藏图标和Href输入框
        $(":radio[name=type]").click(function () {

            if($(this).val() === "1"){ //操作
                $(".type-operator-hide").addClass("hide").find(":input").val("");
            }else{ //菜单
                $(".type-operator-hide").removeClass("hide");
            }
        });

    }

    /**
     * 初始验证信息
     */
    function initFormValidate() {

        $("#editForm").validate({
            rules: {
                name: {
                    remote: {
                        url: baseContextPath + "resources/nameOnlyCheck",
                        type: "get",
                        dataType: 'json',
                        data: {
                            'name': function () {
                                return $('input[name="name"]').val();
                            },
                            'oldName': function () {
                                return $('#nameOldValue').val();
                            }
                        }
                    }
                },
                permission: {
                    remote: {
                        url: baseContextPath + "resources/permissionOnlyCheck",
                        type: "get",
                        dataType: 'json',
                        data: {
                            'permission': function () {
                                return $('input[name="permission"]').val();
                            },
                            'oldPermission': function () {
                                return $('#permissionOldValue').val();
                            }
                        }
                    }
                }
            },
            messages: {
                name: {
                    remote: '资源名称已存在'
                },
                permission: {
                    remote: '权限已存在'
                },
            }
        });
    }


    /**
     * 初始上级菜单树
     */
    function initParentTree() {

        var setting = {
            view: {
                dblClickExpand: false
            },
            data: {
                simpleData: {
                    enable: true,
                    pIdKey: "parentId"
                }
            },
            callback: {
                onClick: onClick
            }
        };

        $.ajax({
            async: false,
            url: baseContextPath + "resources/ztreeList?type=0",
            dataType: 'json',
            success: function (data) {
                zNodes = data;
            }
        });

        $.fn.zTree.init($("#parentIdTreeWrap"), setting, zNodes);

        //设置选中节点
        if($("#parentIdVal").val() != ""){

            var treeObj = $.fn.zTree.getZTreeObj("parentIdTreeWrap");

            var treeNode = treeObj.getNodeByParam("id", $("#parentIdVal").val(), null);
            treeObj.selectNode(treeNode);

            $("#parentIdTree").val(treeNode.name);
        }


    }


    /**
     * 设定选中值
     * @param e
     * @param treeId
     * @param treeNode
     */
    function onClick(e, treeId, treeNode) {

        $("#parentIdTree").val(treeNode.name);
        $("#parentIdVal").val(treeNode.id);

    }


    /**
     * 显示上级菜单树
     */
    var showMenuFunc = function () {

        var parentIdTree = $("#parentIdTree");
        var parentIdTreeOffset = $("#parentIdTree").offset();

        $("#menuContent").css({
            left: parentIdTreeOffset.left + "px",
            top: parentIdTreeOffset.top + parentIdTree.outerHeight() + "px",
            width:parentIdTree.outerWidth()
        }).slideDown("fast");

        $("body").bind("mousedown", onBodyDown);

    }

    /**
     * 隐藏上级菜单树
     */
    function hideMenu() {
        $("#menuContent").fadeOut("fast");
        $("body").unbind("mousedown", onBodyDown);
    }

    /**
     * 点击菜单以外的地方隐藏上级菜单树
     * @param event
     */
    function onBodyDown(event) {
        if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length > 0)) {
            hideMenu();
        }
    }

    return {
        ready: readyFunc,
        showMenu: showMenuFunc,
    };
})(jbootstrap, window, jQuery);

jbootstrap.resourceAdd.ready();