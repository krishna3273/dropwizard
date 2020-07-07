package dropwizard;

import static java.lang.Integer.parseInt;
import java.time.*;

public class Helper {
    public static int hash(String utctime,String ses){
//       UTC-FORMAT "2020-05-02T02:00:00.000Z"
        String []temp=utctime.split("T");
        String currDate=temp[0];
        String currTime=temp[1];
        temp=currDate.split("[-]");
        int year=parseInt(temp[0]);
        int month=parseInt(temp[1]);
        int day=parseInt(temp[2]);
        LocalDate localDate = LocalDate.of(year,month,day);
        DayOfWeek dayOfWeek = DayOfWeek.from(localDate);
        int week = dayOfWeek.getValue();
        temp=currTime.split("[:]");
        int hour=parseInt(temp[0]);
        int [] parsedTime= new int[]{year, month, day,week, hour};
        switch (ses){
            case "weekly":
                return parsedTime[3]*24+parsedTime[4];
            case "daily":
                return parsedTime[4];
            case "monthly":
                return parsedTime[2]*24+parsedTime[4];
            default:
                return 0;
        }

    }
//    public static void main(String[] args) {
//        int [] temp=Helper.parseTimeStamp("2020-07-07T02:00:00.000Z");
//        for (int value : temp) {
//            System.out.println(value);
//        }
//    }
}
