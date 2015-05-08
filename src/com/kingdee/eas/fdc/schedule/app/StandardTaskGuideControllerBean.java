package com.kingdee.eas.fdc.schedule.app;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.base.attachment.AttachmentFactory;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.FDCTreeBaseDataInfo;
import com.kingdee.eas.fdc.schedule.StandardTaskGuideInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.Result;
import com.kingdee.eas.framework.TreeBaseException;
import com.kingdee.eas.framework.TreeBaseInfo;
import com.kingdee.util.StringUtils;

public class StandardTaskGuideControllerBean extends
		AbstractStandardTaskGuideControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.schedule.app.StandardTaskGuideControllerBean");

	public Result addnew(Context arg0, CoreBaseCollection arg1)
			throws BOSException, EASBizException {

		return super.addnew(arg0, arg1);
	}

	public void addnew(Context ctx, IObjectPK pk, FDCTreeBaseDataInfo model)
			throws BOSException, EASBizException {
		super.addnew(ctx, pk, model);
	}

	protected void _save(Context ctx, IObjectPK pk, IObjectValue model)
			throws BOSException, EASBizException {
		super._save(ctx, pk, model);

	}
	
	protected void checkLNForTree(Context ctx, TreeBaseInfo treeBaseInfo)
			throws BOSException, EASBizException, TreeBaseException {
//		super.checkLNForTree(ctx, treeBaseInfo);
	}
	protected void _checkNumberDup(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
//		super._checkNumberDup(ctx, model);
	}
		
	protected void _delete(Context ctx, IObjectPK[] arrayPK) throws BOSException, EASBizException {
		for(int i = 0; i<arrayPK.length;i++){
			_delete( ctx, arrayPK[i]); // 会删除其包含的子节点，子节点删除后更改父节点的isleaf属性
		}
	}
	/**
	 * 删除当前先择的任务指引，如果是非叶子节点，则一并删除其包含的子节点
	 *  
	 */
	protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		if (!exists(ctx, pk)) {
			return;
		}
		StandardTaskGuideInfo guideInfo = getStandardTaskGuideInfo(ctx, pk);
		String parentID = guideInfo.getParent() != null ?guideInfo.getParent().getId().toString():null;
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("longnumber",guideInfo.getLongNumber(),CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("longnumber",guideInfo.getLongNumber()+"!%",CompareType.LIKE));
	    filter.setMaskString("#0 or #1");
	    IObjectPK [] pks = delete(ctx, filter);
	    
	    /**清除对应的附件*/
//		filter = new FilterInfo();
//		filter.getFilterItems().add(new FilterItemInfo("boAttchAsso.BoID", pkSet, CompareType.INCLUDE));
//		AttachmentFactory.getLocalInstance(ctx).delete(filter);
//		
//		filter = new FilterInfo();
//		filter.getFilterItems().add(new FilterItemInfo("BoID", pkSet, CompareType.INCLUDE));
//		BoAttchAssoFactory.getLocalInstance(ctx).delete(filter);
	    
	    
	    StringBuffer str = new StringBuffer("(");
	    int length = pks.length;
	    Set idSet = new HashSet(length);
	    
	    for(int i=0;i<length;i++){
	    	str.append("'");
	    	str.append(pks[i].toString());
	    	idSet.add(pks[i].toString());
	    	if(i<length-1){
	    		str.append("',");
	    	}
	    }
	    str.append("')");

	    /**清除对应的附件*/
	    filter = new FilterInfo();
	    filter.getFilterItems().add(new FilterItemInfo("id","select FAttachmentID from T_BAS_BoAttchAsso where FBoID in "+str.toString(),CompareType.INNER));
		AttachmentFactory.getLocalInstance(ctx).delete(filter);
//		
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("boID",idSet,CompareType.INCLUDE));
		BoAttchAssoFactory.getLocalInstance(ctx).delete(filter);
		
		/**更新父级指引的isLeaf属性 */
		if(!StringUtils.isEmpty(parentID)){
			FDCSQLBuilder builder  = new FDCSQLBuilder(ctx);
			StringBuffer updateLeafSql = new StringBuffer();
			updateLeafSql.append("update T_SCH_StandardTaskGuideNew set fisleaf =(select ");
			updateLeafSql.append(" case ");
			updateLeafSql.append(" when (select  ");
			updateLeafSql.append(" count(*) num from T_SCH_StandardTaskGuideNew  where flongnumber like (select flongnumber || '!%' from  ");
			updateLeafSql.append("  T_SCH_StandardTaskGuideNew   where fid = ? ) and fid <> ? ) >0  then 0  ");
			updateLeafSql.append("  else 1 end ) where fid = ? ");
	        builder.appendSql(updateLeafSql.toString());
	        builder.addParam(parentID);
			builder.addParam(pk.toString());
			builder.addParam(parentID);
			builder.execute();
		}
		
	}
	
}