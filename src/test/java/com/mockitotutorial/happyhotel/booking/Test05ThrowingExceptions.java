package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class Test05ThrowingExceptions {

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
    void shouldThrowExceptionWhenNoRoomAvailable(){
        //Given
        BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2022, 01 ,01),
                LocalDate.of(2022, 01, 05) , 2, false);

        when(this.roomServiceMock.findAvailableRoomId(bookingRequest))
                .thenThrow(BusinessException.class);
        //When
        Executable executable = () -> bookingService.makeBooking(bookingRequest);
        //Then
        assertThrows(BusinessException.class, executable);
    }

    @Test
    void shouldNotCompleteBookingWhenPriceTooHigh(){
        //Given
        BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2022, 01 ,01),
                LocalDate.of(2022, 01, 05) , 2, true);

        when(this.paymentServiceMock.pay(any(),anyDouble()))
                .thenThrow(BusinessException.class);
        //When
        Executable executable = () -> bookingService.makeBooking(bookingRequest);
        //Then
        assertThrows(BusinessException.class, executable);
    }
}
