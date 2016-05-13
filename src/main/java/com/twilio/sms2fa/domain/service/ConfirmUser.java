package com.twilio.sms2fa.domain.service;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import com.twilio.sms2fa.domain.model.User;
import com.twilio.sms2fa.domain.repository.UserRepository;

public class ConfirmUser {

    private UserRepository userRepository;

    @Inject
    public ConfirmUser(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void confirm(final User user, final String verificationCode) {
        user.confirm(verificationCode);
        userRepository.save(user);
    }
}
