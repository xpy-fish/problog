package com.blogPro.website.register;

import java.util.List;

import com.jfinal.core.Controller;

import cn.jbolt.common.model.Noluser;
import cn.jbolt.common.model.Salt;
import com.jfinal.kit.HashKit;

public class RegisterWebsiteController extends Controller {
	public void index() {
		
		render("register.html");

	}
	public void formreg() {
		String name = getPara("name");
		String password = getPara("password");
		String age = getPara("age");
		String sex = getPara("sex");
		String phonenum = getPara("phonenum");
		String email = getPara("email");
		String username = getPara("username");
		List<Noluser> register1 = Noluser.dao.findByUsername(getPara("username"));
		List<Noluser> register2 = Noluser.dao.findByPhonenum(getPara("phonenum"));
		List<Noluser> register3 = Noluser.dao.findByEmail(getPara("email"));

		if (name == null || password == null || age == null || sex == null || phonenum == null || email == null
				|| username == null) {
			render("register.html");
		} else {
			if (register1.size()>0||register2.size()>0||register3.size()>0){ 
				render("clashreg.html");}
			else {
				String salt = HashKit.generateSalt(16);
				String mdPsw = HashKit.md5(password);
				String securityPsw = HashKit.md5(mdPsw+salt);
				Noluser.dao.saveLogin(name, securityPsw, age, sex, phonenum, email, username);
				Salt saltObj = new Salt();
				saltObj.set("username", username).set("salt", salt).save();
				render("successreg.html");
			}
		}
	}
}
