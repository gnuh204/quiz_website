package com.example.qiuzweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.qiuzweb.domain.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    // Có thể thêm method custom nếu cần
}
