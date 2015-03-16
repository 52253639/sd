package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSettlementgGatheringInfo extends com.kingdee.eas.framework.BillBaseInfo implements Serializable 
{
    public AbstractSettlementgGatheringInfo()
    {
        this("id");
    }
    protected AbstractSettlementgGatheringInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:结算方式与收款科目对照表's 是否生成凭证property 
     */
    public boolean isFivouchered()
    {
        return getBoolean("Fivouchered");
    }
    public void setFivouchered(boolean item)
    {
        setBoolean("Fivouchered", item);
    }
    /**
     * Object: 结算方式与收款科目对照表 's 结算方式 property 
     */
    public com.kingdee.eas.basedata.assistant.SettlementTypeInfo getBalance()
    {
        return (com.kingdee.eas.basedata.assistant.SettlementTypeInfo)get("balance");
    }
    public void setBalance(com.kingdee.eas.basedata.assistant.SettlementTypeInfo item)
    {
        put("balance", item);
    }
    /**
     * Object: 结算方式与收款科目对照表 's 银行账户 property 
     */
    public com.kingdee.eas.basedata.assistant.AccountBankInfo getBankAccount()
    {
        return (com.kingdee.eas.basedata.assistant.AccountBankInfo)get("bankAccount");
    }
    public void setBankAccount(com.kingdee.eas.basedata.assistant.AccountBankInfo item)
    {
        put("bankAccount", item);
    }
    /**
     * Object: 结算方式与收款科目对照表 's 收款科目 property 
     */
    public com.kingdee.eas.basedata.master.account.AccountViewInfo getGathering()
    {
        return (com.kingdee.eas.basedata.master.account.AccountViewInfo)get("Gathering");
    }
    public void setGathering(com.kingdee.eas.basedata.master.account.AccountViewInfo item)
    {
        put("Gathering", item);
    }
    /**
     * Object: 结算方式与收款科目对照表 's 组织 property 
     */
    public com.kingdee.eas.basedata.org.FullOrgUnitInfo getOrgUnit()
    {
        return (com.kingdee.eas.basedata.org.FullOrgUnitInfo)get("orgUnit");
    }
    public void setOrgUnit(com.kingdee.eas.basedata.org.FullOrgUnitInfo item)
    {
        put("orgUnit", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("B2E12CA4");
    }
}