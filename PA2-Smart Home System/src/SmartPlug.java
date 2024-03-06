import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a Smart Plug device that extends the Device class.
 */
public class SmartPlug extends Device {

    /**
     * The amperes of the Smart Plug.
     */
    protected float ampere;

    /**
     * The consumption of the Smart Plug.
     */
    protected float consumption;

    /**
     * The voltage of the Smart Plug.
     */
    protected int voltage;

    /**
     * Constructor for creating a SmartPlug instance.
     *
     * @param name     The name of the SmartPlug.
     * @param dateTime The start date and time of the SmartPlug.
     */
    public SmartPlug(String name, LocalDateTime dateTime) {
        super(name);
        this.voltage = 220;
        this.consumption = 0;
        this.startTime = dateTime;
    }

    /**
     * Constructor for creating a SmartPlug instance.
     *
     * @param name     The name of the SmartPlug.
     * @param status   The status of the SmartPlug.
     * @param dateTime The start date and time of the SmartPlug.
     */
    public SmartPlug(String name, String status, LocalDateTime dateTime) {
        super(name, status);
        this.voltage = 220;
        this.consumption = 0;
        this.startTime = dateTime;
    }

    /**
     * Constructor for creating a SmartPlug instance.
     *
     * @param name     The name of the SmartPlug.
     * @param status   The status of the SmartPlug.
     * @param ampere   The amperes of the SmartPlug.
     * @param dateTime The start date and time of the SmartPlug.
     */
    public SmartPlug(String name, String status, String ampere, LocalDateTime dateTime) {
        super(name, status);
        this.ampere = Float.parseFloat(ampere);
        this.voltage = 220;
        this.consumption = 0;
        this.startTime = dateTime;
    }

    /**
     * Overrides the setStatus method in Device class.
     * Sets the status of the SmartPlug and calculates the consumption.
     *
     * @param status The new status of the SmartPlug.
     */
    @Override
    public void setStatus(boolean status) {
        calcConsumption();
        this.status = status;
    }

    /**
     * Gets the amperes of the SmartPlug.
     *
     * @return The amperes of the SmartPlug.
     */
    public float getAmpere() {
        return ampere;
    }

    /**
     * Sets the amperes of the SmartPlug and calculates the consumption.
     *
     * @param ampere The new amperes of the SmartPlug.
     */
    public void setAmpere(float ampere) {
        calcConsumption();
        this.ampere = ampere;
    }

    /**
     * Calculates the consumption of the SmartPlug.
     * Only calculates the consumption if the SmartPlug is currently on.
     */
    public void calcConsumption() {
        if (status) {
            int time = stopTime.getMinute() - startTime.getMinute();
            this.consumption += voltage * ampere * time / 60;
        }
    }

    /**
     * Gets the device info of the SmartPlug.
     *
     * @param formatter The DateTimeFormatter to format the date and time.
     * @return The device info of the SmartPlug.
     */
    public String getDeviceInfo(DateTimeFormatter formatter) {
        String switchTime = super.giveSwitchTime(formatter);
        return String.format("Smart Plug %s is %s and" +
                        " consumed %.2fW so far (excluding current" +
                        " device), and its time to switch its" +
                        " status is %s.\n", this.name,
                this.getStringStatus(), this.consumption,
                switchTime);
    }
}