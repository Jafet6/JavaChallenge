package br.com.jafethenrique.JavaChallenge.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.TimeZone;

public class DateUTC {
    public OffsetDateTime getCurrentUtcTime() {
        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
        return now;
    }
}
