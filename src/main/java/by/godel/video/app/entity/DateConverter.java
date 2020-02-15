package by.godel.video.app.entity;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DateConverter {
    public static final int MIN_POSSIBLE_YEAR = 1900;
    public static final int MIN_POSSIBLE_MONTH = 1;
    public static final int MAX_POSSIBLE_MONTH = 12;
    public static final int MIN_POSSIBLE_DAY = 1;
    public static final int MAX_POSSIBLE_DAY = 31;
    public static final String STR_REG_DATE="^(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\\d\\d$";

    public static LocalDate converterDateFromString(String strDate) {
        LocalDate dateLocalDate = null;
        String strYear = null;
        String strMonth = null;
        String strDay = null;
        List<Integer> indexSep = new ArrayList<>();

        if (strDate != null && DateConverter.validateDate(strDate)) {
            strDate = strDate.replace('-', '.')
                    .replace('/', '.');
            for (int i = 0; i < strDate.length(); i++) {
                if (strDate.charAt(i) == '.') {
                    indexSep.add(i);
                }
            }
            strDay = strDate.trim().substring(0, indexSep.get(0));
            strMonth = strDate.toString().substring(indexSep.get(0) + 1, indexSep.get(1));
            strYear = strDate.toString().substring(indexSep.get(1) + 1, strDate.length());
            Integer yearInt = Integer.parseInt(strYear);
            Integer monthInt = Integer.parseInt(strMonth);
            Integer dayInt = Integer.parseInt(strDay);
            if (DateConverter.validateDay(dayInt) && DateConverter.validateMonth(monthInt) && DateConverter.validateYear(yearInt))
                dateLocalDate = LocalDate.of(yearInt, monthInt, dayInt);
        }
        return dateLocalDate;
    }

    public static String converterStringFromDate(LocalDate date) {
        String strDate = "";

        if (date != null) {
            strDate=date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        }
        return strDate;
    }

    public static Boolean validateDate (String strDate) {
        Boolean ifDateValidate = false;
        if (strDate != null) {
            if (strDate.trim().matches(STR_REG_DATE)) {
                ifDateValidate = true;
            }
        }
         return ifDateValidate;
    }


    public static Boolean validateYear (int year) {
        Boolean ifCurrentYear = false;
        int yearNow = LocalDate.now().getYear();
        if (year>MIN_POSSIBLE_YEAR && year<=yearNow) {
            ifCurrentYear = true;
        }
        return ifCurrentYear;
    }

    public static Boolean validateMonth (int month) {
        Boolean ifCurrentMonth = false;
        if (month>=MIN_POSSIBLE_MONTH && month<=MAX_POSSIBLE_MONTH) {
            ifCurrentMonth = true;
        }
        return ifCurrentMonth;
    }

    public static Boolean validateDay(int day) {
        Boolean ifCurrentDay = false;
        if (day>=MIN_POSSIBLE_DAY && day<=MAX_POSSIBLE_DAY) {
            ifCurrentDay = true;
        }
        return ifCurrentDay;
    }
}
