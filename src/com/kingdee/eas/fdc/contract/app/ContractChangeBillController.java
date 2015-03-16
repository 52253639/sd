package com.kingdee.eas.fdc.contract.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.basedata.app.FDCBillController;
import com.kingdee.eas.fdc.contract.ContractChangeBillCollection;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.contract.ContractChangeBillInfo;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface ContractChangeBillController extends FDCBillController
{
    public ContractChangeBillCollection getContractChangeBillCollection(Context ctx) throws BOSException, RemoteException;
    public ContractChangeBillCollection getContractChangeBillCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public ContractChangeBillCollection getContractChangeBillCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public ContractChangeBillInfo getContractChangeBillInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public ContractChangeBillInfo getContractChangeBillInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public ContractChangeBillInfo getContractChangeBillInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public void disPatch(Context ctx, IObjectPK[] idSet) throws BOSException, EASBizException, RemoteException;
    public boolean visa(Context ctx, IObjectPK[] idSet, IObjectCollection cols) throws BOSException, EASBizException, RemoteException;
    public void settle(Context ctx, IObjectPK pk, IObjectValue changeBill) throws BOSException, EASBizException, RemoteException;
    public void unDispatch(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public void unVisa(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public void submitForWF(Context ctx, ContractChangeBillInfo model) throws BOSException, EASBizException, RemoteException;
    public void setSettAuttingForWF(Context ctx, BOSUuid pk) throws BOSException, EASBizException, RemoteException;
    public void setSettAuditedForWF(Context ctx, BOSUuid pk) throws BOSException, EASBizException, RemoteException;
    public void confirmExecute(Context ctx, BOSUuid billId) throws BOSException, EASBizException, RemoteException;
    public void changeSettle(Context ctx, BOSUuid billId) throws BOSException, EASBizException, RemoteException;
    public void costChangeSplit(Context ctx, BOSUuid billId) throws BOSException, EASBizException, RemoteException;
    public void nonCostChangeSplit(Context ctx, BOSUuid billId) throws BOSException, EASBizException, RemoteException;
}