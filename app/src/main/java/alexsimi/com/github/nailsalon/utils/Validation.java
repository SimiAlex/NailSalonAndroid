package alexsimi.com.github.nailsalon.utils;

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
}
