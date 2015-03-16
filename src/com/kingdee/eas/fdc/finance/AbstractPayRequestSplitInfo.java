package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPayRequestSplitInfo extends com.kingdee.eas.fdc.basedata.FDCSplitBillEntryInfo implements Serializable 
{
    public AbstractPayRequestSplitInfo()
    {
        this("id");
    }
    protected AbstractPayRequestSplitInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.finance.PayRequestSplitEntryCollection());
    }
    /**
     * Object: 付款申请拆分 's 单据 property 
     */
    public com.kingdee.eas.fdc.contract.PayRequestBillInfo getBill()
    {
        return (com.kingdee.eas.fdc.contract.PayRequestBillInfo)get("bill");
    }
    public void setBill(com.kingdee.eas.fdc.contract.PayRequestBillInfo item)
    {
        put("bill", item);
    }
    /**
     * Object: 付款申请拆分 's 分录 property 
     */
    public com.kingdee.eas.fdc.finance.PayRequestSplitEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.finance.PayRequestSplitEntryCollection)get("entrys");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("0A8C9DA4");
    }
}