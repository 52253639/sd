package com.kingdee.eas.fdc.sellhouse.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.contract.ContractException;
import com.kingdee.eas.fdc.sellhouse.AgioSchemeInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;

public class AgioSchemeControllerBean extends AbstractAgioSchemeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.AgioSchemeControllerBean");
    
    
    protected void _checkNameDup(Context ctx, IObjectValue model)
    		throws BOSException, EASBizException {
		//同一根项目内设置时，“编码、名称”不允许重复	
    	AgioSchemeInfo agioInfo = (AgioSchemeInfo)model;
		if(agioInfo.getSellProject()==null) return;
		SellProjectInfo spInfo = SellProjectFactory.getLocalInstance(ctx).getSellProjectInfo("select * where id = '"+agioInfo.getSellProject().getId()+"'");
		while(spInfo.getLevel()!=1 && spInfo.getParent()!=null) {
			spInfo = SellProjectFactory.getLocalInstance(ctx).getSellProjectInfo("select * where id = '"+spInfo.getParent().getId()+"'");
		}
		if(spInfo==null) return;
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("name", agioInfo.getNumber()));
		filter.getFilterItems().add(new FilterItemInfo("sellProject.id", spInfo.getId() ));
		filter.getFilterItems().add(new FilterItemInfo("sellProject.longNumber", spInfo.getLongNumber()+"!%",CompareType.LIKE ));
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
    	AgioSchemeInfo agioInfo = (AgioSchemeInfo)model;
		if(agioInfo.getSellProject()==null) return;
		SellProjectInfo spInfo = SellProjectFactory.getLocalInstance(ctx).getSellProjectInfo("select * where id = '"+agioInfo.getSellProject().getId()+"'");
		while(spInfo.getLevel()!=1 && spInfo.getParent()!=null) {
			spInfo = SellProjectFactory.getLocalInstance(ctx).getSellProjectInfo("select * where id = '"+spInfo.getParent().getId()+"'");
		}
		if(spInfo==null) return;
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("number", agioInfo.getNumber()));
		filter.getFilterItems().add(new FilterItemInfo("sellProject.id", spInfo.getId() ));
		filter.getFilterItems().add(new FilterItemInfo("sellProject.longNumber", spInfo.getLongNumber()+"!%",CompareType.LIKE ));
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
    
    
	
	
}