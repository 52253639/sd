package com.kingdee.eas.fdc.schedule.app;

import java.sql.Timestamp;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.schedule.FDCScheduleFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.ProjectImageInfo;
import com.kingdee.eas.fdc.schedule.ScheduleException;
import com.kingdee.eas.fdc.schedule.framework.ScheduleStateEnum;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.util.StringUtils;

public class ProjectImageControllerBean extends AbstractProjectImageControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.schedule.app.ProjectImageControllerBean");
    
    /**
    *
    * @param ctx Context
    * @param coreBaseInfo CoreBaseInfo
    * @throws BOSException
    * @return IObjectPK
    */
   protected IObjectPK _save(Context ctx, IObjectValue model)
       throws BOSException, EASBizException {
	   
	   ProjectImageInfo info = (ProjectImageInfo)model;
	   //形象进度填报时可以不选 相关任务
	   if(info.getRelateTask()!=null){
		   FDCScheduleTaskInfo taskInfo = FDCScheduleTaskFactory.getLocalInstance(ctx).getFDCScheduleTaskInfo(new ObjectUuidPK(info.getRelateTask().getSrcID()));
		   info.setRelateTask(taskInfo);
	   }

	   if(info.getProject() == null){
		   if(info.getRelateTask()!=null && info.getRelateTask().getSchedule()!=null){
			   SelectorItemCollection sic = new SelectorItemCollection();
			   sic.add("id");
			   sic.add("project.id");
			   sic.add("project.name");
			   sic.add("project.number");
			   sic.add("project.fullorgunit.id");
			   sic.add("project.fullorgunit.name");
			   sic.add("project.fullorgunit.number");
			   FDCScheduleInfo scheduleInfo = FDCScheduleFactory.getLocalInstance(ctx).getFDCScheduleInfo(new ObjectUuidPK(info.getRelateTask().getSchedule().getId()),sic);
			   if(scheduleInfo.getProject()!=null){
				   info.setProject(scheduleInfo.getProject());
			   }
			   
			   if(scheduleInfo.getProject().getFullOrgUnit()!=null){
				   info.setOrgUnit(scheduleInfo.getProject().getFullOrgUnit());
			   }
		   }
	   }
	   
	   _checkNumberDup(ctx,model);
	   _checkNameDup(ctx,model);
	   info.setState(ScheduleStateEnum.SAVED);
	   IObjectPK pk = super._save(ctx, model);
       return pk;
   }
   
   /**
   *
   * @param ctx Context
   * @param pk IObjectPK
   * @param coreBaseInfo CoreBaseInfo
   * @throws BOSException
   */
  protected IObjectPK _submit(Context ctx, IObjectValue model)
      throws BOSException, EASBizException {
	  
	  ProjectImageInfo info = (ProjectImageInfo)model;	  
	  if (info.getId() == null ||
				!this._exists(ctx, new ObjectUuidPK(info.getId()))) {
			handleIntermitNumber(ctx, info);
	  }
	  _checkNumberDup(ctx,model);
	  _checkNameDup(ctx,model);
	  
	   info.setState(ScheduleStateEnum.SUBMITTED);
      return super._submit(ctx, model);
  }
  
  /**
   * 审核
   * @param ctx Context
   * @param pk IObjectPK
   * @param billBaseInfo BillBaseInfo
   * @throws BOSException
   */
  protected void _passAudit(Context ctx, IObjectPK pk, IObjectValue model)throws EASBizException, BOSException{
	  ProjectImageInfo info = (ProjectImageInfo)model;
	  info.setState(ScheduleStateEnum.AUDITTED);
	  
	  super._passAudit(ctx, pk, model);
  }
  
 
  
  /**
   * 反审核
   * @param ctx Context
   * @param pk IObjectPK
   * @param billBaseInfo BillBaseInfo
   * @throws BOSException
   */
  protected void _unpassAudit(Context ctx, IObjectPK pk, IObjectValue model)throws EASBizException, BOSException{
	  ProjectImageInfo info = (ProjectImageInfo)model;
	  info.setState(ScheduleStateEnum.SUBMITTED);
	  
	  super._unpassAudit(ctx, pk, model);
  }
  
  /**
	 * 重载基类校验编码重复代码，改为同一工程项目下的编码不能重复
	 */
	protected void _checkNumberDup(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {

		// ProjectImageInfo piInfo = (ProjectImageInfo) model;
		// FilterInfo filter = new FilterInfo();
		// filter.getFilterItems().add(new
		// FilterItemInfo("project.id",piInfo.getProject
		// ().getId(),CompareType.EQUALS));
		// filter.getFilterItems().add(new
		// FilterItemInfo("number",piInfo.getNumber(),CompareType.EQUALS));
		// filter.getFilterItems().add(new
		// FilterItemInfo("id",piInfo.getId(),CompareType.NOTEQUALS));
		// if(this._exists(ctx, filter)){
		// throw new EASBizException(EASBizException.CHECKDUPLICATED, new
		// Object[] { piInfo.getNumber() });
		// }
	}
	
	/**
	 * 重载基类校验名称重复代码，改为同一工程项目下的名称不能重复
	 */
	protected void _checkNameDup(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		ProjectImageInfo piInfo = (ProjectImageInfo) model;
		FilterInfo filter = new FilterInfo();
		if(piInfo.getProject()!=null){
		 filter.getFilterItems().add(new FilterItemInfo("project.id", piInfo.getProject().getId(), CompareType.EQUALS));
		}
		filter.getFilterItems().add(new FilterItemInfo("name", piInfo.getName(), CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("id", piInfo.getId(), CompareType.NOTEQUALS));
		if (this._exists(ctx, filter)) {
			throw new ScheduleException(ScheduleException.WITHMSG,new Object[]{"在同一工程项目下名称不能重复！"});
		}
	}

	protected Map _audit(Context ctx, Set ids,UserInfo auditor) throws BOSException,
			EASBizException {
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		builder.appendSql("update T_SCH_ProjectImage set fstate=?,fauditorid =? ,faudittime =? where ");
		builder.addParam(ScheduleStateEnum.AUDITTED_VALUE);
		builder.addParam(auditor.getId().toString());
		builder.addParam(new Timestamp(System.currentTimeMillis()));
		builder.appendParam("fid", ids.toArray());
		builder.execute();
		//错误提示等信息以后可以通过Map返回
		return null;
	}



	protected Map _unAudit(Context ctx, Set ids, UserInfo unAuditor)
			throws BOSException, EASBizException {
		//checkBeforeUnAudit
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		builder.appendSql("update T_SCH_ProjectImage set fstate=? ,fauditorid =? ,faudittime =? where ");
		builder.addParam(ScheduleStateEnum.SUBMITTED_VALUE);
		
		builder.addParam(null);
		builder.addParam(null);
		
		builder.appendParam("fid", ids.toArray());
		builder.execute();
		//错误提示等信息以后可以通过Map返回
		return null;
	}
	protected void handleIntermitNumber(Context ctx, ProjectImageInfo info) throws BOSException, CodingRuleException, EASBizException{
		//如果用户在客户端手工选择了断号,则此处不必在抢号
		if(info.getNumber() != null && info.getNumber().length() > 0){
			return;
		}
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
		//对成本中心进行处理
		String costUnitId= info.getOrgUnit().getId().toString();
        if(StringUtils.isEmpty(costUnitId)){
    	   return;
        }
        boolean isExist = true;
        if (!iCodingRuleManager.isExist(info, costUnitId)){
        	costUnitId = ContextUtil.getCurrentOrgUnit(ctx).getId().toString();
      		if (!iCodingRuleManager.isExist(info, costUnitId)){
      			isExist = false; 
      		}
        }
        if(isExist){
    	    // 选择了断号支持或者没有选择新增显示,获取并设置编号
            if (iCodingRuleManager.isUseIntermitNumber(info, costUnitId) || !iCodingRuleManager.isAddView(info, costUnitId)){
            // 此处的orgId与步骤1）的orgId是一致的，判断用户是否启用断号支持功能
                // 启用了断号支持功能，此时只是读取当前最新编码，真正的抢号在保存时
                String number = iCodingRuleManager.getNumber(info,costUnitId);
                info.setNumber(number);
            }
        }
	}
	/**
	 * 回收Number，如果配置了编码规则并支持断号的话
	 * @param ctx
	 * @param pk
	 * @throws BOSException
	 * @throws EASBizException
	 * @throws CodingRuleException
	 */
	protected void recycleNumber(Context ctx, IObjectPK pk) throws BOSException, EASBizException, CodingRuleException {
		ProjectImageInfo info = (ProjectImageInfo)_getValue(ctx, pk);
		
		OrgUnitInfo currentCostUnit = ContextUtil.getCurrentCostUnit(ctx);		
		if(currentCostUnit == null){
			currentCostUnit = ContextUtil.getCurrentOrgUnit(ctx);
		}
		String curOrgId = currentCostUnit.getId().toString();
		if(info.getNumber()!=null&&info.getNumber().length()>0){
			ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
	        if (iCodingRuleManager.isExist(info, curOrgId) && iCodingRuleManager.isUseIntermitNumber(info, curOrgId)) {
	            iCodingRuleManager.recycleNumber(info, curOrgId, info.getNumber());
	        }
		}
	}
	// 添加了 在删除时 回收编码
	protected void _delete(Context ctx, IObjectPK pk) throws BOSException,
			EASBizException {
		recycleNumber(ctx, pk);
		super._delete(ctx, pk);
	}
	protected void _delete(Context ctx, IObjectPK[] arrayPK)
			throws BOSException, EASBizException {
		for (int i = 0; i < arrayPK.length; i++) {
			recycleNumber(ctx, arrayPK[i]);
		}
		super._delete(ctx, arrayPK);
	}
}