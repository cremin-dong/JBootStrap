var jbootstrap = {};


$(function () {

    jbootstrap.ready();

});


/**
 * 初始化函数
 */
jbootstrap.ready = function () {

    jbootstrap.pjaxSetting();


}

/**
 * pjax设定
 */
jbootstrap.pjaxSetting = function () {

    //Pjax页面跳转
    $(document).pjax('a[data-pjax]', '#pjax-container', {fragment: '#pjax-container'})

    //Pjax页面跳转进度条
    $(document).on('pjax:start', function () {
        NProgress.start();
    });
    $(document).on('pjax:end', function (event) {
        $(window).resize();
        NProgress.done();
    });
}