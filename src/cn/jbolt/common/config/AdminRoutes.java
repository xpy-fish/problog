package cn.jbolt.common.config;

import com.blogPro.admin.article.ArticleAdminController;
import com.blogPro.admin.index.IndexAdminController;
import com.blogPro.admin.noluser.NoluserAdminController;
import com.jfinal.config.Routes;

public class AdminRoutes extends Routes {
	
	@Override
	public void config() {
		// TODO Auto-generated method stub
		setBaseViewPath("WEB-INF/view");
		add("/admin", IndexAdminController.class);
		add("/admin/user",NoluserAdminController.class);
		add("/admin/article", ArticleAdminController.class);
	}
}
