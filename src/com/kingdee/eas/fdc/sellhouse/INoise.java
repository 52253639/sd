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
import com.kingdee.eas.framework.IDataBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface INoise extends IDataBase
{
    public NoiseInfo getNoiseInfo(IObjectPK pk) throws BOSException, EASBizException;
    public NoiseInfo getNoiseInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public NoiseInfo getNoiseInfo(String oql) throws BOSException, EASBizException;
    public NoiseCollection getNoiseCollection() throws BOSException;
    public NoiseCollection getNoiseCollection(EntityViewInfo view) throws BOSException;
    public NoiseCollection getNoiseCollection(String oql) throws BOSException;
}