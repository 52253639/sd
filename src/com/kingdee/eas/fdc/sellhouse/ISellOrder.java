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

public interface ISellOrder extends IFDCDataBase
{
    public SellOrderInfo getSellOrderInfo(IObjectPK pk) throws BOSException, EASBizException;
    public SellOrderInfo getSellOrderInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public SellOrderInfo getSellOrderInfo(String oql) throws BOSException, EASBizException;
    public SellOrderCollection getSellOrderCollection() throws BOSException;
    public SellOrderCollection getSellOrderCollection(EntityViewInfo view) throws BOSException;
    public SellOrderCollection getSellOrderCollection(String oql) throws BOSException;
    public void quitOrder(Set quitRoomIds) throws BOSException, EASBizException;
}