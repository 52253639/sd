package com.kingdee.eas.fdc.contract.programming.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.contract.programming.ProgrammingCollection;
import java.math.BigDecimal;
import com.kingdee.eas.fdc.basedata.app.FDCBillController;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.fdc.contract.programming.ProgrammingInfo;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface ProgrammingController extends FDCBillController
{
    public ProgrammingInfo getProgrammingInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public ProgrammingInfo getProgrammingInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public ProgrammingInfo getProgrammingInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public ProgrammingCollection getProgrammingCollection(Context ctx) throws BOSException, RemoteException;
    public ProgrammingCollection getProgrammingCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public ProgrammingCollection getProgrammingCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public ProgrammingInfo getLastVersion(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public boolean isLastVersion(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public ProgrammingInfo getLastVersion(Context ctx, String versionGroup) throws BOSException, EASBizException, RemoteException;
    public void setAuttingForWF(Context ctx, BOSUuid billId) throws BOSException, EASBizException, RemoteException;
    public IObjectPK billModify(Context ctx, String versionGroup, String curVersion) throws BOSException, EASBizException, RemoteException;
    public void setSubmitStatus(Context ctx, BOSUuid billId) throws BOSException, EASBizException, RemoteException;
    public boolean isAddPCPass(Context ctx, BOSUuid billId, BigDecimal amount) throws BOSException, EASBizException, RemoteException;
    public boolean isEditPCPass(Context ctx, BOSUuid billId, BigDecimal amount) throws BOSException, EASBizException, RemoteException;
}