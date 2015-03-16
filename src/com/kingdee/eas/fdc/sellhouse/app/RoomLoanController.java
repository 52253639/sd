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
import com.kingdee.eas.fdc.sellhouse.RoomLoanCollection;
import java.util.Map;
import com.kingdee.eas.fdc.basedata.app.FDCBillController;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.bos.framework.*;
import java.util.List;
import com.kingdee.eas.fdc.sellhouse.RoomLoanInfo;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface RoomLoanController extends FDCBillController
{
    public RoomLoanInfo getRoomLoanInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public RoomLoanInfo getRoomLoanInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public RoomLoanInfo getRoomLoanInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public RoomLoanCollection getRoomLoanCollection(Context ctx) throws BOSException, RemoteException;
    public RoomLoanCollection getRoomLoanCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public RoomLoanCollection getRoomLoanCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public void batchSave(Context ctx, List idList, Map valueMap) throws BOSException, EASBizException, RemoteException;
    public IRowSet exeQuery(Context ctx, String filterStr) throws BOSException, RemoteException;
    public IRowSet getRoomList(Context ctx, String projectID) throws BOSException, EASBizException, RemoteException;
}