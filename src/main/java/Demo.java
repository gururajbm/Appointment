public class Demo {
    public static void main(String[] args) {
        SlotDao slotDao = new SlotDao();
        slotDao.getSlotsBetweenStartAndEndTime("2018-04-18 09:00:00", "2018-04-18 10:00:00");
    }
}
