package com.dongblog.api.repository;

import com.dongblog.api.domain.Post;
import com.dongblog.api.request.PostSearch;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> getList(PostSearch postSearch);
}
