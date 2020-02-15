package by.godel.video.app.action.validator;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The class contains basic client-side validation methods for users.
 */

public class HumanDataValidator {
    private static final String NAME_SURNAME_REGEX = "[A-Za-zА-Яа-я]{3,255}";
    //Regular expression that takes into account the number of days in a month and the leap year
    private static final String DATE_REGEX = "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$";
    private static final int HUMAN_AGE_MIN = 5;
    private static final int HUMAN_AGE_MAX = 100;

    private static final Pattern NAME_SURNAME_PATTERN = Pattern.compile(NAME_SURNAME_REGEX);
    private static final Pattern DATE_PATTERN = Pattern.compile(DATE_REGEX);

    private HumanDataValidator() {
    }

    /**
     * Checks the name / surname matches the required format.
     *
     * @param name name / surname  to be checked
     * @throws IncorrectDataException if the name / surname  is <code>null</code>,
     *                                or does not represent a String of letters
     *                                without any numbers in length from 3 to 255 symbols.
     */
    public static void checkStr(String name) throws IncorrectDataException {
            Matcher matcher = NAME_SURNAME_PATTERN.matcher(name);
            if (!matcher.matches()) {
                throw new IncorrectDataException("Name: " + name + " is incorrect");
        }
    }


    /**
     * Checks the date matches the required format.
     *
     * @param date to be checked
     * @throws IncorrectDataException if the date is <code>null</code>,
     *                                or does not represent a String of format
     *                                XX.XX.XXXX or XX/XX/XXXX or XX-XX-XXXX.
     *                                Accounting for the number of days in a month and leap year
     */
    public static void checkFormateDate(String date) throws IncorrectDataException {
        Matcher matcher = DATE_PATTERN.matcher(date);
        if (!matcher.matches()) {
            throw new IncorrectDataException("Date: " + date + " is incorrect");
        }
    }
    // Leap check not via regular expression
    // code1
//    public static boolean isLeapYear(int year) {
//        if (year % 4 != 0) {
//            return false;
//        } else if (year % 400 == 0) {
//            return true;
//        } else if (year % 100 == 0) {
//            return false;
//        } else {
//            return true;
//        }
//    }
    //  code 2
//    public static boolean isLeapYear(int year) {
//        Calendar cal = Calendar.getInstance();
//        cal.set(Calendar.YEAR, year);
//        return cal.getActualMaximum(Calendar.DAY_OF_YEAR) > 365;
//    }


    /**
     * Check the date for: the user corresponds to the required age in years
     *
     * @param date to be checked the minimum level
     * @throws IncorrectDataException if user under @param USER_AGE_MIN of age
     */
    public static void checkMinDateBirth(String date) throws IncorrectDataException {
        Integer userYear = Integer.valueOf(date.substring(6));
        Calendar curDate = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        curDate.setTime(new java.util.Date());
        Integer curYear = curDate.get(Calendar.YEAR);
        if ((curYear - userYear) < 0) {
            throw new IncorrectDataException("The human's birth cannot be later than current year!");
        }
        if ((curYear - userYear) < HUMAN_AGE_MIN) {
            throw new IncorrectDataException("The human's age cannot be less than " + HUMAN_AGE_MIN + "!");
        }
    }

    public static void checkMaxDateBirth(String date) throws IncorrectDataException {
        Integer userYear = Integer.valueOf(date.substring(6));
        Calendar curDate = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        curDate.setTime(new java.util.Date());
        Integer curYear = curDate.get(Calendar.YEAR);
        if ((curYear - userYear) > HUMAN_AGE_MAX) {
            throw new IncorrectDataException("The human's age cannot be more than " + HUMAN_AGE_MAX + "!");
        }
    }


}
