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

public interface IMediaType extends ITreeBase
{
    public MediaTypeInfo getMediaTypeInfo(IObjectPK pk) throws BOSException, EASBizException;
    public MediaTypeInfo getMediaTypeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public MediaTypeInfo getMediaTypeInfo(String oql) throws BOSException, EASBizException;
    public MediaTypeCollection getMediaTypeCollection() throws BOSException;
    public MediaTypeCollection getMediaTypeCollection(EntityViewInfo view) throws BOSException;
    public MediaTypeCollection getMediaTypeCollection(String oql) throws BOSException;
}