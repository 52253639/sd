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
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface INewQuotingPrice extends IFDCBill
{
    public NewQuotingPriceInfo getNewQuotingPriceInfo(IObjectPK pk) throws BOSException, EASBizException;
    public NewQuotingPriceInfo getNewQuotingPriceInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public NewQuotingPriceInfo getNewQuotingPriceInfo(String oql) throws BOSException, EASBizException;
    public NewQuotingPriceCollection getNewQuotingPriceCollection() throws BOSException;
    public NewQuotingPriceCollection getNewQuotingPriceCollection(EntityViewInfo view) throws BOSException;
    public NewQuotingPriceCollection getNewQuotingPriceCollection(String oql) throws BOSException;
    public void acceptBidExportQuoting(String quotingId, String listingId) throws BOSException, EASBizException;
    public void acceptBid(String quotingId) throws BOSException, EASBizException;
    public void exportQuoting(String quotingId, String listingId) throws BOSException, EASBizException;
    public void unacceptBid(String quotingId) throws BOSException, EASBizException;
}