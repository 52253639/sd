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
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IFDCPayVoucherEntry extends IFDCBaseVoucherEntry
{
    public FDCPayVoucherEntryInfo getFDCPayVoucherEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public FDCPayVoucherEntryInfo getFDCPayVoucherEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public FDCPayVoucherEntryInfo getFDCPayVoucherEntryInfo(String oql) throws BOSException, EASBizException;
    public FDCPayVoucherEntryCollection getFDCPayVoucherEntryCollection() throws BOSException;
    public FDCPayVoucherEntryCollection getFDCPayVoucherEntryCollection(EntityViewInfo view) throws BOSException;
    public FDCPayVoucherEntryCollection getFDCPayVoucherEntryCollection(String oql) throws BOSException;
}