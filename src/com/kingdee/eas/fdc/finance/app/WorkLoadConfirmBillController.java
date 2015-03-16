package com.kingdee.eas.fdc.finance.app;

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
import java.math.BigDecimal;
import com.kingdee.eas.fdc.basedata.app.FDCBillController;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.finance.WorkLoadConfirmBillCollection;
import java.util.Set;
import com.kingdee.eas.fdc.finance.WorkLoadConfirmBillInfo;
import com.kingdee.bos.util.*;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import java.util.List;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface WorkLoadConfirmBillController extends FDCBillController
{
    public WorkLoadConfirmBillInfo getWorkLoadConfirmBillInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public WorkLoadConfirmBillInfo getWorkLoadConfirmBillInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public WorkLoadConfirmBillInfo getWorkLoadConfirmBillInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public WorkLoadConfirmBillCollection getWorkLoadConfirmBillCollection(Context ctx) throws BOSException, RemoteException;
    public WorkLoadConfirmBillCollection getWorkLoadConfirmBillCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public WorkLoadConfirmBillCollection getWorkLoadConfirmBillCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public BigDecimal getWorkLoad(Context ctx, String contractId) throws BOSException, EASBizException, RemoteException;
    public Map getWorkLoad(Context ctx, Set contractIds) throws BOSException, EASBizException, RemoteException;
    public BigDecimal getWorkLoadWithoutId(Context ctx, String contractId, String workLoadId) throws BOSException, EASBizException, RemoteException;
    public Map getConPrjFillBill(Context ctx, Set prjFillBillIds) throws BOSException, EASBizException, RemoteException;
    public Map getRefWorkAmount(Context ctx, Map paramMap) throws BOSException, EASBizException, RemoteException;
    public void save(Context ctx, IObjectValue model, List refWorkAmountList, List willRemoveList) throws BOSException, EASBizException, RemoteException;
}