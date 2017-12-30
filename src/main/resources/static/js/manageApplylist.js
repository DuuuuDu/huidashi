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


$(".markReaded").on("click", function (event) {
    var $this = this;
    $.post("markReaded", {"id": this.id},
        function (data) {

            if (data) {
                $(".unReadCount").text(parseInt($(".unReadCount").text()) - 1);
                $(".readCount").text(parseInt($(".readCount").text()) + 1);
                $($this).parents(".list-group").hide(350);
            }else{
                alert("标记失败，请稍后重试或联系系统管理员");
            }

        });

});