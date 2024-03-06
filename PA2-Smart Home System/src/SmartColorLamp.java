import java.time.format.DateTimeFormatter;

public class SmartColorLamp extends SmartLamp {

    /**
     * Constructs a SmartColorLamp object with the given name.
     *
     * @param name The name of the lamp.
     */
    public SmartColorLamp(String name) {
        super(name);
    }

    /**
     * Constructs a SmartColorLamp object with the given name and status.
     *
     * @param name   The name of the lamp.
     * @param status The status of the lamp.
     */
    public SmartColorLamp(String name, String status) {
        super(name, status);
    }

    /**
     * Constructs a SmartColorLamp object with the given name, status, kelvin and brightness.
     *
     * @param name       The name of the lamp.
     * @param status     The status of the lamp.
     * @param kelvin     The color temperature of the lamp.
     * @param brightness The brightness of the lamp.
     */
    public SmartColorLamp(String name, String status, String kelvin, String brightness) {
        super(name, status);
        this.brightness = Integer.parseInt(brightness);
        this.kelvin = kelvin;
    }

    /**
     * Returns the device information including the color temperature, brightness and switch time.
     *
     * @param formatter The date-time formatter to use for formatting the switch time.
     * @return A formatted string of the device information.
     */
    @Override
    public String getDeviceInfo(DateTimeFormatter formatter) {
        String switchTime = super.giveSwitchTime(formatter);
        String kelvin;
        try {
            Integer.parseInt(this.kelvin);
            kelvin = this.kelvin + "K";
        } catch (Exception e) {
            kelvin = this.kelvin;
        }
        return String.format("Smart Color Lamp %s" +
                        " is %s and its color value is %s" +
                        " with %d%% brightness, and its time" +
                        " to switch its status is %s.\n",
                this.name, this.getStringStatus(),
                kelvin, this.brightness,
                switchTime);
    }
}