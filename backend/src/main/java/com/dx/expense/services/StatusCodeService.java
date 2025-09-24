package com.dx.expense.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dx.expense.dto.ErrorResponseDTO;
import com.dx.expense.entities.StatusCode;
import com.dx.expense.repository.StatusCodeRepository;

@Service
public class StatusCodeService {
    private final StatusCodeRepository statusRepository;

    @Autowired
    public StatusCodeService(StatusCodeRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    public ErrorResponseDTO createErrorResponse(String code) {
        StatusCode statusCode = statusRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Código de status não encontrado: " + code));

        return new ErrorResponseDTO(
                statusCode.getCode(),
                statusCode.getMessage());
    }
}
