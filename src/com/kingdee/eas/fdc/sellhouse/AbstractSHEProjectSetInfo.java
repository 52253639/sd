package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSHEProjectSetInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractSHEProjectSetInfo()
    {
        this("id");
    }
    protected AbstractSHEProjectSetInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 售楼设置-项目设置 's 头 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SHEFunctionSetInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.SHEFunctionSetInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.SHEFunctionSetInfo item)
    {
        put("head", item);
    }
    /**
     * Object: 售楼设置-项目设置 's 项目 property 
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
     * Object:售楼设置-项目设置's 成交总价是否允许修改property 
     */
    public boolean isIsUpdateForAmount()
    {
        return getBoolean("isUpdateForAmount");
    }
    public void setIsUpdateForAmount(boolean item)
    {
        setBoolean("isUpdateForAmount", item);
    }
    /**
     * Object:售楼设置-项目设置's 是否启用折扣方案property 
     */
    public boolean isIsUseAgioScheme()
    {
        return getBoolean("isUseAgioScheme");
    }
    public void setIsUseAgioScheme(boolean item)
    {
        setBoolean("isUseAgioScheme", item);
    }
    /**
     * Object:售楼设置-项目设置's 是否允许调整折扣明细property 
     */
    public boolean isIsUpdateForAgioDetail()
    {
        return getBoolean("isUpdateForAgioDetail");
    }
    public void setIsUpdateForAgioDetail(boolean item)
    {
        setBoolean("isUpdateForAgioDetail", item);
    }
    /**
     * Object:售楼设置-项目设置's 是否底价控制property 
     */
    public boolean isIsBasePriceControl()
    {
        return getBoolean("isBasePriceControl");
    }
    public void setIsBasePriceControl(boolean item)
    {
        setBoolean("isBasePriceControl", item);
    }
    /**
     * Object:售楼设置-项目设置's 是否贷款类款项必须银行放款property 
     */
    public boolean isIsBank()
    {
        return getBoolean("isBank");
    }
    public void setIsBank(boolean item)
    {
        setBoolean("isBank", item);
    }
    /**
     * Object:售楼设置-项目设置's 成交价是否以标准总价为准property 
     */
    public boolean isIsStandardTotalAmount()
    {
        return getBoolean("isStandardTotalAmount");
    }
    public void setIsStandardTotalAmount(boolean item)
    {
        setBoolean("isStandardTotalAmount", item);
    }
    /**
     * Object:售楼设置-项目设置's 退房是否必须价格审核property 
     */
    public boolean isIsPriceAuditedForQuitRoom()
    {
        return getBoolean("isPriceAuditedForQuitRoom");
    }
    public void setIsPriceAuditedForQuitRoom(boolean item)
    {
        setBoolean("isPriceAuditedForQuitRoom", item);
    }
    /**
     * Object:售楼设置-项目设置's 换房是否必须价格审核property 
     */
    public boolean isIsPriceAuditedForChanceRoom()
    {
        return getBoolean("isPriceAuditedForChanceRoom");
    }
    public void setIsPriceAuditedForChanceRoom(boolean item)
    {
        setBoolean("isPriceAuditedForChanceRoom", item);
    }
    /**
     * Object:售楼设置-项目设置's 面积变更是否必须价格审核property 
     */
    public boolean isIsPriceAuditedForAreaChance()
    {
        return getBoolean("isPriceAuditedForAreaChance");
    }
    public void setIsPriceAuditedForAreaChance(boolean item)
    {
        setBoolean("isPriceAuditedForAreaChance", item);
    }
    /**
     * Object:售楼设置-项目设置's 业务归属日期是否允许编辑property 
     */
    public boolean isIsUpdateForbelongDate()
    {
        return getBoolean("isUpdateForbelongDate");
    }
    public void setIsUpdateForbelongDate(boolean item)
    {
        setBoolean("isUpdateForbelongDate", item);
    }
    /**
     * Object:售楼设置-项目设置's 换房业务默认业务归属日期property 
     */
    public com.kingdee.eas.fdc.sellhouse.BizDateToEnum getChangeRoomDefaultBelongDate()
    {
        return com.kingdee.eas.fdc.sellhouse.BizDateToEnum.getEnum(getString("changeRoomDefaultBelongDate"));
    }
    public void setChangeRoomDefaultBelongDate(com.kingdee.eas.fdc.sellhouse.BizDateToEnum item)
    {
		if (item != null) {
        setString("changeRoomDefaultBelongDate", item.getValue());
		}
    }
    /**
     * Object:售楼设置-项目设置's 价格变更业务默认业务归属日期property 
     */
    public com.kingdee.eas.fdc.sellhouse.BizDateToEnum getPriceChangeDefaultBelongDate()
    {
        return com.kingdee.eas.fdc.sellhouse.BizDateToEnum.getEnum(getString("priceChangeDefaultBelongDate"));
    }
    public void setPriceChangeDefaultBelongDate(com.kingdee.eas.fdc.sellhouse.BizDateToEnum item)
    {
		if (item != null) {
        setString("priceChangeDefaultBelongDate", item.getValue());
		}
    }
    /**
     * Object:售楼设置-项目设置's 更名业务默认业务归属日期property 
     */
    public com.kingdee.eas.fdc.sellhouse.BizDateToEnum getChangeNameDefaultBelongDate()
    {
        return com.kingdee.eas.fdc.sellhouse.BizDateToEnum.getEnum(getString("changeNameDefaultBelongDate"));
    }
    public void setChangeNameDefaultBelongDate(com.kingdee.eas.fdc.sellhouse.BizDateToEnum item)
    {
		if (item != null) {
        setString("changeNameDefaultBelongDate", item.getValue());
		}
    }
    /**
     * Object:售楼设置-项目设置's 非可售房源是否允许预约排号property 
     */
    public boolean isIsSincerForNotSell()
    {
        return getBoolean("isSincerForNotSell");
    }
    public void setIsSincerForNotSell(boolean item)
    {
        setBoolean("isSincerForNotSell", item);
    }
    /**
     * Object:售楼设置-项目设置's 是否启用跟进有效期property 
     */
    public boolean isIsUseTrackLimeDate()
    {
        return getBoolean("isUseTrackLimeDate");
    }
    public void setIsUseTrackLimeDate(boolean item)
    {
        setBoolean("isUseTrackLimeDate", item);
    }
    /**
     * Object:售楼设置-项目设置's 客户跟进有效期property 
     */
    public int getCustomerTrackLimitDate()
    {
        return getInt("customerTrackLimitDate");
    }
    public void setCustomerTrackLimitDate(int item)
    {
        setInt("customerTrackLimitDate", item);
    }
    /**
     * Object:售楼设置-项目设置's 商机跟进有效期property 
     */
    public int getCommerTrackLimitDate()
    {
        return getInt("commerTrackLimitDate");
    }
    public void setCommerTrackLimitDate(int item)
    {
        setInt("commerTrackLimitDate", item);
    }
    /**
     * Object:售楼设置-项目设置's 商机有效期property 
     */
    public int getCommerLimitDate()
    {
        return getInt("commerLimitDate");
    }
    public void setCommerLimitDate(int item)
    {
        setInt("commerLimitDate", item);
    }
    /**
     * Object:售楼设置-项目设置's 客户名称重复规则property 
     */
    public com.kingdee.eas.fdc.sellhouse.CusRepeatRuleEnum getNameRepeatRule()
    {
        return com.kingdee.eas.fdc.sellhouse.CusRepeatRuleEnum.getEnum(getString("nameRepeatRule"));
    }
    public void setNameRepeatRule(com.kingdee.eas.fdc.sellhouse.CusRepeatRuleEnum item)
    {
		if (item != null) {
        setString("nameRepeatRule", item.getValue());
		}
    }
    /**
     * Object:售楼设置-项目设置's 客户电话号码重复规则property 
     */
    public com.kingdee.eas.fdc.sellhouse.CusRepeatRuleEnum getPhoneRepeatRule()
    {
        return com.kingdee.eas.fdc.sellhouse.CusRepeatRuleEnum.getEnum(getString("phoneRepeatRule"));
    }
    public void setPhoneRepeatRule(com.kingdee.eas.fdc.sellhouse.CusRepeatRuleEnum item)
    {
		if (item != null) {
        setString("phoneRepeatRule", item.getValue());
		}
    }
    /**
     * Object:售楼设置-项目设置's 客户证件号码重复规则property 
     */
    public com.kingdee.eas.fdc.sellhouse.CusRepeatRuleEnum getCerRepeatRule()
    {
        return com.kingdee.eas.fdc.sellhouse.CusRepeatRuleEnum.getEnum(getString("cerRepeatRule"));
    }
    public void setCerRepeatRule(com.kingdee.eas.fdc.sellhouse.CusRepeatRuleEnum item)
    {
		if (item != null) {
        setString("cerRepeatRule", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("42F0C45E");
    }
}