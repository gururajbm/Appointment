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
    private List slotList;
    private Slot slot;

    @Before
    public void setUp() {
        appointmentBook = new AppointmentBooking();
        slotDao = mock(SlotDao.class);
        slotList = new ArrayList();
        slot = new Slot();
    }

    private void getListOfFreeSlotsForGivenDates(String[][] slotDates) {
        for (String[] slotDate : slotDates) {
            slot.setStartTime(slotDate[0]);
            slot.setEndTime(slotDate[1]);
            slot.setCreateUserId(1);
            slot.setStatus("free");
            slotList.add(slot);
        }
    }

    @Test
    public void test_appointmentDate_startDateLessThanEndDate_isValid() {
        Boolean result
            = appointmentBook.isAppointmentStartTimeLessThanEndTime(
                "2018-04-18 09:00:00", "2018-04-18 10:00:00"
            );
        Assert.assertEquals(true, result);
    }

    @Test
    public void test_appointmentDate_startDateGreaterThanEndDate_isInvalid() {
        Boolean result
                = appointmentBook.isAppointmentStartTimeLessThanEndTime(
                "2018-04-18 10:00:00", "2018-04-18 09:00:00"
        );
        Assert.assertEquals(false, result);
    }

    @Test
    public void test_appointmentDate_startDateEndDateEmptyString_isInvalid() {
        Boolean result
                = appointmentBook.isAppointmentStartTimeLessThanEndTime(
                "", ""
        );
        Assert.assertEquals(false, result);
    }

    @Test
    public void test_appointmentDate_diffStartDateEndDate_lessThanFifteenMin_isInvalid() {
        Boolean result
                = appointmentBook.isAppointmentValidDuration(
                "2018-04-18 10:00:00", "2018-04-18 10:05:00"
        );
        Assert.assertEquals(false, result);
    }

    @Test
    public void test_appointmentDate_diffStartDateEndDate_ofOneHour_isValid() {
        Boolean result
                = appointmentBook.isAppointmentValidDuration(
                "2018-04-18 10:00:00", "2018-04-18 11:00:00"
        );
        Assert.assertEquals(true, result);
    }

    @Test
    public void test_appointmentDate_isOnDifferentDay_isInvalid() {
        Boolean result
                = appointmentBook.isAppointmentValidDuration(
                "2018-04-18 10:00:00", "2019-04-19 11:00:00"
        );
        Assert.assertEquals(false, result);
    }

    @Test
    public void testAppointmentGet_betweenRange() {
        String[][] slotDates = {
            {"2018-04-18 09:00:00","2018-04-18 10:00:00"},
            {"2018-04-18 10:01:00","2018-04-18 11:00:00"}
        };
        getListOfFreeSlotsForGivenDates(slotDates);
        when(
            slotDao.getSlotsBetweenStartAndEndTime(
                "2018-04-18 09:00:00",
                "2018-04-18 10:00:00"
            )
        ).thenReturn(slotList);

        Boolean result
            = appointmentBook.isFreeSlotExistForBooking("2018-04-18 09:00:00", "2018-04-18 10:00:00");
        Assert.assertEquals(true, result);
    }
}
