package com.liqu.wiki.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.liqu.wiki.entity.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Integer>{

	/**
	 * 
	 * @param userId
	 * @param boardId
	 * @param content
	 * @return 업데이트된 row의 개수를 returns
	 */
	@Modifying
	@Query(value="INSERT INTO reply (user_Id, board_Id, content, create_Date) values (?1, ?2, ?3, now())", nativeQuery = true)
	int mSave(int userId, int boardId, String content);
}
