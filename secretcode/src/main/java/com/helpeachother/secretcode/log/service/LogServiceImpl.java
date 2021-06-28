package com.helpeachother.secretcode.log.service;

import com.helpeachother.secretcode.log.entity.Logs;
import com.helpeachother.secretcode.log.repository.LogsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class LogServiceImpl implements LogService {

    private final LogsRepository logsRepository;

    @Override
    public void add(String text) {

        logsRepository.save(Logs.builder()
                .text(text)
                .regDate(LocalDateTime.now())
                .build());
    }
}
