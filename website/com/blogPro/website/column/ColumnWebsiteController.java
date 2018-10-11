package com.blogPro.website.column;

import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.json.JFinalJson;

import cn.jbolt.common.model.Article;
import cn.jbolt.common.model.Noluser;

public class ColumnWebsiteController extends Controller {
	public void index() {
		islog();
		render("index.html");
	}
//  判断是否登录
	public void islog() {
		if(getSessionAttr("noluser.nolname")==null) {
			setAttr("state", false);
			setAttr("userID", 0);
		}else {
			Noluser noluser = Noluser.dao.findFirst("select * from noluser where nolname = '"+getSessionAttr("noluser.nolname")+"'");
			setAttr("state", true);
			setAttr("icon", noluser.getIcon());
			setAttr("userID", noluser.getId());
		}
	}
	public void story() {
		setAttr("tag",getPara("tag"));
		render("story.html");
	}
	public void list() {
		int p = getParaToInt("p");
		int c = getParaToInt("c");
		int a = p-4;
		String artcond;
		if(c == 1) {
			 artcond = "creatTime";
		}else if (c == 2) {
			 artcond = "newCommTime";
		}else {
			 artcond = "art_collect";
		}
		List<Article> arts = Article.dao.find("select article.id,title,s_content,c_url_img,author,comN,clickN from article left join click on article.id=click.id where tags = "+getPara("tag")+" order by "+artcond+" desc limit "+a+",4 ");      
		renderJson("{\"jsonData\":"+JFinalJson.getJson().toJson(arts)+"}");
	}
}
