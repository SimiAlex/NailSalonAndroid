package alexsimi.com.github.nailsalon.utils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class Validation {

    public static String removeCommaFromTextFields(String s)
    {
        String modifiedString = s.replaceAll(","," ");
        return modifiedString;
    }

    public static boolean validateIdInput(String s)
    {
        try
        {
            Integer.parseInt(s);
            return true;
        }
        catch (NumberFormatException e)
        {
            return false;
        }
    }

    public static boolean validatePriceInput(String s)
    {
        try
        {
            Double.parseDouble(s);
            return true;
        }
        catch (NumberFormatException e)
        {
            return false;
        }
    }

    public static boolean validateNameField(String s)
    {
        if (s.isEmpty() || s.trim().isEmpty())
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public static boolean validateDateField(String s)
    {
        try
        {
            LocalDate.parse(s);
            return true;
        }
        catch (DateTimeParseException e)
        {
            return false;
        }
    }

    public static boolean validateTimeField(String s)
    {
        try
        {
            LocalTime.parse(s);
            return true;
        }
        catch (DateTimeParseException e)
        {
            return false;
        }
    }

}
