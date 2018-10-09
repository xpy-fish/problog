package com.blogPro.website.user;

import java.sql.Date;

import cn.jbolt.common.model.Noluser;



public class UserWebsiteService {
	public static final UserWebsiteService me=new UserWebsiteService();
	public  static final Noluser dao = new Noluser().dao();
	
	
	public boolean doSubmit(Noluser user) {		
			return update(user);
			/*return save(user);*/
	}
	private boolean update(Noluser user) {		
		return user.update();
	}
	/*private boolean save(Noluser user) {
		
		return user.save();
		}*/

	
}
