package com.zerobase.Store_Table_Reservation.member.repository;

import com.zerobase.Store_Table_Reservation.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {

}
