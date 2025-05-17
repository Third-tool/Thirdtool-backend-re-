package com.Thethirdtool.backend.common.validation;

import com.Thethirdtool.backend.common.annotation.AuthenticatedUserMatch;

@AuthenticatedUserMatch
public class AuthValidationDto {
    private final Long pathUserId;
    private final Long authenticatedUserId;

    public AuthValidationDto(Long pathUserId, Long authenticatedUserId) {
        this.pathUserId = pathUserId;
        this.authenticatedUserId = authenticatedUserId;
    }

    public Long getPathUserId() {
        return pathUserId;
    }

    public Long getAuthenticatedUserId() {
        return authenticatedUserId;
    }
}