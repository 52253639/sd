/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.app;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.ProjectStatusInfo;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractException;
import com.kingdee.eas.framework.batchHandler.RequestConstant;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.StringUtils;


/**
 * output class name
 */
public class ContractBillEditUIHandler extends AbstractContractBillEditUIHandler
{
	protected void _handleInit(RequestContext request,ResponseContext response, Context context) throws Exception {
		super._handleInit(request,response,context);
	}
	
	protected void _handleActionSplit(RequestContext request,ResponseContext response, Context context) throws Exception {
	
	}
	
	protected void _handleActionViewContent(RequestContext request,ResponseContext response, Context context) throws Exception {
	
	}
	
	protected void _handleActionContractPlan(RequestContext request,ResponseContext response, Context context) throws Exception {
	
	}
	
	protected void _handleActionDelSplit(RequestContext request,ResponseContext response, Context context) throws Exception {
	
	}
	
	protected void _handleActionViewCost(RequestContext request,ResponseContext response, Context context) throws Exception {
	
	}
	
	protected void _handleActionSubmit(RequestContext request,ResponseContext response, Context context) throws Exception {
		super._handleActionSubmit(request,response,context);
	}
	
	/**
	 * 获取数据后转换request的业务组织,coreBillEditUIHandler中进行组织转换
	 * @param request
	 * @param response
	 * @param context
	 * @throws Exception
	 */
	protected void switchRequestOrg(RequestContext request,ResponseContext response, Context context) throws Exception
	{
	   	
	}
	/**
	 * 由业务实现新增数据方法的相应业务逻辑
	 * @param request
	 * @param response
	 * @param context
	 */
	protected IObjectValue createNewData(RequestContext request,ResponseContext response, Context context) throws Exception
	{
		return null;
	}
	/**
	 * 设置默认值
	 * @param vo
	 * @throws Exception
	 */
	protected void applyDefaultValue(IObjectValue vo) throws Exception
	{
		
	}
	/**
	 * 在服务端实现客户端的rpc
	 * @param request
	 * @param response
	 * @param context
	 * @throws Exception
	 */
	protected void loadFields(RequestContext request,ResponseContext response, Context context) throws Exception
	{
		
	}
	/**
	 * 数据校验
	 * @param request
	 * @param response
	 * @param context
	 * @throws Exception
	 */
	protected void verifyInput(RequestContext request,ResponseContext response, Context context) throws Exception
	{
		ContractBillInfo editData =  (ContractBillInfo)request.get(RequestConstant.FRAMEWORK_VALUE_KEY);
		super.verifyInput(request,response,context);
		
		//校验必录项
		String msg =null;
		
		if(msg==null && editData.getContractType()==null){
			msg = "合同类型不能为空";
			
		}	
		
		if(StringUtils.isEmpty(editData.getNumber())){
			msg = "编码不能为空";			
		}
		
		if(msg==null && StringUtils.isEmpty(editData.getName())){
			msg = "名称不能为空";
			
		}
		if(msg==null && editData.getLandDeveloper()==null){
			msg = "甲方不能为空";
			
		}	
		if(msg==null && editData.getPartB()==null){
			
			msg = "乙方不能为空";
		}
		
		if(msg==null && editData.getRespDept()==null){
			msg = "责任部门不能为空";
			
		}
		
		if(msg==null && editData.getOriginalAmount()==null){
			msg = "金额不能为空";
			
		}
		
		if(msg==null && editData.getRespPerson()==null){
			msg = "责任人不能为空";
			
		}
		
//		如果项目状态已关闭，则不能选择是否成本拆分 
		if(editData != null && editData.getCurProject() != null) {
			BOSUuid id = editData.getCurProject().getId();
			
			SelectorItemCollection selectors = new SelectorItemCollection();
			selectors.add("projectStatus");
			CurProjectInfo curProjectInfo = CurProjectFactory.getLocalInstance(context).getCurProjectInfo(new ObjectUuidPK(id), selectors);
		
			if(curProjectInfo.getProjectStatus() != null) {
				String closedState = ProjectStatusInfo.closeID;
				String projStateId = curProjectInfo.getProjectStatus().getId().toString();
				if(projStateId != null && projStateId.equals(closedState)) {
					if(editData.isIsCoseSplit()) {
						msg = "该合同所在的工程项目已经处于关闭状态，不能进入动态成本，请取消\"进入动态成本\"的选择再保存/提交";
//						MsgBox.showWarning(this, "该合同所在的工程项目已经处于关闭状态，不能进入动态成本，请取消\"进入动态成本\"的选择再保存/提交");
//						SysUtil.abort();
						
					}
				}
			}
		}
		
		if(msg!=null){
			throw new ContractException(ContractException.WITHMSG,new Object[]{msg});
		}
	}
	/**
	 * 保存数据时更给数据对象
	 * @param request
	 * @param response
	 * @param context
	 * @throws Exception
	 */
	protected void storeFields(RequestContext request,ResponseContext response, Context context) throws Exception
	{
		
	}

	protected void _handleActionViewBgBalance(RequestContext request,
			ResponseContext response, Context context) throws Exception {
		
	}

	protected void _handleActionViewAttachmentSelf(RequestContext request,
			ResponseContext response, Context context) throws Exception {
		// TODO Auto-generated method stub
		
	}

	protected void _handleActionViewContentSelf(RequestContext request,
			ResponseContext response, Context context) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}