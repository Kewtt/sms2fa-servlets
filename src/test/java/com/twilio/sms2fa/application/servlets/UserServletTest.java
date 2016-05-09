package com.twilio.sms2fa.application.servlets;

import com.twilio.sms2fa.domain.model.User;
import com.twilio.sms2fa.infrastructure.repository.UserInMemoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.when;

public class UserServletTest {

    private UserServlet userServlet;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    @Mock
    private HttpServletResponse response;

    @Before
    public void setUp(){
        this.userServlet = new UserServlet(new UserInMemoryRepository());

        initMocks(this);
        when(request.getSession()).thenReturn(session);
        when(request.getAttribute("first_name")).thenReturn("Foo");
        when(request.getAttribute("last_name")).thenReturn("Bar");
        when(request.getAttribute("email")).thenReturn("foo@bar.com");
        when(request.getAttribute("phone_number")).thenReturn("+111321321321321312");
        when(request.getAttribute("password")).thenReturn("foo@123");
    }

    @Test
    public void itShouldAddNewUserToSessionWhenPost() throws ServletException, IOException {
        //when
        userServlet.doPost(request, response);

        //then
        verify(session).setAttribute(eq("user"), any(User.class));
    }
}