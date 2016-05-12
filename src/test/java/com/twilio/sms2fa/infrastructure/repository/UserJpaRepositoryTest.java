package com.twilio.sms2fa.infrastructure.repository;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.twilio.sms2fa.domain.model.User;
import com.twilio.sms2fa.domain.model.UserBuilder;
import com.twilio.sms2fa.helper.IntegrationTestHelper;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

public class UserJpaRepositoryTest {

    private UserJpaRepository userJpaRepository;
    private IntegrationTestHelper integrationTestHelper;

    @Before
    public void setUp() throws ClassNotFoundException {
        Class.forName("com.twilio.sms2fa.domain.model.User");
        Class.forName("com.twilio.sms2fa.infrastructure.repository.UserJpaRepository");

        JpaPersistModule testPersistModule = new JpaPersistModule("jpa-sms2fa-test");
        Injector injector = Guice.createInjector(testPersistModule);
        PersistService instance = injector.getInstance(PersistService.class);
        instance.start();
        userJpaRepository = injector.getInstance(UserJpaRepository.class);
        integrationTestHelper = injector.getInstance(IntegrationTestHelper.class);
        integrationTestHelper.cleanTable(User.class);
    }

    @Test
    public void shouldCreateNewUser() {
        User user = new UserBuilder().build();
        user = userJpaRepository.save(user);

        assertThat(user.getId(), is(1L));
    }

    @Test
    public void shouldUpdateExistentUser() {
        User user = userJpaRepository.save(new UserBuilder().build());
        String oldCode = user.getVerificationCode();

        user.generateNewVerificationCode();
        userJpaRepository.save(user);

        User userFound = userJpaRepository.findById(user.getId());
        String newCode = userFound.getVerificationCode();
        assertThat(oldCode, is(not(newCode)));
    }

    @Test
    public void shouldFindUserById() {
        User user = new UserBuilder().build();
        user = userJpaRepository.save(user);

        User userFound = userJpaRepository.findById(user.getId());

        assertThat(userFound, is(user));
    }

    @Test
    public void shouldFindUserByEmail() {
        User user = new UserBuilder().withEmail("foo2@bar.com").build();
        user = userJpaRepository.save(user);

        User userFound = userJpaRepository.findByEmail("foo2@bar.com").get();

        assertThat(userFound.getId(), is(user.getId()));
    }

}
