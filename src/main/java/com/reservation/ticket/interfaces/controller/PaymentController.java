package com.reservation.ticket.interfaces.controller;

import com.reservation.ticket.application.usecase.PaymentUsecase;
import com.reservation.ticket.interfaces.controller.dto.payment.PaymentDto;
import com.reservation.ticket.interfaces.controller.dto.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentUsecase paymentUsecase;

    @PostMapping
    public Response<Void> makePaymentDone(@RequestBody PaymentDto.Request request) {
        paymentUsecase.makePaymentForReservationDone(request.reservationId(), request.userId());
        return Response.success();
    }

}
