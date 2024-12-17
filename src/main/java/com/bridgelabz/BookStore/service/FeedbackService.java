package com.bridgelabz.BookStore.service;

import com.bridgelabz.BookStore.dto.FeedbackRequestDTO;
import com.bridgelabz.BookStore.dto.FeedbackResponseDTO;

import java.util.List;

public interface FeedbackService {
    FeedbackResponseDTO addFeedback(Long userId, FeedbackRequestDTO feedbackDTO);

    List<FeedbackResponseDTO> getAllFeedbacks();
}
