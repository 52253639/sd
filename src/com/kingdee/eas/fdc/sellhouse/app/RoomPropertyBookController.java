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
import com.kingdee.eas.fdc.sellhouse.RoomPropertyBookCollection;
import java.util.Map;
import com.kingdee.eas.fdc.basedata.app.FDCBillController;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.sellhouse.RoomPropertyBookInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import java.util.List;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface RoomPropertyBookController extends FDCBillController
{
    public RoomPropertyBookInfo getRoomPropertyBookInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public RoomPropertyBookInfo getRoomPropertyBookInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public RoomPropertyBookInfo getRoomPropertyBookInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public RoomPropertyBookCollection getRoomPropertyBookCollection(Context ctx) throws BOSException, RemoteException;
    public RoomPropertyBookCollection getRoomPropertyBookCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public RoomPropertyBookCollection getRoomPropertyBookCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public void batchSave(Context ctx, List idList, Map valueMap) throws BOSException, EASBizException, RemoteException;
}