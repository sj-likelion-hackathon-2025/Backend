package org.kwakmunsu.flowmate.domain.member.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.kwakmunsu.flowmate.domain.member.controller.dto.MemberCategoryRegisterRequest;
import org.kwakmunsu.flowmate.domain.member.controller.dto.MemberProfileRequest;
import org.kwakmunsu.flowmate.domain.member.service.MemberCommandService;
import org.kwakmunsu.flowmate.domain.member.service.MemberQueryService;
import org.kwakmunsu.flowmate.domain.member.service.dto.MemberInfoResponse;
import org.kwakmunsu.flowmate.global.annotation.AuthMember;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/members")
@RequiredArgsConstructor
@RestController
public class MemberController extends MemberDocsController {

    private final MemberCommandService memberCommandService;
    private final MemberQueryService memberQueryService;

    @Override
    @GetMapping
    public ResponseEntity<MemberInfoResponse> getProfile(@AuthMember Long memberId) {
        return null;
    }

    @Override
    @PostMapping
    public ResponseEntity<Void> register(
            @Valid @RequestPart MemberProfileRequest request,
            @RequestPart(required = false) MultipartFile image,
            @AuthMember Long memberId
    ) {
        memberCommandService.updateProfile(request.toServiceRequest(memberId, image));

        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping("/check-name")
    public ResponseEntity<Void> checkDuplicateName(@RequestParam String name) {
        memberQueryService.checkDuplicateName(name);

        return ResponseEntity.ok().build();
    }

    @Override
    @PostMapping("/categories")
    public ResponseEntity<Void> registerCategory(@RequestBody MemberCategoryRegisterRequest request, @AuthMember Long memberId) {
        return null;
    }


}