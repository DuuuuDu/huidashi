

$(function () {

    $('[data-toggle="tooltip"]').tooltip();

    /*用户需求标签页切换动效*/
    $(".topost-cellphone").click(function(){
        $("input[name='nickName']").val() == ""?{}:$("span.nickName").text($("input[name='nickName']").val());
        $("input[name='city']").val() == ""?{}:$("span.city").text($("input[name='city']").val());
        $("input[name='activityType']").val() == ""?{}:$("span.activityType").text($("input[name='activityType']").val());
        $("input[name='activityBudget']").val() == ""?{}:$("span.activityBudget").text($("input[name='activityBudget']").val());
        $("input[name='beginDate']").val() == ""?{}:$("span.beginDate").text($("input[name='beginDate']").val());
        $(".post-form").fadeOut(250);
        $(".post-cellphone").fadeIn(250);
    });
    $(".topost-form").click(function(){
        $(".post-form").fadeIn(250);
        $(".post-cellphone").fadeOut(250);
    });

    /*需求提交表单下拉菜单点击数据绑定*/
    $(".dropdown-menu li").click(function () {
        $(this).parents(".input-group-btn").next().val(this.innerText);
    });

    /*提交前的基本校验*/
    // $(".postApply").click(function () {
    //     alert(1);
    // });

    /*时间选择器*/
    // $('*[name=beginDate]').appendDtpicker();
    $('#datepicker').datepicker( "option", $.datepicker.regional["zh-CN"] );
    $('#datepicker').datepicker({
        showButtonPanel: true,
        minDate: 0,
        inline: false,
        changeMonth: true,
        changeYear: true,
        showAnim: "fadeIn"
    });



    /*用户需求提交，内容同步到存储手机号码标签页*/

});

