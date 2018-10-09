package cn.jbolt.common.config;

import com.blogPro.website.article.ArticleWebsiteController;
import com.blogPro.website.column.ColumnWebsiteController;
import com.blogPro.website.homePage.BlogWebsiteController;
import com.blogPro.website.login.LoginWebsiteController;
import com.blogPro.website.message.MessageWebsiteController;
import com.blogPro.website.register.RegisterWebsiteController;
import com.blogPro.website.search.SearchWebsiteController;
import com.blogPro.website.user.UserWebsiteController;
import com.jfinal.config.Routes;

public class WebsiteRoutes extends Routes {

	@Override
	public void config() {
		// TODO Auto-generated method stub
		setBaseViewPath("WEB-INF/view");
		add("/website/article", ArticleWebsiteController.class);
		add("/website/homePage", BlogWebsiteController.class);
		add("/website/user", UserWebsiteController.class);
		add("/website/login", LoginWebsiteController.class);
		add("/website/register", RegisterWebsiteController.class);
		add("/website/column", ColumnWebsiteController.class);
		add("/website/search", SearchWebsiteController.class);
		add("/website/message", MessageWebsiteController.class);
	}

}
