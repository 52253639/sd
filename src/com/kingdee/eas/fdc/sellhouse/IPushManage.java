package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.fdc.basedata.IFDCDataBase;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import java.util.Set;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public interface IPushManage extends IFDCDataBase
{
    public PushManageInfo getPushManageInfo(IObjectPK pk) throws BOSException, EASBizException;
    public PushManageInfo getPushManageInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public PushManageInfo getPushManageInfo(String oql) throws BOSException, EASBizException;
    public PushManageCollection getPushManageCollection() throws BOSException;
    public PushManageCollection getPushManageCollection(EntityViewInfo view) throws BOSException;
    public PushManageCollection getPushManageCollection(String oql) throws BOSException;
    public void executed(Set pushRoomIds) throws BOSException;
    public void quitOrder(Set quitRoomIds) throws BOSException;
}