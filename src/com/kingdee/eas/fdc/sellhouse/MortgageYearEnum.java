/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class MortgageYearEnum extends StringEnum
{
    public static final String ONE_VALUE = "1";
    public static final String TWO_VALUE = "2";
    public static final String THREE_VALUE = "3";
    public static final String FOUR_VALUE = "4";
    public static final String FIVE_VALUE = "5";
    public static final String SIX_VALUE = "6";
    public static final String SEVEN_VALUE = "7";
    public static final String EIGHT_VALUE = "8";
    public static final String NINE_VALUE = "9";
    public static final String TEN_VALUE = "10";
    public static final String ELEVEN_VALUE = "11";
    public static final String TWELVE_VALUE = "12";
    public static final String THIRTEEN_VALUE = "13";
    public static final String FOURTEEN_VALUE = "14";
    public static final String FIFTEEN_VALUE = "15";
    public static final String SIXTEEN_VALUE = "16";
    public static final String SEVENTEEN_VALUE = "17";
    public static final String EIGHTEEN_VALUE = "18";
    public static final String NINETEEN_VALUE = "19";
    public static final String TWENTY_VALUE = "20";
    public static final String TWENTYFIVE_VALUE = "25";
    public static final String THIRTY_VALUE = "30";

    public static final MortgageYearEnum one = new MortgageYearEnum("one", ONE_VALUE);
    public static final MortgageYearEnum two = new MortgageYearEnum("two", TWO_VALUE);
    public static final MortgageYearEnum three = new MortgageYearEnum("three", THREE_VALUE);
    public static final MortgageYearEnum four = new MortgageYearEnum("four", FOUR_VALUE);
    public static final MortgageYearEnum five = new MortgageYearEnum("five", FIVE_VALUE);
    public static final MortgageYearEnum six = new MortgageYearEnum("six", SIX_VALUE);
    public static final MortgageYearEnum seven = new MortgageYearEnum("seven", SEVEN_VALUE);
    public static final MortgageYearEnum eight = new MortgageYearEnum("eight", EIGHT_VALUE);
    public static final MortgageYearEnum nine = new MortgageYearEnum("nine", NINE_VALUE);
    public static final MortgageYearEnum ten = new MortgageYearEnum("ten", TEN_VALUE);
    public static final MortgageYearEnum eleven = new MortgageYearEnum("eleven", ELEVEN_VALUE);
    public static final MortgageYearEnum twelve = new MortgageYearEnum("twelve", TWELVE_VALUE);
    public static final MortgageYearEnum thirteen = new MortgageYearEnum("thirteen", THIRTEEN_VALUE);
    public static final MortgageYearEnum fourteen = new MortgageYearEnum("fourteen", FOURTEEN_VALUE);
    public static final MortgageYearEnum fifteen = new MortgageYearEnum("fifteen", FIFTEEN_VALUE);
    public static final MortgageYearEnum sixteen = new MortgageYearEnum("sixteen", SIXTEEN_VALUE);
    public static final MortgageYearEnum seventeen = new MortgageYearEnum("seventeen", SEVENTEEN_VALUE);
    public static final MortgageYearEnum eighteen = new MortgageYearEnum("eighteen", EIGHTEEN_VALUE);
    public static final MortgageYearEnum nineteen = new MortgageYearEnum("nineteen", NINETEEN_VALUE);
    public static final MortgageYearEnum twenty = new MortgageYearEnum("twenty", TWENTY_VALUE);
    public static final MortgageYearEnum twentyfive = new MortgageYearEnum("twentyfive", TWENTYFIVE_VALUE);
    public static final MortgageYearEnum thirty = new MortgageYearEnum("thirty", THIRTY_VALUE);

    /**
     * construct function
     * @param String mortgageYearEnum
     */
    private MortgageYearEnum(String name, String mortgageYearEnum)
    {
        super(name, mortgageYearEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static MortgageYearEnum getEnum(String mortgageYearEnum)
    {
        return (MortgageYearEnum)getEnum(MortgageYearEnum.class, mortgageYearEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(MortgageYearEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(MortgageYearEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(MortgageYearEnum.class);
    }
}