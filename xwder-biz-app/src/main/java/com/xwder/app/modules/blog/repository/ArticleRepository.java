package com.xwder.app.modules.blog.repository;

import com.xwder.app.modules.blog.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {


}
