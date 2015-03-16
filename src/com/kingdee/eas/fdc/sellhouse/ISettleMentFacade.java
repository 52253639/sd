package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.framework.*;
import java.util.Set;

public interface ISettleMentFacade extends IBizCtrl
{
    public Set dealQuitRoom(IObjectPK pk) throws BOSException, EASBizException;
    public Set dealChangeRoom(IObjectPK pk) throws BOSException, EASBizException;
    public void dealSaleBalance(IObjectPK pk) throws BOSException, EASBizException;
    public void dealAntiSaleBalance(IObjectPK pk) throws BOSException, EASBizException;
}