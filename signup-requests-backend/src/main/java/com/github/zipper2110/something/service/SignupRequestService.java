package com.github.zipper2110.something.service;

import com.github.zipper2110.something.entity.SignupRequest;
import com.github.zipper2110.something.repository.SignupRequestRepository;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import static com.github.zipper2110.something.entity.SignupRequest.Status.WAITING_FOR_APPROVAL;

@Service
@Slf4j
public class SignupRequestService {

    private SignupRequestRepository repository;
    private ApprovalService approvalService;
    private NotificationService notificationService;


    public SignupRequestService(SignupRequestRepository repository,
                                ApprovalService approvalService,
                                NotificationService notificationService) {
        this.repository = repository;
        this.approvalService = approvalService;
        this.notificationService = notificationService;
    }

    public SignupRequest create(@NotNull SignupRequest signupRequest) {
        signupRequest.setStatus(WAITING_FOR_APPROVAL);
        val created = repository.save(signupRequest);
        sendForApproval(created);
        return created;
    }

    public SignupRequest.Status getStatus(String signupRequestId) {
        return getById(signupRequestId).getStatus();
    }

    private void updateStatus(SignupRequest signupRequest) {
        updateStatus(signupRequest.getId(), signupRequest.getStatus());
    }

    private void updateStatus(String signupRequestId, SignupRequest.Status status) {
        SignupRequest existingRequest = getById(signupRequestId);
        existingRequest.setStatus(status);
        notifyUserOnStatusChange(existingRequest);
    }

    /**
     * @return signup request, if any was found
     * @throws IllegalArgumentException if request with provided id does not exist
     */
    private SignupRequest getById(String signupRequestId) {
        return repository.findById(signupRequestId)
                .orElseThrow(() -> new IllegalArgumentException("Request with id " + signupRequestId + " was not found"));
    }

    private void sendForApproval(@NotNull SignupRequest signupRequest) {
        approvalService.approve(signupRequest, response -> response.ifPresent(this::updateStatus));
    }

    private void notifyUserOnStatusChange(SignupRequest existingRequest) {
        notificationService.notifyOnStatusChange(existingRequest, signupRequest -> {
            // Тут используем
        });
    }
}
