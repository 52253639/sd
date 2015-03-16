package com.kingdee.eas.fdc.aimcost.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.eas.fdc.aimcost.TemplateMeasureCostCollection;
import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.basedata.app.FDCBillController;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.aimcost.TemplateMeasureCostInfo;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;
import java.util.List;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface TemplateMeasureCostController extends FDCBillController
{
    public TemplateMeasureCostInfo getTemplateMeasureCostInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public TemplateMeasureCostInfo getTemplateMeasureCostInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public TemplateMeasureCostCollection getTemplateMeasureCostCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public TemplateMeasureCostCollection getTemplateMeasureCostCollection(Context ctx) throws BOSException, RemoteException;
    public TemplateMeasureCostCollection getTemplateMeasureCostCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public void audit(Context ctx, BOSUuid billId) throws BOSException, EASBizException, RemoteException;
    public void audit(Context ctx, List idList) throws BOSException, EASBizException, RemoteException;
    public void unaudit(Context ctx, BOSUuid billId) throws BOSException, EASBizException, RemoteException;
    public void unaudit(Context ctx, List idList) throws BOSException, EASBizException, RemoteException;
}