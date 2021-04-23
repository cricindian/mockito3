package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class Test10Annotations {

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
