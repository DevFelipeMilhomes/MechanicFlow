package com.devfelipemilhomes.appUser;

import com.devfelipemilhomes.appUser.dto.CurrentUserResponse;
import com.devfelipemilhomes.security.CustomOidcUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/me")
    public CurrentUserResponse currentUser(
            @AuthenticationPrincipal
            CustomOidcUser principal) {

        return new CurrentUserResponse(
                principal.getAppUserId(),
                principal.getProfessionalId(),
                principal.getRoleNames()
        );
    }
}
