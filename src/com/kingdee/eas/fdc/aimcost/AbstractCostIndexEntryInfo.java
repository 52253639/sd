package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractCostIndexEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractCostIndexEntryInfo()
    {
        this("id");
    }
    protected AbstractCostIndexEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 造价指标库分录 's 造价指标库 property 
     */
    public com.kingdee.eas.fdc.aimcost.CostIndexInfo getHead()
    {
        return (com.kingdee.eas.fdc.aimcost.CostIndexInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.aimcost.CostIndexInfo item)
    {
        put("head", item);
    }
    /**
     * Object: 造价指标库分录 's 造价指标库配置 property 
     */
    public com.kingdee.eas.fdc.aimcost.CostIndexConfigInfo getConfig()
    {
        return (com.kingdee.eas.fdc.aimcost.CostIndexConfigInfo)get("config");
    }
    public void setConfig(com.kingdee.eas.fdc.aimcost.CostIndexConfigInfo item)
    {
        put("config", item);
    }
    /**
     * Object:造价指标库分录's field0property 
     */
    public String getField0()
    {
        return getString("field0");
    }
    public void setField0(String item)
    {
        setString("field0", item);
    }
    /**
     * Object:造价指标库分录's field1property 
     */
    public String getField1()
    {
        return getString("field1");
    }
    public void setField1(String item)
    {
        setString("field1", item);
    }
    /**
     * Object:造价指标库分录's field2property 
     */
    public String getField2()
    {
        return getString("field2");
    }
    public void setField2(String item)
    {
        setString("field2", item);
    }
    /**
     * Object:造价指标库分录's field3property 
     */
    public String getField3()
    {
        return getString("field3");
    }
    public void setField3(String item)
    {
        setString("field3", item);
    }
    /**
     * Object:造价指标库分录's field4property 
     */
    public String getField4()
    {
        return getString("field4");
    }
    public void setField4(String item)
    {
        setString("field4", item);
    }
    /**
     * Object:造价指标库分录's field5property 
     */
    public String getField5()
    {
        return getString("field5");
    }
    public void setField5(String item)
    {
        setString("field5", item);
    }
    /**
     * Object:造价指标库分录's field6property 
     */
    public String getField6()
    {
        return getString("field6");
    }
    public void setField6(String item)
    {
        setString("field6", item);
    }
    /**
     * Object:造价指标库分录's field7property 
     */
    public String getField7()
    {
        return getString("field7");
    }
    public void setField7(String item)
    {
        setString("field7", item);
    }
    /**
     * Object:造价指标库分录's field8property 
     */
    public String getField8()
    {
        return getString("field8");
    }
    public void setField8(String item)
    {
        setString("field8", item);
    }
    /**
     * Object:造价指标库分录's field9property 
     */
    public String getField9()
    {
        return getString("field9");
    }
    public void setField9(String item)
    {
        setString("field9", item);
    }
    /**
     * Object:造价指标库分录's field10property 
     */
    public String getField10()
    {
        return getString("field10");
    }
    public void setField10(String item)
    {
        setString("field10", item);
    }
    /**
     * Object:造价指标库分录's field11property 
     */
    public String getField11()
    {
        return getString("field11");
    }
    public void setField11(String item)
    {
        setString("field11", item);
    }
    /**
     * Object:造价指标库分录's field12property 
     */
    public String getField12()
    {
        return getString("field12");
    }
    public void setField12(String item)
    {
        setString("field12", item);
    }
    /**
     * Object:造价指标库分录's field13property 
     */
    public String getField13()
    {
        return getString("field13");
    }
    public void setField13(String item)
    {
        setString("field13", item);
    }
    /**
     * Object:造价指标库分录's field14property 
     */
    public String getField14()
    {
        return getString("field14");
    }
    public void setField14(String item)
    {
        setString("field14", item);
    }
    /**
     * Object:造价指标库分录's field15property 
     */
    public String getField15()
    {
        return getString("field15");
    }
    public void setField15(String item)
    {
        setString("field15", item);
    }
    /**
     * Object:造价指标库分录's field16property 
     */
    public String getField16()
    {
        return getString("field16");
    }
    public void setField16(String item)
    {
        setString("field16", item);
    }
    /**
     * Object:造价指标库分录's field17property 
     */
    public String getField17()
    {
        return getString("field17");
    }
    public void setField17(String item)
    {
        setString("field17", item);
    }
    /**
     * Object:造价指标库分录's field18property 
     */
    public String getField18()
    {
        return getString("field18");
    }
    public void setField18(String item)
    {
        setString("field18", item);
    }
    /**
     * Object:造价指标库分录's field19property 
     */
    public String getField19()
    {
        return getString("field19");
    }
    public void setField19(String item)
    {
        setString("field19", item);
    }
    /**
     * Object:造价指标库分录's field20property 
     */
    public String getField20()
    {
        return getString("field20");
    }
    public void setField20(String item)
    {
        setString("field20", item);
    }
    /**
     * Object:造价指标库分录's field21property 
     */
    public String getField21()
    {
        return getString("field21");
    }
    public void setField21(String item)
    {
        setString("field21", item);
    }
    /**
     * Object:造价指标库分录's field22property 
     */
    public String getField22()
    {
        return getString("field22");
    }
    public void setField22(String item)
    {
        setString("field22", item);
    }
    /**
     * Object:造价指标库分录's field23property 
     */
    public String getField23()
    {
        return getString("field23");
    }
    public void setField23(String item)
    {
        setString("field23", item);
    }
    /**
     * Object:造价指标库分录's field24property 
     */
    public String getField24()
    {
        return getString("field24");
    }
    public void setField24(String item)
    {
        setString("field24", item);
    }
    /**
     * Object:造价指标库分录's field25property 
     */
    public String getField25()
    {
        return getString("field25");
    }
    public void setField25(String item)
    {
        setString("field25", item);
    }
    /**
     * Object:造价指标库分录's field26property 
     */
    public String getField26()
    {
        return getString("field26");
    }
    public void setField26(String item)
    {
        setString("field26", item);
    }
    /**
     * Object:造价指标库分录's field27property 
     */
    public String getField27()
    {
        return getString("field27");
    }
    public void setField27(String item)
    {
        setString("field27", item);
    }
    /**
     * Object:造价指标库分录's field28property 
     */
    public String getField28()
    {
        return getString("field28");
    }
    public void setField28(String item)
    {
        setString("field28", item);
    }
    /**
     * Object:造价指标库分录's field29property 
     */
    public String getField29()
    {
        return getString("field29");
    }
    public void setField29(String item)
    {
        setString("field29", item);
    }
    /**
     * Object:造价指标库分录's field30property 
     */
    public String getField30()
    {
        return getString("field30");
    }
    public void setField30(String item)
    {
        setString("field30", item);
    }
    /**
     * Object:造价指标库分录's field31property 
     */
    public String getField31()
    {
        return getString("field31");
    }
    public void setField31(String item)
    {
        setString("field31", item);
    }
    /**
     * Object:造价指标库分录's field32property 
     */
    public String getField32()
    {
        return getString("field32");
    }
    public void setField32(String item)
    {
        setString("field32", item);
    }
    /**
     * Object:造价指标库分录's field33property 
     */
    public String getField33()
    {
        return getString("field33");
    }
    public void setField33(String item)
    {
        setString("field33", item);
    }
    /**
     * Object:造价指标库分录's field34property 
     */
    public String getField34()
    {
        return getString("field34");
    }
    public void setField34(String item)
    {
        setString("field34", item);
    }
    /**
     * Object:造价指标库分录's field35property 
     */
    public String getField35()
    {
        return getString("field35");
    }
    public void setField35(String item)
    {
        setString("field35", item);
    }
    /**
     * Object:造价指标库分录's field36property 
     */
    public String getField36()
    {
        return getString("field36");
    }
    public void setField36(String item)
    {
        setString("field36", item);
    }
    /**
     * Object:造价指标库分录's nullproperty 
     */
    public String getField37()
    {
        return getString("field37");
    }
    public void setField37(String item)
    {
        setString("field37", item);
    }
    /**
     * Object:造价指标库分录's field38property 
     */
    public String getField38()
    {
        return getString("field38");
    }
    public void setField38(String item)
    {
        setString("field38", item);
    }
    /**
     * Object:造价指标库分录's field39property 
     */
    public String getField39()
    {
        return getString("field39");
    }
    public void setField39(String item)
    {
        setString("field39", item);
    }
    /**
     * Object:造价指标库分录's field40property 
     */
    public String getField40()
    {
        return getString("field40");
    }
    public void setField40(String item)
    {
        setString("field40", item);
    }
    /**
     * Object:造价指标库分录's field41property 
     */
    public String getField41()
    {
        return getString("field41");
    }
    public void setField41(String item)
    {
        setString("field41", item);
    }
    /**
     * Object:造价指标库分录's field42property 
     */
    public String getField42()
    {
        return getString("field42");
    }
    public void setField42(String item)
    {
        setString("field42", item);
    }
    /**
     * Object:造价指标库分录's field43property 
     */
    public String getField43()
    {
        return getString("field43");
    }
    public void setField43(String item)
    {
        setString("field43", item);
    }
    /**
     * Object:造价指标库分录's nullproperty 
     */
    public String getField44()
    {
        return getString("field44");
    }
    public void setField44(String item)
    {
        setString("field44", item);
    }
    /**
     * Object:造价指标库分录's field45property 
     */
    public String getField45()
    {
        return getString("field45");
    }
    public void setField45(String item)
    {
        setString("field45", item);
    }
    /**
     * Object:造价指标库分录's field46property 
     */
    public String getField46()
    {
        return getString("field46");
    }
    public void setField46(String item)
    {
        setString("field46", item);
    }
    /**
     * Object:造价指标库分录's field47property 
     */
    public String getField47()
    {
        return getString("field47");
    }
    public void setField47(String item)
    {
        setString("field47", item);
    }
    /**
     * Object:造价指标库分录's field48property 
     */
    public String getField48()
    {
        return getString("field48");
    }
    public void setField48(String item)
    {
        setString("field48", item);
    }
    /**
     * Object:造价指标库分录's field49property 
     */
    public String getField49()
    {
        return getString("field49");
    }
    public void setField49(String item)
    {
        setString("field49", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("37D83784");
    }
}