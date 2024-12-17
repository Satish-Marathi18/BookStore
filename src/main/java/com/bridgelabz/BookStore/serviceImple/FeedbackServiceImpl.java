package com.bridgelabz.BookStore.serviceImple;

import com.bridgelabz.BookStore.dto.FeedbackRequestDTO;
import com.bridgelabz.BookStore.dto.FeedbackResponseDTO;
import com.bridgelabz.BookStore.entity.Book;
import com.bridgelabz.BookStore.entity.Feedback;
import com.bridgelabz.BookStore.entity.User;
import com.bridgelabz.BookStore.mapper.Mapper;
import com.bridgelabz.BookStore.repo.BookRepo;
import com.bridgelabz.BookStore.repo.FeedbackRepository;
import com.bridgelabz.BookStore.repo.UserRepository;
import com.bridgelabz.BookStore.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class FeedbackServiceImpl implements FeedbackService {
    @Autowired
    FeedbackRepository feedbackRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepo bookRepo;


    @Override
    public FeedbackResponseDTO addFeedback(Long userId, FeedbackRequestDTO feedbackDTO) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Book book = bookRepo.findById(feedbackDTO.getBookId()).orElseThrow(() -> new IllegalArgumentException("Book not found"));
        Feedback feedback = feedbackRepository.findByBookAndUser(book, user).orElse(null);
        if(!Objects.isNull(feedback)) {
            throw new IllegalArgumentException("Feedback already exists");
        }
        feedback = new Feedback();
        feedback.setUser(user);
        feedback.setBook(book);
        feedback.setFeedback(feedbackDTO.getFeedback());
        if(feedbackDTO.getRating() < 0 || feedbackDTO.getRating()>5){
            throw new IllegalArgumentException("Rating must be between 0 and 5");
        }
        feedback.setRating(feedbackDTO.getRating());
        feedback = feedbackRepository.save(feedback);
        return mapFeedbackToDTO(feedback);
    }

    @Override
    public List<FeedbackResponseDTO> getAllFeedbacks() {
        List<Feedback> feedbackList = feedbackRepository.findAll();
        return feedbackList.stream().map(this::mapFeedbackToDTO).toList();
    }

    private FeedbackResponseDTO mapFeedbackToDTO(Feedback save) {
        FeedbackResponseDTO feedbackResponseDTO = new FeedbackResponseDTO();
        feedbackResponseDTO.setFeedback(save.getFeedback());
        feedbackResponseDTO.setRating(save.getRating());
        feedbackResponseDTO.setBookResponseDTO(Mapper.mapBookToResponseDTO(save.getBook()));
        feedbackResponseDTO.setUserResponseDTO(Mapper.mapUserToResponseDTO(save.getUser()));
        return feedbackResponseDTO;
    }
}
