package com.frew.crew.articleCommand;

import lombok.Data;
import lombok.Getter;

import java.util.UUID;

@Getter
@Data
public class ArticleCommandDTO {
    private UUID articleId;
    private int quantity;
}