package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IBillEntryBase;

public interface IRoomLoanAFMEntrys extends IBillEntryBase
{
    public boolean exists(IObjectPK pk) throws BOSException, EASBizException;
    public boolean exists(FilterInfo filter) throws BOSException, EASBizException;
    public boolean exists(String oql) throws BOSException, EASBizException;
    public RoomLoanAFMEntrysInfo getRoomLoanAFMEntrysInfo(IObjectPK pk) throws BOSException, EASBizException;
    public RoomLoanAFMEntrysInfo getRoomLoanAFMEntrysInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public RoomLoanAFMEntrysInfo getRoomLoanAFMEntrysInfo(String oql) throws BOSException, EASBizException;
    public IObjectPK addnew(RoomLoanAFMEntrysInfo model) throws BOSException, EASBizException;
    public void addnew(IObjectPK pk, RoomLoanAFMEntrysInfo model) throws BOSException, EASBizException;
    public void update(IObjectPK pk, RoomLoanAFMEntrysInfo model) throws BOSException, EASBizException;
    public void updatePartial(RoomLoanAFMEntrysInfo model, SelectorItemCollection selector) throws BOSException, EASBizException;
    public void updateBigObject(IObjectPK pk, RoomLoanAFMEntrysInfo model) throws BOSException;
    public void delete(IObjectPK pk) throws BOSException, EASBizException;
    public IObjectPK[] getPKList() throws BOSException, EASBizException;
    public IObjectPK[] getPKList(String oql) throws BOSException, EASBizException;
    public IObjectPK[] getPKList(FilterInfo filter, SorterItemCollection sorter) throws BOSException, EASBizException;
    public RoomLoanAFMEntrysCollection getRoomLoanAFMEntrysCollection() throws BOSException;
    public RoomLoanAFMEntrysCollection getRoomLoanAFMEntrysCollection(EntityViewInfo view) throws BOSException;
    public RoomLoanAFMEntrysCollection getRoomLoanAFMEntrysCollection(String oql) throws BOSException;
    public IObjectPK[] delete(FilterInfo filter) throws BOSException, EASBizException;
    public IObjectPK[] delete(String oql) throws BOSException, EASBizException;
    public void delete(IObjectPK[] arrayPK) throws BOSException, EASBizException;
}