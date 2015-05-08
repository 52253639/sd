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
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.bos.util.*;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.contract.ContractWithoutTextCollection;
import com.kingdee.bos.util.BOSUuid;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface ContractWithoutTextController extends FDCBillController
{
    public ContractWithoutTextCollection getContractWithoutTextCollection(Context ctx) throws BOSException, RemoteException;
    public ContractWithoutTextCollection getContractWithoutTextCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public ContractWithoutTextCollection getContractWithoutTextCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public ContractWithoutTextInfo getContractWithoutTextInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public ContractWithoutTextInfo getContractWithoutTextInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public ContractWithoutTextInfo getContractWithoutTextInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public String getContractTypeNumber(Context ctx, IObjectPK id) throws BOSException, EASBizException, RemoteException;
    public String getUseDepartment(Context ctx, String id) throws BOSException, EASBizException, RemoteException;
    public String getPaymentType(Context ctx, BOSUuid id) throws BOSException, EASBizException, RemoteException;
    public void synUpdateProgramming(Context ctx, String billId, ProgrammingContractInfo programming) throws BOSException, EASBizException, RemoteException;
    public void synReUpdateProgramming(Context ctx, String billId, IObjectValue programming) throws BOSException, EASBizException, RemoteException;
    public IObjectValue getNoPValue(Context ctx, IObjectPK pk, SelectorItemCollection sel) throws BOSException, RemoteException;
}