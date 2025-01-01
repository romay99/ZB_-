package com.zerobase.Store_Table_Reservation.review.service;

import com.zerobase.Store_Table_Reservation.exception.customException.*;
import com.zerobase.Store_Table_Reservation.reservation.entity.Reservation;
import com.zerobase.Store_Table_Reservation.reservation.repository.ReservationRepository;
import com.zerobase.Store_Table_Reservation.review.dto.request.ReviewModifyDto;
import com.zerobase.Store_Table_Reservation.review.dto.request.ReviewPostDto;
import com.zerobase.Store_Table_Reservation.review.dto.response.ReviewDetailResponse;
import com.zerobase.Store_Table_Reservation.review.entity.Review;
import com.zerobase.Store_Table_Reservation.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReservationRepository reservationRepository;

    /**
     * 리뷰 쓰는 메서드
     */
    public ReviewDetailResponse writeReview(ReviewPostDto dto, String memberId) {
        // 예약 정보를 가져온다. 존재하지 않다면 예외발생
        Reservation reservation = reservationRepository.findById(dto.getReservationCode()).orElseThrow(
                () -> new ReservationNotFoundException("예약 정보가 존재하지 않습니다.")
        );

        // 예약자와 리뷰를 작성하려는 사람이 다르면 예외 발생
        if (!memberId.equals(reservation.getMember().getMemberId())) {
            throw new ReservationMemberNotMatchException("본인의 예약내역만 리뷰작성이 가능합니다.");
        }

        // 예약후 방문하지 않은 (노쇼) 예약에 대해선 리뷰작성 불가능
        if (!reservation.isVisited()) {
            throw new ReservationNoShowException("방문하지 않은 예약내역에는 리뷰작성이 불가능합니다.");
        }

        // 리뷰 Entity 저장
        Review review = reviewRepository.save(Review.builder()
                .content(dto.getContent())
                .rating(dto.getRating())
                .reservation(reservation)
                .memberId(reservation.getMember().getMemberId())
                .modified(false)
                .build());

        // DTO 로 변환후 반환
        return ReviewDetailResponse.builder()
                .reviewCode(review.getReviewCode())
                .content(review.getContent())
                .rating(review.getRating())
                .build();
    }

    /**
     * 리뷰 수정하는 메서드.
     */
    public ReviewDetailResponse modifyReview(ReviewModifyDto dto, String username) {
        // 리뷰 내용이 존재하지 않는다면 예외처리
        Review review = reviewRepository.findById(dto.getReviewCode()).orElseThrow(
                ()-> new ReviewNotFoundException("리뷰 내용이 존재하지 않습니다.")
        );

        // 본인의 리뷰가 아닐시 예외 처리
        if (!review.getMemberId().equals(username)) {
            throw new ReviewMemberNotMatchException("본인의 리뷰만 수정,삭제 가능합니다.");
        }

        // 리뷰 수정 내용 적용
        review.setRating(dto.getRating());
        review.setContent(dto.getContent());
        review.setModified(true);
        reviewRepository.save(review);

        // DTO 로 변환후 반환
        return ReviewDetailResponse.builder()
                .reviewCode(review.getReviewCode())
                .content(review.getContent())
                .rating(review.getRating())
                .build();

    }

    /**
     * 리뷰 삭제하는 메서드
     */
    public void deleteReview(Long reviewCode, String username) {
        // 리뷰 내용이 존재하지 않는다면 예외처리
        Review review = reviewRepository.findById(reviewCode).orElseThrow(
                ()-> new ReviewNotFoundException("리뷰 내용이 존재하지 않습니다.")
        );

        // 리뷰의 작성자가 아니거나, 해당 가게의 점장이 아니라면 예외 처리
        if (!review.getMemberId().equals(username) &&
                !review.getReservation().getStore().getMember().getMemberId().equals(username)) {
            throw new ReviewDeleteRejectedException("삭제 권한이 없습니다.");
        }
        reviewRepository.delete(review);
    }
}
