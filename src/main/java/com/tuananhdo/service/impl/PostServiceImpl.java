package com.tuananhdo.service.impl;

import com.tuananhdo.entity.Category;
import com.tuananhdo.entity.Post;
import com.tuananhdo.exception.ResourceNotFoundException;
import com.tuananhdo.mapper.PostMapper;
import com.tuananhdo.payload.PostDTO;
import com.tuananhdo.payload.PostResponse;
import com.tuananhdo.repository.CategoryRepository;
import com.tuananhdo.repository.PostRepository;
import com.tuananhdo.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final CategoryRepository categoryRepository;

    @Override
    public PostResponse getAllPost(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> posts = postRepository.findAll(pageable);
        List<Post> listOfPosts = posts.getContent();
        List<PostDTO> contents = listOfPosts.stream()
                .map(postMapper::mapToPostDTO)
                .collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(contents);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());
        return postResponse;
    }

    @Override
    public PostDTO createPost(PostDTO postDTO) {
        Category category = categoryRepository.findById(postDTO.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", postDTO.getCategoryId()));
        Post post = postMapper.mapToPost(postDTO);
        post.setCategory(category);
        Post savedPost = postRepository.save(post);
        return postMapper.mapToPostDTO(savedPost);
    }

    @Override
    public PostDTO updatePost(PostDTO postDTO, long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Posts", "id", id));

        Category category = categoryRepository.findById(postDTO.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", postDTO.getCategoryId()));

        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());
        post.setContent(postDTO.getContent());
        post.setCategory(category);
        Post updatedPost = postRepository.save(post);
        return postMapper.mapToPostDTO(updatedPost);
    }

    @Override
    public PostDTO getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Posts", "id", id));
        return postMapper.mapToPostDTO(post);
    }

    @Override
    public void deletePostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Posts", "id", id));
        postRepository.delete(post);
    }

    @Override
    public List<PostDTO> getPostsByCategory(Long categoryId) {
        List<Post> posts = postRepository.findByCategoryId(categoryId);
        return posts.stream()
                .map(postMapper::mapToPostDTO).toList();
    }
}
