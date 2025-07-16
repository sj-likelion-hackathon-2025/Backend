package org.kwakmunsu.flowmate.domain.member.repository.member;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.kwakmunsu.flowmate.domain.member.entity.Member;
import org.kwakmunsu.flowmate.global.exception.NotFoundException;
import org.kwakmunsu.flowmate.global.exception.dto.ErrorStatus;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class MemberRepository {

    private final MemberJpaRepository memberJpaRepository;

    public Member save(Member member) {
        return memberJpaRepository.save(member);
    }

    public Member findById(Long id) {
        return memberJpaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorStatus.NOT_FOUND_MEMBER));
    }

    public Optional<Member> findBySocialId(String socialId) {
        return memberJpaRepository.findBySocialId(socialId);
    }

    public boolean existsByName(String name) {
        return memberJpaRepository.existsByName(name);
    }
}