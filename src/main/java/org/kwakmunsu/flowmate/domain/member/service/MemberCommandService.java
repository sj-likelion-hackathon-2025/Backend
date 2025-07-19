package org.kwakmunsu.flowmate.domain.member.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.kwakmunsu.flowmate.domain.member.controller.dto.MemberProfileServiceRequest;
import org.kwakmunsu.flowmate.domain.member.entity.InterestCategory;
import org.kwakmunsu.flowmate.domain.member.entity.Member;
import org.kwakmunsu.flowmate.domain.member.entity.MemberCategory;
import org.kwakmunsu.flowmate.domain.member.repository.member.MemberRepository;
import org.kwakmunsu.flowmate.domain.member.repository.membercategory.MemberCategoryRepository;
import org.kwakmunsu.flowmate.domain.member.service.dto.MemberCategoryRegisterServiceRequest;
import org.kwakmunsu.flowmate.infrastructure.s3.S3Provider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberCommandService {

    private final MemberRepository memberRepository;
    private final MemberCategoryRepository memberCategoryRepository;
    private final S3Provider s3Provider;

    @Transactional
    public void updateRefreshToken(Long memberId, String refreshToken) {
        Member member = memberRepository.findById(memberId);
        member.updateRefreshToken(refreshToken);
    }

    @Transactional
    public void updateProfile(MemberProfileServiceRequest request) {
        Member member = memberRepository.findById(request.memberId());
        member.upgradeRoleToMember();

        if (!isNullProfileImage(member)) {
            s3Provider.deleteImage(member.getProfileImgUrl());
        }
        if (isEmptyRequestProfileImage(request)) {
            member.updateBaseInfo(request.name(), null);
            return;
        }

        String uploadImage = s3Provider.uploadImage(request.profileImage());
        member.updateBaseInfo(request.name(), uploadImage);
    }

    @Transactional
    public void registerCategory(MemberCategoryRegisterServiceRequest request) {
        List<InterestCategory> categories = request.categories();

        for (InterestCategory category : categories) {
            MemberCategory memberCategory = MemberCategory.builder()
                    .memberId(request.memberId())
                    .category(category)
                    .build();
            memberCategoryRepository.save(memberCategory);
        }

    }

    private boolean isNullProfileImage(Member member) {
        return member.getProfileImgUrl() == null;
    }

    private boolean isEmptyRequestProfileImage(MemberProfileServiceRequest request) {
        return request.profileImage() == null || request.profileImage().isEmpty();
    }

}