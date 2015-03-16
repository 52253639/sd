package com.kingdee.eas.fdc.sellhouse.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.basedata.app.FDCDataBaseController;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.sellhouse.SwapRoomReasonCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.sellhouse.SwapRoomReasonInfo;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface SwapRoomReasonController extends FDCDataBaseController
{
    public SwapRoomReasonInfo getSwapRoomReasonInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public SwapRoomReasonInfo getSwapRoomReasonInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public SwapRoomReasonInfo getSwapRoomReasonInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public SwapRoomReasonCollection getSwapRoomReasonCollection(Context ctx) throws BOSException, RemoteException;
    public SwapRoomReasonCollection getSwapRoomReasonCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public SwapRoomReasonCollection getSwapRoomReasonCollection(Context ctx, String oql) throws BOSException, RemoteException;
}