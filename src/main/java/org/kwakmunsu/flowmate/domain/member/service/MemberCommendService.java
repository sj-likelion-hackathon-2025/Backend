package org.kwakmunsu.flowmate.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.kwakmunsu.flowmate.domain.member.entity.Member;
import org.kwakmunsu.flowmate.domain.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberCommendService {

    private final MemberRepository memberRepository;

    @Transactional
    public void updateRefreshToken(Long memberId, String refreshToken) {
        Member member = memberRepository.findById(memberId);
        member.updateRefreshToken(refreshToken);
    }

}