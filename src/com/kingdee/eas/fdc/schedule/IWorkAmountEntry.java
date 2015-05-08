package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillEntryBase;

public interface IWorkAmountEntry extends ICoreBillEntryBase
{
    public WorkAmountEntryCollection getWorkAmountEntryCollection() throws BOSException;
    public WorkAmountEntryCollection getWorkAmountEntryCollection(EntityViewInfo view) throws BOSException;
    public WorkAmountEntryCollection getWorkAmountEntryCollection(String oql) throws BOSException;
}