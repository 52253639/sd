package com.kingdee.eas.fdc.contract.programming;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import java.math.BigDecimal;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;

public interface IProgramming extends IFDCBill
{
    public ProgrammingInfo getProgrammingInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ProgrammingInfo getProgrammingInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ProgrammingInfo getProgrammingInfo(String oql) throws BOSException, EASBizException;
    public ProgrammingCollection getProgrammingCollection() throws BOSException;
    public ProgrammingCollection getProgrammingCollection(EntityViewInfo view) throws BOSException;
    public ProgrammingCollection getProgrammingCollection(String oql) throws BOSException;
    public ProgrammingInfo getLastVersion(IObjectPK pk) throws BOSException, EASBizException;
    public boolean isLastVersion(IObjectPK pk) throws BOSException, EASBizException;
    public ProgrammingInfo getLastVersion(String versionGroup) throws BOSException, EASBizException;
    public void setAuttingForWF(BOSUuid billId) throws BOSException, EASBizException;
    public IObjectPK billModify(String versionGroup, String curVersion) throws BOSException, EASBizException;
    public void setSubmitStatus(BOSUuid billId) throws BOSException, EASBizException;
    public boolean isAddPCPass(BOSUuid billId, BigDecimal amount) throws BOSException, EASBizException;
    public boolean isEditPCPass(BOSUuid billId, BigDecimal amount) throws BOSException, EASBizException;
}