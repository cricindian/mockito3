package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.mockito.Mockito.*;

public class Test07Spies {

    private BookingService bookingService;
    private PaymentService paymentServiceMock;
    private RoomService roomServiceMock;
    private BookingDAO bookingDAOMock;
    private MailSender mailSenderMock;

    @BeforeEach
    void setUp(){
        paymentServiceMock = mock(PaymentService.class);
        roomServiceMock = mock(RoomService.class);
        bookingDAOMock = spy(BookingDAO.class);
        mailSenderMock = mock(MailSender.class);
        bookingService = new BookingService(paymentServiceMock,roomServiceMock, bookingDAOMock, mailSenderMock);
    }

    @Test
    void should_MakeBooking_When_InputOk(){
        //Given
        BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2022, 01 ,01),
                LocalDate.of(2022, 01, 05) , 2, true);

        //When
        String bookingId = bookingService.makeBooking(bookingRequest);
        //Then

        verify(bookingDAOMock).save(bookingRequest);
        System.out.println("booking Id" + bookingId);

    }

    @Test
    void should_CancelBooking_When_InputOk(){
        //Given
        BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2022, 01 ,01),
                LocalDate.of(2022, 01, 05) , 2, true);
        bookingRequest.setRoomId("1.3");

        String bookingId = "1";
        doReturn(bookingRequest).when(bookingDAOMock).get(bookingId);
        //When
        bookingService.cancelBooking(bookingId);
        //Then

    }
}
