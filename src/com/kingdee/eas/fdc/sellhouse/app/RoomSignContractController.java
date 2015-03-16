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
import com.kingdee.eas.fdc.sellhouse.RoomSignContractCollection;
import java.util.Date;
import com.kingdee.eas.fdc.basedata.app.FDCBillController;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.sellhouse.RoomSignContractInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface RoomSignContractController extends FDCBillController
{
    public RoomSignContractInfo getRoomSignContractInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public RoomSignContractInfo getRoomSignContractInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public RoomSignContractInfo getRoomSignContractInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public RoomSignContractCollection getRoomSignContractCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public RoomSignContractCollection getRoomSignContractCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public IObjectPK[] delete(Context ctx, FilterInfo filter) throws BOSException, EASBizException, RemoteException;
    public void onRecord(Context ctx, IObjectPK[] pks, Date RecordDate, String contractNumber) throws BOSException, EASBizException, RemoteException;
    public void stamp(Context ctx, IObjectPK[] pks, Date stampDate) throws BOSException, EASBizException, RemoteException;
    public void pullDown(Context ctx, IObjectPK[] pks, Date pullDownDate) throws BOSException, EASBizException, RemoteException;
    public void unOnRecord(Context ctx, IObjectPK[] pks) throws BOSException, EASBizException, RemoteException;
    public void unStamp(Context ctx, IObjectPK[] pks) throws BOSException, EASBizException, RemoteException;
    public void unPullDown(Context ctx, IObjectPK[] pks) throws BOSException, EASBizException, RemoteException;
}