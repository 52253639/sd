package com.kingdee.eas.fdc.invite.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean;
//import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.invite.ExpertAppraiseFactory;
import com.kingdee.eas.fdc.invite.ExpertQualifyEntryFactory;
import com.kingdee.eas.fdc.invite.InviteProjectCollection;
import com.kingdee.eas.fdc.invite.InviteProjectException;
import com.kingdee.eas.fdc.invite.InviteProjectFactory;
import com.kingdee.eas.fdc.invite.InviteProjectInfo;
import com.kingdee.eas.fdc.invite.InviteProjectStateEnum;
import com.kingdee.eas.fdc.invite.InviteStateEnum;
import com.kingdee.eas.util.app.ContextUtil;

public class InviteProjectAppraiseFacadeControllerBean extends AbstractInviteProjectAppraiseFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.invite.app.InviteProjectAppraiseFacadeControllerBean");
    protected void _checkCanAddExpertAppraise(Context ctx, String inviteProjectID)throws BOSException, EASBizException
    {
    	String creatorId = ContextUtil.getCurrentUserInfo(ctx).getId().toString();
    	SelectorItemCollection sic = new SelectorItemCollection();
    	sic.add("appraiseTemplate.id");
    	sic.add("techInviteAppDate");
    	InviteProjectInfo project = InviteProjectFactory.getLocalInstance(ctx).getInviteProjectInfo(new ObjectUuidPK(inviteProjectID),sic);
    	
    	java.sql.Timestamp sqlTime= FDCCommonServerHelper.getServerTimeStamp();
		
    	/*deleted by msb 2010/04/27**/
    	/*
		if(project.getTechInviteAppDate()!=null&&sqlTime.compareTo(project.getTechInviteAppDate())<0){
			throw new InviteProjectException(InviteProjectException.CANTADDEXPERTAPPRAISE);
		}*/
		if(project.getAppraiseTemplate()==null)
			throw new InviteProjectException(InviteProjectException.HASNOTSETTEMPLATE);
		
    	FilterInfo filter = new FilterInfo();
    	
    	filter.getFilterItems().clear();
		filter.getFilterItems().add(new FilterItemInfo("parent.inviteProject",inviteProjectID));
		filter.getFilterItems().add(new FilterItemInfo("expert.user",creatorId));
		if(!ExpertQualifyEntryFactory.getLocalInstance(ctx).exists(filter)){
			throw new InviteProjectException(InviteProjectException.LOGINUSERNOTINEXPERTLISTS);
		}
		filter.getFilterItems().clear();
		filter.getFilterItems().add(new FilterItemInfo("inviteProject",inviteProjectID));
		filter.getFilterItems().add(new FilterItemInfo("creator",creatorId));
		if(ExpertAppraiseFactory.getLocalInstance(ctx).exists(filter)){
			throw new InviteProjectException(InviteProjectException.LOGINUSERHASAPPRAISE);
		}
    }
	protected void _aotuSetInviteStatusAppraise(Context ctx)
			throws BOSException, EASBizException {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Set states = new HashSet();
			
			states.add(String.valueOf(InviteProjectStateEnum.AUDITTED_VALUE));
			states.add(String.valueOf(InviteProjectStateEnum.FILEMAKING_VALUE));
			states.add(String.valueOf(InviteProjectStateEnum.ANSWERING_VALUE));
			filter.getFilterItems().add(new FilterItemInfo("techInviteAppDate",sdf.parse(sdf.format(date)),CompareType.GREATER_EQUALS));
			filter.getFilterItems().add(new FilterItemInfo("techInviteAppDate",sdf.parse(sdf.format(FDCDateHelper.addDays(date, 1))),CompareType.LESS));
			filter.getFilterItems().add(new FilterItemInfo("inviteProjectState",InviteProjectStateEnum.AUDITTED_VALUE,CompareType.EQUALS));
			filter.getFilterItems().add(new FilterItemInfo("inviteProjectState",InviteProjectStateEnum.FILEMAKING_VALUE,CompareType.EQUALS));
			filter.getFilterItems().add(new FilterItemInfo("inviteProjectState",InviteProjectStateEnum.ANSWERING_VALUE,CompareType.EQUALS));
			filter.setMaskString(" #0 and #1 and ( #2 or #3 or #4 )");
			InviteProjectCollection cc = InviteProjectFactory.getLocalInstance(ctx).getInviteProjectCollection(view);
			for(int i=0;i<cc.size();i++){
//				InviteProjectFactory.getLocalInstance(ctx).setAppraising(new ObjectUuidPK(cc.get(i).getId()));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	}
}