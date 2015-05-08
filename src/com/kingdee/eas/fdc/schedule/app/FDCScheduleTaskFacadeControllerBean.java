package com.kingdee.eas.fdc.schedule.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import javax.swing.tree.MutableTreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.schedule.ProjectSpecialCollection;
import com.kingdee.eas.fdc.schedule.ProjectSpecialFactory;
import com.kingdee.eas.fdc.schedule.ProjectSpecialInfo;

public class FDCScheduleTaskFacadeControllerBean extends AbstractFDCScheduleTaskFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.schedule.app.FDCScheduleTaskFacadeControllerBean");
    
    protected HashMap _getMuTaskTreeNodes(Context ctx, HashSet projectNodes)throws BOSException, EASBizException
    {
    	if (projectNodes == null)
			return null;
		
		HashMap muTaskNodes = new HashMap();
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("curProject.id", projectNodes, CompareType.INCLUDE));
		view.setFilter(filter);
		ProjectSpecialCollection specialColl = ProjectSpecialFactory.getLocalInstance(ctx).getProjectSpecialCollection(view);
		for(int i = 0; i < specialColl.size(); i++) {
			ProjectSpecialInfo thisInfo = specialColl.get(i);
			MutableTreeNode newNode = new DefaultKingdeeTreeNode(thisInfo.getName());
			String key = thisInfo.getCurProject().getId().toString();
			if(newNode != null) {
				newNode.setUserObject(thisInfo);
				if (muTaskNodes.containsKey(key)) {
					ArrayList nodeList = (ArrayList) muTaskNodes.get(key);
					nodeList.add(newNode);
					muTaskNodes.put(key, nodeList);
				} else {
					ArrayList nodeList = new ArrayList();
					nodeList.add(newNode);
					muTaskNodes.put(key, nodeList);
				}
			}
		}
		return muTaskNodes;
    }
}