package org.kwakmunsu.flowmate.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.kwakmunsu.flowmate.domain.member.entity.Member;
import org.kwakmunsu.flowmate.domain.member.repository.member.MemberRepository;
import org.kwakmunsu.flowmate.domain.member.service.dto.MemberInfoResponse;
import org.kwakmunsu.flowmate.global.exception.BadRequestException;
import org.kwakmunsu.flowmate.global.exception.DuplicationException;
import org.kwakmunsu.flowmate.global.exception.dto.ErrorStatus;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberQueryService {

    private final MemberRepository memberRepository;

    public MemberInfoResponse getProfile(Long memberId) {
        Member member = memberRepository.findById(memberId);

        return MemberInfoResponse.from(member);
    }

    public void checkDuplicateName(String name) {
        if(name == null || name.isEmpty()) {
            throw new BadRequestException(ErrorStatus.BAD_REQUEST);
        }
        if (memberRepository.existsByName(name)) {
            throw new DuplicationException(ErrorStatus.DUPLICATE_NICKNAME);
        }
    }

}