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

public interface IPaySplit4Voucher extends IBillEntryBase
{
    public PaySplit4VoucherInfo getPaySplit4VoucherInfo(IObjectPK pk) throws BOSException, EASBizException;
    public PaySplit4VoucherInfo getPaySplit4VoucherInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public PaySplit4VoucherInfo getPaySplit4VoucherInfo(String oql) throws BOSException, EASBizException;
    public PaySplit4VoucherCollection getPaySplit4VoucherCollection() throws BOSException;
    public PaySplit4VoucherCollection getPaySplit4VoucherCollection(EntityViewInfo view) throws BOSException;
    public PaySplit4VoucherCollection getPaySplit4VoucherCollection(String oql) throws BOSException;
}