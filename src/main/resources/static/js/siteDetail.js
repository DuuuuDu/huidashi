//显示场地明细
// $('#myModal').on('show.bs.modal', function (e) {
//     $("#detailContainer").empty();
//     $.ajax({
//         url: '/site/detail/' + e.relatedTarget.id,
//         type: "get",
//         async: true,
//         success: function (html) {
//             $("#detailContainer").append(html);
//         },
//         fail:function () {
//             $("#detailContainer").append('<div class="alert alert-danger" role="alert">载入失败，正在为您重试</div>');
//         }
//     });
// });