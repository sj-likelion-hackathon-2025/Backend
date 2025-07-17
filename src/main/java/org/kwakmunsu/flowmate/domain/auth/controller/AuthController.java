package org.kwakmunsu.flowmate.domain.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.kwakmunsu.flowmate.domain.auth.controller.dto.ReissueTokenRequest;
import org.kwakmunsu.flowmate.domain.auth.service.AuthService;
import org.kwakmunsu.flowmate.global.jwt.dto.TokenResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AuthController extends AuthDocsController {

    private final AuthService authService;

    @Override
    @PostMapping("/auth/reissue")
    public ResponseEntity<TokenResponse> reissue(@Valid @RequestBody ReissueTokenRequest request) {
        TokenResponse response = authService.reissue(request.toServiceRequest());

        return ResponseEntity.ok(response);
    }

}