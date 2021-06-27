package com.helpeachother.secretcode.user.service;

import com.helpeachother.secretcode.common.model.ServiceResult;
import com.helpeachother.secretcode.user.entity.User;
import com.helpeachother.secretcode.user.entity.UserPoint;
import com.helpeachother.secretcode.user.model.UserPointInput;
import com.helpeachother.secretcode.user.model.UserPointType;
import com.helpeachother.secretcode.user.repository.UserPointRepository;
import com.helpeachother.secretcode.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PointServiceImpl implements PointService {

    private final UserPointRepository userPointRepository;
    private final UserRepository userRepository;

    @Override
    public ServiceResult addPoint(String email, UserPointInput userPointInput) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(!optionalUser.isPresent()) {
            return ServiceResult.fail("회원 정보가 존재하지 않습니다.");
        }
        User user = optionalUser.get();

        UserPoint userPoint = UserPoint.builder()
                .user(user)
                .userPointType(userPointInput.getUserPointType())
                .point(userPointInput.getUserPointType().getValue())
                .build();

        userPointRepository.save(userPoint);

        return ServiceResult.success();
    }
}
