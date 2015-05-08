package com.kingdee.eas.fdc.schedule.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.schedule.AchievementManagerInfo;
import com.kingdee.eas.fdc.schedule.AchievementManagerCollection;
import com.kingdee.eas.fdc.basedata.app.FDCBillController;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface AchievementManagerController extends FDCBillController
{
    public boolean exists(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public boolean exists(Context ctx, FilterInfo filter) throws BOSException, EASBizException, RemoteException;
    public boolean exists(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public AchievementManagerInfo getAchievementManagerInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public AchievementManagerInfo getAchievementManagerInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public AchievementManagerInfo getAchievementManagerInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public AchievementManagerCollection getAchievementManagerCollection(Context ctx) throws BOSException, RemoteException;
    public AchievementManagerCollection getAchievementManagerCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public AchievementManagerCollection getAchievementManagerCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public void afterschreportwriteBack(Context ctx, IObjectValue model) throws BOSException, RemoteException;
}