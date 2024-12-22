package com.zerobase.Store_Table_Reservation.store.controller;

import com.zerobase.Store_Table_Reservation.store.dto.request.StoreUploadDto;
import com.zerobase.Store_Table_Reservation.store.entity.Store;
import com.zerobase.Store_Table_Reservation.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;

    /**
     * 가게 업로드 하는 메서드. @PreAuthorize 어노테이션으로 인가작업 진행
     */
    @PostMapping("/upload")
    @PreAuthorize("hasRole('ROLE_PARTNER')") // 파트너 유저만 이 메서드를 이용가능하다.
    public ResponseEntity<String> uploadStore(@RequestBody StoreUploadDto dto) {
        // SecurityContextHolder 에서 인증된 사용자의 ID 를 가져온다.
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Store store = storeService.uploadStore(dto, username);
        return ResponseEntity.ok().body(store.getName()+"가 정상적으로 등록되었습니다.");
    }
}
