package com.kingdee.eas.fdc.invite.markesupplier.uitl;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.workflow.ProcessInstInfo;
import com.kingdee.bos.workflow.service.EnactmentServiceProxy;
import com.kingdee.bos.workflow.service.IWfDefineService;
import com.kingdee.bos.workflow.service.WfDefineService;
import com.kingdee.bos.workflow.service.ormrpc.EnactmentServiceFactory;
import com.kingdee.bos.workflow.service.ormrpc.IEnactmentService;
import com.kingdee.eas.util.app.ContextUtil;

public class WorkflowHelper {
	/**
     * 描述：判断单据是否在工作流中
     * 创建时间：2006-12-27 <p>
     */
    public static boolean isRunningWorkflow(Context ctx, String objId) throws BOSException {
        boolean hasWorkflow = false;
        IWfDefineService service = WfDefineService.getService(ctx);
        String procDefID = service.findSubmitProcDef((BOSUuid.read(objId)).getType(), ContextUtil.getCurrentUserInfo(ctx).getId().toString());
        if (procDefID != null) {
            IEnactmentService service2 = EnactmentServiceProxy.getEnacementService(ctx);
            ProcessInstInfo[] procInsts = service2.getProcessInstanceByHoldedObjectId(objId);
            for (int i = 0, n = procInsts.length; i < n; i++) {
                if ("open.running".equals(procInsts[i].getState())) {
                    hasWorkflow = true;
                    break;
                }
            }
        }
        return hasWorkflow;
    }
    
    /**
     * 描述：判断单据是否在工作流中 client
     * 创建时间：2009-1-8 <p>
     */
    public static boolean isRunningWorkflow(String objId) throws BOSException {
        boolean hasWorkflow = false;
        IEnactmentService service2 = EnactmentServiceFactory.createRemoteEnactService();
        ProcessInstInfo[] procInsts = service2.getProcessInstanceByHoldedObjectId(objId);
        for (int i = 0, n = procInsts.length; i < n; i++) {
            if ("open.running".equals(procInsts[i].getState())) {
                hasWorkflow = true;
                break;
            }
        }
        return hasWorkflow;
    }
    
    /**
     * 终止某条单据的工作流
     * @param bizObjId
     * @throws BOSException 
     */
    public static void abortProcessWorkflow(Context ctx,String bizObjId) throws BOSException {
		IEnactmentService service = EnactmentServiceFactory.createEnactService(ctx);
		ProcessInstInfo[] procInsts = service.getProcessInstanceByHoldedObjectId(bizObjId);

		for (int j = 0; j < procInsts.length; j++) {
			if ("open.running".equals(procInsts[j].getState())) {
				ProcessInstInfo instInfo = procInsts[j];
				service.abortProcessInst(instInfo.getProcInstId());
			}

		}
    }
}
