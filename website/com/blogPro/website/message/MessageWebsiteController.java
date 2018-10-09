package com.blogPro.website.message;

import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.json.JFinalJson;

import cn.jbolt.common.model.Article;
import cn.jbolt.common.model.Comment;
import cn.jbolt.common.model.Noluser;
import cn.jbolt.common.model.UserCollect;

public class MessageWebsiteController extends Controller {
	public void index() {
		if(islog()) {
			render("index.html");
		}else {
			redirect("/website/login");
		}
		
	}
	public boolean islog() {
		if(getSessionAttr("noluser.nolname")==null) {
			setAttr("state", false);
			setAttr("userID", 0);
			return false;
		}else {
			Noluser noluser = Noluser.dao.findFirst("select * from noluser where nolname = '"+getSessionAttr("noluser.nolname")+"'");
			setAttr("state", true);
			setAttr("icon", noluser.getIcon());
			setAttr("userID", noluser.getId());
			return true;
		}
	}
	public void getSendCommentJson() {
		Noluser user = Noluser.dao.findFirst("select nolname,icon from noluser where id = "+getPara("userID")+""); 
		String thename = user.getNolname();
		String icon = user.getIcon();
		List<Comment> send_comments = Comment.dao.find("select comt,comp,comc,title from comment left join article  on comment.comac=article.id where comp = \""+thename+"\"");
		renderJson("{\"sendCommentData\":"+JFinalJson.getJson().toJson(send_comments)+",\"jsonLen\":"+send_comments.size()+",\"icon\":\""+icon+"\"}");
	}
	
	public void getGetCommentJson() {
		List<Article> articles = Article.dao.find("select article.id from article where userid = "+getPara("userID")+" ");
		int len = articles.size();
		String ids = "(";
		int i ;
		for(i=0;i<len-1;i++) {
			ids += articles.get(i).getId() + ",";
		}
		ids += articles.get(len-1).getId() + ")";
		List<Comment> get_comments = Comment.dao.find("select comt,comp,comc,title,icon from comment left join article  on comment.comac=article.id left join noluser on comp = nolname where comac in "+ids+"");
		renderJson("{\"getCommentData\":"+JFinalJson.getJson().toJson(get_comments)+",\"jsonLen\":"+get_comments.size()+"}");
	}
	
	public void getCollectJson() {
		List<UserCollect> userCollects = UserCollect.dao.find("select article_id from user_collect where user_id = "+getPara("userID")+"");
		int len = userCollects.size();
		String ids = "(";
		int i ;
		for(i=0;i<len-1;i++) {
			ids += userCollects.get(i).getArticleId() + ",";
		}
		ids += userCollects.get(len-1).getArticleId() + ")";
		System.out.println(ids);
		List<Article> get_collects =Article.dao.find("select title,c_url_img,s_content,author,clickN,comN from article left join click on article.id = click.id where article.id in "+ids+"");
		renderJson("{\"getCollectData\":"+JFinalJson.getJson().toJson(get_collects)+",\"jsonLen\":"+get_collects.size()+"}");
	}
	


}
