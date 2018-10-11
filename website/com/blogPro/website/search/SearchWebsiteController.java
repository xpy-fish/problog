package com.blogPro.website.search;


import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.json.JFinalJson;

import cn.jbolt.common.model.Article;
import cn.jbolt.common.model.Noluser;


public class SearchWebsiteController extends Controller {
	
	public static int myid = 0;
	public void index() {
		setAttr("searchContent",getPara("searchContent"));
		islog();
		render("index.html");
	}
	
	public void islog() {
		if(getSessionAttr("noluser.nolname")==null) {
			setAttr("state", false);
			System.out.println("未得到session");
		}else {
			Noluser noluser = Noluser.dao.findFirst("select * from noluser where nolname = '"+getSessionAttr("noluser.nolname")+"'");
			myid = noluser.getId();
			setAttr("state", true);
			setAttr("icon", noluser.getIcon());
			System.out.println("得到session");
			System.out.println(myid);
		}
	}
	

	
//查用户、文章
	public void finding(){
		String keyword = getPara("keyword");
		List<Noluser> user = Noluser.dao.find("select * from noluser where nolname like '%"+keyword+"%' ");
		List<Article> art = Article.dao.find("select * from article where title like '%"+keyword+"%' ");
		renderJson("{\"userData\":"+JFinalJson.getJson().toJson(user)+",\"artData\":"+ JFinalJson.getJson().toJson(art)+"}");
	}
	
}
