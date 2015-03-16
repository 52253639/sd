package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import java.util.Map;
import java.lang.Object;
import com.kingdee.bos.framework.*;

public interface IRoomPropertyBookFacade extends IBizCtrl
{
    public Map getMutilRoomPropertyCollection(Object selectedObj, Map paramMap) throws BOSException, EASBizException;
    public void updateRoomProperty(Map paramMap) throws BOSException, EASBizException;
    public Map getStepAndMatarilState(Map paramMap) throws BOSException, EASBizException;
}