package com.example.qiuzweb.repository;
import com.example.qiuzweb.domain.TopicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends JpaRepository<TopicEntity, Long> {
    // Có thể thêm method custom nếu cần
}
