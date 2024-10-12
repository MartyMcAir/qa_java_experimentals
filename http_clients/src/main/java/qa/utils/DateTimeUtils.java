package qa.utils;

import lombok.SneakyThrows;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtils {
    @SneakyThrows
    public static long getUnixTimestampForDateInUnixTimeMilliseconds(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date parsedDate = dateFormat.parse(date);
        return parsedDate.getTime();
    }
}
