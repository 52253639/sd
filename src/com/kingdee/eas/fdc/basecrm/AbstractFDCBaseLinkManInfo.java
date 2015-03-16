package com.kingdee.eas.fdc.basecrm;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCBaseLinkManInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractFDCBaseLinkManInfo()
    {
        this("id");
    }
    protected AbstractFDCBaseLinkManInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:联系人基类's 是否关联客户property 
     */
    public boolean isIsAssociation()
    {
        return getBoolean("isAssociation");
    }
    public void setIsAssociation(boolean item)
    {
        setBoolean("isAssociation", item);
    }
    /**
     * Object:联系人基类's 关联类型property 
     */
    public com.kingdee.eas.fdc.basecrm.AssociationTypeEnum getAssociationType()
    {
        return com.kingdee.eas.fdc.basecrm.AssociationTypeEnum.getEnum(getString("associationType"));
    }
    public void setAssociationType(com.kingdee.eas.fdc.basecrm.AssociationTypeEnum item)
    {
		if (item != null) {
        setString("associationType", item.getValue());
		}
    }
    /**
     * Object:联系人基类's 紧急联络人property 
     */
    public boolean isIsEmergencyContact()
    {
        return getBoolean("isEmergencyContact");
    }
    public void setIsEmergencyContact(boolean item)
    {
        setBoolean("isEmergencyContact", item);
    }
    /**
     * Object:联系人基类's 委托人property 
     */
    public boolean isIsPrincipal()
    {
        return getBoolean("isPrincipal");
    }
    public void setIsPrincipal(boolean item)
    {
        setBoolean("isPrincipal", item);
    }
    /**
     * Object:联系人基类's 委托书编号property 
     */
    public String getPrincipalNo()
    {
        return getString("principalNo");
    }
    public void setPrincipalNo(String item)
    {
        setString("principalNo", item);
    }
    /**
     * Object:联系人基类's 关系property 
     */
    public String getRelation()
    {
        return getString("relation");
    }
    public void setRelation(String item)
    {
        setString("relation", item);
    }
    /**
     * Object:联系人基类's 性别property 
     */
    public com.kingdee.eas.fdc.sellhouse.SexEnum getSex()
    {
        return com.kingdee.eas.fdc.sellhouse.SexEnum.getEnum(getString("sex"));
    }
    public void setSex(com.kingdee.eas.fdc.sellhouse.SexEnum item)
    {
		if (item != null) {
        setString("sex", item.getValue());
		}
    }
    /**
     * Object:联系人基类's 移动电话property 
     */
    public String getPhone()
    {
        return getString("phone");
    }
    public void setPhone(String item)
    {
        setString("phone", item);
    }
    /**
     * Object:联系人基类's 住宅电话property 
     */
    public String getTel()
    {
        return getString("tel");
    }
    public void setTel(String item)
    {
        setString("tel", item);
    }
    /**
     * Object:联系人基类's 办公电话property 
     */
    public String getOfficeTel()
    {
        return getString("officeTel");
    }
    public void setOfficeTel(String item)
    {
        setString("officeTel", item);
    }
    /**
     * Object:联系人基类's 传真property 
     */
    public String getFax()
    {
        return getString("fax");
    }
    public void setFax(String item)
    {
        setString("fax", item);
    }
    /**
     * Object:联系人基类's 其他电话property 
     */
    public String getOtherTel()
    {
        return getString("otherTel");
    }
    public void setOtherTel(String item)
    {
        setString("otherTel", item);
    }
    /**
     * Object:联系人基类's 电子邮件property 
     */
    public String getEmail()
    {
        return getString("email");
    }
    public void setEmail(String item)
    {
        setString("email", item);
    }
    /**
     * Object:联系人基类's 证件号码property 
     */
    public String getCertificateNumber()
    {
        return getString("certificateNumber");
    }
    public void setCertificateNumber(String item)
    {
        setString("certificateNumber", item);
    }
    /**
     * Object:联系人基类's 出生日期property 
     */
    public String getDayOfBirth()
    {
        return getString("dayOfBirth");
    }
    public void setDayOfBirth(String item)
    {
        setString("dayOfBirth", item);
    }
    /**
     * Object:联系人基类's 通信地址property 
     */
    public String getMailAddress()
    {
        return getString("mailAddress");
    }
    public void setMailAddress(String item)
    {
        setString("mailAddress", item);
    }
    /**
     * Object:联系人基类's 证件地址property 
     */
    public String getBookedAddress()
    {
        return getString("bookedAddress");
    }
    public void setBookedAddress(String item)
    {
        setString("bookedAddress", item);
    }
    /**
     * Object:联系人基类's 邮政编码property 
     */
    public String getPostalCode()
    {
        return getString("postalCode");
    }
    public void setPostalCode(String item)
    {
        setString("postalCode", item);
    }
    /**
     * Object: 联系人基类 's 证件类型 property 
     */
    public com.kingdee.eas.fdc.basecrm.FDCCusBaseDataInfo getCertificateType()
    {
        return (com.kingdee.eas.fdc.basecrm.FDCCusBaseDataInfo)get("certificateType");
    }
    public void setCertificateType(com.kingdee.eas.fdc.basecrm.FDCCusBaseDataInfo item)
    {
        put("certificateType", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("E633C0A6");
    }
}