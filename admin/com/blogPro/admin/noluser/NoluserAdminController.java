package com.blogPro.admin.noluser;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;

import cn.jbolt.common.model.Noluser;

public class NoluserAdminController extends Controller {
	static NoluserAdminService service = NoluserAdminService.me;
	public void index() {
		setSessionAttr("author", "小木");
		render("index.html");
	}
	public void list() {
		Integer pageSize = getParaToInt("limit", 10);
		String keywords = getPara("keywords");
		Boolean enable = getParaToBoolean("enable");
		Integer offset = getParaToInt("offset",0);
		int pageNumber = offset/pageSize+1;
		Page <Noluser> nolUserPageData = service.paginate(pageNumber, pageSize, keywords, enable);
		
		setAttr("total", nolUserPageData.getTotalRow());
		setAttr("rows", nolUserPageData.getList());
		renderJson();
	}
	public void edit() {
		Integer id = getParaToInt(0);
		if(id!=null&&id>=0) {
			setAttr("noluser", service.getById(id));
		}
		render("form.html");
	}
	@Before(NoluserFormValidator.class)
	public void submit() {
	   Noluser noluser = getModel(Noluser.class, "noluser");
	   boolean success = service.doSubmit(noluser);
	   if(success) {
		   render("/WEB-INF/view/admin/common/success.html");
	   }else {
		   render("/WEB-INF/view/admin/common/error.html");
	   }
	}
	public void xediSave() {
		   Noluser noluser = getModel(Noluser.class, "noluser");
		   boolean success = service.doSubmit(noluser);
		   renderJson(success?Ret.ok():Ret.fail());
	}
	
	public void del() {
		Ret ret = service.delById(getParaToInt(0));
	    renderJson(ret);
	}
	public void toggleEnable() {
		Ret ret = service.doToggleEnabel(getParaToInt(0));
	    renderJson(ret);
	}
}
