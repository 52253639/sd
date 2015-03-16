package com.kingdee.eas.fdc.contract.programming.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectCollection;
import java.lang.Object;
import com.kingdee.bos.framework.*;
import java.util.Set;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface RenewRelateProgSaveFacadeController extends BizController
{
    public void save(Context ctx, IObjectCollection objCol) throws BOSException, EASBizException, RemoteException;
    public Set getContractbillID(Context ctx, Object[] id) throws BOSException, RemoteException;
}