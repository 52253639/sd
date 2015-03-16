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

public interface IChangeRoom extends IFDCBill
{
    public ChangeRoomInfo getChangeRoomInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ChangeRoomInfo getChangeRoomInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ChangeRoomInfo getChangeRoomInfo(String oql) throws BOSException, EASBizException;
    public ChangeRoomCollection getChangeRoomCollection() throws BOSException;
    public ChangeRoomCollection getChangeRoomCollection(EntityViewInfo view) throws BOSException;
    public ChangeRoomCollection getChangeRoomCollection(String oql) throws BOSException;
    public void settleMent(BOSUuid billId) throws BOSException, EASBizException;
}