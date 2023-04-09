package com.mineme.server.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mineme.server.entity.Couple;
import com.mineme.server.entity.Post;
import com.mineme.server.entity.User;

public interface PostRepository extends JpaRepository<Post, Long> {

	@Query("select p from Post p join fetch p.userId where p.datedAt between :start and :end and p.userId = :user")
	List<Post> findMonthlyPosts(@Param("user") User currentUser, @Param("start") LocalDate start, @Param("end") LocalDate end);

	List<Post> findByCoupleId(Couple couple);
}
