package com.kingdee.eas.fdc.finance.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.basedata.app.FDCBillController;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.finance.FIProductSettleBillCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.finance.FIProductSettleBillInfo;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface FIProductSettleBillController extends FDCBillController
{
    public FIProductSettleBillInfo getFIProductSettleBillInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public FIProductSettleBillInfo getFIProductSettleBillInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public FIProductSettleBillInfo getFIProductSettleBillInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public FIProductSettleBillCollection getFIProductSettleBillCollection(Context ctx) throws BOSException, RemoteException;
    public FIProductSettleBillCollection getFIProductSettleBillCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public FIProductSettleBillCollection getFIProductSettleBillCollection(Context ctx, String oql) throws BOSException, RemoteException;
}