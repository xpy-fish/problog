$(function(){
	$("#blog_admin_nav li").on("click",function(){
		$("#blog_admin_nav li.active").removeClass("active");
		$(this).addClass("active");
	});
});