package com.kingdee.eas.fdc.invite.app;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.invite.InviteTeamPersonCollection;
import com.kingdee.eas.fdc.invite.InviteTeamPersonFactory;
import com.kingdee.eas.framework.IFWEntityStruct;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 招标小组ControllBean
 * @author liangliang_ye
 *
 */
public class InviteTeamControllerBean extends AbstractInviteTeamControllerBean {
	private static final long serialVersionUID = 1L;
	private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.invite.app.InviteTeamControllerBean");
	
	/**
	 * 获取校验招标立项规则的filter
	 */
	public FilterInfo getCheckInviteProjectFilter(IObjectValue model){
		FilterInfo filter = new FilterInfo();
		if (model.get("id") != null){
			filter.getFilterItems().add(new FilterItemInfo(IFWEntityStruct.coreBase_ID ,
					model.get("id") ,CompareType.NOTEQUALS));
		}
		IObjectValue model2=(IObjectValue) model.get("inviteProject");
		if(model2!=null){
		filter.getFilterItems().add(
					new FilterItemInfo("inviteProject.id",model2.get("id")));
		}
		return filter;
	}
	
	protected void checkInviteProjectDup(Context ctx, IObjectValue model)throws BOSException, EASBizException {
		FilterInfo filter = getCheckInviteProjectFilter(model);
		if (_exists(ctx, filter)) {
			throw new BOSException("招标立项对应的招标小组已经存在");
		}
	}
	
	protected IObjectPK _save(Context ctx, IObjectValue model)throws BOSException, EASBizException {
		checkInviteProjectDup(ctx,model);
		return super._save(ctx, model);
	}
	
	protected IObjectPK _submit(Context ctx, IObjectValue model)throws BOSException, EASBizException {
		checkInviteProjectDup(ctx, model);
		return super._submit(ctx, model);
	}

	/**
	 * 获取某个职员所在的行政组织id
	 * @author liangliang_ye
	 */
	protected String _getPersonAdminOrgUnit(Context ctx, BOSUuid id)
			throws BOSException, EASBizException {
		StringBuffer sql=new StringBuffer(
				"select T_ORG_Position.FADMINORGUNITID AS ADMINID  from T_ORG_PositionMember left outer join T_BD_Person on T_BD_Person.fid=T_ORG_PositionMember.FPersonID");
		sql.append(" left outer join T_ORG_Position")
		.append(" ON T_ORG_Position.FID=T_ORG_PositionMember.FPOSITIONID")
		.append(" where  T_ORG_PositionMember.FIsPrimary=1 and T_ORG_PositionMember.FPersonID='")
		.append(id).append("'");
		logger.info("sql is:"+sql);
		IRowSet rowSet=DbUtil.executeQuery(ctx,sql.toString());
		if(rowSet!=null&&rowSet.size()>0){
			try {
				if(rowSet.next()){
					return rowSet.getString("ADMINID");
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new BOSException(e);
			}
		}
		return null;
	}

    /**
	 * 取消名称的前后空格
	 * @param fDCBillInfo
	 */
	protected void trimName(FDCBillInfo fDCBillInfo) {
		if(fDCBillInfo.getName() != null) {
			fDCBillInfo.setName(fDCBillInfo.getName().trim());
			if(fDCBillInfo.getName().length()>80) {
				fDCBillInfo.setName(fDCBillInfo.getName().substring(0,80));
			}
		}
	}

	/**
	 * 根据立项的id获取该立项所对应所有职员的id数组
	 * @author liangliang_ye
	 */
	protected String[] _getPersonIdbyProject(Context ctx, BOSUuid projectId)
			throws BOSException, EASBizException {
		if(projectId==null){
			return null;
		}
		StringBuffer oql=new StringBuffer("select id,person.id where inviteTeam.inviteProject.id='");
		oql.append(projectId).append("' and inviteTeam.state='")
		.append(FDCBillStateEnum.AUDITTED_VALUE).append("'");
		InviteTeamPersonCollection inviteTeamPersonCollection =InviteTeamPersonFactory.getLocalInstance(ctx).getInviteTeamPersonCollection(oql.toString());
		if(inviteTeamPersonCollection!=null&&inviteTeamPersonCollection.size()>0){
			String[] personids=new String[inviteTeamPersonCollection.size()];
			for(int i=0,n=inviteTeamPersonCollection.size();i<n;i++){
				personids[i]=inviteTeamPersonCollection.get(i).getPerson().getId().toString();
			}
			return personids;
		}
		return null;
	}
}