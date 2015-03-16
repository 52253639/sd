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
import com.kingdee.eas.fdc.basedata.app.FDCBillController;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.sellhouse.MortagageControlInfo;
import com.kingdee.eas.fdc.sellhouse.MortagageControlCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface MortagageControlController extends FDCBillController
{
    public MortagageControlInfo getMortagageControlInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public MortagageControlInfo getMortagageControlInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public MortagageControlInfo getMortagageControlInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public MortagageControlCollection getMortagageControlCollection(Context ctx) throws BOSException, RemoteException;
    public MortagageControlCollection getMortagageControlCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public MortagageControlCollection getMortagageControlCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public boolean antiMortagage(Context ctx, MortagageControlInfo model) throws BOSException, EASBizException, RemoteException;
    public boolean audit(Context ctx, MortagageControlInfo model) throws BOSException, EASBizException, RemoteException;
    public boolean unAudit(Context ctx, MortagageControlInfo model) throws BOSException, EASBizException, RemoteException;
    public void setAuditingStatus(Context ctx, MortagageControlInfo model) throws BOSException, EASBizException, RemoteException;
}