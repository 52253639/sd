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
import java.util.Date;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import java.util.Set;
import com.kingdee.eas.fdc.sellhouse.InsteadCollectOutBillInfo;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.fdc.propertymgmt.app.PPMProjectBillController;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.sellhouse.InsteadCollectOutBillCollection;
import com.kingdee.eas.fdc.sellhouse.SellHouseException;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface InsteadCollectOutBillController extends PPMProjectBillController
{
    public InsteadCollectOutBillCollection getInsteadCollectOutBillCollection(Context ctx) throws BOSException, RemoteException;
    public InsteadCollectOutBillCollection getInsteadCollectOutBillCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public InsteadCollectOutBillCollection getInsteadCollectOutBillCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public InsteadCollectOutBillInfo getInsteadCollectOutBillInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public InsteadCollectOutBillInfo getInsteadCollectOutBillInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public InsteadCollectOutBillInfo getInsteadCollectOutBillInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public void generateNewData(Context ctx, PersonInfo personInfo, Date bizDate, Set rows) throws BOSException, SellHouseException, RemoteException;
}