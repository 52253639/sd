package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.eas.fdc.propertymgmt.IPPMProjectDataBase;
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

public interface IMoneyDefineSellProject extends IPPMProjectDataBase
{
    public MoneyDefineSellProjectInfo getMoneyDefineSellProjectInfo(IObjectPK pk) throws BOSException, EASBizException;
    public MoneyDefineSellProjectInfo getMoneyDefineSellProjectInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public MoneyDefineSellProjectInfo getMoneyDefineSellProjectInfo(String oql) throws BOSException, EASBizException;
    public MoneyDefineSellProjectCollection getMoneyDefineSellProjectCollection() throws BOSException;
    public MoneyDefineSellProjectCollection getMoneyDefineSellProjectCollection(EntityViewInfo view) throws BOSException;
    public MoneyDefineSellProjectCollection getMoneyDefineSellProjectCollection(String oql) throws BOSException;
}