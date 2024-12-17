package com.bridgelabz.BookStore.controller;

import com.bridgelabz.BookStore.dto.FeedbackRequestDTO;
import com.bridgelabz.BookStore.dto.FeedbackResponseDTO;
import com.bridgelabz.BookStore.service.FeedbackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/filter")
public class SecureFeedbackController {
    @Autowired
    private FeedbackService feedbackService;

   @PostMapping("/addfeedback")
   public ResponseEntity<FeedbackResponseDTO> addFeedback(@RequestBody FeedbackRequestDTO feedbackRequestDTO, @RequestAttribute("id") Long userId, @RequestAttribute("role") String role) {
       if("ADMIN".equalsIgnoreCase(role)) {
           throw new RuntimeException("Admin cant add feedback");
       }
       return new ResponseEntity<>(feedbackService.addFeedback(userId, feedbackRequestDTO), HttpStatus.OK);
   }

    @GetMapping("/getfeedback")
    public ResponseEntity<List<FeedbackResponseDTO>> getAllFeedback() {
        return new ResponseEntity<>(feedbackService.getAllFeedbacks(), HttpStatus.OK);

    }
}
