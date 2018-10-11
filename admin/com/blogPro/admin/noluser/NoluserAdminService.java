package com.blogPro.admin.noluser;

import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;

import cn.jbolt.common.model.Noluser;

public class NoluserAdminService {
	public static final NoluserAdminService me = new NoluserAdminService();
	private static final Noluser dao = new Noluser().dao();
	
	public Page<Noluser> paginate(int pageNumber,int pageSize,String keywords,Boolean enable){
		
		StringBuilder where = new StringBuilder();
		if (enable!=null) {
			where.append(" enable = ").append(enable);   //此处的enable应该为Boolean的true或false，但数据库中为0,1bit型，为什么可以查询出来
		}
		if (StrKit.notBlank(keywords)) {
			if (enable!=null) {
				where.append(" and ");
			}
			where.append("(")
			.append("nolname like '%")
			.append(keywords)
			.append("%'")
			.append(" or ")
			.append("email like '%")
			.append(keywords)
			.append("%'")
			.append(")");
		}
		if(where.length()>0) {
			where.insert(0, "from noluser where");
			where.append("order by id desc");
		}else {
			where.append("from noluser order by id desc");
		}
		
		return dao.paginate(pageNumber, pageSize, "select id,nolname,email,icon,enable", where.toString());
	}
	public boolean doSubmit(Noluser noluser) {
		if(noluser.getId()==null || noluser.getId()<=0) {
			return save(noluser);
		}else {
			return update(noluser);
		}
	}
	public boolean save(Noluser noluser) {
		return noluser.save();
	}
	public boolean update(Noluser noluser) {
		return noluser.update();
	}
	public Noluser getById(Integer id) {
		return dao.findById(id);
	}
	public Ret delById(Integer id) {
		// TODO Auto-generated method stub
		boolean success = dao.deleteById(id);
		if(success) {
			return Ret.ok();
		}else {
			return Ret.fail();
		}
		
	}
	public Ret doToggleEnabel(Integer id) {
		// TODO Auto-generated method stub
		int count=Db.update("update noluser set enable=(!enable) where id=?",id);
		if(count==1) {
			return Ret.ok();
		}else {
			return Ret.fail();
		}
	}
}
