package com.kingdee.eas.fdc.aimcost.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.aimcost.CostAcctOrgAssignCollection;
import com.kingdee.eas.fdc.aimcost.CostAcctOrgAssignInfo;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;

public class CostAcctOrgAssignControllerBean extends AbstractCostAcctOrgAssignControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.aimcost.app.CostAcctOrgAssignControllerBean");
    protected void _submitCollection(Context ctx, IObjectCollection col) throws BOSException, EASBizException {
    	CostAcctOrgAssignCollection c=(CostAcctOrgAssignCollection)col;
    	if(c==null||c.isEmpty()){
    		return;
    	}
    	String objectId=c.get(0).getObjectId();
    	String orgId=c.get(0).getOrgUnit().getId().toString();
    	FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
    	builder.appendSql("delete from T_AIM_CostAcctOrgAssign where fobjectId=? and forgunitId=?");
    	builder.addParam(objectId);
    	builder.addParam(orgId);
    	builder.execute();
    	if(c.get(0).getCostAccount()==null){
    		return;
    	}
    	builder.clear();
    	String sql="insert into T_AIM_CostAcctOrgAssign (fid,fcostAccountId,forgUnitId,fobjectId) values(?,?,?,?)";
    	List list=new ArrayList();
    	for(int i=0;i<c.size();i++)	{
    		CostAcctOrgAssignInfo info=(CostAcctOrgAssignInfo)c.get(i);
    		String fid=info.getId()!=null?info.getId().toString():BOSUuid.create(info.getBOSType()).toString();
    		String fcostAccountId=info.getCostAccount().getId().toString();
    		String fobjectId=info.getObjectId();
    		String forgId=info.getOrgUnit().getId().toString();
    		list.add(Arrays.asList(new String[]{
    			fid,fcostAccountId,forgId,fobjectId	
    		}));
    	}
    	builder.executeBatch(sql, list);
    }
}