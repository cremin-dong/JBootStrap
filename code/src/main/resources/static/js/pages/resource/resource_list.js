jbootstrap.resourceList = (function (jbootstrap, window, $) {


    var readyFunc = function () {

        $("#tab-resourcelist").treetable({ expandable: true});

        //展开二级菜单
        $("#tab-resourcelist tbody tr:visible span.indenter a[title=Expand]").click();

        initPageOperate();

    };



    /**
     * 初始化页面操作
     */
    function initPageOperate() {

        //刷新
        $("#btn-re").on("click", function () {
            $.pjax.reload('#pjax-container', {
                url: baseContextPath + "resources/list",
                fragment: '#pjax-container',
                timeout: 5000
            });
        });
    }



    /**
     * 删除
     * @param id
     */
    var delRowFunc = function (id) {


        swal({
            title: "您确定要删除该菜单及所有子菜单项吗？",
            type: "warning",
            showCancelButton: true,
            confirmButtonText: "确定",
            cancelButtonText:"取消",
            closeOnConfirm: false
        }, function () {

            $.ajax({
                url: baseContextPath + 'resources/del/' + id,
                type: 'get',
                success: function (msg) {
                    if (msg.code === "6000") {

                        swal({
                            title: "删除成功！",
                            type: "success",
                            timer: 1000,
                            showConfirmButton: false
                        });

                        $("#btn-re").trigger("click");

                    } else {
                        swal({
                            title: "删除失败！",
                            type: "error",
                            timer: 2000,
                            showConfirmButton: false
                        });
                    }
                },
                error:function () {
                    showAjaxErrorMsg();
                }
            });
        });


    }

    return {
        ready: readyFunc,
        delRow: delRowFunc,
    };
})(jbootstrap, window, jQuery);

jbootstrap.resourceList.ready();