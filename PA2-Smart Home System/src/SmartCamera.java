import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a Smart Camera device that extends the Device class.
 */
public class SmartCamera extends Device {

    /**
     * The storage used by Smart Camera per second.
     */
    protected float mb;

    /**
     * The storage used by Smart Camera.
     */
    protected float totalMb;

    /**
     * Creates a new instance of a SmartCamera object with the given name, used storage in MB and start time.
     *
     * @param name     the name of the smart camera
     * @param mb       the storage used per second in mb
     * @param dateTime the start time of the smart camera
     */
    public SmartCamera(String name, String mb, LocalDateTime dateTime) {
        super(name);
        this.mb = Float.parseFloat(mb);
        this.startTime = dateTime;
    }

    /**
     * Creates a new instance of a SmartCamera object with the given name, used storage in MB, status and start time.
     *
     * @param name     the name of the smart camera
     * @param mb       the storage used per second in mb
     * @param status   the status of the smart camera (ON/OFF)
     * @param dateTime the start time of the smart camera
     */
    public SmartCamera(String name, String mb, String status, LocalDateTime dateTime) {
        super(name, status);
        this.mb = Float.parseFloat(mb);
        this.startTime = dateTime;
    }

    /**
     * Overrides the setStatus method in the Device class to calculate the total used storage in MB for the SmartCamera
     * object when the status changes from ON to OFF.
     *
     * @param status the new status of the smart camera
     */
    @Override
    public void setStatus(boolean status) {
        if (this.status) {
            int time = stopTime.getMinute() - startTime.getMinute();
            this.totalMb += mb * time;
        }
        this.status = status;
    }

    /**
     * Returns a string containing information about the SmartCamera object.
     *
     * @param formatter the DateTimeFormatter to format the switch time
     * @return a string containing the name, status, used storage and switch time of the smart camera
     */
    public String getDeviceInfo(DateTimeFormatter formatter) {
        String switchTime = super.giveSwitchTime(formatter);
        return String.format("Smart Camera %s is %s" +
                        " and used %.2f MB of storage so far" +
                        " (excluding current status), and" +
                        " its time to switch its status is %s.\n"
                , this.name, this.getStringStatus(),
                this.totalMb, switchTime);
    }
}