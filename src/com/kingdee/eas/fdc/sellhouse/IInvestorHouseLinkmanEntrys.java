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

public interface IInvestorHouseLinkmanEntrys extends IDataBase
{
    public InvestorHouseLinkmanEntrysInfo getInvestorHouseLinkmanEntrysInfo(IObjectPK pk) throws BOSException, EASBizException;
    public InvestorHouseLinkmanEntrysInfo getInvestorHouseLinkmanEntrysInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public InvestorHouseLinkmanEntrysInfo getInvestorHouseLinkmanEntrysInfo(String oql) throws BOSException, EASBizException;
    public InvestorHouseLinkmanEntrysCollection getInvestorHouseLinkmanEntrysCollection() throws BOSException;
    public InvestorHouseLinkmanEntrysCollection getInvestorHouseLinkmanEntrysCollection(EntityViewInfo view) throws BOSException;
    public InvestorHouseLinkmanEntrysCollection getInvestorHouseLinkmanEntrysCollection(String oql) throws BOSException;
}