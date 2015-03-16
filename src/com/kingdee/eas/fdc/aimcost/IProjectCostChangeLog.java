package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.CoreBaseCollection;
import java.util.Set;
import com.kingdee.eas.framework.ICoreBase;

public interface IProjectCostChangeLog extends ICoreBase
{
    public boolean insertLog(Set prjSet) throws BOSException;
    public boolean updateLog(Set prjSet) throws BOSException;
    public boolean deleteLog() throws BOSException;
    public Set getChangePrjs(Set prjSet) throws BOSException;
    public Set getAllChangePrjs() throws BOSException;
}