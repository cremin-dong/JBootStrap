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
                url: baseContextPath + "users/save",
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
                            url: baseContextPath + "users/list",
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
                username: {
                    remote: {
                        url: baseContextPath + "users/usernameOnlyCheck",
                        type: "get",
                        dataType: 'json',
                        data: {
                            'username': function () {
                                return $('input[name="username"]').val();
                            },
                            'oldUsername':function () {
                                return $('#usernameOldValue').val();
                            }
                        }
                    }
                },
                no: {
                    remote: {
                        url: baseContextPath + "users/noOnlyCheck",
                        type: "get",
                        dataType: 'json',
                        data: {
                            'no': function () {
                                return $('input[name="no"]').val();
                            },'oldNo': function () {
                                return $('#noOldValue').val();
                            }
                        }
                    }
                }
            },
            messages: {
                username: {
                    remote: '用户名已存在'
                },
                no: {
                    remote: '工号已存在'
                },
            }
        });
    }


    return {
        ready: readyFunc,
    };
})(jbootstrap, window, jQuery);

jbootstrap.userAdd.ready();