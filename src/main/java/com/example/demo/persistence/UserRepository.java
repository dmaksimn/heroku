package com.example.demo.persistence;

import com.example.demo.domain.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
