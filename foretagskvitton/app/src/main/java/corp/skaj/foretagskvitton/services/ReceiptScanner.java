package corp.skaj.foretagskvitton.services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 *
 */
public class ReceiptScanner {
    private List<String> listOfStrings;


    /**
     *
     * @param date
     * @return
     */
    // Checks that the string starts with the current year in ex. 17 or 2017.
    private boolean correctFirstNum(String date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy");
        Calendar cal = Calendar.getInstance();
        String year = "2017";
       //String year = dateFormat.format(cal).toString();
        return date.substring(0, 2).equals(year.substring(0, 4)) || date.equals(year);
    }

    /**
     *
     * @param date
     * @return
     */
    // Checks that the size is correct format, either 170218 or 2017-05-03.
    private boolean correctLength(String date) {
        return date.length() <= 10 && date.length() >= 6;
    }

    /**
     *
     * @param listOfStrings
     * @return listOfDoubles
     */
    private List<Double> findAllDoubles(List<String> listOfStrings) {
        List<Double> listOfDoubles = new ArrayList<>();
        for (int i = 0; i < listOfStrings.size(); i++) {
            String s = listOfStrings.get(i).replace("," , ".");
            if (s.contains(".")) {
                if(isDouble(s)) {
                    listOfDoubles.add(Double.parseDouble(s));
                }
            } else {
                
            }
        }
        return listOfDoubles;
    }

    /**
     *
     * @param s
     * @return <code>true</code> if s is a double
     * <code>false</code> otherwise
     */
    public boolean isDouble (String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }


    /**
     *
     * @return <code>true</code> if kr or sek is found
     * <code>false</code> otherwise
     */
    private boolean checkForText () {
        for (int i = 0; i < listOfStrings.size(); i++) {
            if (listOfStrings.get(i).toLowerCase().equals("kr")
                        || listOfStrings.get(i).toLowerCase().equals("sek")
                            || listOfStrings.get(i).toLowerCase().equals("total")
                                || listOfStrings.get(i).toLowerCase().equals("totalt")) {
                return true;
            }
        }
        return false;
    }

    public double checkBeforeAndAfter (int index) {

        return 0.0;
    }

    public String getTotalCost(List<String> listOfStrings) {
        double totalCost = 0;
        this.listOfStrings = listOfStrings;
        List<Double> listOfDoubles = findAllDoubles(listOfStrings);
        //totalCost = findBiggestDouble(listOfDoubles);

        return String.valueOf(Collections.max(listOfDoubles));
        }

    public String getDate(List<String> listOfStrings) { // Gjorde ändring här, vet inte om det var korrekt? // Joakim
        for (int i = 0; i < listOfStrings.size(); i++) {
            if (correctFirstNum(listOfStrings.get(i).substring(0, 4)) && correctLength(listOfStrings.get(i))) {
                return listOfStrings.get(i);
            }
        }
        return "2017-04-28";
        //return Calendar.getInstance().getTime().toString();
    }

    public void getProducts(List<String> listOfStrings) {

    }

    public void getCardNumber() {
    }

}