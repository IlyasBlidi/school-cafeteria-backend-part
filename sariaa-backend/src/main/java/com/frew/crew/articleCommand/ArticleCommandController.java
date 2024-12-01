package com.frew.crew.articleCommand;

import com.frew.crew.command.CommandDetailsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/articles-commands")
public class ArticleCommandController {
  private final ArticleCommandService articleCommandService;

  @GetMapping
  public ResponseEntity<List<ArticleCommandDTO>> getArticleCommands() {
    List<ArticleCommandDTO> articleCommands = articleCommandService.getAllArticleCommands();
    return ResponseEntity.ok(articleCommands);
  }

//  @PostMapping
//  public ArticleDTO createArticleCommand(@RequestBody ArticleCommand articleCommand) {
//    return articleCommandService.addArticleToCommand(articleCommand);
//  }

  @PostMapping("/{commandId}")
  public CommandDetailsDTO constructCommand(
    @PathVariable UUID commandId,
    @RequestBody Map<UUID, Integer> articlesQuantities
  ) {
    return articleCommandService.constructUserCommand(commandId, articlesQuantities);
  }
}
