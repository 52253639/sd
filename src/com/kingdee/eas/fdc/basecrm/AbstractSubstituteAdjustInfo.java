package com.kingdee.eas.fdc.basecrm;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSubstituteAdjustInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractSubstituteAdjustInfo()
    {
        this("id");
    }
    protected AbstractSubstituteAdjustInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.basecrm.SubstituteAdjustEntryCollection());
    }
    /**
     * Object: 代收费用调整 's 分录 property 
     */
    public com.kingdee.eas.fdc.basecrm.SubstituteAdjustEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.basecrm.SubstituteAdjustEntryCollection)get("entrys");
    }
    /**
     * Object: 代收费用调整 's 销售组织 property 
     */
    public com.kingdee.eas.basedata.org.SaleOrgUnitInfo getSaleOrgUnit()
    {
        return (com.kingdee.eas.basedata.org.SaleOrgUnitInfo)get("saleOrgUnit");
    }
    public void setSaleOrgUnit(com.kingdee.eas.basedata.org.SaleOrgUnitInfo item)
    {
        put("saleOrgUnit", item);
    }
    /**
     * Object: 代收费用调整 's 款项名称 property 
     */
    public com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo getMoneyDefine()
    {
        return (com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo)get("moneyDefine");
    }
    public void setMoneyDefine(com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo item)
    {
        put("moneyDefine", item);
    }
    /**
     * Object: 代收费用调整 's 项目 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellProjectInfo getSellProject()
    {
        return (com.kingdee.eas.fdc.sellhouse.SellProjectInfo)get("sellProject");
    }
    public void setSellProject(com.kingdee.eas.fdc.sellhouse.SellProjectInfo item)
    {
        put("sellProject", item);
    }
    /**
     * Object:代收费用调整's 传递日期property 
     */
    public java.util.Date getTransfAdjustDate()
    {
        return getDate("transfAdjustDate");
    }
    public void setTransfAdjustDate(java.util.Date item)
    {
        setDate("transfAdjustDate", item);
    }
    /**
     * Object: 代收费用调整 's 楼栋 property 
     */
    public com.kingdee.eas.fdc.sellhouse.BuildingInfo getBuilding()
    {
        return (com.kingdee.eas.fdc.sellhouse.BuildingInfo)get("building");
    }
    public void setBuilding(com.kingdee.eas.fdc.sellhouse.BuildingInfo item)
    {
        put("building", item);
    }
    /**
     * Object: 代收费用调整 's 代收费用设置 property 
     */
    public com.kingdee.eas.fdc.sellhouse.CollectionInfo getCollectFunction()
    {
        return (com.kingdee.eas.fdc.sellhouse.CollectionInfo)get("collectFunction");
    }
    public void setCollectFunction(com.kingdee.eas.fdc.sellhouse.CollectionInfo item)
    {
        put("collectFunction", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("F552D025");
    }
}