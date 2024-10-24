package com.petwellness.api;

import com.petwellness.dto.UserProfileDTO;
import com.petwellness.dto.UserRegistroDTO;
import com.petwellness.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/register/customer")
    public ResponseEntity<UserProfileDTO> registerCustomer(@Valid @RequestBody UserRegistroDTO userRegistroDTO) {
        UserProfileDTO userProfileDTO = userService.registerUser(userRegistroDTO);
        return new ResponseEntity<>(userProfileDTO, HttpStatus.CREATED);
    }

    @PostMapping("/register/vet")
    public ResponseEntity<UserProfileDTO> registerVet(@Valid @RequestBody UserRegistroDTO userRegistroDTO) {
        UserProfileDTO userProfileDTO = userService.registerVet(userRegistroDTO);
        return new ResponseEntity<>(userProfileDTO, HttpStatus.CREATED);
    }
}