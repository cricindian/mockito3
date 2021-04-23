package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.mockito.BDDMockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class Test13StaticMethods {

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
        try(MockedStatic<CurrencyConverter> converterMockedStatic = mockStatic(CurrencyConverter.class)){
            //Given
            BookingRequest bookingRequest = new BookingRequest("1",LocalDate.of(2020,01,01),
                    LocalDate.of(2020,01,03), 2,false);

            double expected = 400.0;

            converterMockedStatic.when(() -> CurrencyConverter.toEuro(anyDouble())).thenReturn(400.0);
            //When
            double actual = bookingService.calculatePriceEuro(bookingRequest);
            //Then
            assertEquals(expected, actual);
        }



    }

}
