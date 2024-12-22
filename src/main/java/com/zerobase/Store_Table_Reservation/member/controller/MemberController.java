package com.zerobase.Store_Table_Reservation.member.controller;

import com.zerobase.Store_Table_Reservation.member.dto.request.MemberSignUpDto;
import com.zerobase.Store_Table_Reservation.member.entity.Member;
import com.zerobase.Store_Table_Reservation.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        System.out.println(dto);
        // memberService 에서 저장된 member를 return
        Member member = memberService.signUpMember(dto);
        return ResponseEntity.ok().body(member);
    }
}
