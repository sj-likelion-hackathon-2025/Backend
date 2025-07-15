package org.kwakmunsu.flowmate.domain.auth.controller;

import org.kwakmunsu.flowmate.domain.auth.controller.dto.ReissueTokenRequest;
import org.kwakmunsu.flowmate.global.jwt.dto.TokenResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController extends AuthDocsController {

    @Override
    @PostMapping("/auth/reissue")
    public ResponseEntity<TokenResponse> reissue(@RequestBody ReissueTokenRequest request) {
        return null;
    }

}
