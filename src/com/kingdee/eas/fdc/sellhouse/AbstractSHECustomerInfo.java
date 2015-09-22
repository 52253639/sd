package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSHECustomerInfo extends com.kingdee.eas.fdc.basecrm.FDCBaseCustomerInfo implements Serializable 
{
    public AbstractSHECustomerInfo()
    {
        this("id");
    }
    protected AbstractSHECustomerInfo(String pkField)
    {
        super(pkField);
        put("linkMan", new com.kingdee.eas.fdc.sellhouse.SHECustomerLinkManCollection());
        put("shareProperty", new com.kingdee.eas.fdc.sellhouse.SharePropertyCollection());
        put("shareProject", new com.kingdee.eas.fdc.sellhouse.ShareSellProjectCollection());
    }
    /**
     * Object: ��¥�ͻ� 's ��ҵ���� property 
     */
    public com.kingdee.eas.base.permission.UserInfo getPropertyConsultant()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("propertyConsultant");
    }
    public void setPropertyConsultant(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("propertyConsultant", item);
    }
    /**
     * Object: ��¥�ͻ� 's ������ҵ���� property 
     */
    public com.kingdee.eas.fdc.sellhouse.SharePropertyCollection getShareProperty()
    {
        return (com.kingdee.eas.fdc.sellhouse.SharePropertyCollection)get("shareProperty");
    }
    /**
     * Object: ��¥�ͻ� 's ������Ŀ property 
     */
    public com.kingdee.eas.fdc.sellhouse.ShareSellProjectCollection getShareProject()
    {
        return (com.kingdee.eas.fdc.sellhouse.ShareSellProjectCollection)get("shareProject");
    }
    /**
     * Object: ��¥�ͻ� 's ���ز����ͻ� property 
     */
    public com.kingdee.eas.fdc.basecrm.FDCMainCustomerInfo getMainCustomer()
    {
        return (com.kingdee.eas.fdc.basecrm.FDCMainCustomerInfo)get("mainCustomer");
    }
    public void setMainCustomer(com.kingdee.eas.fdc.basecrm.FDCMainCustomerInfo item)
    {
        put("mainCustomer", item);
    }
    /**
     * Object: ��¥�ͻ� 's ��ϵ�� property 
     */
    public com.kingdee.eas.fdc.sellhouse.SHECustomerLinkManCollection getLinkMan()
    {
        return (com.kingdee.eas.fdc.sellhouse.SHECustomerLinkManCollection)get("linkMan");
    }
    /**
     * Object: ��¥�ͻ� 's ��Դ���� property 
     */
    public com.kingdee.eas.fdc.sellhouse.CluesManageInfo getClues()
    {
        return (com.kingdee.eas.fdc.sellhouse.CluesManageInfo)get("clues");
    }
    public void setClues(com.kingdee.eas.fdc.sellhouse.CluesManageInfo item)
    {
        put("clues", item);
    }
    /**
     * Object: ��¥�ͻ� 's �״νӴ����� property 
     */
    public com.kingdee.eas.base.permission.UserInfo getFirstConsultant()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("firstConsultant");
    }
    public void setFirstConsultant(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("firstConsultant", item);
    }
    /**
     * Object:��¥�ͻ�'s �Ƿ񹫹��ͻ�property 
     */
    public boolean isIsPublic()
    {
        return getBoolean("isPublic");
    }
    public void setIsPublic(boolean item)
    {
        setBoolean("isPublic", item);
    }
    /**
     * Object:��¥�ͻ�'s �Ƽ���property 
     */
    public String getRecommended()
    {
        return getString("recommended");
    }
    public void setRecommended(String item)
    {
        setString("recommended", item);
    }
    /**
     * Object: ��¥�ͻ� 's �Ƽ��� property 
     */
    public com.kingdee.eas.fdc.sellhouse.SHECustomerInfo getF7recommended()
    {
        return (com.kingdee.eas.fdc.sellhouse.SHECustomerInfo)get("f7recommended");
    }
    public void setF7recommended(com.kingdee.eas.fdc.sellhouse.SHECustomerInfo item)
    {
        put("f7recommended", item);
    }
    /**
     * Object:��¥�ͻ�'s �Ƽ�����property 
     */
    public java.util.Date getRecommendDate()
    {
        return getDate("recommendDate");
    }
    public void setRecommendDate(java.util.Date item)
    {
        setDate("recommendDate", item);
    }
    /**
     * Object: ��¥�ͻ� 's �̻����� property 
     */
    public com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo getCommerceLevel()
    {
        return (com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo)get("commerceLevel");
    }
    public void setCommerceLevel(com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo item)
    {
        put("commerceLevel", item);
    }
    /**
     * Object:��¥�ͻ�'s ��������property 
     */
    public java.util.Date getTrackDate()
    {
        return getDate("trackDate");
    }
    public void setTrackDate(java.util.Date item)
    {
        setDate("trackDate", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("F1713EF3");
    }
}