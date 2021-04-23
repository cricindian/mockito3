package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.*;

public class Test03MockWithCustomResponse {

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
    void shouldCountAvailablePlaces_When_OneRoomAvailable(){
        //Given
        when(roomServiceMock.getAvailableRooms())
                .thenReturn(Collections.singletonList(new Room("Room1", 2)));
        int expected = 2;
        //When
        int actual = bookingService.getAvailablePlaceCount();
        //Then
        assertEquals(expected, actual);

    }

    @Test
    void shouldCountAvailablePlaces_When_MoreThanOneRoomAvailable(){
        //Given
        when(roomServiceMock.getAvailableRooms())
                .thenReturn(Arrays.asList(new Room("Room1", 2), new Room("Room2", 5)));
        int expected = 7;
        //When
        int actual = bookingService.getAvailablePlaceCount();
        //Then
        assertEquals(expected, actual);

    }
}
