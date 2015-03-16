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
import com.kingdee.eas.framework.app.DataBaseController;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.sellhouse.ChangeRecordEntryTwoCollection;
import java.util.List;
import com.kingdee.eas.fdc.sellhouse.ChangeRecordEntryTwoInfo;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface ChangeRecordEntryTwoController extends DataBaseController
{
    public ChangeRecordEntryTwoInfo getChangeRecordEntryTwoInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public ChangeRecordEntryTwoInfo getChangeRecordEntryTwoInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public ChangeRecordEntryTwoInfo getChangeRecordEntryTwoInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public ChangeRecordEntryTwoCollection getChangeRecordEntryTwoCollection(Context ctx) throws BOSException, RemoteException;
    public ChangeRecordEntryTwoCollection getChangeRecordEntryTwoCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public ChangeRecordEntryTwoCollection getChangeRecordEntryTwoCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public boolean changeRecordSave(Context ctx, SincerityPurchaseInfo sinPurInfo, String appoinmentPeople, String appoimentPhone, List customList, IObjectValue model) throws BOSException, EASBizException, RemoteException;
}