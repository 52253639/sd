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
public class TransactionStateEnum extends StringEnum
{
    public static final String BAYNUMBER_VALUE = "BayNumber";
    public static final String QUITNUMBER_VALUE = "QuitNumber";
    public static final String SINNULLIFY_VALUE = "sinNullify";
    public static final String TOPRE_VALUE = "ToPre";
    public static final String TOPUR_VALUE = "ToPur";
    public static final String TOSIGN_VALUE = "ToSign";
    public static final String WAITTINGFORDEAL_VALUE = "WaittingForDeal";
    public static final String PREAPPLE_VALUE = "PreApple";
    public static final String PREAUDIT_VALUE = "PreAudit";
    public static final String PRENULLIFY_VALUE = "PreNullify";
    public static final String CHANGENAMEAUDITING_VALUE = "ChangeNameAuditing";
    public static final String QUITROOMAUDITING_VALUE = "QuitRoomAuditing";
    public static final String CHANGEROOMAUDITING_VALUE = "ChangeRoomAuditing";
    public static final String CHANGEPIRCEAUDITING_VALUE = "ChangePirceAuditing";
    public static final String PURAPPLE_VALUE = "PurApple";
    public static final String PURAUDIT_VALUE = "PurAudit";
    public static final String PURNULLIFY_VALUE = "PurNullify";
    public static final String SIGNAPPLE_VALUE = "SignApple";
    public static final String SIGNAUDIT_VALUE = "SignAudit";
    public static final String SIGNNULLIFY_VALUE = "SignNullify";
    public static final String PCNULLIFY_VALUE = "PCNullify";
    public static final String CRNULLIFY_VALUE = "CRNullify";
    public static final String QRNULLIFY_VALUE = "QRNullify";
    public static final String CNNULLIFY_VALUE = "CNNullify";
    public static final String PURAUDITING_VALUE = "PurAuditing";
    public static final String PREAUDITING_VALUE = "PreAuditing";
    public static final String SIGNAUDITING_VALUE = "SignAuditing";
    public static final String PRESAVED_VALUE = "PreSaved";
    public static final String PURSAVED_VALUE = "PurSaved";
    public static final String SIGNSAVED_VALUE = "SignSaved";

    public static final TransactionStateEnum BAYNUMBER = new TransactionStateEnum("BAYNUMBER", BAYNUMBER_VALUE);
    public static final TransactionStateEnum QUITNUMBER = new TransactionStateEnum("QUITNUMBER", QUITNUMBER_VALUE);
    public static final TransactionStateEnum SINNULLIFY = new TransactionStateEnum("SINNULLIFY", SINNULLIFY_VALUE);
    public static final TransactionStateEnum TOPRE = new TransactionStateEnum("TOPRE", TOPRE_VALUE);
    public static final TransactionStateEnum TOPUR = new TransactionStateEnum("TOPUR", TOPUR_VALUE);
    public static final TransactionStateEnum TOSIGN = new TransactionStateEnum("TOSIGN", TOSIGN_VALUE);
    public static final TransactionStateEnum WAITTINGFORDEAL = new TransactionStateEnum("WAITTINGFORDEAL", WAITTINGFORDEAL_VALUE);
    public static final TransactionStateEnum PREAPPLE = new TransactionStateEnum("PREAPPLE", PREAPPLE_VALUE);
    public static final TransactionStateEnum PREAUDIT = new TransactionStateEnum("PREAUDIT", PREAUDIT_VALUE);
    public static final TransactionStateEnum PRENULLIFY = new TransactionStateEnum("PRENULLIFY", PRENULLIFY_VALUE);
    public static final TransactionStateEnum CHANGENAMEAUDITING = new TransactionStateEnum("CHANGENAMEAUDITING", CHANGENAMEAUDITING_VALUE);
    public static final TransactionStateEnum QUITROOMAUDITING = new TransactionStateEnum("QUITROOMAUDITING", QUITROOMAUDITING_VALUE);
    public static final TransactionStateEnum CHANGEROOMAUDITING = new TransactionStateEnum("CHANGEROOMAUDITING", CHANGEROOMAUDITING_VALUE);
    public static final TransactionStateEnum CHANGEPIRCEAUDITING = new TransactionStateEnum("CHANGEPIRCEAUDITING", CHANGEPIRCEAUDITING_VALUE);
    public static final TransactionStateEnum PURAPPLE = new TransactionStateEnum("PURAPPLE", PURAPPLE_VALUE);
    public static final TransactionStateEnum PURAUDIT = new TransactionStateEnum("PURAUDIT", PURAUDIT_VALUE);
    public static final TransactionStateEnum PURNULLIFY = new TransactionStateEnum("PURNULLIFY", PURNULLIFY_VALUE);
    public static final TransactionStateEnum SIGNAPPLE = new TransactionStateEnum("SIGNAPPLE", SIGNAPPLE_VALUE);
    public static final TransactionStateEnum SIGNAUDIT = new TransactionStateEnum("SIGNAUDIT", SIGNAUDIT_VALUE);
    public static final TransactionStateEnum SIGNNULLIFY = new TransactionStateEnum("SIGNNULLIFY", SIGNNULLIFY_VALUE);
    public static final TransactionStateEnum PCNULLIFY = new TransactionStateEnum("PCNULLIFY", PCNULLIFY_VALUE);
    public static final TransactionStateEnum CRNULLIFY = new TransactionStateEnum("CRNULLIFY", CRNULLIFY_VALUE);
    public static final TransactionStateEnum QRNULLIFY = new TransactionStateEnum("QRNULLIFY", QRNULLIFY_VALUE);
    public static final TransactionStateEnum CNNULLIFY = new TransactionStateEnum("CNNULLIFY", CNNULLIFY_VALUE);
    public static final TransactionStateEnum PURAUDITING = new TransactionStateEnum("PURAUDITING", PURAUDITING_VALUE);
    public static final TransactionStateEnum PREAUDITING = new TransactionStateEnum("PREAUDITING", PREAUDITING_VALUE);
    public static final TransactionStateEnum SIGNAUDITING = new TransactionStateEnum("SIGNAUDITING", SIGNAUDITING_VALUE);
    public static final TransactionStateEnum PRESAVED = new TransactionStateEnum("PRESAVED", PRESAVED_VALUE);
    public static final TransactionStateEnum PURSAVED = new TransactionStateEnum("PURSAVED", PURSAVED_VALUE);
    public static final TransactionStateEnum SIGNSAVED = new TransactionStateEnum("SIGNSAVED", SIGNSAVED_VALUE);

    /**
     * construct function
     * @param String transactionStateEnum
     */
    private TransactionStateEnum(String name, String transactionStateEnum)
    {
        super(name, transactionStateEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static TransactionStateEnum getEnum(String transactionStateEnum)
    {
        return (TransactionStateEnum)getEnum(TransactionStateEnum.class, transactionStateEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(TransactionStateEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(TransactionStateEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(TransactionStateEnum.class);
    }
}