package com.kingdee.eas.fdc.sellhouse.app;

import java.util.List;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.sellhouse.ISHEProject;
import com.kingdee.eas.fdc.sellhouse.SHEProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SHEProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectResourceEnum;

public class SHEProjectControllerBean extends AbstractSHEProjectControllerBean {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8087035439103482723L;
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.sellhouse.app.SHEProjectControllerBean");

	protected void _updateRoomModel(Context ctx, List idList, String id)
			throws BOSException, EASBizException {

		if (idList.size() > 0) {
			StringBuffer sb = new StringBuffer();
			sb.append("(");
			for (int i = 0; i < idList.size(); i++) {
				String temp = idList.get(i).toString();
				sb.append("'"+temp+"'");
				if (i != idList.size()-1) {
					sb.append(",");
				}
			}
			sb.append(")");

			String sql = "update t_she_roommodel set fheadid='" + id + "' where fid in "
					+ sb.toString() + "";
			FDCSQLBuilder sbBuilder = new FDCSQLBuilder(ctx);
			sbBuilder.appendSql(sql);
			sbBuilder.execute();
		}
	}

	protected void _deleteRoomModel(Context ctx, List idList)
			throws BOSException, EASBizException {
		if (idList.size() > 0) {
			StringBuffer sb = new StringBuffer();
			sb.append("(");
			for (int i = 0; i < idList.size(); i++) {
				String temp = idList.get(i).toString();
				sb.append("'"+temp+"'");
				if (i != idList.size()-1) {
					sb.append(",");
				}
			}
			sb.append(")");

			String sql = "delete from t_she_roommodel where fid in "
					+ sb.toString() + "";
			FDCSQLBuilder sbBuilder = new FDCSQLBuilder(ctx);
			sbBuilder.appendSql(sql);
			sbBuilder.execute();
		}
	}

	protected void _updateEnable(Context ctx, BOSUuid id, boolean isEnabled)
			throws BOSException, EASBizException {
		SHEProjectInfo info = new SHEProjectInfo();
		info.setId(id);
		if(isEnabled){
			info.setIsEnabled(true);
		}else{
			info.setIsEnabled(false);
		}
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add(new SelectorItemInfo("id"));
		selector.add(new SelectorItemInfo("isEnabled"));
		SHEProjectFactory.getLocalInstance(ctx).updatePartial(info, selector);
	}

	protected void _updateSHEProjectToSellProject(Context ctx, BOSUuid id)
			throws BOSException, EASBizException {
		ISHEProject sheProject  = SHEProjectFactory.getLocalInstance(ctx);
		SHEProjectInfo info  = sheProject.getSHEProjectInfo(new ObjectUuidPK(id));
		if(info!=null){
			SellProjectInfo project = new SellProjectInfo();
			project.setOrgUnit(info.getOrgUnit());
			project.setName(info.getName());
			project.setNumber(info.getNumber());
			project.setCompanyOrgUnit(info.getCompanyOrgUnit());
			project.setProject(info.getProject());
			project.setDescription(info.getDescription());
			project.setTermBegin(info.getStartDate());
			project.setTermEnd(info.getEndDate());
			project.setSimpleName(info.getSimpleName());
			project.setIsForSHE(true);
			project.setProjectResource(SellProjectResourceEnum.DEVELOPER);
			SellProjectFactory.getLocalInstance(ctx).addnew(project);
		}
	}

	protected void _updateSellProjectToSHEProject(Context ctx, String orgId)
			throws BOSException, EASBizException {
		SellProjectInfo info = SellProjectFactory.getLocalInstance(ctx).getSellProjectInfo(new ObjectUuidPK(orgId));
		if(info!=null){
			SHEProjectInfo project = new SHEProjectInfo();
			project.setName(info.getName());
			project.setNumber(info.getNumber());
			project.setOrgUnit(info.getOrgUnit());
			project.setProject(info.getProject());
			project.setCompanyOrgUnit(info.getCompanyOrgUnit());
			project.setIsEnabled(true);
			project.setSrcId(info.getId());
			project.setDescription(info.getDescription());
			project.setSimpleName(info.getSimpleName());
			project.setStartDate(info.getTermBegin());
			project.setEndDate(info.getTermEnd());
			SHEProjectFactory.getLocalInstance(ctx).addnew(project);
		}
	}

}