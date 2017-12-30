/**
 * Created by alvin on 2017/8/11.
 */

$(function() {
    //时间控件
    $('.datepicker').datepicker( "option", $.datepicker.regional["zh-CN"] );
    $('.datepicker').datepicker({
        showButtonPanel: true,
        inline: false,
        changeMonth: true,
        changeYear: true,
        yearRange: "-20:+20",
        showAnim: "fadeIn"
    });
});

/**
 * 每个房间名和标签页的值相对应，方便用户查找
 */
$("#roomTabContent").on("keyup","input[name='roomName']",function () {
    var parentDivId = this.parentNode.parentNode.id;
    if($(this).val() == ""){
        $("#roomTab").find("a[href='#"+ parentDivId+"']").text("房间");
    }
    else{
        $("#roomTab").find("a[href='#"+ parentDivId+"']").text($(this).val());
    }
});

/**
 * 添加新房间
 */
$('#addRoom').click(function(){
    //内容构造，单选按钮的标签name属性添加下标，防止无法选中的情况
    var contentHtml = $('#roomTemplate');
    var index = $('#roomTabContent').find('div.tab-pane').length - 1;//新增标签的下标

    contentHtml.find("input.hasThroughLiftToMeeting").attr("name","hasThroughLiftToMeeting_"+index);
    contentHtml.find("input.hasFreeLounge").attr("name","hasFreeLounge_"+index);
    contentHtml.find("input.hasLobby").attr("name","hasLobby_"+index);
    contentHtml.find("input.hasWindow").attr("name","hasWindow_"+index);
    contentHtml.find("input.hasSquareArea").attr("name","hasSquareArea_"+index);
    contentHtml.find("input.hasPillar").attr("name","hasPillar_"+index);
    contentHtml.find("input.hasCarpet").attr("name","hasCarpet_"+index);
    contentHtml.find("input.hasStage").attr("name","hasStage_"+index);
    contentHtml.find("input.hasProjection").attr("name","hasProjection_"+index);
    contentHtml.find("input.hasLed").attr("name","hasLed_"+index);
    contentHtml.find("input.ledSize").attr("name","ledSize_"+index);

    //内容填充
    $('#roomTabContent').append(contentHtml.html());
    $('#roomTab').append($('#roomTabTemplate').html());

    var id = 'room' + $('#roomTabContent').children('div').length;
    var hrefVal = '#' + id;

    //事件绑定
    $('#roomTab a.roomname').bind('click',bindTabContent);
    $('#roomTab a.deleteRoom').bind('click',bindDeleteTabContent);

    //tab和tabContent的联动绑定
    $('#roomTabContent').children('div:last').attr('id',id);
    $('#roomTab').children('li:last').addClass(id);
    $('#roomTab').children('li:last').find('a.roomname').attr('href',hrefVal).click();
    $('#roomTab').children('li:last').find('a.deleteRoom').attr('href',id);


});
function bindTabContent(e) {
    e.preventDefault();
    $(this).tab('show');
}
function bindDeleteTabContent(e) {
    e.preventDefault();
    //移除当前所选标签机器内容
    var id = $(this).attr('href');
    $('li.' + id).remove();
    $('#'+id).remove();
    //第一个标签及其内容显示
    $('#roomTab').children('li:first').find('a.roomname').click();

}

/**
 * 初始房间标签信息和具体内容信息联动事件绑定
 */
$('#roomTab a').bind('click',bindTabContent);


/**
 * 保存场地信息
 */
$("#saveOrUpdateSite").on("click", function (e) {

    //必填项校验
    var validFlag = true;
    $("div.basic-site-info").find("input").each(function (i) {
        if($(this).attr("name") != "pdfBrief" && $(this).val() == ""){
            validFlag = false;
            $(this).focus();
            $("#tipMessage").removeClass("alert-warning").addClass("alert-danger")
            return false;
        }
    });
    if(!validFlag){
        return;
    }

    //#1 上传预览图和pdf简介文件
    var fileItem = new FormData();
    fileItem.append("previewImg",$("input[name='previewImg']")[0].files[0]);
    fileItem.append("pdfBrief",$("input[name='pdfBrief']")[0].files[0]);

    $.ajax({
        url:"/systemManage/site/file",
        type:"post",
        data:fileItem,
        processData:false,
        contentType:false,
        success:function(data){

            if(!data.flag){
                alert('文件保存失败，请重试');
                return;
            }

            // #2 保存会场和房间信息
            //构建数据
            var rooms = $('#roomTabContent').find('div.tab-pane');
            var site = {};

            site.previewImg = $("input[name='previewImg']").val();
            site.siteName = $("input[name='siteName']").val();
            site.location = $("input[name='location']").val();
            site.startingPrice = $("input[name='startingPrice']").val();
            site.siteBriefIntroduction = $("input[name='siteBriefIntroduction']").val();
            site.siteServices = $("input[name='siteServices']").val();
            site.openingDate = $("input[name='openingDate']").val();
            site.lastDecorationDate = $("input[name='lastDecorationDate']").val();
            site.sitePolicy = $("input[name='sitePolicy']").val();

            var item = {};
            var items = [];
            for(var i = 1;i < rooms.length;i++){

                item.name = $(rooms[i]).find("input[name='roomName']").val();
                item.area = $(rooms[i]).find("input[name='area']").val();
                item.storyHeight = $(rooms[i]).find("input[name='storyHeight']").val();
                item.size = $(rooms[i]).find("input[name='size']").val();
                item.lumen = $(rooms[i]).find("input[name='lumen']").val();
                item.type = $(rooms[i]).find("input[name='type']").val();
                item.freeServices = $(rooms[i]).find("input[name='freeServices']").val();
                item.retailFullDay = $(rooms[i]).find("input[name='retailFullDay']").val();
                item.retailHalfDay = $(rooms[i]).find("input[name='retailHalfDay']").val();
                item.meetingOnlyFullDay = $(rooms[i]).find("input[name='meetingOnlyFullDay']").val();
                item.meetingOnlyHalfDay = $(rooms[i]).find("input[name='meetingOnlyHalfDay']").val();
                item.overtimeChargePurHour = $(rooms[i]).find("input[name='overtimeChargePurHour']").val();
                item.meetingLevel = $(rooms[i]).find("input[name='meetingLevel']").val();

                item.hasThroughLiftToMeeting = $(rooms[i]).find("input.hasThroughLiftToMeeting").prop('checked');
                item.hasFreeLounge = $(rooms[i]).find("input.hasFreeLounge").prop('checked');
                item.hasLobby = $(rooms[i]).find("input.hasLobby").prop('checked');
                item.hasWindow = $(rooms[i]).find("input.hasWindow").prop('checked');
                item.hasSquareArea = $(rooms[i]).find("input.hasSquareArea").prop('checked');
                item.hasPillar = $(rooms[i]).find("input.hasPillar").prop('checked');
                item.hasCarpet = $(rooms[i]).find("input.hasCarpet").prop('checked');
                item.hasStage = $(rooms[i]).find("input.hasStage").prop('checked');
                item.hasProjection = $(rooms[i]).find("input.hasProjection").prop('checked');
                item.hasLed = $(rooms[i]).find("input.hasLed").prop('checked');
                item.ledSize = $(rooms[i]).find("input[name='ledSize']").val();
                item.ledRent = $(rooms[i]).find("input[name='ledRent']").val();
                items.push(item);
                item = {};
            }

            var requestParam = {};
            requestParam.site = site;
            requestParam.meetingRooms = items;
            requestParam.previewImg = data.mapData.previewImg;
            requestParam.pdfBrief = data.mapData.pdfBrief;

            $.ajax({
                url: "/systemManage/site/update",
                type: "post",
                data: JSON.stringify(requestParam),
                dataType:"json",
                contentType: "application/json",
                success: function (data) {
                    if(data.flag){
                        alert('场地信息保存成功');
                        window.location.reload(true);
                    }
                },
                error: function (data) {
                    alert('文件保存失败，请重试');
                }
            });
        },
        error:function(data){
            alert('文件保存失败，请重试');
        }
    });
    return;
});



/**
 * 移除会场信息
 */
$('#delete-site').on('show.bs.modal', function (e) {
    $("#confirm-delete").attr("id", e.relatedTarget.id);
});
$("#confirm-delete").bind("click", function () {
    $.ajax({
        url: '/systemManage/site/delete/' + this.id,
        type: "get",
        async: true,
        success: function (html) {
            window.location.reload();
        }
    });
})

//编辑会场信息
$("#update-site").on("show.bs.modal", function (e) {

    //更新功能暂不启用
    return;

    $("#update-site div.modal-content").empty();
    $.ajax({
        url: '/systemManage/site/update/' + e.relatedTarget.id,
        type: "get",
        async: true,
        success: function (data) {
            $("#update-site div.modal-content").append(data);
        }
    });
});

$(".updSite").on("click", function (event) {
    var $this = this;
    $('#updForm' + this.id).submit();
    //重设样式为“更新”
    // $($this).text("更新");
    // $($this).removeClass("btn-danger editSite").addClass("btn-success updSite");
    // $($this).parent().next().find(".form-control").attr("disabled",false);
    // $.post("markReaded", {"id": this.id},
    //     function (data) {
    //
    //         if (data) {
    //             $(".unReadCount").text(parseInt($(".unReadCount").text()) - 1);
    //             $(".readCount").text(parseInt($(".readCount").text()) + 1);
    //             $($this).parents(".list-group").hide(350);
    //         }else{
    //             alert("标记失败，请稍后重试或联系系统管理员");
    //         }
    //
    //     });

});

