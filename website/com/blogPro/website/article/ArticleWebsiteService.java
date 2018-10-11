package com.blogPro.website.article;

import cn.jbolt.common.model.Article;

public class ArticleWebsiteService {
	public static final ArticleWebsiteService me = new ArticleWebsiteService();
	public static final Article dao = new Article().dao();
	public boolean doSubmit(Article article) {
		// TODO Auto-generated method stub
		if(article.getId()==null || article.getId()<=0) {
			return save(article);
		}else {
			return update(article);
		}
	}
	private boolean update(Article article) {
		// TODO Auto-generated method stub
		return article.update();
	}
	private boolean save(Article article) {
		// TODO Auto-generated method stub
		return article.save();
	}
	
}
