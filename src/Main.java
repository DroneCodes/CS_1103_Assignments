import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(String[] args) {
        ZoneId[] zoneIds = {
                ZoneId.of("Europe/London"),
                ZoneId.of("America/New_York"),
                ZoneId.of("Africa/Lagos"),
        };

        for (ZoneId zoneId : zoneIds) {
            //System.out.println(LocalDateTime.now(zoneId).format(DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy")));

            System.out.println(LocalDateTime.now(zoneId));
        }
    }
}