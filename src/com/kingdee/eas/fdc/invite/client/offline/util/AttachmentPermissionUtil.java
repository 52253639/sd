package com.kingdee.eas.fdc.invite.client.offline.util;

import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.workflow.ActivityInstInfo;
import com.kingdee.bos.workflow.AssignmentInfo;
import com.kingdee.bos.workflow.ProcessInstInfo;
import com.kingdee.bos.workflow.metas.WfAssignmentState;
import com.kingdee.bos.workflow.metas.WfState;
import com.kingdee.bos.workflow.service.ormrpc.EnactmentServiceFactory;
import com.kingdee.bos.workflow.service.ormrpc.IEnactmentService;
import com.kingdee.bos.workflow.service.ormrpc.IWfFacade;
import com.kingdee.bos.workflow.service.ormrpc.WfFacadeFactory;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fi.cas.BillStatusEnum;

/**
 * 招标附件管理审批中状态判断
 * @author guangyue_liu
 * 2010-04-29
 */
public class AttachmentPermissionUtil {

	
	/**
	 * 判断附件管理功能是否可用，在审批中状态，只有当前流程的执行人才可以进行编辑附件，且只能是编辑自己上传的
	 * @param state
	 * @param billID
	 * @return
	 * @throws BOSException
	 */
	public static boolean checkAuditingAttachmentEdit(Object state,String billID,boolean isEdit) throws BOSException{
		//如果是审批中状态，则判断当前审批流程对应的执行是不是当前登录，如果是则可以进行编辑。否则不允许
		if (state != null
				&& (state.toString().equals(FDCBillStateEnum.AUDITTING.toString())
					||state.toString().equals(BillStatusEnum.AUDITING.toString()))) {
			IEnactmentService service2 = EnactmentServiceFactory.createRemoteEnactService();
			ProcessInstInfo[] processInsts = service2.getProcessInstanceByHoldedObjectId(billID);
			if(processInsts != null && processInsts.length > 0 && processInsts[0]!=null){
				//获取第一个流程实例
				String procInstId = processInsts[0].getProcInstId();
				
				ActivityInstInfo[] actInsts = service2.getActInstMetaArrayByProcInstId(procInstId);
				ActivityInstInfo currentactInst = null;
				for(int i = 0;i < actInsts.length;i ++){
					if(actInsts[i] != null 
							&& WfState.NOT_STARTED_VALUE.equals(actInsts[i].getState())){
						currentactInst = actInsts[i];
					}
				}
		
				if(currentactInst!=null){
					//得到当前活动未开始（活动）的节点的执行人
//					IWfFacade facade=WfFacadeFactory.getRemoteInstance();
//			        Set userIds=facade.getPerformerUserIds(currentactInst.getActInstId());
			        
			        IEnactmentService esc = EnactmentServiceFactory.createRemoteEnactService();
	
			        AssignmentInfo[] assignments = esc.getAllAssignmentByActInstId(currentactInst.getActInstId());
			        
			        //得到当前登录用户
			        String currUserId = SysContext.getSysContext().getCurrentUserInfo().getId().toString();
//			        if(userIds.contains(currUserId)){
//			        	isEdit = true;
//			        }else{
//			        	isEdit = false;
//			        }
			        
			        for(int i=0;i<assignments.length;i++){
			        	AssignmentInfo assignment = assignments[i];
			        	if((WfAssignmentState.ASSIGNED.equals(assignment.getState())
			        			||  WfAssignmentState.PROCESSING.equals(assignment.getState()) )
			        			&& currUserId.equals(assignment.getUserId())){
			        		isEdit = true;
			        		break;
			        	}else{
			        		isEdit = false;
			        	}
			        }
			        
		        }
			}         
		} 
		
		return isEdit;
	}
	
}
