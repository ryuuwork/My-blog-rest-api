package com.tuananhdo.mapper;

import com.tuananhdo.entity.Post;
import com.tuananhdo.payload.PostDTO;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PostMapper {

    private final ModelMapper mapper;

    public PostDTO mapToPostDTO(Post post) {
        return mapper.map(post, PostDTO.class);
    }

    public Post mapToPost(PostDTO postDTO) {
        return mapper.map(postDTO, Post.class);
    }
}
