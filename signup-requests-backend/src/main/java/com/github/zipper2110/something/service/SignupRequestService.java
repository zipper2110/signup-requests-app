package com.github.zipper2110.something.service;

import com.github.zipper2110.something.entity.SignupRequest;
import com.github.zipper2110.something.mailing.SendMailer;
import com.github.zipper2110.something.messaging.MessageId;
import com.github.zipper2110.something.messaging.MessagingService;
import com.github.zipper2110.something.repository.SignupRequestRepository;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.jetbrains.annotations.NotNull;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Service;

import static com.github.zipper2110.something.entity.SignupRequest.Status.*;

@Service
@Slf4j
public class SignupRequestService {

    private SignupRequestRepository repository;
    private MessagingService messagingService;
    private SendMailer mailer;

    public SignupRequestService(SignupRequestRepository repository,
                                MessagingService messagingService,
                                SendMailer mailer) {
        this.repository = repository;
        this.messagingService = messagingService;
        this.mailer = mailer;
    }

    public SignupRequest create(@NotNull SignupRequest signupRequest) {
        signupRequest.setStatus(WAITING_FOR_APPROVAL);
        val created = repository.save(signupRequest);
        sendToApproval(created);
        return created;
    }

    public void approve(@NotNull String signupRequestId) {
        udpateStatus(signupRequestId, APPROVED);
    }

    public void reject(@NotNull String signupRequestId) {
        udpateStatus(signupRequestId, REJECTED);
    }

    private void udpateStatus(String signupRequestId, SignupRequest.Status status) {
        SignupRequest existingRequest = getById(signupRequestId);
        existingRequest.setStatus(status);
        sendStatusChangeNotification(existingRequest);
    }

    /**
     * @return signup request, if any was found
     * @throws IllegalArgumentException if request with provided id does not exist
     */
    private SignupRequest getById(String signupRequestId) {
        return repository.findById(signupRequestId)
                .orElseThrow(() -> new IllegalArgumentException("Request with id " + signupRequestId + " was not found"));
    }

    private void sendToApproval(@NotNull SignupRequest signupRequest) {
        MessageId sentMessageId = messagingService.send(new GenericMessage<>(signupRequest));
        log.info("Signup request {} was sent for approval", signupRequest);

        // TODO: receive in another thread with retry. If failed, then we would find pending stuff in DB and send it again
        Message<SignupRequest> approvalResponse = messagingService.receive(sentMessageId, SignupRequest.class);
        SignupRequest requestFromApproval = approvalResponse.getPayload();
        String id = requestFromApproval.getId();
        SignupRequest.Status updatedStatus = requestFromApproval.getStatus();
        udpateStatus(id, updatedStatus);
    }

    private void sendStatusChangeNotification(SignupRequest signupRequest) {
        // TODO: do in another thread with retry. If failed, then we would find pending stuff in DB and send it again
        mailer.sendMail(signupRequest.getEmailAddress(), new SendMailer.EmailContent(signupRequest.getStatus().name()));
    }
}
