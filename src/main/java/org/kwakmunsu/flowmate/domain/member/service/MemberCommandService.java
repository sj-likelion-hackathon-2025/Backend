package org.kwakmunsu.flowmate.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.kwakmunsu.flowmate.domain.member.controller.dto.MemberProfileServiceRequest;
import org.kwakmunsu.flowmate.domain.member.entity.Member;
import org.kwakmunsu.flowmate.domain.member.repository.MemberRepository;
import org.kwakmunsu.flowmate.infrastructure.s3.S3Provider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberCommandService {

    private final MemberRepository memberRepository;
    private final S3Provider s3Provider;

    @Transactional
    public void updateRefreshToken(Long memberId, String refreshToken) {
        Member member = memberRepository.findById(memberId);
        member.updateRefreshToken(refreshToken);
    }

    @Transactional
    public void updateProfile(MemberProfileServiceRequest request) {
        Member member = memberRepository.findById(request.memberId());

        if (!isBasicProfileImage(member)) {
            s3Provider.deleteImage(member.getProfileImgUrl());
        }
        if (isEmptyRequestProfileImage(request)) {
            member.updateBaseInfo(request.name(), null);
            return;
        }

        String uploadImage = s3Provider.uploadImage(request.profileImage());
        member.updateBaseInfo(request.name(), uploadImage);
    }

    private boolean isBasicProfileImage(Member member) {
        return member.getProfileImgUrl() != null;
    }

    private boolean isEmptyRequestProfileImage(MemberProfileServiceRequest request) {
        return request.profileImage() == null || request.profileImage().isEmpty();
    }

}