package com.kingdee.eas.fdc.sellhouse.app;

import java.util.List;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.sellhouse.PropertyDoSchemeFactory;
import com.kingdee.eas.fdc.sellhouse.PropertyDoSchemeInfo;
import com.kingdee.eas.framework.DataBaseInfo;
import com.kingdee.eas.framework.IFWEntityStruct;

public class PropertyDoSchemeControllerBean extends AbstractPropertyDoSchemeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.PropertyDoSchemeControllerBean");

	protected void _disEnable(Context ctx, IObjectPK pk) throws BOSException,
			EASBizException {
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder
				.appendSql("update T_SHE_PropertyDoScheme set  FIsEnabled = ?  where FID = ? ");
		builder.addParam(new Integer(0));
		builder.addParam(pk.toString());
		builder.executeUpdate(ctx);
	}

	  protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		DataBaseCodeRuleHelper.handleIntermitNumber(ctx, (DataBaseInfo) model);
		return super._submit(ctx, model);
	}
	  
	protected void _enable(Context ctx, IObjectPK pk) throws BOSException,
			EASBizException {
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder
				.appendSql("update T_SHE_PropertyDoScheme set  FIsEnabled = ?  where FID = ? ");
		builder.addParam(new Integer(1));
		builder.addParam(pk.toString());
		builder.executeUpdate(ctx);
	}

	protected void _checkNumberDup(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		PropertyDoSchemeInfo pdsInfo = (PropertyDoSchemeInfo)model;
		FilterInfo filter = new FilterInfo();
		filter.appendFilterItem("number", pdsInfo.getNumber());
		if(pdsInfo.getProject().getId()!=null){
			filter.appendFilterItem("project.id", pdsInfo.getProject().getId().toString());
		}
		
		if(PropertyDoSchemeFactory.getLocalInstance(ctx).exists(filter)){
			String number = this._getPropertyAlias(ctx, pdsInfo, IFWEntityStruct.dataBase_Number) + pdsInfo.getNumber();;
			throw new EASBizException(EASBizException.CHECKDUPLICATED, new Object[] { number });
		}
	}

	protected void _checkNameDup(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		PropertyDoSchemeInfo pdsInfo = (PropertyDoSchemeInfo)model;
		FilterInfo filter = new FilterInfo();
		filter.appendFilterItem("name", pdsInfo.getName());
		filter.appendFilterItem("project.id", pdsInfo.getProject().getId().toString());
		if(PropertyDoSchemeFactory.getLocalInstance(ctx).exists(filter)){
			String name = this._getPropertyAlias(ctx,pdsInfo,IFWEntityStruct.dataBase_Name) + pdsInfo.getName();
			throw new EASBizException(EASBizException.CHECKDUPLICATED ,new Object[]{name});
		}
	}

	protected void _setEnabled(Context ctx, List idList, boolean isEnabled)
			throws BOSException, EASBizException {
		if(idList!=null && idList.size()>0){
			try {
				String sql = "update T_SHE_PropertyDoScheme set fisenabled=? where fid=?";
				FDCSQLBuilder sqlBuilder = new FDCSQLBuilder(ctx);
				sqlBuilder.setPrepareStatementSql(sql);
				sqlBuilder.setBatchType(FDCSQLBuilder.PREPARESTATEMENT_TYPE);

				for (int i = 0; i < idList.size(); i++) {
					if(isEnabled){
						sqlBuilder.addParam(new Integer("1"));
					}else{
						sqlBuilder.addParam(new Integer("0"));
					}
					sqlBuilder.addParam(idList.get(i).toString());
					sqlBuilder.addBatch();
				}
				sqlBuilder.executeBatch();
			} catch (BOSException ex) {
				logger.error(ex.getMessage() + "更新产权办理方案失败!");
				throw new BOSException(ex.getMessage() + "更新产权办理方案失败!");
			}

		}
		
	}
}