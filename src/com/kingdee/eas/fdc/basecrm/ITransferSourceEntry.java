package com.kingdee.eas.fdc.basecrm;

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
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBase;

public interface ITransferSourceEntry extends ICoreBase
{
    public TransferSourceEntryInfo getTransferSourceEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public TransferSourceEntryInfo getTransferSourceEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public TransferSourceEntryInfo getTransferSourceEntryInfo(String oql) throws BOSException, EASBizException;
    public TransferSourceEntryCollection getTransferSourceEntryCollection() throws BOSException;
    public TransferSourceEntryCollection getTransferSourceEntryCollection(EntityViewInfo view) throws BOSException;
    public TransferSourceEntryCollection getTransferSourceEntryCollection(String oql) throws BOSException;
}