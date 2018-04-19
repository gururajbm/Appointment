import Appointment.AppointmentBooking;
import Appointment.Slot;
import Appointment.SlotDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AppointmentBookingTest {

    private AppointmentBooking appointmentBook;
    private SlotDao slotDao;

    @Before
    public void setUp() {
        appointmentBook = new AppointmentBooking();
        slotDao = mock(SlotDao.class);
    }

    @Test
    public void testAppointmentGet_betweenRange() {
        List slotList = new ArrayList();
        Slot slot = new Slot();
        slot.setStartTime("2018-04-18 09:00:00");
        slot.setEndTime("2018-04-18 10:00:00");
        slot.setCreateUserId(1);
        slot.setStatus("free");
        slotList.add(slot);

        when(
            slotDao.getSlotsBetweenStartAndEndTime(
                "2018-04-18 09:00:00",
                "2018-04-18 10:00:00"
            )
        ).thenReturn(slotList);

        Boolean result = appointmentBook.isFreeSlotExistForBooking("2018-04-18 09:00:00", "2018-04-18 10:00:00");
        Assert.assertEquals(true, result);
    }

}
