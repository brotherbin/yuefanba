/**
 * 
 */
/**
 * 定时器检查订单更新
 */
function isNewOrder(){
	$.ajax({
		type: "POST",
		url: basePath+"shopManager/listOrder",
		dataType: "json",
		data: {
			status:0,
			pageNum: page.pageNum,
			pageSize: 20,
			sortTag: 0
		},
		success: function(data){
			if(data.list.length >0){				
				new Toast("亲，你有新订单哦，请注意查看").show();
				mediaPlay();
			}
		}
	});
}
function  mediaPlay(){
	var  myaudio=document.getElementById("myaudio");
	myaudio.play()
}