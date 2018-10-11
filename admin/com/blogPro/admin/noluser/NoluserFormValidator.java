package com.blogPro.admin.noluser;

import com.jfinal.core.Controller;
import cn.jbolt.common.model.Noluser;
import com.jfinal.validate.Validator;

public class NoluserFormValidator extends Validator {

	@Override
	protected void validate(Controller c) {
		// TODO Auto-generated method stub
		validateEmail("noluser.email", "emailMsg", "email格式不正确");
		Integer id = c.getParaToInt("noluser.id");
		if(id==null || id<=0) {
			validateRequired("noluser.psw", "passwordMsg", "密码不能为空");
		}
	}

	@Override
	protected void handleError(Controller c) {
		// TODO Auto-generated method stub
		c.keepModel(Noluser.class,"noluser");
		c.render("form.html");
	}

}
