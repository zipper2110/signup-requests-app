package com.github.zipper2110.something.repository;

import com.github.zipper2110.something.entity.SignupRequest;
import org.springframework.data.repository.CrudRepository;

public interface Repository extends CrudRepository<SignupRequest, String> {
}
