package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.eas.fdc.basedata.IFDCDataBase;
import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IInviteMode extends IFDCDataBase
{
    public InviteModeInfo getInviteModeInfo(IObjectPK pk) throws BOSException, EASBizException;
    public InviteModeInfo getInviteModeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public InviteModeInfo getInviteModeInfo(String oql) throws BOSException, EASBizException;
    public InviteModeCollection getInviteModeCollection() throws BOSException;
    public InviteModeCollection getInviteModeCollection(EntityViewInfo view) throws BOSException;
    public InviteModeCollection getInviteModeCollection(String oql) throws BOSException;
    public void delete(IObjectPK pk) throws BOSException, EASBizException;
}