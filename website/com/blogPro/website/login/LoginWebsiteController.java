package com.blogPro.website.login;

import com.jfinal.kit.HashKit;
import cn.jbolt.common.model.Noluser;
import com.jfinal.core.Controller;
import cn.jbolt.common.model.Salt;;
public class LoginWebsiteController extends Controller {

	public void index() {
		render("login.html");

	}
	public void formlog() {
		boolean result = validateCaptcha("captcha");
		if (result) {
		String password = getPara("noluser.psw");
		String username = getPara("noluser.nolname");
		Noluser user = Noluser.dao.findFirst("select * from noluser where nolname = '"+username+"' or phonenum= '"+username+"' or email= '"+username+"' ");
		if(user==null) {
			render("faillogin.html");
		}else {
			Salt usersalt = Salt.dao.findFirst("select * from salt where username = '"+user.getNolname()+"' ");
			String mdPsw = HashKit.md5(password);
			String testPsw = HashKit.md5(mdPsw+usersalt.getSalt());
			if(testPsw.equals(user.getPsw())) {
				setSessionAttr("noluser.nolname", user.getNolname());
				redirect("/website/homePage");
			}else {
				render("faillogin.html");
			}
		}
		}else {
			render("faillogin.html");
		}
	}

	public void img() {
		renderCaptcha();
	}

}
