package com.kingdee.eas.fdc.schedule.framework.ext;

import java.util.GregorianCalendar;

import net.sourceforge.ganttproject.CustomPropertyDefinition;
import net.sourceforge.ganttproject.GPLogger;
import net.sourceforge.ganttproject.GanttCalendar;

import org.w3c.util.DateParser;
import org.w3c.util.InvalidDateException;

/**
 * 
 * 这个类基于net.sourceforge.ganttproject.CustomPropertyManager.PropertyTypeEncoder 做修改
 * @author xiaohong_shi
 *
 */
public class PropertyTypeEncoder {
	public static String encodeFieldType(Class fieldType) {
		String result = null;
		if (fieldType.equals(String.class)) {
			result = "text";
		} else if (fieldType.equals(Boolean.class)) {
			result = "boolean";
		} else if (fieldType.equals(Integer.class)) {
			result = "int";
		} else if (fieldType.equals(Double.class)) {
			result = "double";
		} else if (fieldType.isAssignableFrom(GregorianCalendar.class)) {
			result = "date";
		}else{
			//直接返回类名 by sxhong
			result=fieldType.getName();
		}
		return result;
	}

	/**
	 * @param typeAsString 类型
	 * @param valueAsString 默认值
	 * @return
	 */
	public static CustomPropertyDefinition decodeTypeAndDefaultValue(final String typeAsString, final String valueAsString) {
		final Class type;
		final Object defaultValue;
		if (typeAsString.equals("text")) {
			type = String.class;
			defaultValue = valueAsString == null ? null : valueAsString.toString();
		} else if (typeAsString.equals("boolean")) {
			type = Boolean.class;
			defaultValue = valueAsString == null ? Boolean.FALSE : Boolean.valueOf(valueAsString);
		} else if (typeAsString.equals("int")) {
			type = Integer.class;
			defaultValue = valueAsString == null ? null : Integer.valueOf(valueAsString);
		} else if (typeAsString.equals("double")) {
			type = Double.class;
			defaultValue = valueAsString == null ? null : Double.valueOf(valueAsString);
		} else if (typeAsString.equals("date")) {
			type = GregorianCalendar.class;
			if (valueAsString != null) {
				GanttCalendar c = null;
				try {
					c = new GanttCalendar(DateParser.parse(valueAsString));
				} catch (InvalidDateException e) {
					if (!GPLogger.log(e)) {
						e.printStackTrace(System.err);
					}
				}
				defaultValue = c;
			} else {
				defaultValue = null;
			}
		} else if (typeAsString.equals("enum")) {
			type = String.class;
			defaultValue = valueAsString == null ? null : valueAsString
					.toString();
		} else {
			/*
			 * type = String.class; // TODO remove if(...text) defaultValue =
			 * "";
			 */
			//直接生成类
			Class myType=null;
			try {
				myType=Class.forName(typeAsString);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			type=myType;
			defaultValue = null;
		}
		return new CustomPropertyDefinition() {
			public Object getDefaultValue() {
				return defaultValue;
			}

			public String getDefaultValueAsString() {
				return valueAsString;
			}

			public String getID() {
				return null;
			}

			public String getName() {
				return null;
			}

			public Class getType() {
				return type;
			}

			public String getTypeAsString() {
				return typeAsString;
			}
		};
	}

}