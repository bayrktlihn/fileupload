package io.bayrktlihn.fileuploadproject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

public class Randomizer {

    public Randomizer() throws InstantiationException {
        throw new InstantiationException();
    }

    public static String generateByDateTime() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        long randomNumber = ThreadLocalRandom.current().nextLong(1L, 999_999_999_999L);
        return dateTimeFormatter.format(LocalDateTime.now()) + String.format("%012d", randomNumber);
    }
}