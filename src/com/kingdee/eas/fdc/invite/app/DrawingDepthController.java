package com.kingdee.eas.fdc.invite.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.invite.DrawingDepthInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.basedata.app.FDCDataBaseController;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.invite.DrawingDepthCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface DrawingDepthController extends FDCDataBaseController
{
    public DrawingDepthInfo getDrawingDepthInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public DrawingDepthInfo getDrawingDepthInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public DrawingDepthInfo getDrawingDepthInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public DrawingDepthCollection getDrawingDepthCollection(Context ctx) throws BOSException, RemoteException;
    public DrawingDepthCollection getDrawingDepthCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public DrawingDepthCollection getDrawingDepthCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public boolean updateRelateData(Context ctx, String oldID, String newID, Object[] tables) throws BOSException, RemoteException;
    public Object[] getRelateData(Context ctx, String id, String[] tables) throws BOSException, RemoteException;
}