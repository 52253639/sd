package com.kingdee.eas.fdc.sellhouse.app;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.service.formula.engine.FormulaEngine;
import com.kingdee.bos.service.formula.engine.RunFormulaException;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.ContractException;
import com.kingdee.eas.fdc.sellhouse.AgioSchemeInfo;
import com.kingdee.eas.fdc.sellhouse.CollectionInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.DataBaseException;
import com.kingdee.eas.framework.DataBaseInfo;


public class CollectionControllerBean extends AbstractCollectionControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.CollectionControllerBean");
    
    protected void _disEnable(Context ctx, IObjectPK pk) throws BOSException {
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder
				.appendSql("update T_SHE_Collection set  FIsEnabled = ?  where FID = ? ");
		builder.addParam(new Integer(0));
		builder.addParam(pk.toString());
		builder.executeUpdate(ctx);
	}
    protected void _enable(Context ctx, IObjectPK pk) throws BOSException {
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder
				.appendSql("update T_SHE_Collection set  FIsEnabled = ?  where FID = ? ");
		builder.addParam(new Integer(1));
		builder.addParam(pk.toString());
		builder.executeUpdate(ctx);
	}
    protected Map _getArAmout(Context ctx, Map map) throws BOSException {
		Map resultvalue = new HashMap();
		BigDecimal arAmout = new BigDecimal(0);
		String formulaStr = (String)map.get("stdFormulaScript");
		Map param = (Map)map.get("value");
		Object result;
		try {
			result = FormulaEngine.runFormula(formulaStr, param, ctx);
			resultvalue.put("result", result);
		} catch (RunFormulaException e) {
			throw new BOSException();
		}
		return resultvalue;
	}

    protected void _checkNameDup(Context ctx, IObjectValue model)
    		throws BOSException, EASBizException {
		//同一根项目内设置时，“编码、名称”不允许重复	
    	CollectionInfo agioInfo = (CollectionInfo)model;
		if(agioInfo.getProject()==null) return;
		SellProjectInfo spInfo = SellProjectFactory.getLocalInstance(ctx).getSellProjectInfo("select * where id = '"+agioInfo.getProject().getId()+"'");
		while(spInfo.getLevel()!=1 && spInfo.getParent()!=null) {
			spInfo = SellProjectFactory.getLocalInstance(ctx).getSellProjectInfo("select * where id = '"+spInfo.getParent().getId()+"'");
		}
		if(spInfo==null) return;
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("name", agioInfo.getNumber()));
		filter.getFilterItems().add(new FilterItemInfo("project.id", spInfo.getId() ));
		filter.getFilterItems().add(new FilterItemInfo("project.longNumber", spInfo.getLongNumber()+"!%",CompareType.LIKE ));
		if (agioInfo.getId() != null) {
			filter.getFilterItems().add(new FilterItemInfo("id", agioInfo.getId().toString(), CompareType.NOTEQUALS));
			filter.setMaskString("#0 and (#1 or #2) and #3");
		}else{
			filter.setMaskString("#0 and (#1 or #2) ");
		}

		if (_exists(ctx, filter)) {
			throw new ContractException(ContractException.NAME_DUP);
		}
    }
    
    
    protected void _checkNumberDup(Context ctx, IObjectValue model)
    		throws BOSException, EASBizException {
		//同一根项目内设置时，“编码、名称”不允许重复		
    	CollectionInfo agioInfo = (CollectionInfo)model;
		if(agioInfo.getProject()==null) return;
		SellProjectInfo spInfo = SellProjectFactory.getLocalInstance(ctx).getSellProjectInfo("select * where id = '"+agioInfo.getProject().getId()+"'");
		while(spInfo.getLevel()!=1 && spInfo.getParent()!=null) {
			spInfo = SellProjectFactory.getLocalInstance(ctx).getSellProjectInfo("select * where id = '"+spInfo.getParent().getId()+"'");
		}
		if(spInfo==null) return;
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("number", agioInfo.getNumber()));
		filter.getFilterItems().add(new FilterItemInfo("project.id", spInfo.getId() ));
		filter.getFilterItems().add(new FilterItemInfo("project.longNumber", spInfo.getLongNumber()+"!%",CompareType.LIKE ));
		if (agioInfo.getId() != null) {
			filter.getFilterItems().add(new FilterItemInfo("id", agioInfo.getId().toString(), CompareType.NOTEQUALS));
			filter.setMaskString("#0 and (#1 or #2) and #3");
		}else{
			filter.setMaskString("#0 and (#1 or #2) ");
		}		
		
		if (_exists(ctx, filter)) {
			throw new ContractException(ContractException.NUMBER_DUP);
		}
    }
    
    protected void _checkNameBlank(Context ctx, IObjectValue model)
    		throws BOSException, EASBizException {
    }

	protected void updateCheck(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException {
		this._checkNumberBlank(ctx, model);

		DataBaseInfo oldModel = this.getDataBaseInfo(ctx, pk);
		if (!((DataBaseInfo) model).getNumber().equals(oldModel.getNumber())) {
			this._checkNumberDup(ctx, model);
		}

	}
    


}