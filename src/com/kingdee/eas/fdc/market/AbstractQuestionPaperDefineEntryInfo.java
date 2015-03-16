package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractQuestionPaperDefineEntryInfo extends com.kingdee.eas.framework.BillEntryBaseInfo implements Serializable 
{
    public AbstractQuestionPaperDefineEntryInfo()
    {
        this("id");
    }
    protected AbstractQuestionPaperDefineEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 分录 's 单据头 property 
     */
    public com.kingdee.eas.fdc.market.QuestionPaperDefineInfo getParent()
    {
        return (com.kingdee.eas.fdc.market.QuestionPaperDefineInfo)get("Parent");
    }
    public void setParent(com.kingdee.eas.fdc.market.QuestionPaperDefineInfo item)
    {
        put("Parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("1BB8B922");
    }
}