package com.helpeachother.secretcode.user.service;

import com.helpeachother.secretcode.common.model.ServiceResult;
import com.helpeachother.secretcode.user.model.UserPointInput;

public interface PointService {

    ServiceResult addPoint(String email, UserPointInput userPointInput);
}
