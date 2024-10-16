package com.reservation.ticket.interfaces.controller;

import com.reservation.ticket.domain.entity.queue.QueueRedisService;
import com.reservation.ticket.interfaces.dto.QueueDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Queue Api", description = "대기열 API")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공", content = {@Content(schema = @Schema(implementation = QueueDto.Request.class))}),
        @ApiResponse(responseCode = "200", description = "코드가 존재하지 않습니다."),
})
@RestController
@RequestMapping("/api/queue")
@RequiredArgsConstructor
public class QueueController {

    private final QueueRedisService queueService;

    /**
     * 사용자의 정보를 이용하여 최초 대기열 데이터 생성한다.
     */
    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(examples = {
            @ExampleObject(name = "queue", value = """ 
                        {
                            "userId" : 1
                        }
                    """)
    }))
    @Operation(summary = "사용자를 검색하여 Waiting Queue의 토큰을 발급한다.")
    @Parameter(name = "userId", description = "사용자 id")
    @PostMapping("/token")
    public ResponseEntity<Void> createToken(@RequestBody QueueDto.Request request) {
        String token = queueService.createWaitQueue(request.userId());

        // 여기서 토큰을 해더에 담아줘야한다.
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, token);

        return ResponseEntity.status(HttpStatus.OK).headers(headers).build();
    }

}

