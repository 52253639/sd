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
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface ISHECusAssistantDataGroup extends ITreeBase
{
    public SHECusAssistantDataGroupInfo getSHECusAssistantDataGroupInfo(IObjectPK pk) throws BOSException, EASBizException;
    public SHECusAssistantDataGroupInfo getSHECusAssistantDataGroupInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public SHECusAssistantDataGroupInfo getSHECusAssistantDataGroupInfo(String oql) throws BOSException, EASBizException;
    public SHECusAssistantDataGroupCollection getSHECusAssistantDataGroupCollection() throws BOSException;
    public SHECusAssistantDataGroupCollection getSHECusAssistantDataGroupCollection(EntityViewInfo view) throws BOSException;
    public SHECusAssistantDataGroupCollection getSHECusAssistantDataGroupCollection(String oql) throws BOSException;
}