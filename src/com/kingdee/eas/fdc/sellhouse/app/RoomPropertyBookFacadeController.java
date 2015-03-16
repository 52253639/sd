package com.kingdee.eas.fdc.sellhouse.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import java.util.Map;
import java.lang.Object;
import com.kingdee.bos.framework.*;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface RoomPropertyBookFacadeController extends BizController
{
    public Map getMutilRoomPropertyCollection(Context ctx, Object selectedObj, Map paramMap) throws BOSException, EASBizException, RemoteException;
    public void updateRoomProperty(Context ctx, Map paramMap) throws BOSException, EASBizException, RemoteException;
    public Map getStepAndMatarilState(Context ctx, Map paramMap) throws BOSException, EASBizException, RemoteException;
}