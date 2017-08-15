var jbootstrap = {};


$(function () {

    jbootstrap.ready();

});


/**
 * dataTables默认值设定
 */
$.extend(true, $.fn.dataTable.defaults, {
    serverSide: true,//开启服务器模式,分页，取数据等等的都放到服务端去
    processing: true,//载入数据的时候是否显示"载入中"
    pageLength: 10,  //首次加载的数据条数
    ordering: false, //排序操作在服务端进行，所以可以关了。
    pagingType: "full_numbers",
    autoWidth: false,
    stateSave: true,//保持翻页状态，和comTable.fnDraw(false);结合使用
    searching: false,//禁用datatables搜索
    dom: 'rti<"paginationWrap" pl><"clearfix" >',
    language: {
        processing: "加载中...",
        search: "",
        lengthMenu: "每页 _MENU_ 项",
        info: "第 _START_ ~ _END_ 条，共 _TOTAL_ 条",
        infoEmpty: "没有显示条目",
        infoFiltered: "(由 _MAX_ 项结果过滤)",
        infoPostFix: "",
        loadingRecords: "数据读取中...",
        zeroRecords: "无符合数据",
        emptyTable: "没有数据",
        paginate: {
            first: "首页",
            previous: "前一页",
            next: "后一页",
            last: "最后"
        },
        aria: {
            sortAscending: ": 升序排列",
            sortDescending: ": 降序排列"
        }
    },
})


// 手机号码验证
jQuery.validator.addMethod("isMobile", function(value, element) {
    var length = value.length;
    var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;
    return this.optional(element) || (length == 11 && mobile.test(value));
}, "请正确填写您的手机号码");

// 字符验证
jQuery.validator.addMethod("stringCheck", function(value, element) {
    return this.optional(element) || /^[u0391-uFFE5w]+$/.test(value);
}, "只能包括中文字、英文字母、数字和下划线");

// 电话号码验证
jQuery.validator.addMethod("isTel", function(value, element) {
    var tel = /^d{3,4}-?d{7,9}$/;    //电话号码格式010-12345678
    return this.optional(element) || (tel.test(value));
}, "请正确填写您的电话号码");

// 联系电话(手机/电话皆可)验证
jQuery.validator.addMethod("isPhone", function(value,element) {
    var length = value.length;
    var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;
    var tel = /^d{3,4}-?d{7,9}$/;
    return this.optional(element) || (tel.test(value) || mobile.test(value));
}, "请正确填写您的联系电话");

//身份证号码验证
jQuery.validator.addMethod("idcardno", function(value, element){
    return this.optional(element) || isIdCardNo(value);
}, "请正确输入身份证号码");

//字母数字
jQuery.validator.addMethod("alnum", function(value, element){
    return this.optional(element) ||/^[a-zA-Z0-9]+$/.test(value);
}, "只能包括英文字母和数字");

//字母
jQuery.validator.addMethod("letter", function(value, element){
    return this.optional(element) ||/^[a-zA-Z]+$/.test(value);
}, "只能输入英文字母");


// 邮政编码验证
jQuery.validator.addMethod("zipcode", function(value, element){
    var tel = /^[0-9]{6}$/;
    return this.optional(element) || (tel.test(value));
}, "请正确填写邮政编码");

// 汉字
jQuery.validator.addMethod("chcharacter", function(value, element){
    var tel = /^[\u4E00-\u9FA5]+$/;
    return this.optional(element) || (tel.test(value));
}, "请输入汉字");

/**
 * 验证错误默认设定
 */
$.validator.setDefaults({
    errorClass: "error",
    success: 'valid',
    unhighlight: function (element, errorClass, validClass) { //验证通过
        $(element).popover('destroy');
        $(element).closest(".form-group").removeClass("has-error");
    },
    errorPlacement: function (error, element) {

        $(element).attr("data-placement","bottom").attr("data-content", $(error).text()).popover("show");
        $(element).closest(".form-group").addClass("has-error");
    },
});


/**
 * 初始化函数
 */
jbootstrap.ready = function () {

    jbootstrap.pjaxSetting();

    jbootstrap.menuActiveSetting();

}


/**
 * pjax设定
 */
jbootstrap.pjaxSetting = function () {

    //Pjax页面跳转
    $(document).pjax('a[data-pjax]', '#pjax-container', {fragment: '#pjax-container', timeout: 5000})

    //Pjax页面跳转进度条
    $(document).on('pjax:start', function () {
        NProgress.start();
    });
    $(document).on('pjax:end', function (event) {
        $(window).resize();
        NProgress.done();
        jbootstrap.menuActiveSetting();
    });
}


/**
 * 设定菜单当前选中项目
 */
jbootstrap.menuActiveSetting = function () {

    $('.sidebar-menu li a').each(function () {

        var href = $(this)[0].href.replace(window.location.origin,"");

        var url =  window.location.pathname;

        if(href == "/") {

        }else if ( href.split("/")[1] == url.split("/")[1]) {
            $(".sidebar-menu li").removeClass("active");
            $(this).parents("li").addClass('active');
        }
    });

}

/**
 * ajax erro显示信息
 */
function showAjaxErrorMsg() {
    swal({
        title: "服务器或网络异常！",
        type: "error",
        timer: 2000,
        showConfirmButton: false
    });
}


