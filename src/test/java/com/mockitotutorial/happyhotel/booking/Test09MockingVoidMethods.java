package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.*;

public class Test09MockingVoidMethods {

    private BookingService bookingService;
    private PaymentService paymentServiceMock;
    private RoomService roomServiceMock;
    private BookingDAO bookingDAOMock;
    private MailSender mailSenderMock;

    @BeforeEach
    void setUp(){
        paymentServiceMock = mock(PaymentService.class);
        roomServiceMock = mock(RoomService.class);
        bookingDAOMock = mock(BookingDAO.class);
        mailSenderMock = mock(MailSender.class);
        bookingService = new BookingService(paymentServiceMock,roomServiceMock, bookingDAOMock, mailSenderMock);
    }

    @Test
    void shouldThrowExceptionWhenMailNotReady(){
        //Given
        BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2022, 01 ,01),
                LocalDate.of(2022, 01, 05) , 2, false);

        doThrow(new BusinessException()).when(mailSenderMock).sendBookingConfirmation(any());
        //When
        Executable executable = () -> bookingService.makeBooking(bookingRequest);
        //Then
        assertThrows(BusinessException.class, executable);
    }

    @Test
    void shouldNotThrowExceptionWhenMailNotReady(){
        //Given
        BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2022, 01 ,01),
                LocalDate.of(2022, 01, 05) , 2, false);

        doNothing().when(mailSenderMock).sendBookingConfirmation(any());
        //When
        bookingService.makeBooking(bookingRequest);
        //Then
        // No exception is thown
    }
}
