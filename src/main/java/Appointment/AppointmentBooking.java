package Appointment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AppointmentBooking {

    public Boolean isFreeSlotExistForBooking(String startDateTime, String endDateTime) {
        return true;
    }

    public Boolean isAppointmentStartTimeLessThanEndTime(String startDateTime, String endDateTime) {
        try {
            Date appointmentStartTime = convertStringToDateTime(startDateTime);
            Date appointmentEndTime = convertStringToDateTime(endDateTime);
            if (appointmentStartTime.compareTo(appointmentEndTime) < 0) {
                return true;
            }
        } catch (ParseException e) {
            return false;
        }

        return false;
    }

   public Boolean isAppointmentValidDuration(String startDateTime, String endDateTime) {
       try {
           Date appointmentStartTime = convertStringToDateTime(startDateTime);
           Date appointmentEndTime = convertStringToDateTime(endDateTime);

           if (!isAppointmentOnSameDay(appointmentStartTime, appointmentEndTime)) {
               return false;
           }

           Long appointmentDuration = getDateDifferenceInSeconds(appointmentStartTime, appointmentEndTime);
           if (appointmentDuration < 900 || appointmentDuration > 86400) {
               return false;
           }

       } catch (ParseException e) {
           return false;
       }

       return true;
   }

   private Long getDateDifferenceInSeconds(Date startDateTime, Date endDateTime) {
       return (endDateTime.getTime() - startDateTime.getTime())/1000;
   }

   private Boolean isAppointmentOnSameDay(Date startDateTime, Date endDateTime) {
       SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
       return dateFormat.format(startDateTime).equals(dateFormat.format(endDateTime));
   }

   private Date convertStringToDateTime(String dateTime) throws ParseException {
       String pattern = "yyyy-mm-dd hh:mm:ss";
       SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
       try {
           return simpleDateFormat.parse(dateTime);
       } catch (ParseException e) {
           throw e;
       }
   }
}
