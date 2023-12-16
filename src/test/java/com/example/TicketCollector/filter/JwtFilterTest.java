package com.example.TicketCollector.filter;

import com.example.TicketCollector.util.JwtUtil;
import org.apache.catalina.connector.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.DelegatingServletInputStream;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import javax.servlet.*;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {JwtFilter.class})
@WebAppConfiguration
@ExtendWith(SpringExtension.class)
class JwtFilterTest {
    @Autowired
    private JwtFilter jwtFilter;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private UserDetailsService userDetailsService;


    @Test
    void testDoFilterInternal() throws IOException, ServletException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        Response response = new Response();
        FilterChain chain = mock(FilterChain.class);
        doNothing().when(chain).doFilter(Mockito.<ServletRequest>any(), Mockito.<ServletResponse>any());
        jwtFilter.doFilterInternal(request, response, chain);
        verify(chain).doFilter(Mockito.<ServletRequest>any(), Mockito.<ServletResponse>any());
        assertFalse(request.isRequestedSessionIdFromURL());
        assertTrue(request.isRequestedSessionIdFromCookie());
        assertFalse(request.isAsyncSupported());
        assertFalse(request.isAsyncStarted());
        assertTrue(request.isActive());
        assertTrue(request.getSession() instanceof MockHttpSession);
        assertEquals("", request.getServletPath());
        assertEquals(80, request.getServerPort());
        assertEquals("localhost", request.getServerName());
        assertEquals("http", request.getScheme());
        assertEquals("", request.getRequestURI());
        assertEquals(80, request.getRemotePort());
        assertEquals("localhost", request.getRemoteHost());
        assertEquals("HTTP/1.1", request.getProtocol());
        assertEquals("", request.getMethod());
        assertEquals(80, request.getLocalPort());
        assertEquals("localhost", request.getLocalName());
        assertTrue(request.getInputStream() instanceof DelegatingServletInputStream);
        assertEquals(DispatcherType.REQUEST, request.getDispatcherType());
        assertEquals("", request.getContextPath());
        assertEquals(-1L, request.getContentLengthLong());
    }

    @Test
    void testDoFilterInternal3() throws IOException, ServletException {
        DefaultMultipartHttpServletRequest request = mock(DefaultMultipartHttpServletRequest.class);
        when(request.getHeader(Mockito.<String>any())).thenReturn("https://example.org/example");
        Response response = new Response();
        FilterChain chain = mock(FilterChain.class);
        doNothing().when(chain).doFilter(Mockito.<ServletRequest>any(), Mockito.<ServletResponse>any());
        jwtFilter.doFilterInternal(request, response, chain);
        verify(request).getHeader(Mockito.<String>any());
        verify(chain).doFilter(Mockito.<ServletRequest>any(), Mockito.<ServletResponse>any());
    }

    @Test
    void testDoFilterInternal4() throws IOException, ServletException, UsernameNotFoundException {
        when(jwtUtil.validateToken(Mockito.<String>any(), Mockito.<UserDetails>any())).thenReturn(true);
        when(jwtUtil.getUsernameFromToken(Mockito.<String>any())).thenReturn("janedoe");
        when(userDetailsService.loadUserByUsername(Mockito.<String>any()))
                .thenReturn(new User("janedoe", "iloveyou", new ArrayList<>()));
        DefaultMultipartHttpServletRequest request = mock(DefaultMultipartHttpServletRequest.class);
        when(request.getRemoteAddr()).thenReturn("42 Main St");
        when(request.getSession(anyBoolean())).thenReturn(new MockHttpSession());
        when(request.getHeader(Mockito.<String>any())).thenReturn("Bearer ");
        Response response = new Response();
        FilterChain chain = mock(FilterChain.class);
        doNothing().when(chain).doFilter(Mockito.<ServletRequest>any(), Mockito.<ServletResponse>any());
        jwtFilter.doFilterInternal(request, response, chain);
        verify(jwtUtil).validateToken(Mockito.<String>any(), Mockito.<UserDetails>any());
        verify(jwtUtil).getUsernameFromToken(Mockito.<String>any());
        verify(userDetailsService).loadUserByUsername(Mockito.<String>any());
        verify(request).getRemoteAddr();
        verify(request).getHeader(Mockito.<String>any());
        verify(request).getSession(anyBoolean());
        verify(chain).doFilter(Mockito.<ServletRequest>any(), Mockito.<ServletResponse>any());
    }

    @Test
    void testDoFilterInternal6() throws IOException, ServletException, UsernameNotFoundException {
        when(jwtUtil.validateToken(Mockito.<String>any(), Mockito.<UserDetails>any())).thenReturn(false);
        when(jwtUtil.getUsernameFromToken(Mockito.<String>any())).thenReturn("janedoe");
        when(userDetailsService.loadUserByUsername(Mockito.<String>any()))
                .thenReturn(new User("janedoe", "iloveyou", new ArrayList<>()));
        DefaultMultipartHttpServletRequest request = mock(DefaultMultipartHttpServletRequest.class);
        when(request.getRemoteAddr()).thenReturn("42 Main St");
        when(request.getSession(anyBoolean())).thenReturn(new MockHttpSession());
        when(request.getHeader(Mockito.<String>any())).thenReturn("Bearer ");
        Response response = new Response();
        FilterChain chain = mock(FilterChain.class);
        doNothing().when(chain).doFilter(Mockito.<ServletRequest>any(), Mockito.<ServletResponse>any());
        jwtFilter.doFilterInternal(request, response, chain);
        verify(jwtUtil).validateToken(Mockito.<String>any(), Mockito.<UserDetails>any());
        verify(jwtUtil).getUsernameFromToken(Mockito.<String>any());
        verify(userDetailsService).loadUserByUsername(Mockito.<String>any());
        verify(request).getHeader(Mockito.<String>any());
        verify(chain).doFilter(Mockito.<ServletRequest>any(), Mockito.<ServletResponse>any());
    }

}


