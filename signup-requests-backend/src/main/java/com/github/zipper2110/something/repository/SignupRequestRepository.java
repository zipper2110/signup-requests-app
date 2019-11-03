package com.github.zipper2110.something.repository;

import com.github.zipper2110.something.entity.SignupRequest;
import org.springframework.data.repository.CrudRepository;

public interface SignupRequestRepository extends CrudRepository<SignupRequest, String> {
}
