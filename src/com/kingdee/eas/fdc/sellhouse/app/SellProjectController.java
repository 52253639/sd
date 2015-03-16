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
import java.util.Set;
import com.kingdee.eas.framework.app.TreeBaseController;
import com.kingdee.bos.util.*;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.base.permission.UserInfo;
import java.util.ArrayList;
import com.kingdee.bos.util.BOSUuid;
import java.util.List;
import com.kingdee.eas.fdc.sellhouse.SellProjectCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface SellProjectController extends TreeBaseController
{
    public SellProjectInfo getSellProjectInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public SellProjectInfo getSellProjectInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public SellProjectInfo getSellProjectInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public SellProjectCollection getSellProjectCollection(Context ctx) throws BOSException, RemoteException;
    public SellProjectCollection getSellProjectCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public SellProjectCollection getSellProjectCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public SellProjectInfo getBaseValue(Context ctx, String idStr) throws BOSException, RemoteException;
    public SellProjectCollection getBaseCollection(Context ctx, Set idSet) throws BOSException, RemoteException;
    public boolean endInit(Context ctx, List projectIds, String orgUnitId, UserInfo userInfo) throws BOSException, EASBizException, RemoteException;
    public boolean unInit(Context ctx, List projectIds, String orgUnitId, UserInfo userInfo) throws BOSException, EASBizException, RemoteException;
    public boolean allEndInit(Context ctx, String orgUnitId, UserInfo userInfo) throws BOSException, EASBizException, RemoteException;
    public boolean allUnInit(Context ctx, String orgUnitId, UserInfo userInfo) throws BOSException, EASBizException, RemoteException;
    public void nextSystem(Context ctx, String comId, UserInfo userInfo) throws BOSException, EASBizException, RemoteException;
    public void preSystem(Context ctx, String comId) throws BOSException, EASBizException, RemoteException;
    public void projectDataUpdate(Context ctx, IObjectValue model) throws BOSException, EASBizException, RemoteException;
    public void allProjectDataUpdate(Context ctx) throws BOSException, EASBizException, RemoteException;
    public void updateToSHEProject(Context ctx, BOSUuid id, BOSUuid orgUnitID, String longNumber) throws BOSException, EASBizException, RemoteException;
    public void updateToSellProject(Context ctx, BOSUuid id, String number, String name) throws BOSException, EASBizException, RemoteException;
    public ArrayList getSellProTreeNodes(Context ctx, String type) throws BOSException, EASBizException, RemoteException;
    public void updateDeleteStatus(Context ctx, BOSUuid billId) throws BOSException, EASBizException, RemoteException;
    public void deleteSellProject(Context ctx, BOSUuid billId) throws BOSException, EASBizException, RemoteException;
    public void deleteProjectInSystem(Context ctx, BOSUuid billId) throws BOSException, EASBizException, RemoteException;
    public void updateRoomModelForChild(Context ctx, BOSUuid billId, List roomModelList) throws BOSException, EASBizException, RemoteException;
}