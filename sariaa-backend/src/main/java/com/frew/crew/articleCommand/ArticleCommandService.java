package com.frew.crew.articleCommand;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleCommandService {
  private final ArticleCommandRepository articleCommandRepository;

  public ArticleCommand saveArticleCommand(ArticleCommand articleCommand){
  return articleCommandRepository.save(articleCommand) ;
  }

//  public ArticleCommand addArticleToCommand(UUID commandId, UUID ArticleId) {
//
//  }
}
