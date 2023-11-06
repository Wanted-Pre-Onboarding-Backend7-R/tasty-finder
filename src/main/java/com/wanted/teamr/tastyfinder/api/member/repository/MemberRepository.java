package com.wanted.teamr.tastyfinder.api.member.repository;

import com.wanted.teamr.tastyfinder.api.member.domain.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);
}
