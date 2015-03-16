package com.kingdee.eas.fdc.invite.app;

import java.text.MessageFormat;
import java.util.Hashtable;
import java.util.Locale;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.eas.fdc.invite.InviteTypeFactory;
import com.kingdee.eas.fdc.invite.InviteTypeInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.tools.datatask.AbstractDataTransmission;
import com.kingdee.eas.tools.datatask.core.TaskExternalException;
import com.kingdee.eas.tools.datatask.runtime.DataToken;
import com.kingdee.eas.util.ResourceBase;
import com.kingdee.jdbc.rowset.IRowSet;

public class InviteTypeImport extends AbstractDataTransmission {
	public static final String resourcePath = "com.kingdee.eas.fdc.invite.FDCInviteResource";

	protected ICoreBase getController(Context ctx) throws TaskExternalException {
		// TODO 自动生成方法存根
		try {
			return InviteTypeFactory.getLocalInstance(ctx);
		} catch (BOSException bex) {
			throw new TaskExternalException("", bex);
		}
	}

	public CoreBaseInfo transmit(Hashtable hsData, Context ctx)
			throws TaskExternalException {
		// TODO 自动生成方法存根
		InviteTypeInfo info = new InviteTypeInfo();
		String longNumber = getFieldValue(hsData, "FLongNumber");
		info.setLongNumber(longNumber.replace('.', '!'));
		String number = getFieldValue(hsData, "FNumber");
		info.setNumber(number);
		String name = getFieldValue(hsData, "FName");
		info.setName(name);
		info.setDescription(getFieldValue(hsData, "FDescription"));
		Locale locale = ctx.getLocale();
		if ("".equals(longNumber)) {
			throw new TaskExternalException(ResourceBase.getString(resourcePath,
					"LongNumberIsNull", locale));
		}

		if ("".equals(number)) {
			throw new TaskExternalException(ResourceBase.getString(resourcePath,
					"NumberIsNull", locale));
		}

		if ("".equals(name)) {
			throw new TaskExternalException(ResourceBase.getString(resourcePath,
					"NameIsNull", locale));
		}

		// 长编码以 .开始或者结尾 属于数据不完整 抛出异常
		if (longNumber.startsWith(".") || longNumber.endsWith(".")) {
			String err = ResourceBase.getString(resourcePath,
					"LongNumberIsComplete", locale);
			err = MessageFormat.format(err, new Object[] { longNumber });
			throw new TaskExternalException(err);
		}

		// 长编码最后一段字符和编码不一样 抛出异常
		if (!number.equals(longNumber
				.substring(longNumber.lastIndexOf(".") + 1))) {
			String err = ResourceBase.getString(resourcePath,
					"LongNumberAndNumber", locale);
			err = MessageFormat.format(err, new Object[] { longNumber,
					longNumber.substring(longNumber.lastIndexOf(".") + 1),
					number });
			throw new TaskExternalException(err);
		}

		// 有父节点
		if (longNumber.indexOf(".") != -1) {
			String parentNumber = longNumber.substring(0, longNumber
					.lastIndexOf("."));
			try {
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(
						new FilterItemInfo("longNumber", parentNumber));
				view.setFilter(filter);
				CoreBaseCollection collection = InviteTypeFactory
						.getLocalInstance(ctx).getCollection(view);
				// 查询父节点是否存在
				if (collection.size() > 0) {
					InviteTypeInfo parentInfo = (InviteTypeInfo) collection
							.get(0);
					info.setParent(parentInfo);
				} else {
					String err = ResourceBase.getString(resourcePath,
							"LongNumberNoFoundParent", locale);
					err = MessageFormat.format(err, new Object[] { longNumber,
							parentNumber });
					throw new TaskExternalException(err);
				}
			} catch (TaskExternalException e) {
				throw new TaskExternalException(e.getMessage());
			} catch (Exception e) {
				throw new TaskExternalException(e.getMessage());
			}
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
