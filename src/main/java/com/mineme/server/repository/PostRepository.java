package com.mineme.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mineme.server.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

}
