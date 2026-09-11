package com.ari.devconnect.service;

import com.ari.devconnect.model.Blog;
import com.ari.devconnect.model.Project;
import com.ari.devconnect.model.User;
import com.ari.devconnect.repository.BlogCommentRepository;
import com.ari.devconnect.repository.BlogLikeRepository;
import com.ari.devconnect.repository.BlogRepository;
import com.ari.devconnect.repository.ProjectCommentRepository;
import com.ari.devconnect.repository.ProjectLikeRepository;
import com.ari.devconnect.repository.ProjectRepository;
import com.ari.devconnect.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final ProjectLikeRepository projectLikeRepository;
    private final ProjectCommentRepository projectCommentRepository;
    private final BlogRepository blogRepository;
    private final BlogLikeRepository blogLikeRepository;
    private final BlogCommentRepository blogCommentRepository;

    public UserService(
            UserRepository userRepository,
            ProjectRepository projectRepository,
            ProjectLikeRepository projectLikeRepository,
            ProjectCommentRepository projectCommentRepository,
            BlogRepository blogRepository,
            BlogLikeRepository blogLikeRepository,
            BlogCommentRepository blogCommentRepository
    ) {
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.projectLikeRepository = projectLikeRepository;
        this.projectCommentRepository = projectCommentRepository;
        this.blogRepository = blogRepository;
        this.blogLikeRepository = blogLikeRepository;
        this.blogCommentRepository = blogCommentRepository;
    }

    @Transactional
    public void deleteAccount(String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found: " + username
                        )
                );

        Long userId = user.getId();

        /*
         * 1. Delete likes and comments made by this user
         * on other people's projects/blogs.
         */
        projectLikeRepository.deleteByUserId(userId);
        projectCommentRepository.deleteByUserId(userId);

        blogLikeRepository.deleteByUserId(userId);
        blogCommentRepository.deleteByUserId(userId);


        /*
         * 2. Delete projects owned by this user.
         *
         * Their likes/comments must be deleted first
         * because they reference the project.
         */
        List<Project> projects =
                projectRepository.findByUserId(userId);

        for (Project project : projects) {

            projectLikeRepository.deleteByProjectId(project.getId());

            projectCommentRepository.deleteByProjectId(project.getId());

            projectRepository.delete(project);
        }


        /*
         * 3. Delete blogs owned by this user.
         *
         * Their likes/comments must be deleted first.
         */
        List<Blog> blogs =
                blogRepository.findByUserId(userId);

        for (Blog blog : blogs) {

            blogLikeRepository.deleteByBlogId(blog.getId());

            blogCommentRepository.deleteByBlogId(blog.getId());

            blogRepository.delete(blog);
        }


        /*
         * 4. Finally delete the user.
         *
         * User.profile has CascadeType.ALL,
         * so the associated Profile is deleted too.
         */
        userRepository.delete(user);
    }
}