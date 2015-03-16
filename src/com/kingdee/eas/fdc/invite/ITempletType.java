package com.kingdee.eas.fdc.invite;

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

public interface ITempletType extends ITreeBase
{
    public TempletTypeInfo getTempletTypeInfo(IObjectPK pk) throws BOSException, EASBizException;
    public TempletTypeInfo getTempletTypeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public TempletTypeInfo getTempletTypeInfo(String oql) throws BOSException, EASBizException;
    public TempletTypeCollection getTempletTypeCollection() throws BOSException;
    public TempletTypeCollection getTempletTypeCollection(EntityViewInfo view) throws BOSException;
    public TempletTypeCollection getTempletTypeCollection(String oql) throws BOSException;
    public boolean updateRelateData(String oldID, String newID, Object[] tables) throws BOSException;
    public Object[] getRelateData(String id, String[] tables) throws BOSException;
}