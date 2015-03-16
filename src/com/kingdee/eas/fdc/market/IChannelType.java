package com.kingdee.eas.fdc.market;

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
import com.kingdee.eas.framework.IDataBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IChannelType extends IDataBase
{
    public ChannelTypeInfo getChannelTypeInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ChannelTypeInfo getChannelTypeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ChannelTypeInfo getChannelTypeInfo(String oql) throws BOSException, EASBizException;
    public ChannelTypeCollection getChannelTypeCollection() throws BOSException;
    public ChannelTypeCollection getChannelTypeCollection(EntityViewInfo view) throws BOSException;
    public ChannelTypeCollection getChannelTypeCollection(String oql) throws BOSException;
}