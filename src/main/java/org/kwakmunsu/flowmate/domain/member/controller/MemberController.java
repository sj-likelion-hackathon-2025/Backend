package org.kwakmunsu.flowmate.domain.member.controller;

import org.kwakmunsu.flowmate.domain.member.controller.dto.MemberCategoryRegisterRequest;
import org.kwakmunsu.flowmate.domain.member.controller.dto.MemberProfileRequest;
import org.kwakmunsu.flowmate.domain.member.service.MemberInfoResponse;
import org.kwakmunsu.flowmate.global.annotation.AuthMember;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/members")
@RestController
public class MemberController extends MemberDocsController {

    @Override
    @GetMapping
    public ResponseEntity<MemberInfoResponse> getProfile(@AuthMember Long memberId) {
        return null;
    }

    @Override
    @PostMapping
    public ResponseEntity<Void> register(@RequestBody MemberProfileRequest request, @AuthMember Long memberId) {
        return null;
    }

    @Override
    @GetMapping("/check-name")
    public ResponseEntity<Void> checkDuplicateName(@RequestParam String name, @AuthMember Long memberId) {
        return null;
    }

    @Override
    @PostMapping("/categories")
    public ResponseEntity<Void> registerCategory(@RequestBody MemberCategoryRegisterRequest request, @AuthMember Long memberId) {
        return null;
    }


}