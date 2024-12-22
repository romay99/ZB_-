package com.zerobase.Store_Table_Reservation.member.service;

import com.zerobase.Store_Table_Reservation.exception.customException.IdAlreadyUsedException;
import com.zerobase.Store_Table_Reservation.exception.customException.IdNotNullException;
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
        return memberRepository.save(MemberSignUpDto.toEntity(dto));
    }



}
