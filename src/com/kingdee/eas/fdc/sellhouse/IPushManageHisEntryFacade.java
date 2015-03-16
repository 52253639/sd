package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.framework.IBizCtrl;
import com.kingdee.jdbc.rowset.IRowSet;

public interface IPushManageHisEntryFacade extends IBizCtrl
{
    public IRowSet getPushManageHisEntry(String roomID) throws BOSException;
    public IRowSet getPushManage(String roomId) throws BOSException;
}