package cmri.utils.lang;

import org.junit.Test;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by zhuyin on 1/24/16.
 */
public class TimeHelperTest {
    @Test
    public void testConvertToUTC() {
        long epochMilli = System.currentTimeMillis();
        Instant instant = Instant.ofEpochMilli(epochMilli);
        ZoneId zoneId = ZoneId.of("Asia/Shanghai");
        ZonedDateTime now = instant.atZone(zoneId);
        ZonedDateTime utc = now.withZoneSameInstant(ZoneOffset.UTC);
        System.out.println("now: " + now);
        System.out.println("utc: " + utc);
        System.out.println(epochMilli);
        System.out.println(Date.from(utc.toInstant()).getTime());
    }
}