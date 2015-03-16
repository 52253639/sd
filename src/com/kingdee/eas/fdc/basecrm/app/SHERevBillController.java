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
import com.kingdee.eas.fdc.basecrm.SHERevBillCollection;
import com.kingdee.eas.fdc.basecrm.SHERevBillInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;
import java.util.List;
import com.kingdee.eas.fdc.sellhouse.TransactionInfo;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface SHERevBillController extends CoreBillBaseController
{
    public SHERevBillCollection getSHERevBillCollection(Context ctx) throws BOSException, RemoteException;
    public SHERevBillCollection getSHERevBillCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public SHERevBillCollection getSHERevBillCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public SHERevBillInfo getSHERevBillInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public SHERevBillInfo getSHERevBillInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public SHERevBillInfo getSHERevBillInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public void audit(Context ctx, BOSUuid billId) throws BOSException, EASBizException, RemoteException;
    public void audit(Context ctx, List idList) throws BOSException, EASBizException, RemoteException;
    public void unAudit(Context ctx, BOSUuid billId) throws BOSException, EASBizException, RemoteException;
    public void unAudit(Context ctx, List idList) throws BOSException, EASBizException, RemoteException;
    public void setAudittingStatus(Context ctx, BOSUuid billId) throws BOSException, EASBizException, RemoteException;
    public void setSubmitStatus(Context ctx, BOSUuid billId) throws BOSException, EASBizException, RemoteException;
    public void whenTransEntryChange(Context ctx, TransactionInfo oldTransInfo, TransactionInfo newTransInfo) throws BOSException, EASBizException, RemoteException;
    public void whenTransCustChange(Context ctx, TransactionInfo transInfo) throws BOSException, EASBizException, RemoteException;
}