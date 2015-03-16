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
import java.util.Map;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import java.util.List;

public interface IRoomPropertyBook extends IFDCBill
{
    public RoomPropertyBookInfo getRoomPropertyBookInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public RoomPropertyBookInfo getRoomPropertyBookInfo(String oql) throws BOSException, EASBizException;
    public RoomPropertyBookInfo getRoomPropertyBookInfo(IObjectPK pk) throws BOSException, EASBizException;
    public RoomPropertyBookCollection getRoomPropertyBookCollection() throws BOSException;
    public RoomPropertyBookCollection getRoomPropertyBookCollection(EntityViewInfo view) throws BOSException;
    public RoomPropertyBookCollection getRoomPropertyBookCollection(String oql) throws BOSException;
    public void batchSave(List idList, Map valueMap) throws BOSException, EASBizException;
}