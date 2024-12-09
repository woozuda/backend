package com.woozuda.backend.diary.repository;

import com.woozuda.backend.account.entity.UserEntity;
import com.woozuda.backend.diary.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DiaryRepository extends JpaRepository<Diary, Long>, CustomDiaryRepository {

    Optional<Diary> findByIdAndUser(Long diaryId, UserEntity user);

    @Modifying
    @Query("delete from Diary d where d.id = :id and d.user.username = :username ")
    void deleteUserDiary(@Param("id") Long id, @Param("username") String username);
}
