package com.mineme.server.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mineme.server.entity.UserMatchingCode;

/**
 * @todo 이후 기능 개발에서 진행
 */
//@Repository
public interface UserMatchingCodeRepository extends JpaRepository<UserMatchingCode, Long> {
	Optional<UserMatchingCode> findByEncodedCode(String encoded);

	//@Query(value = "SELECT USER_MATCHING_CODE_ID, MIN(RAW_CODE), ENCODED_CODE FROM USER_MATCHING_CODE WHERE RAW_CODE NOT IN (SELECT RAW_CODE FROM USER_MATHCING_CODE)", nativeQuery = true)
	Optional<UserMatchingCode> findByRawCode(Long raw);
}
