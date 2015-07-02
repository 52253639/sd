package com.kingdee.eas.fdc.aimcost.app;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.ContractWFEntryFactory;
import com.kingdee.eas.fdc.basedata.FDCBasedataException;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.aimcost.MeasureEntryFactory;
import com.kingdee.eas.fdc.aimcost.NewPlanIndexFactory;
import com.kingdee.eas.fdc.aimcost.NewPlanIndexPTFactory;
import com.kingdee.eas.fdc.aimcost.PlanIndexConfigCollection;
import com.kingdee.eas.fdc.aimcost.PlanIndexConfigFactory;
import com.kingdee.eas.fdc.aimcost.PlanIndexConfigInfo;
import com.kingdee.eas.fdc.aimcost.IPlanIndexConfig;
import com.kingdee.eas.fdc.aimcost.TemplateNewPlanIndexFactory;
import com.kingdee.eas.fdc.aimcost.TemplateNewPlanIndexPTFactory;
import com.kingdee.eas.framework.IFWEntityStruct;
import com.kingdee.eas.framework.TreeBaseCollection;
import com.kingdee.eas.framework.TreeBaseException;
import com.kingdee.eas.framework.TreeBaseInfo;
import com.kingdee.util.NumericExceptionSubItem;

public class PlanIndexConfigControllerBean extends AbstractPlanIndexConfigControllerBean{
	protected IObjectPK _addnew(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		trimBlank(model);
		PlanIndexConfigInfo contractTypeModel = (PlanIndexConfigInfo) model;
		// 检查是否存在相同的编码，如存在就抛出异常
		CtrlUnitInfo rootCU = new CtrlUnitInfo();
		rootCU.setId(BOSUuid.read(OrgConstants.DEF_CU_ID));
		contractTypeModel.setCU(rootCU);
		super._checkNumberDup(ctx, contractTypeModel);
		super._checkNameBlank(ctx, contractTypeModel);
		super._checkNameDup(ctx, contractTypeModel);
		super._checkNumberBlank(ctx, contractTypeModel);

		// 取得父级节点
		PlanIndexConfigInfo parentInfo = contractTypeModel.getParent();
		if (parentInfo != null && !parentInfo.getId().equals(contractTypeModel.getId())) {
			BOSUuid parentID = parentInfo.getId();
			parentInfo = super.getPlanIndexConfigInfo(ctx, new ObjectUuidPK(parentID));
			IObjectPK pk = new ObjectUuidPK(parentInfo.getId().toString());
			pk = super._addnew(ctx, contractTypeModel);
			
			return pk;
		} else {
			IObjectPK pk = super._addnew(ctx, contractTypeModel);
			return pk;
		}
	}

    protected void _update(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException
    {
    	trimBlank(model);
    	PlanIndexConfigInfo info = (PlanIndexConfigInfo) model;
		PlanIndexConfigInfo oldInfo=this.getPlanIndexConfigInfo(ctx, pk);
		FDCSQLBuilder fdcSB = new FDCSQLBuilder(ctx);
		fdcSB.setBatchType(FDCSQLBuilder.STATEMENT_TYPE);
    	StringBuffer sql = new StringBuffer();
		sql.append("update T_AIM_PlanIndexConfig set fnumber = '"+info.getNumber()+"'||subString(fnumber,length('"+oldInfo.getNumber()+"')+1,length(fnumber)-length('"+oldInfo.getNumber()+"')) where fnumber like '"+oldInfo.getNumber()+".%'");
		fdcSB.addBatch(sql.toString());
		fdcSB.executeBatch();
    	
    	super._update(ctx, pk, model);
    
    	if(info.getParent()==null){
    		fdcSB = new FDCSQLBuilder(ctx);
    		fdcSB.setBatchType(FDCSQLBuilder.STATEMENT_TYPE);
        	sql = new StringBuffer();
        	int isProductType=0;
        	if(info.isIsProductType())isProductType=1;
    		sql.append("update T_AIM_PlanIndexConfig set fisProductType = "+isProductType+" where fnumber like '"+info.getNumber()+"%'");
    		fdcSB.addBatch(sql.toString());
    		fdcSB.executeBatch();
    	}
    }
    
    /**
	 * 清除前后空格
	 * 
	 * @param id
	 * @return
	 */
	private IObjectValue trimBlank(IObjectValue model){
		PlanIndexConfigInfo contractTypeModel = (PlanIndexConfigInfo) model;
		if(contractTypeModel.getNumber() != null){
			contractTypeModel.setNumber(contractTypeModel.getNumber().trim());
		}
		if(contractTypeModel.getName() != null){
			contractTypeModel.setName(contractTypeModel.getName().trim());
		}
		if(contractTypeModel.getDescription() != null){
			contractTypeModel.setDescription(contractTypeModel.getDescription().trim());
		}
		return model;
	}
	
	/**
	 * @Modified By Owen_wen 2010-11-17 添加限制条件：被招标类型引用，不能删除
	 */
	protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		// 郑邦弘说：删除后剩下全部为禁用状态也不可以
		PlanIndexConfigInfo PlanIndexConfigInfo = this.getPlanIndexConfigInfo(ctx, pk);
		PlanIndexConfigInfo cti = PlanIndexConfigInfo.getParent();
		if (PlanIndexConfigInfo.isIsEnabled() && cti != null) {
			EntityViewInfo evi = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("parent.id", cti.getId().toString()));
			evi.setFilter(filter);
			if (this.getPlanIndexConfigCollection(ctx, evi).size() > 1) {
				filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
				if(this.getPlanIndexConfigCollection(ctx, evi).size() <= 1)
					throw new FDCBasedataException(FDCBasedataException.DISENABLE_CANNOT_ONLY);
			}
		}

		if(!PlanIndexConfigInfo.isIsLeaf()){//非叶结点，不允许删除
			throw new FDCBasedataException(FDCBasedataException.DELETE_ISPARENT_FAIL);
		}
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("config.id", PlanIndexConfigInfo.getId().toString()));
		if(NewPlanIndexFactory.getLocalInstance(ctx).exists(filter)){
			throw new EASBizException(new NumericExceptionSubItem("100","已经被目标成本测算引用，不能进行删除操作！"));
		}
		if(NewPlanIndexPTFactory.getLocalInstance(ctx).exists(filter)){
			throw new EASBizException(new NumericExceptionSubItem("100","已经被目标成本测算引用，不能进行删除操作！"));
		}
		if(TemplateNewPlanIndexFactory.getLocalInstance(ctx).exists(filter)){
			throw new EASBizException(new NumericExceptionSubItem("100","已经被目标成本测算引用，不能进行删除操作！"));
		}
		if(TemplateNewPlanIndexPTFactory.getLocalInstance(ctx).exists(filter)){
			throw new EASBizException(new NumericExceptionSubItem("100","已经被目标成本测算引用，不能进行删除操作！"));
		}
		if(MeasureEntryFactory.getLocalInstance(ctx).exists(filter)){
			throw new EASBizException(new NumericExceptionSubItem("100","已经被目标成本测算引用，不能进行删除操作！"));
		}
		super._delete(ctx, pk);
	}

	protected boolean _disEnabled(Context ctx, IObjectPK ctPK) throws BOSException, EASBizException {
		if (this.checkIsOnlyOneEnabled(ctx, ctPK)) {
			throw new com.kingdee.eas.fdc.basedata.FDCBasedataException(FDCBasedataException.DISENABLE_CANNOT_ONLY);
		}
		if(changeUseStatus(ctx,ctPK,false))
			return true;
		else 
			return false;
	}
	private boolean checkIsOnlyOneEnabled(Context ctx, IObjectPK PK) throws BOSException, EASBizException {
		PlanIndexConfigInfo cti = this.getPlanIndexConfigInfo(ctx, PK).getParent();
		if (cti != null) {
			EntityViewInfo evi = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("parent.id", cti.getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.valueOf(true)));
			evi.setFilter(filter);
			if (this.getPlanIndexConfigCollection(ctx, evi).size() == 1) {
				return true;
			}
		}
		return false;
	}
	protected boolean _enabled(Context ctx, IObjectPK ctPK) throws BOSException, EASBizException {
		boolean flag = false;
		IPlanIndexConfig iContractType = PlanIndexConfigFactory.getLocalInstance(ctx);    
    	PlanIndexConfigInfo PlanIndexConfigInfo = new PlanIndexConfigInfo();

			PlanIndexConfigInfo = iContractType.getPlanIndexConfigInfo(ctPK);
	    	if(PlanIndexConfigInfo.getParent()!=null){
	    		IObjectPK parentPK = new ObjectStringPK(PlanIndexConfigInfo.getParent().getId().toString());
	    		PlanIndexConfigInfo parentPlanIndexConfigInfo = iContractType.getPlanIndexConfigInfo(parentPK);
	    		if(!parentPlanIndexConfigInfo.isIsEnabled()){
	    			//如果上级被禁用,给出提示并返回    			
	    			throw new com.kingdee.eas.fdc.basedata.FDCBasedataException(FDCBasedataException.CONTRACTTYPE_PARENT_DISENABLE); 			  			
	    		}else{
	    			if(changeUseStatus(ctx,ctPK,true))
	    				flag = true;
	    		}
	    			
	    	}else{
				if(changeUseStatus(ctx,ctPK,true))
					flag = true;
	    	}				

    	return flag;
	}
    /*
     */
    protected boolean changeUseStatus(Context ctx, IObjectPK PK,boolean flag) throws EASBizException, BOSException {
    	IPlanIndexConfig iContractType = PlanIndexConfigFactory.getLocalInstance(ctx);    
    	PlanIndexConfigInfo PlanIndexConfigInfo = iContractType.getPlanIndexConfigInfo(PK);    
    	
    		EntityViewInfo evi=new EntityViewInfo();
    		FilterInfo filter=new FilterInfo();
    		filter.getFilterItems().add(new FilterItemInfo("parent.id",PlanIndexConfigInfo.getId().toString()));
    		evi.setFilter(filter); 
    		PlanIndexConfigCollection contractTypeCollection = iContractType.getPlanIndexConfigCollection(evi);
    		//如果有下级,要同时启用/禁用下级
    		if(contractTypeCollection.size()>0){
    		//先启用/禁用自己
    			PlanIndexConfigInfo .setIsEnabled(flag);
    			_update(ctx,PK,PlanIndexConfigInfo);
    			//再启用/禁用下级	
    			PlanIndexConfigInfo childPlanIndexConfigInfo ;
    			IObjectPK childPK;
    			for(int i = 0;i<contractTypeCollection.size();i++){
    				//boolean变量比较
    				if(contractTypeCollection.get(i).isIsEnabled()!=flag){
    					childPlanIndexConfigInfo = contractTypeCollection.get(i);
    					childPlanIndexConfigInfo.setIsEnabled(flag);
	    				childPK = new ObjectStringPK(childPlanIndexConfigInfo.getId().toString());
	    				changeUseStatus(ctx,childPK,flag);

    				}
    			}    			
    		}else{
    			//如果没有下级
    			PlanIndexConfigInfo.setIsEnabled(flag);
    			_update(ctx,PK,PlanIndexConfigInfo);
    		}
//    	}else{
    		
//    	}
    		
    		
     return true;
    }
    /**
     * 相同的父节点下不能相同的编码。
     * @param ctx Context
     * @param model DataBaseInfo
     * @throws BOSException
     * @throws EASBizException
     */
    protected void _checkNumberDup(Context ctx , IObjectValue model) throws
        BOSException , EASBizException
    {

        TreeBaseInfo treeModel = (TreeBaseInfo) model;

        //if no parent,no need to check
        FilterInfo filter = new FilterInfo();
        FilterItemInfo filterItem = null;
        //父节点为空时检查根对象编码是否重复。
        if (treeModel.innerGetParent() == null)
        {
            filterItem = new FilterItemInfo(IFWEntityStruct.dataBase_Number ,
                                            treeModel.getNumber() ,
                                            CompareType.EQUALS);
            filter.getFilterItems().add(filterItem);
            filter.getFilterItems().add(new FilterItemInfo(IFWEntityStruct.tree_Parent ,null ,CompareType.EQUALS));
            filter.setMaskString("#0 and #1");
            if (treeModel.getId() != null)
            {
                filterItem = new FilterItemInfo(IFWEntityStruct.coreBase_ID ,
                                                treeModel.getId().
                                                toString() ,
                                                CompareType.NOTEQUALS);
                filter.getFilterItems().add(filterItem);
                //filter.getFilterItems().add(new FilterItemInfo("level",new Integer(treeModel.getLevel()),CompareType.EQUALS));
                //修改，应当使用parentID，因为levle是计算生成的，不应由客户端传递。 Jacky at 2004-11-4
                filter.setMaskString("#0 and #1 and #2");
            }
        }
        else
        {
            filterItem = new FilterItemInfo(IFWEntityStruct.dataBase_Number ,
                                            treeModel.getNumber() ,
                                            CompareType.EQUALS);
            filter.getFilterItems().add(filterItem);
            if (treeModel.innerGetParent().getId() != null)
            {
                filterItem = new FilterItemInfo(IFWEntityStruct.tree_Parent ,
                                                treeModel.innerGetParent().
                                                getId().
                                                toString() , CompareType.EQUALS);
                filter.getFilterItems().add(filterItem);
                filter.setMaskString("#0 and #1");
            }
            if (treeModel.getId() != null)
            {
                filterItem = new FilterItemInfo(IFWEntityStruct.coreBase_ID ,
                                                treeModel.getId().
                                                toString() ,
                                                CompareType.NOTEQUALS);
                filter.getFilterItems().add(filterItem);
                if (treeModel.innerGetParent().getId() != null)
                {
                    filter.setMaskString("#0 and #1 and #2");
                }
                else
                {
                    filter.setMaskString("#0 and #1");
                }
            }
        }

        EntityViewInfo view = new EntityViewInfo();
        //CU隔离
//        FilterInfo filterCU = getFilterForDefaultCU(ctx,treeModel);
//        if(FilterUtility.hasFilterItem(filterCU))
//        {
//            if(FilterUtility.hasFilterItem(filter))
//            {
//                filter.mergeFilter(filterCU,"AND");
//            }
//            else
//            {
//                filter = filterCU;
//            }
//        }

        view.setFilter(filter);

        TreeBaseCollection results = this.getTreeBaseCollection(ctx , view);

        //DataBaseCollection results = this.getDataBaseCollection(ctx,view);

        if (results != null && results.size() > 0)
        {
            throw new TreeBaseException(TreeBaseException.CHECKNUMBERDUPLICATED ,
                                        new Object[]
                                        {treeModel.getNumber()});

        }

    }
    /**
     * 默认实现对于相 同的长编码，但ID不同进行处理。子类可按需要覆盖实现。
	 * @param ctx
	 * @param treeBaseInfo
	 * @throws BOSException
	 * @throws EASBizException
	 * @throws TreeBaseException
	 */
	protected void checkLNForTree(Context ctx, TreeBaseInfo treeBaseInfo) throws BOSException, EASBizException, TreeBaseException {
		//如果数据库存在相同longNumber但ID不同的数据，则异常。
		FilterInfo lNfilter = new FilterInfo();
		lNfilter.getFilterItems().add(new FilterItemInfo(IFWEntityStruct.tree_LongNumber,treeBaseInfo.getLongNumber()));
		lNfilter.getFilterItems().add(new FilterItemInfo(IFWEntityStruct.coreBase_ID,treeBaseInfo.getId().toString(),CompareType.NOTEQUALS));
		lNfilter.setMaskString("#0 AND #1");
		
//        //CU隔离
//        FilterInfo filterCU = getFilterForDefaultCU(ctx,treeBaseInfo);
//        if(FilterUtility.hasFilterItem(filterCU))
//        {
//            lNfilter.mergeFilter(filterCU,"AND");
//        }
        
		
		if(exists(ctx,lNfilter))
		{
			 throw new TreeBaseException(TreeBaseException.CHECKNUMBERDUPLICATED ,
		            new Object[]
		            {treeBaseInfo.getNumber()});
		}
	}
}
