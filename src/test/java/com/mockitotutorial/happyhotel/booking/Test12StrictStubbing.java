package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class Test12StrictStubbing {

    @InjectMocks
    private BookingService bookingService;
    @Mock
    private PaymentService paymentServiceMock;
    @Mock
    private RoomService roomServiceMock;
    @Spy
    private BookingDAO bookingDAOMock;
    @Mock
    private MailSender mailSenderMock;

    @Test
    void shouldInvokePaymentWhenPrepaid(){
        //Given
        BookingRequest bookingRequest = new BookingRequest("1",LocalDate.of(2020,01,01),
                LocalDate.of(2020,01,03), 2,false);

        lenient().when(paymentServiceMock.pay(any(),anyDouble())).thenReturn("1"); //Unnecessary when is stubbing
        // lenient used when strict stubbing not required.
        //When
        bookingService.makeBooking(bookingRequest);
        //Then
        //no exceptions

    }

}
