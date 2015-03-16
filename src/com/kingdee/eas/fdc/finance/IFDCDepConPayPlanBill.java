package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import java.util.Map;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;
import java.util.List;

public interface IFDCDepConPayPlanBill extends IFDCBill
{
    public Map fetchData(Map param) throws BOSException, EASBizException;
    public FDCDepConPayPlanBillInfo getFDCDepConPayPlanBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public FDCDepConPayPlanBillCollection getFDCDepConPayPlanBillCollection(EntityViewInfo view) throws BOSException;
    public Map statisticsPay(Map valuse) throws BOSException;
    public Map getPlanPay(Map value) throws BOSException;
    public void publish(List ids) throws BOSException, EASBizException;
    public void back(List ids) throws BOSException, EASBizException;
    public void setPublish(BOSUuid billid) throws BOSException, EASBizException;
    public void setBack(BOSUuid billid) throws BOSException, EASBizException;
    public void setBackByEntryID(String entryID) throws BOSException;
}