package com.kingdee.eas.fdc.sellhouse.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.sellhouse.BasePriceControlCollection;
import com.kingdee.eas.fdc.basedata.app.FDCBillController;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.sellhouse.BasePriceControlInfo;
import com.kingdee.bos.util.BOSUuid;
import java.util.List;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface BasePriceControlController extends FDCBillController
{
    public BasePriceControlInfo getBasePriceControlInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public BasePriceControlInfo getBasePriceControlInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public BasePriceControlInfo getBasePriceControlInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public BasePriceControlCollection getBasePriceControlCollection(Context ctx) throws BOSException, RemoteException;
    public BasePriceControlCollection getBasePriceControlCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public BasePriceControlCollection getBasePriceControlCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public void updateRoom(Context ctx, List roomList) throws BOSException, EASBizException, RemoteException;
    public List getRoomInfoList(Context ctx, String filterString) throws BOSException, EASBizException, RemoteException;
    public void auditBasePrice(Context ctx, BOSUuid billId) throws BOSException, EASBizException, RemoteException;
    public void unAuditBasePrice(Context ctx, BOSUuid billId) throws BOSException, EASBizException, RemoteException;
    public void setSubmit(Context ctx, BOSUuid billId) throws BOSException, EASBizException, RemoteException;
    public void setAuditing(Context ctx, BOSUuid billId) throws BOSException, EASBizException, RemoteException;
}