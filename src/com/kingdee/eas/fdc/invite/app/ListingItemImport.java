package com.kingdee.eas.fdc.invite.app;

import java.text.MessageFormat;
import java.util.Hashtable;
import java.util.Locale;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.fdc.invite.HeadTypeFactory;
import com.kingdee.eas.fdc.invite.HeadTypeInfo;
import com.kingdee.eas.fdc.invite.ListingItemFactory;
import com.kingdee.eas.fdc.invite.ListingItemInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.tools.datatask.AbstractDataTransmission;
import com.kingdee.eas.tools.datatask.core.TaskExternalException;
import com.kingdee.eas.tools.datatask.runtime.DataToken;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.ResourceBase;
import com.kingdee.jdbc.rowset.IRowSet;

public class ListingItemImport extends AbstractDataTransmission {
	public static final String resourcePath = "com.kingdee.eas.fdc.invite.FDCInviteResource";

	protected ICoreBase getController(Context ctx) throws TaskExternalException {
		// TODO 自动生成方法存根
		try {
			return ListingItemFactory.getLocalInstance(ctx);
		} catch (BOSException bex) {
			throw new TaskExternalException("", bex);
		}
	}

	public CoreBaseInfo transmit(Hashtable hsData, Context ctx)
			throws TaskExternalException {
		// TODO 自动生成方法存根
		ListingItemInfo info = new ListingItemInfo();
		Locale locale = ctx.getLocale();
		String number = getFieldValue(hsData, "FNumber");
		if ("".equals(number)) {
			throw new TaskExternalException(ResourceBase.getString(resourcePath,
					"ListingItemNumberIsNull", locale));
		}
		if(number!=null&&number.length()>499){
			throw new TaskExternalException("子目编码超长!");
		}
		info.setNumber(number);

		String name = getFieldValue(hsData, "FName");
		if ("".equals(name)) {
			throw new TaskExternalException(ResourceBase.getString(resourcePath,
					"NameIsNull", locale));
		}
		if(name!=null&&name.length()>499){
			throw new TaskExternalException("子目名称超长!");
		}
		info.setName(name);

		String unit = getFieldValue(hsData, "FUnit");
		// if ("".equals(unit)) {
		// throw new TaskExternalException(ResourceBase.getString(resourcePath,
		// "UnitIsNull"));
		// }
		info.setUnit(unit);

		String longNumber = getFieldValue(hsData, "FHeadTypeLongNumber");
		if ("".equals(longNumber)) {
			throw new TaskExternalException(ResourceBase.getString(resourcePath,
					"LongNumberIsNull", locale));
		}
		// 长编码以.开始或者结尾 属于数据不完整 抛出异常
		if (longNumber.startsWith(".") || longNumber.endsWith(".")) {
			String err = ResourceBase.getString(resourcePath,
					"LongNumberIsComplete", locale);
			err = MessageFormat.format(err, new Object[] { longNumber });
			throw new TaskExternalException(err);
		}
		String org = ContextUtil.getCurrentOrgUnit(ctx).getId().toString();
		try {
			// 判断同一组织下编码唯一
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("number", number));
			filter.getFilterItems().add(new FilterItemInfo("orgUnit", org));
			view.setFilter(filter);
			CoreBaseCollection collection = ListingItemFactory
					.getLocalInstance(ctx).getCollection(view);
			ListingItemInfo oldInfo = null;
			if (collection.size() > 0) {
				if (isSltImportUpdate()) {
					oldInfo = (ListingItemInfo) collection.get(0);
					oldInfo.setNumber(info.getNumber());
					oldInfo.setName(info.getName());
					oldInfo.setUnit(info.getUnit());
					oldInfo.setIsImportant(info.isIsImportant());
					info = oldInfo;
				} else {
					String err = ResourceBase.getString(resourcePath,
							"ListingItemNumberIsExist", locale);
					err = MessageFormat.format(err, new Object[] { number });
					throw new TaskExternalException(err);
				}
			}

			// 判断同一组织下名称唯一
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("name", name));
			filter.getFilterItems().add(new FilterItemInfo("orgUnit", org));
			if (oldInfo != null) {
				filter.getFilterItems().add(
						new FilterItemInfo("id", oldInfo.getId().toString(),
								CompareType.NOTEQUALS));
			}
			if (ListingItemFactory.getLocalInstance(ctx).exists(filter)) {
				String err = ResourceBase.getString(resourcePath,
						"ListingItemNameIsExist", locale);
				err = MessageFormat.format(err, new Object[] { name });
				throw new TaskExternalException(err);

			}

			// 判断表头类型长编码是否存在
			view = new EntityViewInfo();
			filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("longNumber", longNumber.replace('.',
							'!')));
			view.setFilter(filter);
			collection = HeadTypeFactory.getLocalInstance(ctx).getCollection(
					view);
			if (collection.size() > 0) {
				HeadTypeInfo headInfo = (HeadTypeInfo) collection.get(0);
				if (headInfo.isIsLeaf()) {
					info.setHeadType(headInfo);
				} else {
					String err = ResourceBase.getString(resourcePath,
							"InviteTypeIsLeaf", locale);
					err = MessageFormat
							.format(err, new Object[] { longNumber });
					throw new TaskExternalException(err);
				}
			} else {
				String err = ResourceBase.getString(resourcePath,
						"InviteTypeLongNumberIsExist", locale);
				err = MessageFormat.format(err, new Object[] { longNumber });
				throw new TaskExternalException(err);
			}

			info.setOrgUnit(FullOrgUnitFactory.getLocalInstance(ctx)
					.getFullOrgUnitInfo(new ObjectUuidPK(org)));
		} catch (TaskExternalException e) {
			throw new TaskExternalException(e.getMessage());
		} catch (Exception e) {
			throw new TaskExternalException(e.getMessage());
		}

		return info;
	}

	public void submit(CoreBaseInfo coreBaseInfo, Context ctx)
			throws TaskExternalException {
		super.submit(coreBaseInfo, ctx);
	}

	public Hashtable exportTransmit(IRowSet rs, Context ctx)
			throws TaskExternalException {
		// 导出用
		return null;
	}

	public FilterInfo getExportFilterForQuery(Context ctx) {// 导出用
		return null;
	}

	public String getExportQueryInfo(Context ctx) {// 导出用
		return null;
	}

	/**
	 * 从Hashtable中提取字段值。
	 */
	protected String getFieldValue(Hashtable hsData, String key) {
		return ((String) ((DataToken) hsData.get(key)).data).trim();
	}
}
