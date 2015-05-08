package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractWBSCodeRuleInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractWBSCodeRuleInfo()
    {
        this("id");
    }
    protected AbstractWBSCodeRuleInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.schedule.WBSCodeRuleEntryCollection());
    }
    /**
     * Object: WBS±‡¬ÎπÊ‘Ú 's ∑÷¬º property 
     */
    public com.kingdee.eas.fdc.schedule.WBSCodeRuleEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.schedule.WBSCodeRuleEntryCollection)get("entrys");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("05BAD01B");
    }
}