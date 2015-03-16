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
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IChannelTypeTree extends ITreeBase
{
    public ChannelTypeTreeInfo getChannelTypeTreeInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ChannelTypeTreeInfo getChannelTypeTreeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ChannelTypeTreeInfo getChannelTypeTreeInfo(String oql) throws BOSException, EASBizException;
    public ChannelTypeTreeCollection getChannelTypeTreeCollection() throws BOSException;
    public ChannelTypeTreeCollection getChannelTypeTreeCollection(EntityViewInfo view) throws BOSException;
    public ChannelTypeTreeCollection getChannelTypeTreeCollection(String oql) throws BOSException;
}