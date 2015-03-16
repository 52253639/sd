package com.kingdee.eas.fdc.finance.app;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.finance.SubjectLevelFactory;
import com.kingdee.eas.fdc.finance.SubjectLevelInfo;
import com.kingdee.eas.framework.DataBaseInfo;
import com.kingdee.eas.framework.FrameWorkUtils;
import com.kingdee.eas.framework.IFWEntityStruct;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;

public class SubjectLevelControllerBean extends
		AbstractSubjectLevelControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.finance.app.SubjectLevelControllerBean");

	 protected void setNumberFromCodingRule(Context ctx, DataBaseInfo caller,
	            String orgId) throws BOSException, EASBizException
	    {
		 if (!FDCHelper.isEmpty(caller.getNumber())) {
			return;
		}
//	        // 暂定，只有基础资料的才自动使用编码规则生成编码
//	        if (caller.getClass().getPackage().getName().toLowerCase().indexOf(
//	                FrameWorkUtils.strDataBase_Assistant) < 0
//	                && caller.getClass().getPackage().getName().toLowerCase()
//	                        .indexOf(FrameWorkUtils.strDataBase_Master) < 0)
//	        {
//	            return;
//	        }

	        // getNextCompanyId(Context ctx)

	        ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory
	                .getLocalInstance(ctx);
	        if (orgId == null || orgId.trim().length() == 0)
	        {
	            // 无当前组织，或者当前组织没定义编码规则，用集团的
	            orgId = getNextCompanyId(ctx);
	        }
	        //如果orgID为空，则不处理。
	        if (orgId == null || orgId.trim().length() == 0)
	        {
	            return;
	        }
	            
	        if (iCodingRuleManager.isExist(caller, orgId))
	        {
	            /*
	            if (iCodingRuleManager.isUseIntermitNumber(caller, orgId)
	                    && (!iCodingRuleManager.isUserSelect(caller, orgId)))
	            {
	                caller.setNumber(iCodingRuleManager.getNumber(caller, orgId));
	            }
	            else
	            {
	                if (iCodingRuleManager.isModifiable(caller, orgId))
	                {
	                    // 判断是否修改了编码,是否改大了顺序号
	                    iCodingRuleManager.checkModifiedNumber(caller, orgId,
	                            caller.getNumber().toString());
	                }
	            }*/
	            
	            if (iCodingRuleManager.isUseIntermitNumber(caller, orgId)
	                    && (!iCodingRuleManager.isUserSelect(caller, orgId)))
	            {
	                caller.setNumber(iCodingRuleManager.getNumber(caller, orgId));
	            }
	            else
	            {
	                if (iCodingRuleManager.isAddView(caller, orgId))
	                {
	                    if (iCodingRuleManager.isModifiable(caller, orgId))
	                    {
	                        // 判断是否修改了编码,是否改大了顺序号
	                        iCodingRuleManager.checkModifiedNumber(caller, orgId,
	                                caller.getNumber().toString());
	                    }
	                }
	                else
	                {
	                    // 什么都没选,新增不显示,允许断号,业务传空number值,在此设置number
	                    caller.setNumber(iCodingRuleManager
	                            .getNumber(caller, orgId));
	                }
	            }
	        }

	    }
	/**
	 * 返回分录里面选择的成本科目<br>
	 * 用于前台根据ID匹配哪些科目打钩<br>
	 * 因为未审批前，都读取最新的成本科目表
	 */
	protected Map _getSelectedIDS(Context ctx, String pk) throws BOSException {
		Map ids = new HashMap();

		StringBuffer sql = new StringBuffer();
		sql
				.append(" select FAccountItemID,FDescription from T_FNC_SubjectLevelSubject where ");
		sql.append(" FParentID ='").append(pk).append("' ");
		sql.append(" and FIsSelected = 1 ");
		IRowSet rs = DbUtil.executeQuery(ctx, sql.toString());
		try {
			while (rs.next()) {
				ids.put(rs.getString("FAccountItemID"), rs.getString("FDescription"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ids;
	}
	
	protected IObjectPK _addnew(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		_checkNumberDup(ctx, model);
		_checkNameDup(ctx, model);
		_checkNameBlank(ctx, model);
		return super._addnew(ctx, model);
	}
	protected void _cancel(Context ctx, IObjectPK pk, IObjectValue model)
			throws BOSException, EASBizException {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("isEnabled");
		SubjectLevelInfo info = (SubjectLevelInfo) model;
		info.setIsEnabled(false);
		super.updatePartial(ctx, info, sic);
	}
	
	protected void _cancelCancel(Context ctx, IObjectPK pk, IObjectValue model)
			throws BOSException, EASBizException {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("isEnabled");
		SubjectLevelInfo info = (SubjectLevelInfo) model;
		info.setIsEnabled(true);
		super.updatePartial(ctx, info, sic);
	}
    /**
	 * 描述：判断是否被引用
	 */
	protected void _delete(Context ctx, IObjectPK pk) throws BOSException,
			EASBizException {
		this.isReferenced(ctx, pk);
		super._delete(ctx, pk);
	}

	protected void _delete(Context ctx, IObjectPK[] arrayPK)
			throws BOSException, EASBizException {
		if (arrayPK != null) {
			for (int i = 0; i < arrayPK.length; i++) {
				super.isReferenced(ctx, arrayPK[i]);
			}
		}
		super._delete(ctx, arrayPK);
	}
	

	protected void _update(Context ctx, IObjectPK pk, IObjectValue model)
			throws BOSException, EASBizException {
		_checkNumberDup(ctx, model);
		_checkNameDup(ctx, model);
		_checkNameBlank(ctx, model);
		super._update(ctx, pk, model);
	}

	protected void _checkNameDup(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		SubjectLevelInfo pdsInfo = (SubjectLevelInfo) model;
		FilterInfo filter = new FilterInfo();
		filter.appendFilterItem("name", pdsInfo.getName());

		// 排除自身的校验 add by masb 2010/06/12
		FilterItemInfo filterItem = null;
		if (pdsInfo.getId() != null) {
			filterItem = new FilterItemInfo(IFWEntityStruct.coreBase_ID,
					pdsInfo.getString("id"), CompareType.NOTEQUALS);
			filter.getFilterItems().add(filterItem);
		}
		if (SubjectLevelFactory.getLocalInstance(ctx).exists(filter)) {
			String name = "名称" + pdsInfo.getName();
			throw new EASBizException(EASBizException.CHECKDUPLICATED,
					new Object[] { name });
		}
	}
}