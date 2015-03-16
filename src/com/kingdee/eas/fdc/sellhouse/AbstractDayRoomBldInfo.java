package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractDayRoomBldInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractDayRoomBldInfo()
    {
        this("id");
    }
    protected AbstractDayRoomBldInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 月结房间搂栋 's 销售月结 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SaleBalanceInfo getSaleBalance()
    {
        return (com.kingdee.eas.fdc.sellhouse.SaleBalanceInfo)get("saleBalance");
    }
    public void setSaleBalance(com.kingdee.eas.fdc.sellhouse.SaleBalanceInfo item)
    {
        put("saleBalance", item);
    }
    /**
     * Object: 月结房间搂栋 's 会计区间 property 
     */
    public com.kingdee.eas.basedata.assistant.PeriodInfo getPeriod()
    {
        return (com.kingdee.eas.basedata.assistant.PeriodInfo)get("period");
    }
    public void setPeriod(com.kingdee.eas.basedata.assistant.PeriodInfo item)
    {
        put("period", item);
    }
    /**
     * Object: 月结房间搂栋 's 搂栋 property 
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
     * Object:月结房间搂栋's 房间状态property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum getSellState()
    {
        return com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum.getEnum(getString("sellState"));
    }
    public void setSellState(com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum item)
    {
		if (item != null) {
        setString("sellState", item.getValue());
		}
    }
    /**
     * Object:月结房间搂栋's 总套数property 
     */
    public int getRoomCount()
    {
        return getInt("roomCount");
    }
    public void setRoomCount(int item)
    {
        setInt("roomCount", item);
    }
    /**
     * Object:月结房间搂栋's 预测建筑面积property 
     */
    public java.math.BigDecimal getPreArea()
    {
        return getBigDecimal("preArea");
    }
    public void setPreArea(java.math.BigDecimal item)
    {
        setBigDecimal("preArea", item);
    }
    /**
     * Object:月结房间搂栋's 实测建筑面积property 
     */
    public java.math.BigDecimal getActArea()
    {
        return getBigDecimal("actArea");
    }
    public void setActArea(java.math.BigDecimal item)
    {
        setBigDecimal("actArea", item);
    }
    /**
     * Object:月结房间搂栋's 标准总价property 
     */
    public java.math.BigDecimal getStandAmout()
    {
        return getBigDecimal("standAmout");
    }
    public void setStandAmout(java.math.BigDecimal item)
    {
        setBigDecimal("standAmout", item);
    }
    /**
     * Object:月结房间搂栋's 合同数property 
     */
    public int getContractCount()
    {
        return getInt("contractCount");
    }
    public void setContractCount(int item)
    {
        setInt("contractCount", item);
    }
    /**
     * Object:月结房间搂栋's 合同总价property 
     */
    public java.math.BigDecimal getContractAmount()
    {
        return getBigDecimal("contractAmount");
    }
    public void setContractAmount(java.math.BigDecimal item)
    {
        setBigDecimal("contractAmount", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("A53EF05E");
    }
}