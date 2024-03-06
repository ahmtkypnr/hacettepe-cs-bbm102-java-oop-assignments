import java.time.format.DateTimeFormatter;

/**
 * Represents a Smart Lamp device that extends the Device class.
 */
public class SmartLamp extends Device {

    /**
     * The kelvin value of the Smart Lamp.
     */
    protected String kelvin;

    /**
     * The brightness of the Smart Lamp.
     */
    protected int brightness;

    /**
     * Constructs a SmartLamp object with the given name, default brightness of 100%, and default kelvin value of 4000K.
     *
     * @param name the name of the SmartLamp
     */
    public SmartLamp(String name) {
        super(name);
        this.brightness = 100;
        this.kelvin = "4000";
    }

    /**
     * Constructs a SmartLamp object with the given name and status, default brightness of 100%, and default kelvin value of 4000K.
     *
     * @param name   the name of the SmartLamp
     * @param status the initial status of the SmartLamp, either "ON" or "OFF"
     */
    public SmartLamp(String name, String status) {
        super(name, status);
        this.brightness = 100;
        this.kelvin = "4000";
    }

    /**
     * Constructs a SmartLamp object with the given name, status, kelvin value, and brightness.
     *
     * @param name       the name of the SmartLamp
     * @param status     the initial status of the SmartLamp, either "ON" or "OFF"
     * @param kelvin     the kelvin value of the SmartLamp
     * @param brightness the brightness of the SmartLamp, a percentage value between 0 and 100
     */
    public SmartLamp(String name, String status, String kelvin, String brightness) {
        super(name, status);
        this.kelvin = kelvin;
        this.brightness = Integer.parseInt(brightness);
    }

    /**
     * Sets the kelvin value of the SmartLamp.
     *
     * @param kelvin the kelvin value of the SmartLamp
     */
    public void setKelvin(String kelvin) {
        this.kelvin = kelvin;
    }

    /**
     * Sets the brightness of the SmartLamp.
     *
     * @param brightness the brightness of the SmartLamp, a percentage value between 0 and 100
     */
    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    /**
     * Returns a string containing the device information of the SmartLamp, including its name, status, kelvin value,
     * brightness, and time to switch its status in the given format.
     *
     * @param formatter the format in which to return the time to switch the status
     * @return a string containing the device information of the SmartLamp
     */
    public String getDeviceInfo(DateTimeFormatter formatter) {
        String switchTime = super.giveSwitchTime(formatter);
        return String.format("Smart Lamp %s is %s" +
                        " and its kelvin value is %sK" +
                        " with %d%% brightness, and its" +
                        " time to switch its status is" +
                        " %s.\n", this.name,
                this.getStringStatus(), this.kelvin,
                this.brightness, switchTime);
    }
}