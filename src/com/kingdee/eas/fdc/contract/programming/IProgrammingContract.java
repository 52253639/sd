package com.kingdee.eas.fdc.contract.programming;

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
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IProgrammingContract extends ITreeBase
{
    public ProgrammingContractInfo getProgrammingContractInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ProgrammingContractInfo getProgrammingContractInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ProgrammingContractInfo getProgrammingContractInfo(String oql) throws BOSException, EASBizException;
    public ProgrammingContractCollection getProgrammingContractCollection() throws BOSException;
    public ProgrammingContractCollection getProgrammingContractCollection(EntityViewInfo view) throws BOSException;
    public ProgrammingContractCollection getProgrammingContractCollection(String oql) throws BOSException;
}