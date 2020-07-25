package alexsimi.com.github.nailsalon.utils;

public class Validation {

    public static String removeCommaFromTextFields(String s)
    {
        String modifiedString = s.replaceAll(","," ");
        return modifiedString;
    }
}
