import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        // Read the input file
        String[] input = FileInput.readFile(args[0], true, true);

        // Initialize variables
        ArrayList<Device> devices = new ArrayList<>();
        String output = "";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");
        DateTimeFormatter takenFormat = DateTimeFormatter.ofPattern("uuuu-M-d[_H:m[:s]]");
        LocalDateTime dateTime = null;

        // Process each command
        for (int i = 0; i < input.length; i++) {
            String line = input[i];
            String[] command = line.split("\t");
            output += String.format("COMMAND: %s\n", line);
            String tempOut;
            // Check if this is the first command in the file, which sets the initial time
            if (i == 0) {
                tempOut = Functions.checkSetInitialTime(command, takenFormat, formatter);
                output += tempOut;
                if (tempOut.contains("ERROR")) {
                    break;
                }
                dateTime = LocalDateTime.parse(command[1], takenFormat);
                continue;
            }
            // Process the command based on its type
            switch (command[0]) {
                case "SetTime":
                    tempOut = Functions.checkSetTime(command, takenFormat, dateTime);
                    output += tempOut;
                    if (tempOut.equals("")) {
                        dateTime = LocalDateTime.parse(command[1], takenFormat);
                        Functions.checkDevices(devices, dateTime);
                    }
                    break;
                case "SkipMinutes":
                    tempOut = Functions.checkSkipMinutes(command);
                    output += tempOut;
                    if (tempOut.equals("")) {
                        dateTime = dateTime.plusMinutes(Integer.parseInt(command[1]));
                        Functions.checkDevices(devices, dateTime);
                    }
                    break;
                case "Nop":
                    tempOut = Functions.nop(devices, command);
                    output += tempOut;
                    if (tempOut.equals("")) {
                        dateTime = devices.get(0).getSwitchTime();
                        Functions.checkDevices(devices, dateTime);
                    }
                    break;
                case "Add":
                    output += Functions.addDevice(command, devices, dateTime);
                    break;
                case "Remove":
                    output += Functions.removeDevice(devices, command, takenFormat, dateTime);
                    break;
                case "SetSwitchTime":
                    output += Functions.setSwitchTime(devices, command, takenFormat, dateTime);
                    break;
                case "Switch":
                    output += Functions.switchDevice(devices, command, dateTime);
                    break;
                case "ChangeName":
                    output += Functions.changeName(devices, command);
                    break;
                case "PlugIn":
                    output += Functions.plugIn(devices, command, dateTime);
                    break;
                case "PlugOut":
                    output += Functions.plugOut(devices, command, dateTime);
                    break;
                case "SetKelvin":
                    output += Functions.setKelvin(devices, command);
                    break;
                case "SetBrightness":
                    output += Functions.setBrightness(devices, command);
                    break;
                case "SetColorCode":
                    output += Functions.setColorCode(devices, command);
                    break;
                case "SetWhite":
                    output += Functions.setWhite(devices, command);
                    break;
                case "SetColor":
                    output += Functions.setColor(devices, command);
                    break;
                case "ZReport":
                    output += String.format("Time is:\t%s\n", dateTime.format(formatter));
                    for (Device dev : devices) {
                        output += Functions.findDeviceType(dev, formatter);
                    }
                    break;
                default:
                    output += "ERROR: Erroneous command!\n";
                    break;
            }
            // Checks if this is the last command in the file, which should be zreport
            if (i == input.length - 1 && !command[0].equals("ZReport")) {
                output += String.format("ZReport:\nTime is:\t%s\n", dateTime.format(formatter));
                for (Device dev : devices) {
                    output += Functions.findDeviceType(dev, formatter);
                }
            }
        }
        // Write into the output file
        FileOutput.writeToFile(args[1], output, false, false);
    }
}