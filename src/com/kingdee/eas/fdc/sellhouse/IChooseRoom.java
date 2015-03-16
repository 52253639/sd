package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IChooseRoom extends IFDCBill
{
    public ChooseRoomInfo getChooseRoomInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ChooseRoomInfo getChooseRoomInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ChooseRoomInfo getChooseRoomInfo(String oql) throws BOSException, EASBizException;
    public ChooseRoomCollection getChooseRoomCollection() throws BOSException;
    public ChooseRoomCollection getChooseRoomCollection(EntityViewInfo view) throws BOSException;
    public ChooseRoomCollection getChooseRoomCollection(String oql) throws BOSException;
    public boolean isValid(String billID) throws BOSException, EASBizException;
    public void cancelChooseRoom(String billID) throws BOSException, EASBizException;
    public void updateTrans(String billID, ChooseRoomStateEnum chooseRoomState) throws BOSException, EASBizException;
}