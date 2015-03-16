package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillEntryBase;

public interface IInviteChangeFormSupplier extends ICoreBillEntryBase
{
    public InviteChangeFormSupplierInfo getInviteChangeFormSupplierInfo(IObjectPK pk) throws BOSException, EASBizException;
    public InviteChangeFormSupplierCollection getInviteChangeFormSupplierCollection() throws BOSException;
    public InviteChangeFormSupplierCollection getInviteChangeFormSupplierCollection(EntityViewInfo view) throws BOSException;
}