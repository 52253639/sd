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
import com.kingdee.eas.fdc.finance.PaySplit4VoucherCollection;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.app.BillEntryBaseController;
import com.kingdee.eas.fdc.finance.PaySplit4VoucherInfo;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface PaySplit4VoucherController extends BillEntryBaseController
{
    public PaySplit4VoucherInfo getPaySplit4VoucherInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public PaySplit4VoucherInfo getPaySplit4VoucherInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public PaySplit4VoucherInfo getPaySplit4VoucherInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public PaySplit4VoucherCollection getPaySplit4VoucherCollection(Context ctx) throws BOSException, RemoteException;
    public PaySplit4VoucherCollection getPaySplit4VoucherCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public PaySplit4VoucherCollection getPaySplit4VoucherCollection(Context ctx, String oql) throws BOSException, RemoteException;
}