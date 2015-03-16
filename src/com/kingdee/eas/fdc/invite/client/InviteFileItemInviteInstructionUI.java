/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.commonquery.QuerySolutionInfo;
import com.kingdee.eas.base.commonquery.client.Util;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.fdc.invite.InviteFileItemTypeEnum;
import com.kingdee.eas.fdc.invite.InviteTypeInfo;

/**
 * output class name
 */
public class InviteFileItemInviteInstructionUI extends AbstractInviteFileItemInviteInstructionUI
{
    private static final Logger logger = CoreUIObject.getLogger(InviteFileItemInviteInstructionUI.class);
    
    /**
     * output class constructor
     */
    public InviteFileItemInviteInstructionUI() throws Exception
    {
        super();
    }

	public void onLoad() throws Exception {
		// TODO Auto-generated method stub
		super.onLoad();
		getUIContext().put("billType", InviteFileItemTypeEnum.INVITEINSTRUCTION_VALUE);
//	
	}

//public void actionQuery_actionPerformed(ActionEvent e) throws Exception {
//		
//		super.actionQuery_actionPerformed(e);
//			
//}    
//    
//protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
//	
//	
//	FilterInfo filter = null;
//	try {
//		filter = this.getMainFilter();
//		if(this.getDialog()!=null){
//			FilterInfo commFilter = this.getDialog().getCommonFilter();
//			if(filter!=null&&commFilter!=null)
//				filter.mergeFilter(commFilter, "and");
//		}else{
//			QuerySolutionInfo querySolution = this.getQuerySolutionInfo();
//			if (querySolution!=null&&querySolution.getEntityViewInfo()!=null)
//	        {
//	            EntityViewInfo ev=Util.getInnerFilterInfo(querySolution);
//	            if(ev.getFilter()!=null){
//	            	filter.mergeFilter(ev.getFilter(), "and");
//	            }
//	        }
//		}
//		filter.mergeFilter(getFileTypeFilter(), "and");
//		} catch (BOSException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	
//	
//	viewInfo.setFilter(filter);
//	
//	
//	return super.getQueryExecutor(queryPK, viewInfo);
//}
}