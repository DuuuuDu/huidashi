/**
 * Created by alvin on 2017/8/11.
 */
// function markReaded(id,event) {
//     $this = this;
//     $.post("markReaded", {"id": id},
//         function (data) {
//
//             if(data){$($this).fadeOut();}
//
//         });
// }


$(".addSite").on("click", function (event) {
    $(".table-responsive").prepend("<div class='list-group'id='createAdDiv'style='display: none;'><div class='panel panel-default'><div class='panel-heading'><span>保存新的广告信息</span><button class='btn btn-default navbar-btn cancelAd'style='float: right;margin: 0 0 0 5px;padding: 0px 15px;'type='button'>取消</button><button class='btn btn-default navbar-btn createAd'style='float: right;margin: 0 0 0 5px;padding: 0px 15px;'type='button'>保存</button></div><div class='media'style='margin: 5px'><div class='media-left'style='padding-right: 5px'><a href='javascript:void(0);'><img class='media-object'style='width: 533px; height: 100px;'src=''></a></div><div class='media-body'><form action='update'enctype='multipart/form-data'method='post'id='createAd'><input name='id'type='hidden'value=''><div class='input-group input-group-sm'><span class='input-group-addon'>预览图</span><input class='form-control'name='adImageUrl'type='file'></div><div class='input-group input-group-sm'><span class='input-group-addon'>广告名称</span><input class='form-control'name='adName'value=''></div><div class='input-group input-group-sm'><span class='input-group-addon'>链接URL</span><input class='form-control'name='linkUrl'value=''></div><div class='input-group input-group-sm'><span class='input-group-addon'>是否生效</span><input class='form-control'name='valid'type='hidden'value='false'><div aria-label='...'class='btn-group btn-group-sm'role='group'><button disabled=''type='button'class='btn btn-default y valid'>是</button><button disabled=''type='button'class='btn btn-default valid active'>否</button></div></div></form></div></div></div></div>");
    $("#createAdDiv").slideDown();
});

$(".table-responsive").on("click", ".cancelAd", function (event) {
    $("#createAdDiv").slideUp(function () {
        $("#createAdDiv").remove();
    });
});

$(".table-responsive").on("click", ".createAd", function (event) {
    $("#createAd").submit();
});


/**
 * 取消编辑
 */
$(".cancelAd").on("click", function (event) {
    var $this = this;
    $($this).hide();
    $($this).siblings(".updAd").hide();
    $($this).siblings(".editAd").show();
    $($this).parent().next().find(".form-control").attr("disabled", true);
    $($this).parent().next().find(".valid").attr("disabled", true);
});

$(".editAd").on("click", function (event) {
    var $this = this;
    $($this).hide();
    $($this).siblings(".updAd").show();
    $($this).siblings(".cancelAd").show();
    $($this).parent().next().find(".form-control").attr("disabled", false);
    $($this).parent().next().find(".valid").attr("disabled", false);

});

$(".valid").on("click", function (event) {
    var $this = this;
    $($this).addClass("active");
    $($this).siblings().removeClass("active");

    if($($this).hasClass("y")){
        $($this).parents().siblings(".form-control").val(true);
    }else{
        $($this).parents().siblings(".form-control").val(false);
    }
});

$(".updAd").on("click", function (event) {
    var $this = this;
    $('#updForm' + this.id).submit();
});