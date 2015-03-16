package com.kingdee.eas.fdc.invite.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.basedata.org.FullOrgUnitCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface NewListingTempletDistributFacadeController extends BizController
{
    public void Distribute(Context ctx, String templetId, FullOrgUnitCollection selectOrgs) throws BOSException, EASBizException, RemoteException;
}