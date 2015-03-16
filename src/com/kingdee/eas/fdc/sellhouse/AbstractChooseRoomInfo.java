package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractChooseRoomInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractChooseRoomInfo()
    {
        this("id");
    }
    protected AbstractChooseRoomInfo(String pkField)
    {
        super(pkField);
        put("customerEntry", new com.kingdee.eas.fdc.sellhouse.ChooseRoomCusEntryCollection());
    }
    /**
     * Object: 选房单 's 房间信息 property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomInfo getRoom()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomInfo)get("room");
    }
    public void setRoom(com.kingdee.eas.fdc.sellhouse.RoomInfo item)
    {
        put("room", item);
    }
    /**
     * Object: 选房单 's 售楼客户 property 
     */
    public com.kingdee.eas.fdc.sellhouse.ChooseRoomCusEntryCollection getCustomerEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.ChooseRoomCusEntryCollection)get("customerEntry");
    }
    /**
     * Object: 选房单 's 销售项目 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellProjectInfo getProject()
    {
        return (com.kingdee.eas.fdc.sellhouse.SellProjectInfo)get("project");
    }
    public void setProject(com.kingdee.eas.fdc.sellhouse.SellProjectInfo item)
    {
        put("project", item);
    }
    /**
     * Object: 选房单 's 销售人 property 
     */
    public com.kingdee.eas.base.permission.UserInfo getSalesMan()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("salesMan");
    }
    public void setSalesMan(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("salesMan", item);
    }
    /**
     * Object: 选房单 's 预留单 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo getSincerityPurchase()
    {
        return (com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo)get("sincerityPurchase");
    }
    public void setSincerityPurchase(com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo item)
    {
        put("sincerityPurchase", item);
    }
    /**
     * Object:选房单's 选房人property 
     */
    public String getChooser()
    {
        return getString("chooser");
    }
    public void setChooser(String item)
    {
        setString("chooser", item);
    }
    /**
     * Object:选房单's 联系电话property 
     */
    public String getLinkPhone()
    {
        return getString("linkPhone");
    }
    public void setLinkPhone(String item)
    {
        setString("linkPhone", item);
    }
    /**
     * Object:选房单's 选房单状态property 
     */
    public com.kingdee.eas.fdc.sellhouse.ChooseRoomStateEnum getChooseRoomStateEnum()
    {
        return com.kingdee.eas.fdc.sellhouse.ChooseRoomStateEnum.getEnum(getString("chooseRoomStateEnum"));
    }
    public void setChooseRoomStateEnum(com.kingdee.eas.fdc.sellhouse.ChooseRoomStateEnum item)
    {
		if (item != null) {
        setString("chooseRoomStateEnum", item.getValue());
		}
    }
    /**
     * Object:选房单's 选房单号property 
     */
    public int getChooseRoomNo()
    {
        return getInt("chooseRoomNo");
    }
    public void setChooseRoomNo(int item)
    {
        setInt("chooseRoomNo", item);
    }
    /**
     * Object: 选房单 's 线索客户 property 
     */
    public com.kingdee.eas.fdc.sellhouse.CluesManageInfo getCluesCus()
    {
        return (com.kingdee.eas.fdc.sellhouse.CluesManageInfo)get("cluesCus");
    }
    public void setCluesCus(com.kingdee.eas.fdc.sellhouse.CluesManageInfo item)
    {
        put("cluesCus", item);
    }
    /**
     * Object:选房单's 选房客户组合property 
     */
    public String getCusStr()
    {
        return getString("cusStr");
    }
    public void setCusStr(String item)
    {
        setString("cusStr", item);
    }
    /**
     * Object:选房单's 选房序号property 
     */
    public String getChooseNo()
    {
        return getString("chooseNo");
    }
    public void setChooseNo(String item)
    {
        setString("chooseNo", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("B45858AD");
    }
}