public class Slot {

    private Integer id;
    private String startTime;
    private String endTime;
    private Integer createUserId;
    private String status;
    private Integer bookedUserId;

    public Slot() {
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setBookedUserId(Integer bookedUserId) {
        this.bookedUserId = bookedUserId;
    }

    public Integer getId() {
        return this.id;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public String getEndTime() {
        return this.endTime;
    }

    public Integer getCreateUserId() {
        return this.createUserId;
    }

    public Integer getBookedUserId() {
        return this.bookedUserId;
    }
}