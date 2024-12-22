package com.zerobase.Store_Table_Reservation.member.controller;

import com.zerobase.Store_Table_Reservation.member.dto.request.MemberLoginDto;
import com.zerobase.Store_Table_Reservation.member.dto.request.MemberSignUpDto;
import com.zerobase.Store_Table_Reservation.member.entity.Member;
import com.zerobase.Store_Table_Reservation.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    /**
     * 회원가입 하는 메서드
     */
    @PostMapping("/signup")
    public ResponseEntity<Member> memberSignup(@RequestBody MemberSignUpDto dto) {
        // memberService 에서 저장된 member를 return
        Member member = memberService.signUpMember(dto);
        return ResponseEntity.ok().body(member);
    }

    /**
     * 로그인 하는 메서드
     */
    @PostMapping("/login")
    public ResponseEntity<String> memberLogin(@RequestBody MemberLoginDto dto) {
        String token = memberService.longinMember(dto);
        return ResponseEntity.ok().body(token);
    }

    @GetMapping("/test")
    @PreAuthorize("hasRole('ROLE_PARTNER')")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok().build();
    }
}
