package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class Test11Bdd {

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
    void shouldCountAvaialablePlacesWhenOneRoomAvailable(){

        //Given
        given(roomServiceMock.getAvailableRooms()).willReturn(Collections.singletonList(new Room("1", 5)));

        //When
        int avialbelRoomsCount = bookingService.getAvailablePlaceCount();
        //Then
        assertEquals(5, avialbelRoomsCount);

    }

    @Test
    void shouldInvokePaymentWhenPrepaid(){
        //Given
        BookingRequest bookingRequest = new BookingRequest("1",LocalDate.of(2020,01,01),
                LocalDate.of(2020,01,03), 2,true);
        //When
        bookingService.makeBooking(bookingRequest);
        //
        then(paymentServiceMock).should(times(1)).pay(bookingRequest,400.0);
        verifyNoMoreInteractions(paymentServiceMock);

    }

}
