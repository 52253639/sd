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
import com.kingdee.jdbc.rowset.IRowSet;
import java.util.List;

public interface IRoomLoan extends IFDCBill
{
    public RoomLoanInfo getRoomLoanInfo(IObjectPK pk) throws BOSException, EASBizException;
    public RoomLoanInfo getRoomLoanInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public RoomLoanInfo getRoomLoanInfo(String oql) throws BOSException, EASBizException;
    public RoomLoanCollection getRoomLoanCollection() throws BOSException;
    public RoomLoanCollection getRoomLoanCollection(EntityViewInfo view) throws BOSException;
    public RoomLoanCollection getRoomLoanCollection(String oql) throws BOSException;
    public void batchSave(List idList, Map valueMap) throws BOSException, EASBizException;
    public IRowSet exeQuery(String filterStr) throws BOSException;
    public IRowSet getRoomList(String projectID) throws BOSException, EASBizException;
}