package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
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

public interface IWorkAmountBill extends IFDCBill
{
    public WorkAmountBillInfo getWorkAmountBillInfo(IObjectPK pk) throws BOSException, EASBizException;
    public WorkAmountBillInfo getWorkAmountBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public WorkAmountBillInfo getWorkAmountBillInfo(String oql) throws BOSException, EASBizException;
    public WorkAmountBillCollection getWorkAmountBillCollection() throws BOSException;
    public WorkAmountBillCollection getWorkAmountBillCollection(String oql) throws BOSException;
    public WorkAmountBillCollection getWorkAmountBillCollection(EntityViewInfo view) throws BOSException;
    public Map initTaskInfo(String projectId) throws BOSException;
    public Map getSelectedTask(Map param) throws BOSException;
}