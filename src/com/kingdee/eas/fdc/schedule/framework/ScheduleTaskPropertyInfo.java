package com.kingdee.eas.fdc.schedule.framework;

import java.io.Serializable;

import com.kingdee.bos.util.BOSUuid;

public class ScheduleTaskPropertyInfo extends AbstractScheduleTaskPropertyInfo
		implements Serializable {
	public ScheduleTaskPropertyInfo() {
		super();
	}

	protected ScheduleTaskPropertyInfo(String pkField) {
		super(pkField);
	}

	public ScheduleTaskPropertyInfo(String id, String name, String propertyId,
			TaskPropertyTypeEnum type, String valueType) {
		if (id == null) {
			this.setId(BOSUuid.create(this.getBOSType()));
		} else {
			this.setId(BOSUuid.read(id));
		}
		this.setName(name);
		this.setPropertyId(propertyId);
		this.setType(type);
		this.setValueType(valueType);
	}

	public ScheduleTaskPropertyInfo(String id, String name, String propertyId,
			TaskPropertyTypeEnum type, String valueType, String key,
			String mapKey) {
		this(id, name, propertyId, type, valueType);
		this.setKey(key);
		this.setMapKey(mapKey);
	}

	public ScheduleTaskPropertyInfo(String id, String name, String propertyId,
			TaskPropertyTypeEnum type, String valueType, String key,
			String mapKey, String query) {
		this(id, name, propertyId, type, valueType);
		this.setKey(key);
		this.setMapKey(mapKey);
		this.setQuery(query);

	}

	private static ScheduleTaskPropertyCollection taskPropertys = null;

	/**
	 * 默认列属性，从数据库内取
	 * <p>
	 * 之前写死的列，现改为从数据库取值，可以自定义列。<br>
	 * 如果在数据库中新增一列，大部分属性已经指定好了，但是如果要默认显示出来，多半还要<br>
	 * 修改ScheduleBaseUI.load2Gantt()方法，在数组中加好默认显示的列。<br>
	 * 系统预设的21列代码中多有调用，不可删除，避免不必要的错误<br>
	 * add by emanon
	 * 
	 * @return
	 */
	// public static ScheduleTaskPropertyCollection
	// getDefaultScheduleTaskProperty(
	// int UI) {
	// try {
	// EntityViewInfo view = new EntityViewInfo();
	// view.getSorter().add(new SorterItemInfo("seq"));
	// taskPropertys = ScheduleTaskPropertyFactory.getRemoteInstance()
	// .getScheduleTaskPropertyCollection(view);
	// for (int i = taskPropertys.size() - 1; i >= 0; i--) {
	// int displayUI = taskPropertys.get(i).getDisplayUI();
	// int hideUI = taskPropertys.get(i).getHideUI();
	// if (ScheduleTaskPropertyHelper.isIncludeUI(UI, displayUI)
	// || ScheduleTaskPropertyHelper.isIncludeUI(UI, hideUI)) {
	//
	// } else {
	// taskPropertys.removeObject(i);
	// }
	// }
	// } catch (BOSException e) {
	// e.printStackTrace();
	// }
	// return taskPropertys;
	// }

	public String getQuery() {
		return this.getString("query");
	}

	public void setQuery(String query) {
		this.setString("query", query);
	}
}