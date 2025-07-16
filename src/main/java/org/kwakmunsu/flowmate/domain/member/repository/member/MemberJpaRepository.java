package org.kwakmunsu.flowmate.domain.member.repository.member;

import java.util.Optional;
import org.kwakmunsu.flowmate.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {

    Optional<Member> findBySocialId(String socialId);

}