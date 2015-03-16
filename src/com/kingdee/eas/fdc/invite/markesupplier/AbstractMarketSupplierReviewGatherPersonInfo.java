package com.kingdee.eas.fdc.invite.markesupplier;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMarketSupplierReviewGatherPersonInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractMarketSupplierReviewGatherPersonInfo()
    {
        this("id");
    }
    protected AbstractMarketSupplierReviewGatherPersonInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 参与评审人员分录 's null property 
     */
    public com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierReviewGatherInfo getParent()
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierReviewGatherInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierReviewGatherInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 参与评审人员分录 's 人员 property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getPerson()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("person");
    }
    public void setPerson(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("person", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("3A506A7E");
    }
}