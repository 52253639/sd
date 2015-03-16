package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;

public interface IQuitRoom extends IFDCBill
{
    public QuitRoomInfo getQuitRoomInfo(IObjectPK pk) throws BOSException, EASBizException;
    public QuitRoomInfo getQuitRoomInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public QuitRoomInfo getQuitRoomInfo(String oql) throws BOSException, EASBizException;
    public QuitRoomCollection getQuitRoomCollection() throws BOSException;
    public QuitRoomCollection getQuitRoomCollection(EntityViewInfo view) throws BOSException;
    public QuitRoomCollection getQuitRoomCollection(String oql) throws BOSException;
    public boolean exeQuit(String id) throws BOSException, EASBizException;
    public void receiveBill(BOSUuid billId) throws BOSException, EASBizException;
    public void settleMent(BOSUuid billId) throws BOSException, EASBizException;
}