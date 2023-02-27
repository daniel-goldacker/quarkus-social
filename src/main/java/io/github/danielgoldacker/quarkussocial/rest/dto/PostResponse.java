package io.github.danielgoldacker.quarkussocial.rest.dto;

import java.time.LocalDateTime;

import io.github.danielgoldacker.quarkussocial.domain.model.Post;
import lombok.Data;

@Data
public class PostResponse {
    private String text;
    private LocalDateTime dateTime;

    public static PostResponse fromEntity(Post post){
        PostResponse response = new PostResponse();
        
        response.setDateTime(post.getDateTime());
        response.setText(post.getText());

        return response;
    }
}
