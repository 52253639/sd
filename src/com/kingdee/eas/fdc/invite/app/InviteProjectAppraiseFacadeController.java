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

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface InviteProjectAppraiseFacadeController extends BizController
{
    public void checkCanAddExpertAppraise(Context ctx, String inviteProjectID) throws BOSException, EASBizException, RemoteException;
    public void aotuSetInviteStatusAppraise(Context ctx) throws BOSException, EASBizException, RemoteException;
}