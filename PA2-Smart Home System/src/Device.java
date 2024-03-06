import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * A Device class that stores the name, status, switch time, start time, and stop time of a device.
 */
public class Device {

    /**
     * The name of the device.
     */
    protected String name;

    /**
     * The current status of the device (ON or OFF).
     */
    protected boolean status;

    /**
     * The time the device when will switch on or off.
     */
    protected LocalDateTime switchTime;

    /**
     * The time the device was started (if applicable).
     */
    protected LocalDateTime startTime;

    /**
     * The time the device was stopped (if applicable).
     */
    protected LocalDateTime stopTime;

    /**
     * Constructs a new Device object with the given name.
     *
     * @param name the name of the device
     */
    public Device(String name) {
        this.name = name;
    }

    /**
     * Constructs a new Device object with the given name and status.
     *
     * @param name   the name of the device
     * @param status the status of the device (ON or OFF)
     */
    public Device(String name, String status) {
        this.name = name;
        this.status = status.equalsIgnoreCase("ON");
    }

    /**
     * Returns the name of the device.
     *
     * @return the name of the device
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the device.
     *
     * @param name the name of the device
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the current status of the device (ON or OFF).
     *
     * @return the current status of the device
     */
    public boolean getStatus() {
        return status;
    }

    /**
     * Sets the current status of the device (ON or OFF).
     *
     * @param status the current status of the device
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

    /**
     * Returns the current status of the device as a string ("on" or "off").
     *
     * @return the current status of the device as a string
     */
    public String getStringStatus() {
        if (status) {
            return "on";
        } else {
            return "off";
        }
    }

    /**
     * Returns the time the device when will switch on or off.
     *
     * @return the time the device when will switch on or off
     */
    public LocalDateTime getSwitchTime() {
        return switchTime;
    }

    /**
     * Sets the time the device when will switch on or off.
     *
     * @param switchTime the time the device when will switch on or off
     */
    public void setSwitchTime(LocalDateTime switchTime) {
        this.switchTime = switchTime;
    }

    /**
     * Returns the time the device when will switch on or off, or a default null-safe time if switchTime is null.
     *
     * @return the time the device when will switch on or off, or a default null-safe time if switchTime is null
     */
    public LocalDateTime getNullSafeSwitchTime() {
        if (switchTime == null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");
            return LocalDateTime.parse("9999-12-31_23:59:59", formatter);
        }
        return switchTime;
    }

    /**
     * Sets the time thedevice was started.
     *
     * @param startTime the time the device was started
     */
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    /**
     * Sets the time the device was stopped.
     *
     * @param stopTime the time the device was stopped
     */
    public void setStopTime(LocalDateTime stopTime) {
        this.stopTime = stopTime;
    }

    /**
     * Returns the time the device when will switch on or off as a string.
     *
     * @param formatter the format in which to return the time
     * @return the time the device when will switch on or off as a string
     */
    public String giveSwitchTime(DateTimeFormatter formatter) {
        String switchTime = "null";
        if (this.getSwitchTime() != null) {
            switchTime = this.getSwitchTime().format(formatter);
        }
        return switchTime;
    }
}