package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPlanisphereInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractPlanisphereInfo()
    {
        this("id");
    }
    protected AbstractPlanisphereInfo(String pkField)
    {
        super(pkField);
        put("elementEntry", new com.kingdee.eas.fdc.sellhouse.PlanisphereElementEntryCollection());
    }
    /**
     * Object:平面示意图's 类型property 
     */
    public com.kingdee.eas.fdc.sellhouse.PlanisphereTypeEnum getPtype()
    {
        return com.kingdee.eas.fdc.sellhouse.PlanisphereTypeEnum.getEnum(getString("ptype"));
    }
    public void setPtype(com.kingdee.eas.fdc.sellhouse.PlanisphereTypeEnum item)
    {
		if (item != null) {
        setString("ptype", item.getValue());
		}
    }
    /**
     * Object: 平面示意图 's 租售项目 property 
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
     * Object: 平面示意图 's 楼栋 property 
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
     * Object:平面示意图's 是否区分单元property 
     */
    public boolean isIsShowUnit()
    {
        return getBoolean("isShowUnit");
    }
    public void setIsShowUnit(boolean item)
    {
        setBoolean("isShowUnit", item);
    }
    /**
     * Object:平面示意图's 单元property 
     */
    public int getUnit()
    {
        return getInt("unit");
    }
    public void setUnit(int item)
    {
        setInt("unit", item);
    }
    /**
     * Object:平面示意图's 楼层property 
     */
    public int getFloor()
    {
        return getInt("floor");
    }
    public void setFloor(int item)
    {
        setInt("floor", item);
    }
    /**
     * Object:平面示意图's 方格宽度property 
     */
    public int getCellWidth()
    {
        return getInt("cellWidth");
    }
    public void setCellWidth(int item)
    {
        setInt("cellWidth", item);
    }
    /**
     * Object:平面示意图's 方格高度property 
     */
    public int getCellHeigth()
    {
        return getInt("cellHeigth");
    }
    public void setCellHeigth(int item)
    {
        setInt("cellHeigth", item);
    }
    /**
     * Object:平面示意图's 方格横向数目property 
     */
    public int getCellHorizCount()
    {
        return getInt("cellHorizCount");
    }
    public void setCellHorizCount(int item)
    {
        setInt("cellHorizCount", item);
    }
    /**
     * Object:平面示意图's 方格纵向数目property 
     */
    public int getCellVertiCount()
    {
        return getInt("cellVertiCount");
    }
    public void setCellVertiCount(int item)
    {
        setInt("cellVertiCount", item);
    }
    /**
     * Object: 平面示意图 's 元素分录 property 
     */
    public com.kingdee.eas.fdc.sellhouse.PlanisphereElementEntryCollection getElementEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.PlanisphereElementEntryCollection)get("elementEntry");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("BC5F4C72");
    }
}