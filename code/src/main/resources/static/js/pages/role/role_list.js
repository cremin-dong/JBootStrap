jbootstrap.roleList = (function (jbootstrap, window, $) {


    var table;

    var readyFunc = function () {

        initTableData();

        initPageOperate();

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
                },{
                    "data": null,
                    "render": function (data, type, row, meta) {

                        return row.isSys == "1"?"是":"否";
                    }
                }, {
                    "data": "updateDate"
                }, {
                    "data": null,
                    "render": function (data, type, row, meta) {

                        var source = $("#tpl").html();
                        var template = Handlebars.compile(source);

                        var context = {
                            id: row.id,
                            show: row.isSys == "1" ? false :true,
                            editUrl: baseContextPath + "roles/form?id=" + row.id
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
     * 删除
     * @param id
     */
    var delRowFunc = function (id) {


        swal({
            title: "您确定要删除该条数据吗？",
            type: "warning",
            showCancelButton: true,
            confirmButtonText: "确定",
            cancelButtonText:"取消",
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

jbootstrap.roleList.ready();