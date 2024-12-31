package com.zerobase.Store_Table_Reservation.review.controller;

import com.zerobase.Store_Table_Reservation.review.dto.request.ReviewPostDto;
import com.zerobase.Store_Table_Reservation.review.dto.response.ReviewDetailResponse;
import com.zerobase.Store_Table_Reservation.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/write")
    // 리뷰 작성은 이용자 계정으로만 가능
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<ReviewDetailResponse> writeReview(@RequestBody ReviewPostDto dto) {
        // SecurityContextHolder 에서 인증된 사용자의 ID 를 가져온다.
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        ReviewDetailResponse response = reviewService.writeReview(dto, username);
        return ResponseEntity.ok(response);
    }
}
