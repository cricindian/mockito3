package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class Test08ArguementCaptor {

    private BookingService bookingService;
    private PaymentService paymentServiceMock;
    private RoomService roomServiceMock;
    private BookingDAO bookingDAOMock;
    private MailSender mailSenderMock;
    private ArgumentCaptor<Double> doubleCaptor;

    @BeforeEach
    void setUp(){
        paymentServiceMock = mock(PaymentService.class);
        roomServiceMock = mock(RoomService.class);
        bookingDAOMock = mock(BookingDAO.class);
        mailSenderMock = mock(MailSender.class);
        bookingService = new BookingService(paymentServiceMock,roomServiceMock, bookingDAOMock, mailSenderMock);
        doubleCaptor = ArgumentCaptor.forClass(Double.class);
    }

    @Test
    void shouldPayCorrectPriceWhenInputOk(){
        //Given
        BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2022, 01 ,01),
                LocalDate.of(2022, 01, 05) , 2, true);
        //When
        bookingService.makeBooking(bookingRequest);
        //Then
        verify(paymentServiceMock,times(1)).pay(eq(bookingRequest),doubleCaptor.capture());
        double expected = doubleCaptor.getValue();
        assertEquals(400.0, expected);
    }

    @Test
    void shouldPayCorrectPriceWhenMultipleCalls(){
        //Given
        BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2022, 01 ,01),
                LocalDate.of(2022, 01, 05) , 2, true);
        BookingRequest bookingRequest1 = new BookingRequest("1", LocalDate.of(2022, 01 ,01),
                LocalDate.of(2022, 01, 02) , 2, true);

        List<Double> expected = Arrays.asList(400.0,100.0);
        //When
        bookingService.makeBooking(bookingRequest);
        bookingService.makeBooking(bookingRequest1);
        //Then
        verify(paymentServiceMock,times(2)).pay(any(),doubleCaptor.capture());
        List<Double> actual = doubleCaptor.getAllValues();
        assertEquals(expected, actual);
    }
}

