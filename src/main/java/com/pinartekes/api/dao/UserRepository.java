package com.pinartekes.api.dao;

import com.pinartekes.api.dao.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
    long deleteUserByUsername(String username);
    Page<User> findAll(Pageable pageable);

    User findByUsername(String username);

    List<User> findTop10ByOrderByNameAsc();
}
