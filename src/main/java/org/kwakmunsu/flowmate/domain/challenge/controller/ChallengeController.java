package org.kwakmunsu.flowmate.domain.challenge.controller;

import org.kwakmunsu.flowmate.domain.challenge.controller.docs.ChallengeDocsController;
import org.kwakmunsu.flowmate.domain.challenge.controller.dto.ChallengeApplyRequest;
import org.kwakmunsu.flowmate.domain.challenge.controller.dto.ChallengeCreateRequest;
import org.kwakmunsu.flowmate.domain.challenge.entity.ChallengeListType;
import org.kwakmunsu.flowmate.domain.challenge.entity.SortBy;
import org.kwakmunsu.flowmate.domain.challenge.service.dto.challenge.ChallengeDetailResponse;
import org.kwakmunsu.flowmate.domain.challenge.service.dto.challenge.ChallengeListResponse;
import org.kwakmunsu.flowmate.domain.member.entity.InterestCategory;
import org.kwakmunsu.flowmate.global.annotation.AuthMember;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/challenges")
@RestController
public class ChallengeController extends ChallengeDocsController {

    @Override
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody ChallengeCreateRequest request, @AuthMember Long memberId) {
        return null;
    }

    @Override
    @PostMapping("/apply")
    public ResponseEntity<Void> apply(@RequestBody ChallengeApplyRequest request, @AuthMember Long memberId) {
        return null;
    }

    @Override
    @GetMapping
    public ResponseEntity<ChallengeListResponse> readAll(
            @AuthMember Long memberId,
            @RequestParam(required = false) String q,
            @RequestParam(required = false, defaultValue = "NEWEST") SortBy sortBy,
            @RequestParam(required = false) InterestCategory interestCategory,
            @RequestParam(required = false, defaultValue = "RECRUITING") ChallengeListType challengeListType,
            @RequestParam(required = false) Long lastChallengeId
    ) {
        return null;
    }

    @Override
    @GetMapping("/{challengeId}")
    public ResponseEntity<ChallengeDetailResponse> read(@PathVariable Long challengeId, @AuthMember Long memberId) {
        return null;
    }

    @Override
    @PatchMapping("/{challengeId}")
    public ResponseEntity<Void> update(@PathVariable Long challengeId, @AuthMember Long memberId) {
        return null;
    }

    @Override
    @DeleteMapping("/{challengeId}")
    public ResponseEntity<Void> delete(@PathVariable Long challengeId, @AuthMember Long memberId) {
        return null;
    }
}
