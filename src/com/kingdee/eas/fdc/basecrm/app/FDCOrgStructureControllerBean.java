package com.kingdee.eas.fdc.basecrm.app;

import java.util.List;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basecrm.FDCOrgStructureInfo;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;

public class FDCOrgStructureControllerBean extends
		AbstractFDCOrgStructureControllerBean {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3905924101531468492L;
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.basecrm.app.FDCOrgStructureControllerBean");

	protected void _updateData(Context ctx) throws BOSException,
			EASBizException {

		StringBuffer sbInsert = new StringBuffer();
		sbInsert
				.append(" insert into T_BDC_FDCOrgStructure(fid,FOrgStructureID,flongnumber,flevel,fisleaf,ftreeid,funitid,fparentid,fisvalid,fdisplayname_l2,fdisplayname_l3,FSortCode) ");
		sbInsert
				.append(" select newbosid('6A12CC18') id ,tt.fid,tt.flongnumber,tt.flevel,tt.fisleaf,tt.ftreeid,tt.funitid,tt.fparentid,tt.fisvalid,tt.fdisplayname_l2,tt.fdisplayname_l3,tt.FSortCode ");
		sbInsert
				.append(" from T_ORG_Structure tt where tt.fid not in (select fdc.forgstructureid from T_BDC_FDCOrgStructure fdc) ");
//		sbInsert
//				.append(" and tt.funitid not in (select fdc.funitid from T_BDC_FDCOrgStructure fdc)");

		StringBuffer sbUpdateIsHis = new StringBuffer();
		sbUpdateIsHis.append("update T_BDC_FDCOrgStructure set fishis=1 where fid in ( ");
		sbUpdateIsHis.append(" select fdc.fid from T_BDC_FDCOrgStructure fdc ");
		sbUpdateIsHis.append(" left join T_ORG_BaseUnit org on fdc.FUnitId = org.fid ");
		sbUpdateIsHis
				.append(" where fdc.forgstructureid not in (select tt.fid from T_ORG_Structure tt)) ");
		
		StringBuffer sbUpsetHisIsNull = new StringBuffer();
		sbUpsetHisIsNull.append("update T_BDC_FDCOrgStructure set fishis=0 where fishis is null");
		
		StringBuffer sbUpdateCustIsNull = new StringBuffer();
		sbUpdateCustIsNull.append("update T_BDC_FDCOrgStructure set FCustMangageUnit=0 where FCustMangageUnit is null");
		
		StringBuffer sbUpdateSellIsNull = new StringBuffer();
		sbUpdateSellIsNull.append("update T_BDC_FDCOrgStructure set FSellHouseStrut=0 where FSellHouseStrut is null");
		
		StringBuffer sbUpdateMain = new StringBuffer();
		sbUpdateMain.append("update T_BDC_FDCOrgStructure  set FCustMangageUnit=1 where funitid='00000000-0000-0000-0000-000000000000CCE7AED4'");

		
		StringBuffer sbUpdateSellHouse = new StringBuffer();
		sbUpdateSellHouse
				.append("update T_BDC_FDCOrgStructure set fsellhousestrut=1,FIsExecuted=1 where FIsExecuted=0 and forgstructureid in");
		sbUpdateSellHouse.append("(");
		sbUpdateSellHouse
				.append(" select org.forgstructureid from T_BDC_FDCOrgStructure org ");
		sbUpdateSellHouse
				.append(" left join T_ORG_BaseUnit unit on org.FUnitId =unit.fid ");
		sbUpdateSellHouse
				.append(" left join T_ORG_OUPartSale ou on unit.fid = ou.FUnitID ");
		sbUpdateSellHouse.append("where ou.FIsBizUnit = 1 ");
		sbUpdateSellHouse.append(")");
		
		StringBuffer sbUpdateNotIsHis = new StringBuffer();
		sbUpdateNotIsHis
				.append(" update T_BDC_FDCOrgStructure set fishis=0 where fid in( ");
		sbUpdateNotIsHis
				.append(" select fdc.fid from T_BDC_FDCOrgStructure fdc ");
		sbUpdateNotIsHis
				.append(" where fdc.flongnumber in (select org.flongnumber from T_ORG_Structure org) ");
		sbUpdateNotIsHis
				.append(" and fdc.fid not in(select org.fid from t_org_structure org)) ");
		
		
		try {
			FDCSQLBuilder sqlBuilder = new FDCSQLBuilder(ctx);
			sqlBuilder.setBatchType(FDCSQLBuilder.STATEMENT_TYPE);
			sqlBuilder.addBatch(sbInsert.toString());
			sqlBuilder.addBatch(sbUpdateIsHis.toString());
			sqlBuilder.addBatch(sbUpsetHisIsNull.toString());
			sqlBuilder.addBatch(sbUpdateCustIsNull.toString());
			sqlBuilder.addBatch(sbUpdateSellIsNull.toString());
			sqlBuilder.addBatch(sbUpdateMain.toString());
			sqlBuilder.addBatch(sbUpdateSellHouse.toString());
			sqlBuilder.addBatch(sbUpdateNotIsHis.toString());
			sqlBuilder.executeBatch();
		} catch (Exception ex) {
			logger.error("同步组织数据失败！");
			throw new BOSException();
		}

	}

	protected void _saveData(Context ctx, List idList) throws BOSException,
			EASBizException {
		if (idList != null && idList.size() > 0) {
			try {
				String sql = "update T_BDC_FDCOrgStructure set FCustMangageUnit=?,FSellHouseStrut=? where fid=?";
				FDCSQLBuilder sqlBuilder = new FDCSQLBuilder(ctx);
				sqlBuilder.setPrepareStatementSql(sql);
				sqlBuilder.setBatchType(FDCSQLBuilder.PREPARESTATEMENT_TYPE);

				for (int i = 0; i < idList.size(); i++) {
					FDCOrgStructureInfo form = (FDCOrgStructureInfo) idList
							.get(i);
					if (form.isCustMangageUnit()) {
						sqlBuilder.addParam(new Integer(1));
					} else {
						sqlBuilder.addParam(new Integer(0));
					}
					if (form.isSellHouseStrut()) {
						sqlBuilder.addParam(new Integer(1));
					} else {
						sqlBuilder.addParam(new Integer(0));
					}
					sqlBuilder.addParam(form.getId().toString());
					sqlBuilder.addBatch();
				}
				sqlBuilder.executeBatch();
			} catch (Exception ex) {
				logger.error(ex.getMessage() + "保存数据失败!");
			}
		}
	}
}