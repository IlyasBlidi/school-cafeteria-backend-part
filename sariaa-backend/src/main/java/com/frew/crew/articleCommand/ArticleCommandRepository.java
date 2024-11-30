package com.frew.crew.articleCommand;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleCommandRepository extends JpaRepository<ArticleCommand, ArticleCommandId> {
}
