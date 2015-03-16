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

public interface IInviteType extends ITreeBase
{
    public InviteTypeInfo getInviteTypeInfo(IObjectPK pk) throws BOSException, EASBizException;
    public InviteTypeInfo getInviteTypeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public InviteTypeInfo getInviteTypeInfo(String oql) throws BOSException, EASBizException;
    public InviteTypeCollection getInviteTypeCollection() throws BOSException;
    public InviteTypeCollection getInviteTypeCollection(EntityViewInfo view) throws BOSException;
    public InviteTypeCollection getInviteTypeCollection(String oql) throws BOSException;
    public boolean updateRelateData(String oldID, String newID, Object[] tables) throws BOSException;
    public Object[] getRelateData(String id, String[] tables) throws BOSException;
}