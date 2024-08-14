package com.tuananhdo.mapper;

import com.tuananhdo.entity.Comment;
import com.tuananhdo.payload.CommentDTO;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CommentMapper {
    
    private final ModelMapper mapper;
    public CommentDTO mapToCommentDTO(Comment comment) {
        return mapper.map(comment, CommentDTO.class);
    }

    public Comment mapToCommentEntity(CommentDTO commentDTO) {
        return mapper.map(commentDTO, Comment.class);
    }
}
