package com.tuananhdo.service.impl;

import com.tuananhdo.entity.Comment;
import com.tuananhdo.entity.Post;
import com.tuananhdo.exception.BlogAPIException;
import com.tuananhdo.exception.ResourceNotFoundException;
import com.tuananhdo.mapper.CommentMapper;
import com.tuananhdo.payload.CommentDTO;
import com.tuananhdo.repository.CommentRepository;
import com.tuananhdo.repository.PostRepository;
import com.tuananhdo.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final CommentMapper commentMapper;
    @Override
    public CommentDTO createComment(long id, CommentDTO commentDTO) {
        Comment comment = commentMapper.mapToCommentEntity(commentDTO);
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Posts", "id", id));
        comment.setPost(post);
        Comment newComment = commentRepository.save(comment);
        return commentMapper.mapToCommentDTO(newComment);
    }

    @Override
    public CommentDTO updateComment(long postId, long commentId, CommentDTO commentDTO) {
        Comment comment = validatePostAndComment(postId, commentId);
        comment.setName(commentDTO.getName());
        comment.setEmail(commentDTO.getEmail());
        comment.setContent(commentDTO.getContent());
        Comment updatedComment = commentRepository.save(comment);
        return commentMapper.mapToCommentDTO(updatedComment);
    }

    @Override
    public List<CommentDTO> getCommentByPostId(long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream().map(commentMapper::mapToCommentDTO).toList();
    }

    @Override
    public CommentDTO getCommentById(long postId, long commentId) {
        Comment comment = validatePostAndComment(postId, commentId);
        return commentMapper.mapToCommentDTO(comment);
    }

    @Override
    public void deleteCommentById(long postId, long commentId) {
        Comment comment = validatePostAndComment(postId, commentId);
        commentRepository.delete(comment);
    }

    private Comment validatePostAndComment(long postId, long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(()
                -> new ResourceNotFoundException("Posts", "postId", postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(()
                -> new ResourceNotFoundException("Comments", "commentId", commentId));
        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }
        return comment;
    }
}
