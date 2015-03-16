package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractShareSellerInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractShareSellerInfo()
    {
        this("id");
    }
    protected AbstractShareSellerInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 共享销售顾问 's 头 property 
     */
    public com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo item)
    {
        put("head", item);
    }
    /**
     * Object:共享销售顾问's 再共享权property 
     */
    public boolean isIsAgainShare()
    {
        return getBoolean("isAgainShare");
    }
    public void setIsAgainShare(boolean item)
    {
        setBoolean("isAgainShare", item);
    }
    /**
     * Object:共享销售顾问's 修改权property 
     */
    public boolean isIsUpdate()
    {
        return getBoolean("isUpdate");
    }
    public void setIsUpdate(boolean item)
    {
        setBoolean("isUpdate", item);
    }
    /**
     * Object: 共享销售顾问 's 销售顾问 property 
     */
    public com.kingdee.eas.base.permission.UserInfo getSeller()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("seller");
    }
    public void setSeller(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("seller", item);
    }
    /**
     * Object: 共享销售顾问 's 共享操作人 property 
     */
    public com.kingdee.eas.base.permission.UserInfo getOperationPerson()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("operationPerson");
    }
    public void setOperationPerson(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("operationPerson", item);
    }
    /**
     * Object:共享销售顾问's 共享日期property 
     */
    public java.util.Date getShareDate()
    {
        return getDate("shareDate");
    }
    public void setShareDate(java.util.Date item)
    {
        setDate("shareDate", item);
    }
    /**
     * Object:共享销售顾问's 共享方式property 
     */
    public com.kingdee.eas.fdc.sellhouse.ShareModelEnum getShareModel()
    {
        return com.kingdee.eas.fdc.sellhouse.ShareModelEnum.getEnum(getString("shareModel"));
    }
    public void setShareModel(com.kingdee.eas.fdc.sellhouse.ShareModelEnum item)
    {
		if (item != null) {
        setString("shareModel", item.getValue());
		}
    }
    /**
     * Object: 共享销售顾问 's 共享营销单元 property 
     */
    public com.kingdee.eas.fdc.tenancy.MarketingUnitInfo getMarketingUnit()
    {
        return (com.kingdee.eas.fdc.tenancy.MarketingUnitInfo)get("marketingUnit");
    }
    public void setMarketingUnit(com.kingdee.eas.fdc.tenancy.MarketingUnitInfo item)
    {
        put("marketingUnit", item);
    }
    /**
     * Object: 共享销售顾问 's 共享组织 property 
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
        return new BOSObjectType("3E9F3A83");
    }
}