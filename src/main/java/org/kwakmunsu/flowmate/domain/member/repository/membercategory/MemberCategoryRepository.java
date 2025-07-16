package org.kwakmunsu.flowmate.domain.member.repository.membercategory;

import lombok.RequiredArgsConstructor;
import org.kwakmunsu.flowmate.domain.member.entity.MemberCategory;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class MemberCategoryRepository {

    private final MemberCategoryJpaRepository memberCategoryJpaRepository;

    public void save(MemberCategory memberCategory) {
        memberCategoryJpaRepository.save(memberCategory);
    }

}