jbootstrap.userAdd = (function (jbootstrap, window, $) {

    var readyFunc = function () {

        initPageOperate();

        initFormValidate();

    }
    /**
     * 初始化页面操作
     */
    function initPageOperate() {

        //保存按钮
        $(".btn-save").on("click", function () {

            if(  !$("#editForm").valid()){
                return;
            }

            $.ajax({
                type: "POST",
                url: baseContextPath + "roles/save",
                data:$("#editForm").serialize(),
                async: false,
                success: function(data) {
                    if (data.code === "6000") {

                        swal({
                            title: "保存成功！",
                            type: "success",
                            timer: 2000,
                            showConfirmButton: false
                        });


                        $.pjax.reload('#pjax-container', {
                            url: baseContextPath + "roles/list",
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
                error: function(request) {
                    showAjaxErrorMsg();
                }
            });

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
                        url: baseContextPath + "roles/nameOnlyCheck",
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
                    },
                },
                description: {
                    remote: {
                        url: baseContextPath + "roles/descriptionOnlyCheck",
                        type: "get",
                        dataType: 'json',
                        data: {
                            'description': function () {
                                return $('input[name="description"]').val();
                            },
                            'oldDescription': function () {
                                return $('#descriptionOldValue').val();
                            }
                        }
                    }
                }
            },
            messages: {
                name: {
                    remote: '角色名称已经存在'
                },
                description: {
                    remote: '中文名称已经存在'
                },
            }
        });
    }


    return {
        ready: readyFunc,
    };
})(jbootstrap, window, jQuery);

jbootstrap.userAdd.ready();