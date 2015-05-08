package com.kingdee.eas.fdc.schedule.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.eas.common.EASBizException;

public class SelfAndFinalEvaluationControllerBean extends AbstractSelfAndFinalEvaluationControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.schedule.app.SelfAndFinalEvaluationControllerBean");
    /**
	 * @param ctx
	 * @param pk
	 * @param model
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 */
	protected boolean _checkNumberBlank(Context ctx, IObjectPK pk, IObjectValue model) throws EASBizException, BOSException {
		// 不校验编码为空
		// return super._checkNumberBlank(ctx, pk, model);
		return true;
	}
	@Override
	protected IObjectValue _getValue(Context ctx, IObjectPK pk)
			throws BOSException, EASBizException {
		// TODO Auto-generated method stub
//		return super._getValue(ctx, pk);
		/* TODO 自动生成方法存根 */
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("selfEvaluationDes");
		sic.add("adminDept.id");
		sic.add("adminDept.name");
		sic.add("adminDept.number");
		sic.add("finalEvaluationDes");
		sic.add("finalEvaluationDate");
		sic.add("selfEvaluationPerson");
		sic.add(new SelectorItemInfo("selfEvaluationPerson.*"));
		sic.add("entries.relateTask.id");
		sic.add("entries.relateTask.taskType");
		sic.add("entries.relateTask.taskName");
		sic.add("entries.relateTask.planFinishDate");
		sic.add("entries.relateTask.finishStandard");
		sic.add("entries.relateTask.weightRate");
		sic.add("entries.relateTask.adminPerson.name");
		sic.add("entries.relateTask.adminPerson.id");
		sic.add("entries.relateTask.relatedTask.id");
		sic.add("entries.relateTask.seq");
		sic.add("entries.actualEndDate");
		sic.add("entries.completePercent");
		sic.add("entries.selfEvaluationScore");
		sic.add("entries.selfCompleteDes");
		sic.add("entries.finalEvaluationScore");
		sic.add("entries.finalCompleteDes");
		sic.add("state");
		sic.add(new SelectorItemInfo("finalEvaluationPerson.*"));
		sic.add(new SelectorItemInfo("finalEvaluationDate"));
		sic.add(new SelectorItemInfo("finalEvaluationScore"));
		sic.add(new SelectorItemInfo("finalEvaluationDes"));
		//BT737822标准产品中，在部门月度计划执行中，自评和终评界面中任务的排序和主界面的排序不一致
		SorterItemCollection item = new SorterItemCollection();
		SorterItemInfo sorter = new SorterItemInfo("entries.relateTask.planFinishDate");
		sorter.setSortType(SortType.ASCEND);
		item.add(sorter);

		return super._getValue(ctx, pk, sic,item);
	}

	/**
	 * @param ctx
	 * @param pk
	 * @param model
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 */
	protected boolean _checkNumberDup(Context ctx, IObjectPK pk, IObjectValue model) throws EASBizException, BOSException {
		// 不校验编码重复
		// return super._checkNumberDup(ctx, pk, model);
		return true;
	}

	/**
	 * @param ctx
	 * @param model
	 * @throws BOSException
	 * @throws EASBizException
	 */
	protected void checkBill(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		
	}
	
	@Override
	protected IObjectValue _getValue(Context ctx, IObjectPK pk, SelectorItemCollection selector ) throws BOSException, EASBizException {
		/* TODO 自动生成方法存根 */
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("selfEvaluationDes");
		sic.add("adminDept.id");
		sic.add("adminDept.name");
		sic.add("adminDept.number");
		sic.add("finalEvaluationDes");
		sic.add("finalEvaluationDate");
		sic.add("selfEvaluationPerson");
		sic.add(new SelectorItemInfo("selfEvaluationPerson.*"));
		sic.add("entries.relateTask.id");
		sic.add("entries.relateTask.taskType");
		sic.add("entries.relateTask.taskName");
		sic.add("entries.relateTask.planFinishDate");
		sic.add("entries.relateTask.finishStandard");
		sic.add("entries.relateTask.weightRate");
		sic.add("entries.relateTask.adminPerson.name");
		sic.add("entries.relateTask.adminPerson.id");
		sic.add("entries.relateTask.relatedTask.id");
		sic.add("entries.relateTask.seq");
		sic.add("entries.actualEndDate");
		sic.add("entries.completePercent");
		sic.add("entries.selfEvaluationScore");
		sic.add("entries.selfCompleteDes");
		sic.add("entries.finalEvaluationScore");
		sic.add("entries.finalCompleteDes");
		sic.add("state");
		sic.add(new SelectorItemInfo("finalEvaluationPerson.*"));
		sic.add(new SelectorItemInfo("finalEvaluationDate"));
		sic.add(new SelectorItemInfo("finalEvaluationScore"));
		sic.add(new SelectorItemInfo("finalEvaluationDes"));
		//BT737822标准产品中，在部门月度计划执行中，自评和终评界面中任务的排序和主界面的排序不一致
		SorterItemCollection item = new SorterItemCollection();
		SorterItemInfo sorter = new SorterItemInfo("entries.relateTask.planFinishDate");
		sorter.setSortType(SortType.ASCEND);
		item.add(sorter);

		return super._getValue(ctx, pk, sic,item);
	}

}