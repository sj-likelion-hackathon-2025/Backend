package org.kwakmunsu.flowmate.domain.challenge.controller;

import org.kwakmunsu.flowmate.domain.challenge.controller.docs.ChallengeCertificationDocsController;
import org.kwakmunsu.flowmate.domain.challenge.controller.dto.CertificationStatusRequest;
import org.kwakmunsu.flowmate.domain.challenge.service.dto.challengecertification.ChallengeCertificationDetailResponse;
import org.kwakmunsu.flowmate.domain.challenge.service.dto.challengecertification.ChallengeCertificationPreviewResponse;
import org.kwakmunsu.flowmate.global.annotation.AuthMember;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/challenges")
@RestController
public class ChallengeCertificationController extends ChallengeCertificationDocsController {

    @Override
    @PostMapping("/{challengeId}/certifications")
    public ResponseEntity<Void> submit(
            @PathVariable Long challengeId,
            @RequestPart("image") MultipartFile image,
            @AuthMember Long memberId
    ) {
        return null;
    }

    @Override
    @GetMapping("/certifications/{certificationId}")
    public ResponseEntity<ChallengeCertificationDetailResponse> read(@PathVariable Long certificationId, @AuthMember Long memberId) {
        return null;
    }

    @Override
    @PostMapping("/{certificationId}/approval")
    public ResponseEntity<Void> approval(
            @PathVariable Long certificationId,
            @RequestBody CertificationStatusRequest status,
            @AuthMember Long memberId
    ) {
        return null;
    }

    @Override
    @GetMapping("/{challengeId}/certifications/members")
    public ResponseEntity<ChallengeCertificationPreviewResponse> readAllByMember(
            @PathVariable Long challengeId,
            @AuthMember Long memberId
    ) {
        return null;
    }
}
