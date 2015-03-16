package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fi.cas.ReceivingBillInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import java.util.ArrayList;
import com.kingdee.eas.framework.IDataBase;
import com.kingdee.eas.fi.cas.ReceivingBillCollection;

public interface IFDCReceiveBill extends IDataBase
{
    public FDCReceiveBillInfo getFDCReceiveBillInfo(IObjectPK pk) throws BOSException, EASBizException;
    public FDCReceiveBillInfo getFDCReceiveBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public FDCReceiveBillInfo getFDCReceiveBillInfo(String oql) throws BOSException, EASBizException;
    public FDCReceiveBillCollection getFDCReceiveBillCollection() throws BOSException;
    public FDCReceiveBillCollection getFDCReceiveBillCollection(EntityViewInfo view) throws BOSException;
    public FDCReceiveBillCollection getFDCReceiveBillCollection(String oql) throws BOSException;
    public String submitByCasRev(ReceivingBillInfo casRev) throws BOSException, EASBizException;
    public void submitByCasRevColl(ReceivingBillCollection casRevColl) throws BOSException, EASBizException;
    public void addTemporaBill(ArrayList listIds) throws BOSException, EASBizException;
    public int getPrintCount(String billID) throws BOSException, EASBizException;
    public void updatePrintCount(String billID, int printCount) throws BOSException, EASBizException;
    public void addTemporaBill(ArrayList listBills, IObjectValue billInfo) throws BOSException, EASBizException;
}