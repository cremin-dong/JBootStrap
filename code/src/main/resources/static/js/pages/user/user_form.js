jbootstrap.userAdd = (function (jbootstrap, window, $) {

    var editFlag = false;

    var readyFunc = function () {

        if($("#editForm #id").val() != ""){
            editFlag = true;
        }

        initPageOperate();

       if(editFlag){
           $("#username,#no").attr("readonly","readonly");
       }else{
           initFormValidate();
       }

    }
    /**
     * 初始化页面操作
     */
    function initPageOperate() {

        //查询按钮
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


                        $.pjax({
                            url: baseContextPath + "users/list",
                            container: '#pjax-container'
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