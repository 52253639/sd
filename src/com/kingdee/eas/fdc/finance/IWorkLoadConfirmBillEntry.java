package com.kingdee.eas.fdc.finance;

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
import com.kingdee.eas.framework.IBillEntryBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IWorkLoadConfirmBillEntry extends IBillEntryBase
{
    public WorkLoadConfirmBillEntryInfo getWorkLoadConfirmBillEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public WorkLoadConfirmBillEntryInfo getWorkLoadConfirmBillEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public WorkLoadConfirmBillEntryInfo getWorkLoadConfirmBillEntryInfo(String oql) throws BOSException, EASBizException;
    public WorkLoadConfirmBillEntryCollection getWorkLoadConfirmBillEntryCollection() throws BOSException;
    public WorkLoadConfirmBillEntryCollection getWorkLoadConfirmBillEntryCollection(EntityViewInfo view) throws BOSException;
    public WorkLoadConfirmBillEntryCollection getWorkLoadConfirmBillEntryCollection(String oql) throws BOSException;
}