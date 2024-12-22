package com.zerobase.Store_Table_Reservation.member.service;

import com.zerobase.Store_Table_Reservation.exception.customException.MemberNotFoundException;
import com.zerobase.Store_Table_Reservation.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findByMemberId(username).orElseThrow(
                ()-> new MemberNotFoundException("존재하지 않는 사용자입니다.")
        );
    }
}
