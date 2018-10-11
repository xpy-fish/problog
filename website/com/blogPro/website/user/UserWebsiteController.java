package com.blogPro.website.user;

import java.io.Console;
import java.util.List;

import org.eclipse.jetty.server.UserIdentity;

import com.blogPro.website.user.UserWebsiteService;
import com.jfinal.core.Controller;
import com.jfinal.json.JFinalJson;
import com.jfinal.upload.UploadFile;

import cn.jbolt.common.model.Article;
import cn.jbolt.common.model.Noluser;



public class UserWebsiteController extends Controller {
	static UserWebsiteService service=UserWebsiteService.me;
	
	public static int myid = 0;
	public static int userid = 0;
	public void index() {
		//String picUrl = getSessionAttr("picUrl");
		//如果用户登录了，判断是否有userid，有则表示导航条渲染登录用户头像信息，主体部分渲染userid用户的信息
		//如果没有，则主体部分渲染登录用户的信息
		//如果用户没用登录，继续判断是否有userid，有则主体部分渲染userid用户信息，没有则重定向到登录界面(此状态一般为bug)
		if(islog()) {
			if(getPara("userid")==null) {
				userid = 0;
				render("index.html");
			}else {
				userid = getParaToInt("userid");
				setAttr("userid", userid);
				render("index.html");
			}
		}else {
			if(getPara("userid")==null) {
				redirect("/website/login");
			}else {
				userid = getParaToInt("userid");
				setAttr("userid", userid);
				render("index.html");
			}

		}
		
	}
	public boolean islog() {
		if(getSessionAttr("noluser.nolname")==null) {
			setAttr("state", false);
			System.out.println("未得到session");
			return false;
		}else {
			Noluser noluser = Noluser.dao.findFirst("select * from noluser where nolname = '"+getSessionAttr("noluser.nolname")+"'");
			myid = noluser.getId();
			setAttr("state", true);
			setAttr("icon", noluser.getIcon());
			System.out.println("得到session");
			System.out.println(myid);
			return true;
		}
	}
	public void art1() {
		setAttr("p",getPara("title_id"));
		render("article.html");
	}
	public void logout() {
		setSessionAttr("noluser.nolname", null);
		redirect("/website/homePage");
	}
	
	 //index中用的edit方法，修改头像
    public void edit() {
 
   	 render("headpicform.html");
    }
    
    //headpicform中用的ajaxForm方法提交头像
    public void ajaxForm() {
    	//render("common/success.html");
    	UploadFile file=getFile("file");
    	setAttr("filepath",file.getUploadPath()+"\\"+file.getFileName());
    	setAttr("picUrl","upload/temp/"+file.getFileName());
    	String photourl = "upload/temp/"+file.getFileName();
    	//setAttr("pic","file");
    	//setSessionAttr("picUrl","upload/temp/"+file.getFileName());
    	Noluser user = Noluser.dao.findFirst("select * from noluser where id = '"+myid+"'");
    	user.setIcon(photourl);
    	boolean success = service.doSubmit(user);
    	if(success) {
    		System.out.println("写入数据库成功");
    		render("common/success.html");
    		
    	}
    	renderJson() ;  
    	}
    
    
   //index中用的editmsg方法修改个人信息
    public void editmsg() {
    	 int id = getParaToInt(0);
    	 Noluser user  = Noluser.dao.findById(id);
    	 setAttr("user",user);
      	 render("msgform.html");
       }

  //msgform中用的submit方法，提交form修改的个人信息数据
    public void submit() {
   	 Noluser user=getModel(Noluser.class,"user");
   	 boolean success=service.doSubmit(user);//调用Service中的doSubmit方法
   	 if(success) {
   		 render("common/success.html");
   		 
   	 }else {
   		 render("common/error.html");
   	 }
    }
    
    //index中渲染主页网名等调用的homemsg方法
    public void homemsg() {
    	getResponse().addHeader("Access-Control-Allow-Origin", "*");
//    	String nolname = getSessionAttr("noluser.nolname");
//    	List<Noluser> user = Noluser.dao.find("select * from noluser where nolname = '"+nolname+"' limit 1");
    	Noluser user;
    	if(userid != 0) {
    		user = Noluser.dao.findById(userid);
    	}else {
    		user = Noluser.dao.findById(myid);
    	}
    	renderJson("{\"userData\":"+JFinalJson.getJson().toJson(user)+"}"); 
    	
	}
    
    //index中渲染文章调用的homearticles方法
    public void homearticles() {
    	getResponse().addHeader("Access-Control-Allow-Origin", "*");
    //	String callback=getRequest().getParameter("callback");
    	List<Article> ifaces;
    	if(userid != 0) {
		    ifaces = Article.dao.find("select article.title,article.id,article.s_content,article.c_url_img from article,noluser where noluser.id = article.userid and noluser.id = '"+ userid+"' ");
    	}else {
		    ifaces = Article.dao.find("select article.title,article.id,article.s_content,article.c_url_img from article,noluser where noluser.id = article.userid and noluser.id = '"+ myid+"' ");
    	}
		System.out.println(ifaces);
		renderJson("{\"jsonData\":"+JFinalJson.getJson().toJson(ifaces)+"}"); 
    	
	}	
    
    //article中调用的article方法，渲染文章详情页面
    public void detailarticle() {
		List<Article> article = Article.dao.find("select * from article where id = "+getPara("title_id")+"");
		renderJson("{\"artData\":"+JFinalJson.getJson().toJson(article)+"}"); 
	}

}
