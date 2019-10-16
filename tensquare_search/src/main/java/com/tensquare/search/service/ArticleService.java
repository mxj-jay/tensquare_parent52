package com.tensquare.search.service;

import com.tensquare.search.dao.ArticleDao;
import com.tensquare.search.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import util.IdWorker;


@Service
public class ArticleService {

    @Autowired
    private ArticleDao articleDao;
//    @Autowired
//    private IdWorker idWorker;

    public void save(Article article){
//        article.setId(idWorker.nextId()+"");
        // 使用默认id
        articleDao.save(article);
    }
}
