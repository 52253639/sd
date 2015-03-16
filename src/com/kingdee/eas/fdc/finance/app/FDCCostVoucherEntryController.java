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
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.finance.FDCCostVoucherEntryInfo;
import com.kingdee.eas.fdc.finance.FDCCostVoucherEntryCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface FDCCostVoucherEntryController extends FDCBaseVoucherEntryController
{
    public FDCCostVoucherEntryInfo getFDCCostVoucherEntryInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public FDCCostVoucherEntryInfo getFDCCostVoucherEntryInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public FDCCostVoucherEntryInfo getFDCCostVoucherEntryInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public FDCCostVoucherEntryCollection getFDCCostVoucherEntryCollection(Context ctx) throws BOSException, RemoteException;
    public FDCCostVoucherEntryCollection getFDCCostVoucherEntryCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public FDCCostVoucherEntryCollection getFDCCostVoucherEntryCollection(Context ctx, String oql) throws BOSException, RemoteException;
}