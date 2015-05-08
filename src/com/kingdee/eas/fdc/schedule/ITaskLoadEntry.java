package com.kingdee.eas.fdc.schedule;

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
import java.util.Set;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillEntryBase;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public interface ITaskLoadEntry extends ICoreBillEntryBase
{
    public TaskLoadEntryInfo getTaskLoadEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public TaskLoadEntryInfo getTaskLoadEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public TaskLoadEntryInfo getTaskLoadEntryInfo(String oql) throws BOSException, EASBizException;
    public TaskLoadEntryCollection getTaskLoadEntryCollection() throws BOSException;
    public TaskLoadEntryCollection getTaskLoadEntryCollection(EntityViewInfo view) throws BOSException;
    public TaskLoadEntryCollection getTaskLoadEntryCollection(String oql) throws BOSException;
    public void updateConfirmStatus(Set idSet, boolean isConfirm) throws BOSException, EASBizException;
}