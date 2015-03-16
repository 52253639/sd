package com.kingdee.eas.fdc.basecrm.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.app.CoreBillBaseController;
import com.kingdee.eas.fdc.sellhouse.CollectionInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.basecrm.SubstituteAdjustEntryCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.basecrm.SubstituteAdjustInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.fdc.basecrm.SubstituteAdjustCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface SubstituteAdjustController extends CoreBillBaseController
{
    public SubstituteAdjustCollection getSubstituteAdjustCollection(Context ctx) throws BOSException, RemoteException;
    public SubstituteAdjustCollection getSubstituteAdjustCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public SubstituteAdjustCollection getSubstituteAdjustCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public SubstituteAdjustInfo getSubstituteAdjustInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public SubstituteAdjustInfo getSubstituteAdjustInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public SubstituteAdjustInfo getSubstituteAdjustInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public void transfTo(Context ctx, SubstituteAdjustInfo adjustInfo) throws BOSException, EASBizException, RemoteException;
    public SubstituteAdjustEntryCollection getCalculateResult(Context ctx, BOSUuid moneyDefineId, BOSUuid sellPorjectId, BOSUuid buildingId, CollectionInfo collInfo) throws BOSException, EASBizException, RemoteException;
}