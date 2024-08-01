package com.reservation.ticket.dummy;

import java.util.List;
import java.util.Random;

public class DummyData {

    /**
     * 메서드 호출시 토큰을 랜덤하게 뽑아오도록 만든다. (ACTIVE 상태값인 토큰들)
     */
    public static String getToken() {
        Random random = new Random();
        int randomNumber = random.nextInt(100); // nextInt(100)은 0부터 99까지의 숫자를 반환
        List<String> tokens = tokens();

        return tokens.get(randomNumber);
    }

    public static List<String> tokens() {
        List<String> tokens = List.of(
                "734488355d85",
                "b02567dca468",
                "66b40f8df234",
                "2ff449c014be",
                "4fb8cf64e7fc",
                "9b8c1b3b43d6",
                "053ce98d889e",
                "8c7ea614a541",
                "1f504eb92b17",
                "5faa5b0e096a",
                "f0821578376e",
                "a2fad0ab0b91",
                "6de0beb8dd9c",
                "a8a35ed32dc6",
                "90035b74abd2",
                "69de36dd13da",
                "f34c40bf862c",
                "f5a66d909e82",
                "3cc3706dde50",
                "9217614788bc",
                "1bcb268fae04",
                "de96614d48ff",
                "f8690b657706",
                "88a04915f20f",
                "5415bd9063f3",
                "e78fa290fa55",
                "66377694ad39",
                "69327559bd0e",
                "167eb2329e46",
                "a7c9a66556c7"
        );
        return tokens;
    }

}
