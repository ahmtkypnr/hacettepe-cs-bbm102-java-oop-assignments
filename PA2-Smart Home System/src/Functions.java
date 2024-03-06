import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Functions {

    /**
     * Returns a Device object with the given name from the given ArrayList.
     * If there is no device with the given name, returns null.
     *
     * @param devices the ArrayList of devices to search in
     * @param name the name of the device to search for
     * @return the Device object with the given name, or null if it is not found
     */
    public static Device findDevice(ArrayList<Device> devices, String name) {
        for (Device device : devices) {
            if (device.getName().equals(name)) {
                return device;
            }
        }
        return null;
    }

    /**
     * Updates the status and stop/start time of each device in the given ArrayList,
     * and sets the switch time to null.
     *
     * @param devices the ArrayList of devices to switch
     * @param dateTime the LocalDateTime to set as the stop/start time for each device
     */
    public static void switchDevices(ArrayList<Device> devices, LocalDateTime dateTime) {
        for (Device device : devices) {
            device.setStopTime(dateTime);
            device.setStatus(!device.getStatus());
            device.setStartTime(dateTime);
            device.setSwitchTime(null);
        }
    }

    /**
     * Checks the switch time of each device in the given ArrayList against the given
     * LocalDateTime, and switches the devices that switch times have come.
     * It sorts list after every switch operation. If multiple devices have the same switch time,
     * it sorts after switch them.
     *
     * @param devices the ArrayList of devices to check and switch
     * @param dateTime the LocalDateTime to check switch times against
     */
    public static void checkDevices(ArrayList<Device> devices, LocalDateTime dateTime) {
        ArrayList<Device> tempList = new ArrayList<>();
        for (Device device : devices) {
            if (device.getSwitchTime() == null) {
                continue;
            }
            if (device.getSwitchTime().isAfter(dateTime)) {
                continue;
            }
            if (tempList.size() == 0) {
                tempList.add(device);
            } else {
                if (tempList.get(0).getSwitchTime().equals(device.getSwitchTime())) {
                    tempList.add(device);
                } else {
                    insertionSort(devices);
                    switchDevices(tempList, dateTime);
                    insertionSort(devices);
                    tempList.clear();
                    tempList.add(device);
                }
            }
        }
        insertionSort(devices);
        switchDevices(tempList, dateTime);
        insertionSort(devices);
    }

    /**
     * Returns a string with the device information of the given device, formatted using
     * the given DateTimeFormatter. If the device is a SmartCamera, returns the device
     * information of the SmartCamera. If the device is a SmartPlug, returns the device
     * information of the SmartPlug. If the device is a SmartLamp, returns the device
     * information of the SmartLamp. If the device is a SmartColorLamp, returns the device
     * information of the SmartColorLamp.
     *
     * @param device the device to get the device information for
     * @param formatter the DateTimeFormatter to format the device information with
     * @return a string with the formatted device information
     */
    public static String findDeviceType(Device device, DateTimeFormatter formatter) {
        String output = "";
        if (device instanceof SmartCamera) {
            SmartCamera camera1 = (SmartCamera) device;
            output = camera1.getDeviceInfo(formatter);
        } else if (device instanceof SmartPlug) {
            SmartPlug plug1 = (SmartPlug) device;
            output = plug1.getDeviceInfo(formatter);
        } else if (device instanceof SmartLamp) {
            if (device instanceof SmartColorLamp) {
                SmartColorLamp clamp1 = (SmartColorLamp) device;
                output = clamp1.getDeviceInfo(formatter);
            } else {
                SmartLamp lamp1 = (SmartLamp) device;
                output = lamp1.getDeviceInfo(formatter);
            }
        }
        return output;
    }

    /**
     * Sorts the given ArrayList of devices by switch time using the insertion sort algorithm.
     *
     * @param devices the ArrayList of devices to sort
     */
    public static void insertionSort(ArrayList<Device> devices) {
        for (int i = 1; i < devices.size(); i++) {
            Device key = devices.get(i);
            int j = i - 1;
            while (j >= 0 && (devices.get(j).getNullSafeSwitchTime().isAfter(key.getNullSafeSwitchTime())
                    || (devices.get(j).getNullSafeSwitchTime().equals(key.getNullSafeSwitchTime())
                    && j > 0 && j - 1 > i))) {
                devices.set(j + 1, devices.get(j));
                j = j - 1;
            }
            devices.set(j + 1, key);
        }
    }

    /**
     * Checks if the given status string is valid (i.e. "On" or "Off").
     *
     * @param status the status string to check
     * @return true if the status string is invalid, false otherwise
     */
    public static boolean checkStatus(String status) {
        return !status.equalsIgnoreCase("On") && !status.equalsIgnoreCase("Off");
    }

    /**
     * Checks if the given string represents a valid float number.
     *
     * @param number the string to check
     * @return true if the string does not represent a valid float number, false otherwise
     */
    public static boolean checkFloatNumber(String number) {
        try {
            Float.parseFloat(number);
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * Checks if the given string represents a valid integer number.
     *
     * @param number the string to check
     * @return true if the string does not represent a valid integer number, false otherwise
     */
    public static boolean checkIntNumber(String number) {
        try {
            Integer.parseInt(number);
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * Checks if the given string represents a valid hexadecimal number (with the 0x prefix).
     *
     * @param number the string to check
     * @return true if the string does not represent a valid hexadecimal number, false otherwise
     */
    public static boolean checkHexadecimal(String number) {
        try {
            Integer.parseInt(number.substring(2), 16);
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * Add devices to the ArrayList. It checks for every error with if blocks.
     * It returns error if it has given an error. It returns an empty string if everything goes well.
     * @param command the command line that taken from txt file
     * @param devices the device ArrayList
     * @param dateTime current time
     * @return it returns error if it has given an error. It returns an empty string if everything goes well.
     */
    public static String addDevice(String[] command, ArrayList<Device> devices, LocalDateTime dateTime) {
        switch (command[1]) {
            case "SmartPlug":
                if (command.length == 3) {
                    if (findDevice(devices, command[2]) != null) {
                        return "ERROR: There is already a smart device with same name!\n";
                    }
                    devices.add(new SmartPlug(command[2], dateTime));
                    break;
                } else if (command.length == 4) {
                    if (findDevice(devices, command[2]) != null) {
                        return "ERROR: There is already a smart device with same name!\n";
                    }
                    if (checkStatus(command[3])) {
                        return "ERROR: Erroneous command!\n";
                    }
                    devices.add(new SmartPlug(command[2], command[3], dateTime));
                    break;
                } else if (command.length == 5) {
                    if (findDevice(devices, command[2]) != null) {
                        return "ERROR: There is already a smart device with same name!\n";
                    }
                    if (checkStatus(command[3]) || checkFloatNumber(command[4])) {
                        return "ERROR: Erroneous command!\n";
                    }
                    if (Float.parseFloat(command[4]) <= 0) {
                        return "ERROR: Ampere value must be a positive number!\n";
                    }
                    devices.add(new SmartPlug(command[2], command[3], command[4], dateTime));
                    break;
                } else {
                    return "ERROR: Erroneous command!\n";
                }
            case "SmartCamera":
                if (command.length == 4) {
                    if (findDevice(devices, command[2]) != null) {
                        return "ERROR: There is already a smart device with same name!\n";
                    }
                    if (checkFloatNumber(command[3])) {
                        return "ERROR: Erroneous command!\n";
                    }
                    if (Float.parseFloat(command[3]) <= 0) {
                        return "ERROR: Megabyte value must be a positive number!\n";
                    }
                    devices.add(new SmartCamera(command[2], command[3], dateTime));
                    break;
                } else if (command.length == 5) {
                    if (findDevice(devices, command[2]) != null) {
                        return "ERROR: There is already a smart device with same name!\n";
                    }
                    if (checkFloatNumber(command[3])) {
                        return "ERROR: Erroneous command!\n";
                    }
                    if (Float.parseFloat(command[3]) <= 0) {
                        return "ERROR: Megabyte value must be a positive number!\n";
                    }
                    if (checkStatus(command[4])) {
                        return "ERROR: Erroneous command!\n";
                    }
                    devices.add(new SmartCamera(command[2], command[3], command[4], dateTime));
                    break;
                } else {
                    return "ERROR: Erroneous command!\n";
                }
            case "SmartLamp":
                if (command.length == 3) {
                    if (findDevice(devices, command[2]) != null) {
                        return "ERROR: There is already a smart device with same name!\n";
                    }
                    devices.add(new SmartLamp(command[2]));
                    break;
                } else if (command.length == 4) {
                    if (findDevice(devices, command[2]) != null) {
                        return "ERROR: There is already a smart device with same name!\n";
                    }
                    if (checkStatus(command[3])) {
                        return "ERROR: Erroneous command!\n";
                    }
                    devices.add(new SmartLamp(command[2], command[3]));
                    break;
                } else if (command.length == 6) {
                    if (findDevice(devices, command[2]) != null) {
                        return "ERROR: There is already a smart device with same name!\n";
                    }
                    if (checkStatus(command[3]) || checkIntNumber(command[4])) {
                        return "ERROR: Erroneous command!\n";
                    }
                    if (6500 < Integer.parseInt(command[4]) || Integer.parseInt(command[4]) < 2000) {
                        return "ERROR: Kelvin value must be in range of 2000K-6500K!\n";
                    }
                    if (checkIntNumber(command[5])) {
                        return "ERROR: Erroneous command!\n";
                    }
                    if (0 > Integer.parseInt(command[5]) || Integer.parseInt(command[5]) > 100) {
                        return "ERROR: Brightness must be in range of 0%-100%!\n";
                    }
                    devices.add(new SmartLamp(command[2], command[3], command[4], command[5]));
                    break;
                } else {
                    return "ERROR: Erroneous command!\n";
                }
            case "SmartColorLamp":
                if (command.length == 3) {
                    if (findDevice(devices, command[2]) != null) {
                        return "ERROR: There is already a smart device with same name!\n";
                    }
                    devices.add(new SmartColorLamp(command[2]));
                    break;
                } else if (command.length == 4) {
                    if (checkStatus(command[3])) {
                        return "ERROR: Erroneous command!\n";
                    }
                    if (findDevice(devices, command[2]) != null) {
                        return "ERROR: There is already a smart device with same name!\n";
                    }
                    devices.add(new SmartColorLamp(command[2], command[3]));
                    break;
                } else if (command.length == 6) {
                    if (checkStatus(command[3])) {
                        return "ERROR: Erroneous command!\n";
                    }
                    if (findDevice(devices, command[2]) != null) {
                        return "ERROR: There is already a smart device with same name!\n";
                    }
                    if (checkIntNumber(command[4]) && checkHexadecimal(command[4])) {
                        return "ERROR: Erroneous command!\n";
                    }
                    if (!checkIntNumber(command[4])) {
                        if (6500 < Integer.parseInt(command[4]) || Integer.parseInt(command[4]) < 2000) {
                            return "ERROR: Kelvin value must be in range of 2000K-6500K!\n";
                        }
                    } else {
                        int temp = Integer.parseInt(command[4].substring(2), 16);
                        if (0 > temp || temp > 16777215) {
                            return "ERROR: Color code value must be in range of 0x0-0xFFFFFF!\n";
                        }
                    }
                    if (checkIntNumber(command[5])) {
                        return "ERROR: Erroneous command!\n";
                    }
                    if (0 > Integer.parseInt(command[5]) || Integer.parseInt(command[5]) > 100) {
                        return "ERROR: Brightness must be in range of 0%-100%!\n";
                    }
                    devices.add(new SmartColorLamp(command[2], command[3], command[4], command[5]));
                    break;
                } else {
                    return "ERROR: Erroneous command!\n";
                }
            default:
                return "ERROR: Erroneous command!\n";
        }
        return "";
    }

    /**
     * Checks if the given command is to set the initial time and sets it to the specified format.
     *
     * @param command a string array that contains the command to set the initial time and the initial time itself
     * @param takenFormat the format of the initial time in the command
     * @param formatter the format to which the initial time will be converted
     * @return a string that indicates whether the setting of the initial time was successful or not
     */
    public static String checkSetInitialTime(String[] command,
                                             DateTimeFormatter takenFormat, DateTimeFormatter formatter) {
        LocalDateTime dateTime;
        if (command[0].equals("SetInitialTime") && command.length == 2) {
            try {
                dateTime = LocalDateTime.parse(command[1], takenFormat);
            } catch (Exception e) {
                return "ERROR: Format of the initial date is wrong!" +
                        " Program is going to terminate!\n";
            }
        } else {
            return "ERROR: First command must be set initial time!" +
                    " Program is going to terminate!\n";
        }
        return String.format("SUCCESS: Time has been set to %s!\n", dateTime.format(formatter));
    }

    /**
     * Checks if the given command is to set the time and verifies whether it can be set to the specified format.
     *
     * @param command a string array that contains the command to set the time and the time itself
     * @param takenFormat the format of the time in the command
     * @param dateTime the current date and time
     * @return a string that indicates whether the setting of the time was successful or not
     */
    public static String checkSetTime(String[] command, DateTimeFormatter takenFormat, LocalDateTime dateTime) {
        if (command.length != 2) {
            return "ERROR: Erroneous command!\n";
        }
        try {
            LocalDateTime tempTime = LocalDateTime.parse(command[1], takenFormat);
            if (tempTime.equals(dateTime)) {
                return "ERROR: There is nothing to change!\n";
            } else if (tempTime.isAfter(dateTime)) {
                return "";
            } else {
                return "ERROR: Time cannot be reversed!\n";
            }
        } catch (Exception e) {
            return "ERROR: Time format is not correct!\n";
        }
    }

    /**
     * Checks if the given command is to skip minutes and verifies whether it can be skipped or not.
     *
     * @param command a string array that contains the command to skip minutes and the number of minutes to skip
     * @return a string that indicates whether the skipping of minutes was successful or not
     */
    public static String checkSkipMinutes(String[] command) {
        if (command.length != 2 || checkIntNumber(command[1])) {
            return "ERROR: Erroneous command!\n";
        }
        if (Integer.parseInt(command[1]) == 0) {
            return "ERROR: There is nothing to skip!\n";
        }
        if (Integer.parseInt(command[1]) < 0) {
            return "ERROR: Time cannot be reversed!\n";
        }
        return "";
    }

    /**
     * Sets the switch time of a device to the specified time and verifies whether it can be set or not.
     *
     * @param devices an ArrayList that contains all the devices
     * @param command a string array that contains the command to set the switch time,
     *                the name of the device, and the switch time
     * @param takenFormat the format of the switch time in the command
     * @param globalTime the current date and time
     * @return a string that indicates whether the setting of the switch time was successful or not
     */
    public static String setSwitchTime(ArrayList<Device> devices, String[] command,
                                       DateTimeFormatter takenFormat, LocalDateTime globalTime) {
        if (command.length != 3) {
            return "ERROR: Erroneous command!\n";
        }
        if (findDevice(devices, command[1]) == null) {
            return "ERROR: There is not such a device!\n";
        }
        Device device = findDevice(devices, command[1]);
        try {
            LocalDateTime dateTime = LocalDateTime.parse(command[2], takenFormat);
            if (dateTime.isBefore(globalTime)) {
                return "ERROR: Switch time cannot be in the past!\n";
            } else {
                device.setSwitchTime(dateTime);
                checkDevices(devices, globalTime);
            }
        } catch (Exception e) {
            return "ERROR: Time format is not correct!\n";
        }
        return "";
    }

    /**
     * Removes the specified device from the ArrayList of devices and
     * returns a string containing information about the removed device.
     *
     * @param devices an ArrayList that contains all the devices
     * @param command a string array that contains the command to remove a device and the name of the device
     * @param takenFormat the format of the date and time in the device information
     * @param dateTime the current date and time
     * @return a string containing information about the removed device
     */
    public static String removeDevice(ArrayList<Device> devices, String[] command,
                                      DateTimeFormatter takenFormat, LocalDateTime dateTime) {
        if (command.length != 2) {
            return "ERROR: Erroneous command!\n";
        }
        if (findDevice(devices, command[1]) == null) {
            return "ERROR: There is not such a device!\n";
        }
        Device device = findDevice(devices, command[1]);
        boolean status = device.getStatus();
        if (status) {
            device.setStopTime(dateTime);
            device.setStatus(false);
            device.setStartTime(dateTime);
        }
        devices.remove(device);
        return "SUCCESS: Information about removed smart device is as follows:\n" + findDeviceType(device, takenFormat);
    }

    /**

     * This method switches a smart device on or off and updates its status, start and stop times accordingly.
     *
     * @param devices the list of all smart devices
     * @param command the command that specifies which device to switch and the status to switch to
     * @param dateTime the current date and time
     * @return an error message if the command is erroneous or
     *         if the device is already switched to the specified status,
     *         or an empty string if the operation is successful.
     */
    public static String switchDevice(ArrayList<Device> devices, String[] command, LocalDateTime dateTime) {
        if (command.length != 3) {
            return "ERROR: Erroneous command!\n";
        }
        if (findDevice(devices, command[1]) == null) {
            return "ERROR: There is not such a device!\n";
        }
        if (checkStatus(command[2])) {
            return "ERROR: Erroneous command!\n";
        }
        Device device = findDevice(devices, command[1]);
        boolean deviceStatus = device.getStatus();
        boolean status = command[2].equalsIgnoreCase("ON");
        if (status != deviceStatus) {
            device.setStopTime(dateTime);
            device.setStatus(status);
            insertionSort(devices);
            device.setStartTime(dateTime);
            return "";
        } else {
            String currentStatus = status ? "on" : "off";
            return String.format("ERROR: This device is already switched %s!\n", currentStatus);
        }
    }

    /**
     * Sets the kelvin value of a SmartLamp device, given a command string and a list of devices.
     * If the command string is not well-formed or the device is not found or not a SmartLamp,
     * an error message is returned instead.
     *
     * @param devices a list of devices to search for the target device
     * @param command an array of three strings, where the first element is a command keyword,
     *                the second element is the name of the target device, and the third element
     *                is the kelvin value to set
     * @return an empty string if the kelvin value was set successfully, or an error message
     *         otherwise
     */
    public static String setKelvin(ArrayList<Device> devices, String[] command) {
        if (command.length != 3) {
            return "ERROR: Erroneous command!\n";
        }
        if (findDevice(devices, command[1]) == null) {
            return "ERROR: There is not such a device!\n";
        }
        Device device = findDevice(devices, command[1]);
        if (!(device instanceof SmartLamp)) {
            return "ERROR: This device is not a smart lamp!\n";
        }
        if (checkIntNumber(command[2])) {
            return "ERROR: Erroneous command!\n";
        }
        if (6500 < Integer.parseInt(command[2]) || Integer.parseInt(command[2]) < 2000) {
            return "ERROR: Kelvin value must be in range of 2000K-6500K!\n";
        }
        SmartLamp lamp = (SmartLamp) device;
        lamp.setKelvin(command[2]);
        return "";
    }

    /**
     * Sets the brightness value of a SmartLamp device, given a command string and a list of devices.
     * If the command string is not well-formed or the device is not found or not a SmartLamp,
     * an error message is returned instead.
     *
     * @param devices a list of devices to search for the target device
     * @param command an array of three strings, where the first element is a command keyword,
     *                the second element is the name of the target device, and the third element
     *                is the brightness value to set
     * @return an empty string if the brightness value was set successfully, or an error message
     *         otherwise
     */
    public static String setBrightness(ArrayList<Device> devices, String[] command) {
        if (command.length != 3) {
            return "ERROR: Erroneous command!\n";
        }
        if (findDevice(devices, command[1]) == null) {
            return "ERROR: There is not such a device!\n";
        }
        Device device = findDevice(devices, command[1]);
        if (!(device instanceof SmartLamp)) {
            return "ERROR: This device is not a smart lamp!\n";
        }
        if (checkIntNumber(command[2])) {
            return "ERROR: Erroneous command!\n";
        }
        if (100 < Integer.parseInt(command[2]) || Integer.parseInt(command[2]) < 0) {
            return "ERROR: Brightness must be in range of 0%-100%!\n";
        }
        SmartLamp lamp = (SmartLamp) device;
        lamp.setBrightness(Integer.parseInt(command[2]));
        return "";
    }

    /**
     * Sets the color of a SmartColorLamp device using a hexadecimal color code,
     * given a command string and a list of devices. If the command string is not
     * well-formed or the device is not found or not a SmartColorLamp, an error
     * message is returned instead.
     *
     * @param devices a list of devices to search for the target device
     * @param command an array of three strings, where the first element is a command keyword,
     *                the second element is the name of the target device, and the third element
     *                is the hexadecimal color code to set
     * @return an empty string if the color code was set successfully, or an error message
     *         otherwise
     */
    public static String setColorCode(ArrayList<Device> devices, String[] command) {
        if (command.length != 3) {
            return "ERROR: Erroneous command!\n";
        }
        if (findDevice(devices, command[1]) == null) {
            return "ERROR: There is not such a device!\n";
        }
        Device device = findDevice(devices, command[1]);
        if (!(device instanceof SmartColorLamp)) {
            return "ERROR: This device is not a smart color lamp!\n";
        }
        if (checkHexadecimal(command[2])) {
            return "ERROR: Erroneous command!\n";
        }
        int temp = Integer.parseInt(command[2].substring(2), 16);
        if (0 > temp || temp > 16777215) {
            return "ERROR: Color code value must be in range of 0x0-0xFFFFFF!\n";
        }
        SmartLamp lamp = (SmartColorLamp) device;
        lamp.setKelvin(command[2]);
        return "";
    }

    /**
     * Sets the temperature and brightness of a smart lamp to white color.
     *
     * @param devices the ArrayList of devices to search the device from
     * @param command the command that includes the name of the device,
     *                the temperature value and brightness value in percent
     * @return an empty string if the command is successful, or an error message otherwise
     */
    public static String setWhite(ArrayList<Device> devices, String[] command) {
        if (command.length != 4 || checkIntNumber(command[2]) || checkIntNumber(command[3])) {
            return "ERROR: Erroneous command!\n";
        }
        if (findDevice(devices, command[1]) == null) {
            return "ERROR: There is not such a device!\n";
        }
        Device device = findDevice(devices, command[1]);
        if (!(device instanceof SmartLamp)) {
            return "ERROR: This device is not a smart lamp!\n";
        }
        if (checkIntNumber(command[2])) {
            return "ERROR: Erroneous command!\n";
        }
        if (6500 < Integer.parseInt(command[2]) || Integer.parseInt(command[2]) < 2000) {
            return "ERROR: Kelvin value must be in range of 2000K-6500K!\n";
        }
        if (checkIntNumber(command[3])) {
            return "ERROR: Erroneous command!\n";
        }
        if (100 < Integer.parseInt(command[3]) || Integer.parseInt(command[3]) < 0) {
            return "ERROR: Brightness must be in range of 0%-100%!\n";
        }
        SmartLamp lamp = (SmartLamp) device;
        lamp.setKelvin(command[2]);
        lamp.setBrightness(Integer.parseInt(command[3]));
        return "";
    }

    /**
     * Sets the color and brightness of a smart color lamp.
     *
     * @param devices an ArrayList of Device objects
     * @param command a String array of command parameters
     * @return an empty String if the command is executed successfully, an error message otherwise
     */
    public static String setColor(ArrayList<Device> devices, String[] command) {
        if (command.length != 4) {
            return "ERROR: Erroneous command!\n";
        }
        if (findDevice(devices, command[1]) == null) {
            return "ERROR: There is not such a device!\n";
        }
        Device device = findDevice(devices, command[1]);
        if (!(device instanceof SmartColorLamp)) {
            return "ERROR: This device is not a smart color lamp!\n";
        }
        if (checkHexadecimal(command[2])) {
            return "ERROR: Erroneous command!\n";
        }
        int temp = Integer.parseInt(command[2].substring(2), 16);
        if (0 > temp || temp > 16777215) {
            return "ERROR: Color code value must be in range of 0x0-0xFFFFFF!\n";
        }
        if (checkIntNumber(command[3])) {
            return "ERROR: Erroneous command!\n";
        }
        if (100 < Integer.parseInt(command[3]) || Integer.parseInt(command[3]) < 0) {
            return "ERROR: Brightness must be in range of 0%-100%!\n";
        }
        SmartLamp lamp = (SmartColorLamp) device;
        lamp.setKelvin(command[2]);
        lamp.setBrightness(Integer.parseInt(command[3]));
        return "";
    }

    /**
     * Plugs in a device to a smart plug.
     *
     * @param devices an ArrayList of Device objects
     * @param command a String array of command parameters
     * @param dateTime the current date and time
     * @return an empty String if the command is executed successfully, an error message otherwise
     */
    public static String plugIn(ArrayList<Device> devices, String[] command, LocalDateTime dateTime) {
        if (command.length != 3) {
            return "ERROR: Erroneous command!\n";
        }
        if (findDevice(devices, command[1]) == null) {
            return "ERROR: There is not such a device!\n";
        }
        Device device = findDevice(devices, command[1]);
        if (!(device instanceof SmartPlug)) {
            return "ERROR: This device is not a smart plug!\n";
        }
        SmartPlug plug = (SmartPlug) device;
        if (plug.getAmpere() != 0) {
            return "ERROR: There is already an item plugged in to that plug!\n";
        }
        if (checkFloatNumber(command[2])) {
            return "ERROR: Erroneous command!\n";
        }
        if (Float.parseFloat(command[2]) < 0) {
            return "ERROR: Ampere value must be a positive number!\n";
        }
        plug.setStopTime(dateTime);
        plug.setAmpere(Float.parseFloat(command[2]));
        plug.setStartTime(dateTime);
        return "";
    }

    /**
     * Unplugs a device from a smart plug.
     *
     * @param devices an ArrayList of Device objects
     * @param command a String array of command parameters
     * @param dateTime the current date and time
     * @return an empty String if the command is executed successfully, an error message otherwise
     */
    public static String plugOut(ArrayList<Device> devices, String[] command, LocalDateTime dateTime) {
        if (command.length != 2) {
            return "ERROR: Erroneous command!\n";
        }
        if (findDevice(devices, command[1]) == null) {
            return "ERROR: There is not such a device!\n";
        }
        Device device = findDevice(devices, command[1]);
        if (!(device instanceof SmartPlug)) {
            return "ERROR: This device is not a smart plug!\n";
        }
        SmartPlug plug = (SmartPlug) device;
        if (plug.getAmpere() == 0) {
            return "ERROR: This plug has no item to plug out from that plug!\n";
        }
        plug.setStopTime(dateTime);
        plug.setAmpere(0);
        plug.setStartTime(dateTime);
        return "";
    }

    /**
     * Changes the name of a smart device.
     *
     * @param devices an ArrayList of Device objects containing all the smart devices.
     * @param command a String array containing the command arguments.
     * The first argument is the name of the device to be renamed, and the second argument is the new name.
     * @return an empty String if the name change is successful, otherwise an error message.
     */
    public static String changeName(ArrayList<Device> devices, String[] command) {
        if (command.length != 3) {
            return "ERROR: Erroneous command!\n";
        }
        if (command[2].equals(command[1])) {
            return "ERROR: Both of the names are the same, nothing changed!\n";
        }
        if (findDevice(devices, command[1]) == null) {
            return "ERROR: There is not such a device!\n";
        }
        if (findDevice(devices, command[2]) != null) {
            return "ERROR: There is already a smart device with same name!\n";
        }
        Device device = findDevice(devices, command[1]);
        device.setName(command[2]);
        return "";
    }

    /**
     * Performs the nop command, checking if there is anything to switch.
     *
     * @param devices a list of smart devices
     * @param command the command to be executed
     * @return an empty string if there is something to switch, an error message otherwise
     */
    public static String nop(ArrayList<Device> devices, String[] command) {
        if (command.length != 1) {
            return "ERROR: Erroneous command!\n";
        } else if (devices.size() == 0) {
            return "ERROR: There is nothing to switch!\n";
        } else if (devices.get(0).getSwitchTime() != null) {
            return "";
        } else {
            return "ERROR: There is nothing to switch!\n";
        }
    }
}