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
        yearRange: "-50:+0",
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
    var index = $('#roomTabContent').find('div.tab-pane').length;//新增标签的下标

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
        if($(this).attr("required") && $(this).val() == ""){
            validFlag = false;
            $(this).focus();
            $("#tipMessage").removeClass("alert-warning").addClass("alert-danger")
            return false;
        }
    });
    if(!validFlag){
        return;
    }

    //参数构建
    var requestParam = new FormData();
    requestParam.append("previewImg",$("input[name='previewImg']")[0].files[0]);
    requestParam.append("pdfBrief",$("input[name='pdfBrief']")[0].files[0]);

    var rooms = $('#roomTabContent').find('div.tab-pane');
    var site = {};

    //保存当前记录的id，区别新增和新增
    site.id = $("#id-for-update").val();
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
        item.id = $(rooms[i]).find("input[name='roomId']").val();
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

    requestParam.append("site",JSON.stringify(site));
    requestParam.append("meetingRooms",JSON.stringify(items));

    $.ajax({
        url:"/systemManage/site/saveOrUpdate",
        type:"post",
        data:requestParam,
        processData:false,
        contentType:false,
        success:function(data){
            if(data.flag){
                alert(data.message);
                window.location.reload(true);
            }else{
                alert(data.message);
                return;
            }
        },
        error:function(data){
            alert('场地信息保存失败，请检查登录状态是否超时，或联系系统管理员');
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
        success: function (html) {
            window.location.reload();
        }
    });
})

$("#add-new-site").on("hidden.bs.modal",function(e){
    window.location.reload(true);
});

//新增，编辑会场信息
$("#add-new-site").on("show.bs.modal", function (e) {

    //id存在为更新操作
    if(e.relatedTarget.id){
        $.ajax({
            url: '/site/detail/api/' + e.relatedTarget.id,
            type: "get",
            success: function (data) {
                if(data.flag){

                    //更改描述信息

                    //移除required
                    $($("input[name='previewImg']")[0]).removeAttr("required");
                    //移除必填图标
                    $($("input[name='previewImg']")[0]).prev().find("span").remove();
                    $("#modal-title").html("更新会场信息");
                    $("#tipMessage").append("更新时上传新预览图，旧文件会删除");

                    //填充会场信息
                    var site = data.mapData.site;
                    $("#id-for-update").val(site.id);
                    $("input[name='previewImg']").val(site.previewImg);
                    $("input[name='siteName']").val(site.siteName);
                    $("input[name='location']").val(site.location);
                    $("input[name='startingPrice']").val(site.startingPrice);
                    $("input[name='siteBriefIntroduction']").val(site.siteBriefIntroduction);
                    $("input[name='siteServices']").val(site.siteServices);
                    $("input[name='openingDate']").val(moment(new Date(site.openingDate)).format('YYYY-MM-DD'));
                    $("input[name='lastDecorationDate']").val(moment(new Date(site.lastDecorationDate)).format('YYYY-MM-DD'));
                    $("input[name='sitePolicy']").val(site.sitePolicy);

                    //填充房间信息
                    var item = {};
                    var items = [];
                    var rooms = data.mapData.rooms;
                    //填充前清除第一个已经存在的空白TabContent
                    $('#roomTabContent').children("div:last").remove();
                    $('#roomTab').children("li:last").remove();
                    // $('#roomTabContent').empty();
                    // $('#roomTab').empty();

                    for(var i = 0;i < rooms.length;i++){
                        //内容构造，单选按钮的标签name属性添加下标，防止无法选中的情况
                        //内容填充
                        var index = i + 1;//构建房间下标

                        $('#roomTabContent').append($('#roomTemplate').html());
                        var contentHtml = $('#roomTabContent').children("div:last");

                        var tab = $('#roomTabTemplate');
                        $(tab.find("a.roomname")[0]).html(rooms[i].name);
                        $('#roomTab').append(tab.html());

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

                        //填充内容
                        $(contentHtml.find("input[name='roomId']")[0]).val(rooms[i].id);
                        $(contentHtml.find("input[name='roomName']")[0]).val(rooms[i].name);
                        $(contentHtml.find("input[name='area']")[0]).val(rooms[i].area);
                        $(contentHtml.find("input[name='storyHeight']")[0]).val(rooms[i].storyHeight);
                        $(contentHtml.find("input[name='size']")[0]).val(rooms[i].size);
                        $(contentHtml.find("input[name='lumen']")[0]).val(rooms[i].lumen);
                        $(contentHtml.find("input[name='type']")[0]).val(rooms[i].type);
                        $(contentHtml.find("input[name='freeServices']")[0]).val(rooms[i].freeServices);
                        $(contentHtml.find("input[name='retailFullDay']")[0]).val(rooms[i].retailFullDay);
                        $(contentHtml.find("input[name='retailHalfDay']")[0]).val(rooms[i].retailHalfDay);
                        $(contentHtml.find("input[name='meetingOnlyFullDay']")[0]).val(rooms[i].meetingOnlyFullDay);
                        $(contentHtml.find("input[name='meetingOnlyHalfDay']")[0]).val(rooms[i].meetingOnlyHalfDay);
                        $(contentHtml.find("input[name='overtimeChargePurHour']")[0]).val(rooms[i].overtimeChargePurHour);
                        $(contentHtml.find("input[name='meetingLevel']")[0]).val(rooms[i].meetingLevel);

                        $(contentHtml.find("input.hasThroughLiftToMeeting")[(rooms[i].hasThroughLiftToMeeting?0:1)]).prop('checked',true);
                        $(contentHtml.find("input.hasFreeLounge")[(rooms[i].hasFreeLounge?0:1)]).prop('checked',true);
                        $(contentHtml.find("input.hasLobby")[(rooms[i].hasLobby?0:1)]).prop('checked',true);
                        $(contentHtml.find("input.hasWindow")[(rooms[i].hasWindow?0:1)]).prop('checked',true);
                        $(contentHtml.find("input.hasSquareArea")[(rooms[i].hasSquareArea?0:1)]).prop('checked',true);
                        $(contentHtml.find("input.hasPillar")[(rooms[i].hasPillar?0:1)]).prop('checked',true);
                        $(contentHtml.find("input.hasCarpet")[(rooms[i].hasCarpet?0:1)]).prop('checked',true);
                        $(contentHtml.find("input.hasStage")[(rooms[i].hasStage?0:1)]).prop('checked',true);
                        $(contentHtml.find("input.hasProjection")[(rooms[i].hasProjection?0:1)]).prop('checked',true);
                        $(contentHtml.find("input.hasLed")[(rooms[i].hasLed?0:1)]).prop('checked',true);
                        $(contentHtml.find("input[name='ledSize']")[0]).val(rooms[i].ledSize);
                        $(contentHtml.find("input[name='ledRent']")[0]).val(rooms[i].ledRent);

                        var id = 'room' + i;
                        var hrefVal = '#' + id;

                        //事件绑定
                        $('#roomTab a.roomname').bind('click',bindTabContent);
                        $('#roomTab a.deleteRoom').bind('click',bindDeleteTabContent);

                        //tab和tabContent的联动绑定
                        $('#roomTabContent').children('div:last').attr('id',id);
                        $('#roomTab').children('li:last').addClass(id);
                        $('#roomTab').children('li:last').find('a.roomname').attr('href',hrefVal);
                        $('#roomTab').children('li:last').find('a.deleteRoom').attr('href',id);
                    }

                    //展示第一个tab页
                    $('#roomTab').children('li:first').find('a.roomname').click();
                    //重置tab模板的内容
                    $($('#roomTabTemplate').find("a.roomname")[0]).html("房间");

                }else{
                    alert('未获取到会场信息');
                }
            }
        });
    }

});


