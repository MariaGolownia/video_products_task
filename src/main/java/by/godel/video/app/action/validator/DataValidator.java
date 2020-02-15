package by.godel.video.app.action.validator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataValidator {

    private static final String NUMBER_REGEX = "^\\d+$";

    private static final Pattern NUMBER_PATTERN = Pattern.compile(NUMBER_REGEX);

    private DataValidator() {
    }

    /**
     * Checks the number  matches the required format.
     *
     * @param number to be checked
     * @throws IncorrectDataException if the number is not from digit
     */
    public static void checkIfNumber(String number) throws IncorrectDataException {
        if (number == null) {
            throw new IncorrectDataException("Number is null!");
        }
            Matcher matcher = NUMBER_PATTERN.matcher(number);
            if (!matcher.matches()) {
                throw new IncorrectDataException("Number: is incorrect");
        }
    }

}
