package com.zerobase.Store_Table_Reservation.review.controller;

import com.zerobase.Store_Table_Reservation.review.dto.request.ReviewModifyDto;
import com.zerobase.Store_Table_Reservation.review.dto.request.ReviewPostDto;
import com.zerobase.Store_Table_Reservation.review.dto.response.ReviewDetailResponse;
import com.zerobase.Store_Table_Reservation.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;

    /**
     * 리뷰 작성하기
     */
    @PostMapping("/")
    // 리뷰 작성은 이용자 계정으로만 가능
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<ReviewDetailResponse> writeReview(@RequestBody ReviewPostDto dto) {
        // SecurityContextHolder 에서 인증된 사용자의 ID 를 가져온다.
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        ReviewDetailResponse response = reviewService.writeReview(dto, username);
        return ResponseEntity.ok(response);
    }

    /**
     * 리뷰 수정하기
     */
    @PutMapping("/")
    // 리뷰 수정은 이용자 계정으로만 가능
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<ReviewDetailResponse> modifyReview(@RequestBody ReviewModifyDto dto) {
        // SecurityContextHolder 에서 인증된 사용자의 ID 를 가져온다.
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        ReviewDetailResponse response = reviewService.modifyReview(dto, username);
        return ResponseEntity.ok(response);
    }

    /**
     * 리뷰 삭제하기
     */
    @DeleteMapping("/")
    // 리뷰 삭제는 작성자와 매점 점장 둘다 가능
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_PARTNER')")
    public ResponseEntity<String> deleteReview(@RequestParam Long reviewCode) {
        // SecurityContextHolder 에서 인증된 사용자의 ID 를 가져온다.
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        reviewService.deleteReview(reviewCode, username);
        return ResponseEntity.ok().body("리뷰 삭제가 완료되었습니다.");
    }
}
