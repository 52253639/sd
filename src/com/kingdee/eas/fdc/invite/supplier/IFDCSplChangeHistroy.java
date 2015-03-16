package com.kingdee.eas.fdc.invite.supplier;

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

public interface IFDCSplChangeHistroy extends IFDCBill
{
    public FDCSplChangeHistroyInfo getFDCSplChangeHistroyInfo(IObjectPK pk) throws BOSException, EASBizException;
    public FDCSplChangeHistroyInfo getFDCSplChangeHistroyInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public FDCSplChangeHistroyInfo getFDCSplChangeHistroyInfo(String oql) throws BOSException, EASBizException;
    public FDCSplChangeHistroyCollection getFDCSplChangeHistroyCollection() throws BOSException;
    public FDCSplChangeHistroyCollection getFDCSplChangeHistroyCollection(EntityViewInfo view) throws BOSException;
    public FDCSplChangeHistroyCollection getFDCSplChangeHistroyCollection(String oql) throws BOSException;
}