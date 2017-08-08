$(function () {

    //Pjax页面跳转
    $(document).pjax('a[data-pjax]', '#pjax-container', {fragment: '#pjax-container'})

    //Pjax页面跳转进度条
    $(document).on('pjax:start', function () {
       debugger
        NProgress.start();
    });
    $(document).on('pjax:end', function (event) {
        $(window).resize();
        NProgress.done();
    });

})