package com.kingdee.eas.fdc.sellhouse.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.sellhouse.LoanDataInfo;
import com.kingdee.eas.fdc.sellhouse.SHEPayTypeFactory;
import com.kingdee.eas.fdc.sellhouse.SellHouseException;
import com.kingdee.eas.framework.IFWEntityStruct;

public class LoanDataControllerBean extends AbstractLoanDataControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.sellhouse.app.LoanDataControllerBean");

//	默认采用编码。
    protected String _getLogInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException
    {
        LoanDataInfo info = (LoanDataInfo)super._getValue(ctx,pk);
        String retValue = "";
        if(info.getNumber()!= null)
        {
            retValue = info.getNumber();
            if(info.getName()!=null){
            	retValue = retValue + " " + info.getName();
            }
        }
        return retValue;
    }
	
	protected IObjectPK _save(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		// LoanDataInfo loanData = (LoanDataInfo) model;
		// if (loanData.getBank().getId() != null
		// && loanData.getSellProject().getId() != null) {
		// FilterInfo filter = new FilterInfo();
		// filter.getFilterItems().add(
		// new FilterItemInfo("bank.id", loanData.getBank().getId()
		// .toString()));
		// filter.getFilterItems().add(
		// new FilterItemInfo("sellProject.id",
		// loanData.getSellProject().getId()
		// .toString()));
		// if(super.exists(ctx, filter))
		// throw new SellHouseException(SellHouseException.SAMEBANK);
		// }
		_checkNameDup(ctx, model);
		_checkNumberDup(ctx, model);
		return super._save(ctx, model);
	}

	protected IObjectPK _submit(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		// LoanDataInfo loanData = (LoanDataInfo) model;
		// if (loanData.getBank().getId() != null
		// && loanData.getSellProject().getId() != null) {
		// FilterInfo filter = new FilterInfo();
		// filter.getFilterItems().add(
		// new FilterItemInfo("bank.id", loanData.getBank().getId()
		// .toString()));
		// filter.getFilterItems().add(
		// new FilterItemInfo("sellProject.id",
		// loanData.getSellProject().getId()
		// .toString()));
		// if(super.exists(ctx, filter))
		// throw new SellHouseException(SellHouseException.SAMEBANK);
		// }
		_checkNameDup(ctx, model);
		_checkNumberDup(ctx, model);
		return super._submit(ctx, model);
	}

	protected void _checkNameDup(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		LoanDataInfo loan = (LoanDataInfo) model;
		FilterInfo filter = new FilterInfo();
		FilterItemInfo filterItem = new FilterItemInfo(
				IFWEntityStruct.dataBase_Name, loan.getName(),
				CompareType.EQUALS);
		filter.getFilterItems().add(filterItem);
		if (loan.getId() != null) {
			filterItem = new FilterItemInfo(IFWEntityStruct.coreBase_ID, loan
					.getId(), CompareType.NOTEQUALS);
			filter.getFilterItems().add(filterItem);
		}
		filterItem = new FilterItemInfo("sellProject.id", loan.getSellProject()
				.getId().toString());
		filter.getFilterItems().add(filterItem);
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		SorterItemCollection sorter = new SorterItemCollection();
		sorter.add(new SorterItemInfo(IFWEntityStruct.coreBase_ID));

		if (super._exists(ctx, filter)) {
			throw new EASBizException(EASBizException.CHECKDUPLICATED,
					new Object[] { "名称" });
		}
	}

	protected void _checkNumberDup(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		LoanDataInfo loan = (LoanDataInfo) model;
		FilterInfo filter = new FilterInfo();
		FilterItemInfo filterItem = new FilterItemInfo(
				IFWEntityStruct.dataBase_Number, loan.getNumber(),
				CompareType.EQUALS);
		filter.getFilterItems().add(filterItem);
		if (loan.getId() != null) {
			filterItem = new FilterItemInfo(IFWEntityStruct.coreBase_ID, loan
					.getId(), CompareType.NOTEQUALS);
			filter.getFilterItems().add(filterItem);
		}
		filterItem = new FilterItemInfo("sellProject.id", loan.getSellProject()
				.getId().toString());
		filter.getFilterItems().add(filterItem);
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		SorterItemCollection sorter = new SorterItemCollection();
		sorter.add(new SorterItemInfo(IFWEntityStruct.coreBase_ID));

		if (super._exists(ctx, filter)) {

			throw new EASBizException(EASBizException.CHECKDUPLICATED,
					new Object[] { "编码" });
		}
	}

	protected void _enable(Context ctx, IObjectPK pk) throws BOSException,
			EASBizException {
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder
				.appendSql("update T_SHE_LoanData set  FIsEnabled = ?  where FID = ? ");
		builder.addParam(new Integer(1));
		builder.addParam(pk.toString());
		builder.executeUpdate(ctx);
	}

	protected void _disEnable(Context ctx, IObjectPK pk) throws BOSException,
			EASBizException {
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder
				.appendSql("update T_SHE_LoanData set  FIsEnabled = ?  where FID = ? ");
		builder.addParam(new Integer(0));
		builder.addParam(pk.toString());
		builder.executeUpdate(ctx);
	}

	protected void _delete(Context ctx, IObjectPK pk) throws BOSException,
			EASBizException {
		LoanDataInfo info = super.getLoanDataInfo(ctx, pk);
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("loanLoanData.id", info.getId().toString()));
		if (SHEPayTypeFactory.getLocalInstance(ctx).exists(filter)) {
			throw new SellHouseException(SellHouseException.USEDBYSHEPAYTYPE);
		}
		filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("afLoanData.id", info.getId().toString()));
		if (SHEPayTypeFactory.getLocalInstance(ctx).exists(filter)) {
			throw new SellHouseException(SellHouseException.USEDBYSHEPAYTYPE);
		}
		super._delete(ctx, pk);
	}
}