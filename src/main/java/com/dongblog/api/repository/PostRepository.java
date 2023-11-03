package com.dongblog.api.repository;

import com.dongblog.api.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> , PostRepositoryCustom{
}
