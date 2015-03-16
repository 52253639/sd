package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import java.util.List;
import com.kingdee.eas.framework.IDataBase;

public interface IChangeRecordEntryTwo extends IDataBase
{
    public ChangeRecordEntryTwoInfo getChangeRecordEntryTwoInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ChangeRecordEntryTwoInfo getChangeRecordEntryTwoInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ChangeRecordEntryTwoInfo getChangeRecordEntryTwoInfo(String oql) throws BOSException, EASBizException;
    public ChangeRecordEntryTwoCollection getChangeRecordEntryTwoCollection() throws BOSException;
    public ChangeRecordEntryTwoCollection getChangeRecordEntryTwoCollection(EntityViewInfo view) throws BOSException;
    public ChangeRecordEntryTwoCollection getChangeRecordEntryTwoCollection(String oql) throws BOSException;
    public boolean changeRecordSave(SincerityPurchaseInfo sinPurInfo, String appoinmentPeople, String appoimentPhone, List customList, IObjectValue model) throws BOSException, EASBizException;
}