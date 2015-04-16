package com.kingdee.eas.fdc.contract.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import java.util.Map;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.basedata.app.FDCBillController;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import java.util.Set;
import com.kingdee.bos.util.*;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import java.util.List;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface ContractBillController extends FDCBillController
{
    public ContractBillCollection getContractBillCollection(Context ctx) throws BOSException, RemoteException;
    public ContractBillCollection getContractBillCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public ContractBillCollection getContractBillCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public ContractBillInfo getContractBillInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public ContractBillInfo getContractBillInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public ContractBillInfo getContractBillInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public boolean contractBillStore(Context ctx, ContractBillInfo cbInfo, String storeNumber) throws BOSException, EASBizException, RemoteException;
    public Map getAmtByAmtWithoutCost(Context ctx, Map idMap) throws BOSException, EASBizException, RemoteException;
    public boolean contractBillAntiStore(Context ctx, List idList) throws BOSException, EASBizException, RemoteException;
    public boolean isContractSplit(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public void split(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public boolean autoDelSplit(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public String getContractTypeNumber(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public Map getOtherInfo(Context ctx, Set contractIds) throws BOSException, EASBizException, RemoteException;
    public void synUpdateProgramming(Context ctx, String billId, ProgrammingContractInfo programming) throws BOSException, EASBizException, RemoteException;
    public void synReUpdateProgramming(Context ctx, String billId, IObjectValue programming) throws BOSException, EASBizException, RemoteException;
    public void costIndex(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
}