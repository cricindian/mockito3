package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.anyDouble;
import static org.mockito.BDDMockito.mockStatic;

@ExtendWith(MockitoExtension.class)
public class Test14Answers {

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
                    LocalDate.of(2020,01,05), 2,false);

            double expected = 400.0 * 0.8;

            converterMockedStatic.when(() -> CurrencyConverter.toEuro(anyDouble()))
            .thenAnswer(inv -> (double) inv.getArgument(0)* 0.8);
            //When
            double actual = bookingService.calculatePriceEuro(bookingRequest);
            //Then
            assertEquals(expected, actual);
        }



    }

}
