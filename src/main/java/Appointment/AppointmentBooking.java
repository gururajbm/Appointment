package Appointment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AppointmentBooking {

    private SlotDao slotDao;
    private Slot slot;

    public void setSlotDao(SlotDao slotDao) {
        this.slotDao = slotDao;
    }

    public SlotDao getSlotDao() {
        return this.slotDao;
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

    public Boolean isAppointmentExist(String startDateTime, String endDateTime) {
        List<Slot> slots = new ArrayList();
        slot = new Slot();
        slots = getSlotDao().getSlotsBetweenStartAndEndTime(startDateTime, endDateTime);
        for (Slot slot : slots) {
            if (slot.getStatus() == "booked") {
                return false;
            }
        }

        return true;
    }
}
