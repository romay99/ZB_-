package com.zerobase.Store_Table_Reservation.member.service;

import com.zerobase.Store_Table_Reservation.exception.customException.IdAlreadyUsedException;
import com.zerobase.Store_Table_Reservation.exception.customException.IdNotNullException;
import com.zerobase.Store_Table_Reservation.exception.customException.MemberNotFoundException;
import com.zerobase.Store_Table_Reservation.exception.customException.MemberPasswordNotMatchException;
import com.zerobase.Store_Table_Reservation.jwt.JwtUtil;
import com.zerobase.Store_Table_Reservation.member.dto.request.MemberDto;
import com.zerobase.Store_Table_Reservation.member.dto.request.MemberLoginDto;
import com.zerobase.Store_Table_Reservation.member.dto.request.MemberSignUpDto;
import com.zerobase.Store_Table_Reservation.member.entity.Member;
import com.zerobase.Store_Table_Reservation.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findByMemberId(username)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 사용자 입니다."));
    }

    /**
     * 회원가입 하는 메서드
     * @param dto 회원가입을 위한 DTO
     * @return 저장된 MemberEntity 리턴
     */
    public Member signUpMember(MemberSignUpDto dto) {
        // ID 값이 존재하지 않는다면 예외 발생
        if (dto.getMemberId() == null) {
            throw new IdNotNullException("ID 값은 null 일 수 없습니다.");
        }
        // 이미 존재하는 ID 라면 예외 발생
        if (memberRepository.existsByMemberId(dto.getMemberId())) {
            throw new IdAlreadyUsedException("이미 존재하는 ID 입니다.");
        }
        //비밀번호 인코드
        dto.setMemberPassword(passwordEncoder.encode(dto.getMemberPassword()));

        // 저장된 MemberEntity 를 return
        // MemberSignUpDto 의 partner 변수로 점장 이용자인지 일반 이용자인지 구분한다.
        return memberRepository.save(MemberSignUpDto.toEntity(dto));
    }


    /**
     * 로그인 하는 메서드
     * @param dto 회원의 ID 와 비밀번호를 담은 DTO
     * @return 정상적으로 로그인 성공시 JWT 응답
     */
    public String longinMember(MemberLoginDto dto) {
        // ID 값으로 멤버를 찾는다. 존재하지 않는다면 예외 발생
        Member member = memberRepository.findByMemberId(dto.getUsername()).orElseThrow(
                () -> new MemberNotFoundException("존재하지 않는 회원정보 입니다.")
        );

        // 비밀번호가 일치하지 않으면 예외 발생
        if (!passwordEncoder.matches(dto.getPassword(),member.getPassword())) {
            throw new MemberPasswordNotMatchException("유효하지 않은 비밀번호 입니다.");
        }

        // 토큰 생성
        return jwtUtil.createAccessToken(
                MemberDto.builder()
                        .memberId(member.getMemberId())
                        .role(member.getRole())
                        .build()
        );
    }

}
