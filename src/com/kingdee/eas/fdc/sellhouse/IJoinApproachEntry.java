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
import com.kingdee.eas.framework.IBillEntryBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IJoinApproachEntry extends IBillEntryBase
{
    public JoinApproachEntryInfo getJoinApproachEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public JoinApproachEntryInfo getJoinApproachEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public JoinApproachEntryInfo getJoinApproachEntryInfo(String oql) throws BOSException, EASBizException;
    public JoinApproachEntryCollection getJoinApproachEntryCollection() throws BOSException;
    public JoinApproachEntryCollection getJoinApproachEntryCollection(EntityViewInfo view) throws BOSException;
    public JoinApproachEntryCollection getJoinApproachEntryCollection(String oql) throws BOSException;
}