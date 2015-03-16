package com.kingdee.eas.fdc.invite.markesupplier.uitl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.util.backport.Collections;
import com.kingdee.eas.base.permission.util.IObjectCol2List;
import com.kingdee.eas.base.permission.util.IObjectCol2Map;
import com.kingdee.eas.basedata.org.FullOrgUnitCollection;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.util.StringUtils;

public class CollectionHelper {
	/**
	 * 降序
	 */
	public static final int DESC = 0;
	/**
	 * 升序
	 */
	public static final int ASC = 1;

	/**
	 * 排序
	 * 
	 * @param number
	 * @param sortType
	 *            排序方式
	 */
	public static void sort(int[] number, int sortType) {
		boolean flag = true;
		for (int i = 0; i < number.length - 1 && flag; i++) {
			flag = false;
			for (int j = 0; j < number.length - i - 1; j++) {
				if (ASC == sortType) {
					if (number[j + 1] < number[j]) {
						swap(number, j + 1, j);
						flag = true;
					}
				} else if (DESC == sortType) {
					if (number[j + 1] > number[j]) {
						swap(number, j + 1, j);
						flag = true;
					}
				}
			}
		}
	}
	static void swap(int[] number, int i, int j) {
		int t;
		t = number[i];
		number[i] = number[j];
		number[j] = t;
	}
	/**
	 * 将str分割后返回list
	 * @param str
	 * @param spiltStr
	 * @return
	 */
	public static List split(String str, String splitStr) {
		ArrayList list = new ArrayList();
		String[] ss = str.split(splitStr);
		for (int i = 0; i < ss.length; i++) {
			list.add(ss[i]);
		}
		return list;
	}
	/**
	 * 根据map的key排序(自然排序)后返回值的list
	 * @param map
	 * @return
	 */
	public static List sortByKeyToList(Map map) {
		return sortByKeyToList(map, null);
	}

	/**
	 * 根据map的key排序(按指定的排序器排序)后返回值的list
	 * @param map
	 * @return
	 */
	public static List sortByKeyToList(Map map, Comparator comparator) {
		List keyList = sortKey(map, comparator);
		ArrayList valueList = new ArrayList();
		for (int i = keyList.size(); --i >= 0;) {
			valueList.add(map.get(keyList.get(i)));
		}
		return valueList;
	}

	/**
	 * 将map的key按排序器排序后返回key的list
	 * @param map
	 * @param comparator
	 */
	public static List sortKey(Map map, Comparator comparator) {
		if (map == null || map.isEmpty()) {
			return null;
		}
		Iterator it = map.keySet().iterator();
		ArrayList keyList = new ArrayList();
		while (it.hasNext()) {
			keyList.add(it.next());
		}
		Collections.sort(keyList, comparator);
		return keyList;
	}
	/**
	 * 将list去重复
	 * @param list
	 */
	public static List toSingleElement(List list) {
		ArrayList temp = new ArrayList(list.size());
		HashSet tempSet = new HashSet();
		for (int i = 0, k = list.size(); i < k; i++) {
			tempSet.add(list.get(i));
		}
		Iterator it = tempSet.iterator();
		while (it.hasNext()) {
			temp.add(it.next());
		}
		return temp;
	}
	/**
	 * Set集合转换成字符集合('','','')
	 * @param idSet 集合
	 * */
	public static String toStringBySet(Set idSet)
    {
		if(idSet == null || idSet.size() < 1)
			return "('')";
		String ids = null;
		Iterator iter = idSet.iterator();
		for(int i = 0; iter.hasNext() && i < 999; i++){
			if(ids == null)
				ids = "('" + (String)iter.next() + "'";
			else
				ids = ids + ", '" + (String)iter.next() + "'";
		}
		ids = ids + ")";
		return ids;
    }
	/**
	 * Collection集合转换成字符集合('','','')
	 * @param idSet 集合
	 * */
	public static String toStringByColl(Collection idColl)
    {
		if(idColl == null || idColl.size() < 1)
			return null;
		String ids = null;
		Iterator iter = idColl.iterator();
		for(int i = 0; iter.hasNext() && i < 999; i++){
			if(ids == null)
				ids = "('" + (String)iter.next() + "'";
			else
				ids = ids + ", '" + (String)iter.next() + "'";
		}
		ids = ids + ")";
		return ids;
    }
	/**
	 * IObjectCollection集合转换成字符集合('','','')
	 * @param idSet 集合
	 * */
    public static String toStringByObjectColl(IObjectCollection coll)
    {
    	if(coll == null || coll.size() < 1)
    		return null;
    	StringBuffer sql = new StringBuffer();
    	Iterator it = coll.iterator();
    	for(int i = 0; it.hasNext() && i < 999; i++){
    		if(i == 0)
            {
    			sql.append("('");
    			sql.append(((CoreBaseInfo)it.next()).getId().toString());
    			sql.append("'");
            } else
            {
            	sql.append(",'");
            	sql.append(((CoreBaseInfo)it.next()).getId().toString());
            	sql.append("'");
            }
    	}
    	sql.append(")");
    	return sql.toString();
    }
    /**
	 * array[]集合转换成字符集合('','','')
	 * @param idSet 集合
	 * */
    public static String Array2String(String array[])
    {
    	if(array == null || array.length < 1)
    		return null;
    	String ids = null;
    	int i = 0;
    	for(int len = array.length; i < len && i < 999; i++){
    		if(ids == null)
    			ids = "('" + array[i] + "'";
    		else
    			ids = ids + ", '" + array[i] + "'";
    	}
    	ids = ids + ")";
    	return ids;
    }
    /**
	 * array[]集合转换成Set集合
	 * @param idSet 集合
	 * */
    public static Set toSetByStrArray(String array[])
    {
    	if(array == null || array.length < 1)
    		return null;
    	Set idSet = new HashSet();
    	int i = 0;
    	for(int len = array.length; i < len; i++)
    		idSet.add(array[i]);
    	return idSet;
    }
	/**
	 * 对集合按某字段进行排序,该字段的值需要是Comparable类型的.
	 * @param cols 要排序的集合
	 * @param sortColName 要排序的字段
	 * @param sortType 是否正序
	 * */
	public static void sortCollection(IObjectCollection cols, final String sortColName, final boolean sortType) {
		Object[] toSortData = cols.toArray();
		
		Arrays.sort(toSortData, new Comparator(){
			public int compare(Object arg0, Object arg1) {
				IObjectValue obj0 = (IObjectValue)arg0;
				IObjectValue obj1 = (IObjectValue)arg1;
				if(obj0 == null  ||  obj1 == null){
					return 0;
				}
				
				Comparable tmp0 = (Comparable)getValue(obj0,sortColName);
				Comparable tmp1 = (Comparable)getValue(obj1,sortColName);
				if(tmp0 == null  ||  tmp1 == null){
					return 0;
				}
				
				return sortType ? tmp0.compareTo(tmp1) : -tmp0.compareTo(tmp1);
			}
		});
		
		cols.clear();
		for(int j=0; j<toSortData.length; j++){
			cols.addObject((IObjectValue) toSortData[j]);
		}
	}
	/**
	 * 对集合按多字段进行排序,该字段的值需要是Comparable类型的.
	 * @param cols 要排序的集合
	 * @param sortColName 要排序的字段
	 * @param sortType 是否正序
	 * */
	public static void sortCollection(IObjectCollection cols, final String[] sortColNames, final boolean sortType) {
		Object[] toSortData = cols.toArray();
		
		Arrays.sort(toSortData, new Comparator(){
			public int compare(Object arg0, Object arg1) {
				IObjectValue obj0 = (IObjectValue)arg0;
				IObjectValue obj1 = (IObjectValue)arg1;
				if(obj0 == null  ||  obj1 == null){
					return 0;
				}
				for(int i=0; i<sortColNames.length; i++){
					Comparable tmp0 = (Comparable)getValue(obj0,sortColNames[i]);
					Comparable tmp1 = (Comparable)getValue(obj1,sortColNames[i]);
					if(tmp0 != null  &&  tmp1 != null){
						if(tmp0.compareTo(tmp1) == 0){
							continue;
						}else{
							return sortType ? tmp0.compareTo(tmp1) : -tmp0.compareTo(tmp1);
						}
					}
					if(tmp0 == null){
						return sortType ? -1 : 1;
					}
					if(tmp1 == null){
						return sortType ? 1 : -1;
					}
				}
				return 0;
			}
		});
		
		cols.clear();
		for(int j=0; j<toSortData.length; j++){
			cols.addObject((IObjectValue) toSortData[j]);
		}
	}
	/**
	 * 对数组 按字段进行排序,该字段的值需要是Comparable类型的.
	 * @param cols 要排序的集合
	 * @param sortColName 要排序的字段
	 * @param sortType 是否正序
	 * */
	public static void sortCollection(Object[] toSortData, final String sortColName, final boolean sortType) {
		if(toSortData==null || toSortData.length==0) return;
		Arrays.sort(toSortData, new Comparator(){
			public int compare(Object arg0, Object arg1) {
				IObjectValue obj0 = (IObjectValue)arg0;
				IObjectValue obj1 = (IObjectValue)arg1;
				if(obj0 == null  ||  obj1 == null){
					return 0;
				}
				Comparable tmp0 = (Comparable)getValue(obj0,sortColName);
				Comparable tmp1 = (Comparable)getValue(obj1,sortColName);
				if(tmp0 == null  ||  tmp1 == null){
					return 0;
				}
				return sortType ? tmp0.compareTo(tmp1) : -tmp0.compareTo(tmp1);
			}
		});
	}
	
	
	/**
	 * 获取某对象的属性值，支持及联获取.key可以是 room.number 这种格式
	 * */
	public static Object getValue(IObjectValue value, String key){
		int in = key.indexOf(".");
		if(in == -1){
			return value.get(key);
		}else{
			Object tmp = value.get(key.substring(0, in));
			if(tmp != null  &&  tmp instanceof IObjectValue){
				return getValue((IObjectValue) tmp, key.substring(in + 1, key.length()));
			}
		}
		return null;
	}
	public static List toListByObjAry(Object[] obj)
	  {
	    if ((obj == null) || (obj.length == 0)) return new ArrayList();
	    List retList = new ArrayList();
	    int i = 0; for (int size = obj.length; i < size; i++)
	    {
	      retList.add(StringUtils.cnulls(obj[i]));
	    }
	    return retList;
	  }
	public static List toListByColl(Collection col)
	  {
	    List retList = new ArrayList();
	    if ((col == null) || (col.isEmpty()))
	    {
	      return retList;
	    }
	    Iterator it = col.iterator();
	    while (it.hasNext())
	    {
	      retList.add(it.next());
	    }
	    return retList;
	  }
	public static List toListByIObjectColl(IObjectCollection col) {
	    List ret = new ArrayList();
	    for (int i = 0; i < col.size(); i++) {
	      ret.add(col.getObject(i));
	    }
	    return ret;
	 }
	  public static Set toSetByColl(Collection col)
	  {
	    Set retMap = new HashSet();
	    if ((col == null) || (col.isEmpty()))
	    {
	      return retMap;
	    }
	    Iterator it = col.iterator();
	    while (it.hasNext())
	    {
	      Object obj = it.next();
	      retMap.add(StringUtils.cnulls(obj));
	    }
	    return retMap;
	  }
	  
	  public static HashMap toMapByColl(Collection userOrgRangeList)
	  {
	    HashMap retMap = new HashMap();
	    if ((userOrgRangeList == null) || (userOrgRangeList.isEmpty()))
	    {
	      return retMap;
	    }
	    Iterator it = userOrgRangeList.iterator();
	    while (it.hasNext())
	    {
	      Object obj = it.next();
	      retMap.put(StringUtils.cnulls(obj), Boolean.FALSE);
	    }
	    return retMap;
	  }

	  public static String aryToStr(String[] idAry)
	  {
	    return aryToStr(idAry, true);
	  }

	  public static String aryToStr(String[] idAry, boolean needSeperate)
	  {
	    if ((idAry == null) || (idAry.length == 0))
	    {
	      return aryToStr(new ArrayList());
	    }
	    List retList = new ArrayList();
	    int i = 0; for (int size = idAry.length; i < size; i++)
	    {
	      retList.add(idAry[i]);
	    }
	    return aryToStr(retList, needSeperate);
	  }

	  public static String aryToStr(String[] idAry, int number)
	  {
	    if ((idAry == null) || (idAry.length == 0))
	    {
	      return aryToStr(new ArrayList());
	    }
	    List retList = new ArrayList();
	    int lastAryPos = number;
	    if (number >= idAry.length)
	    {
	      lastAryPos = idAry.length;
	    }

	    int i = 0; for (int size = lastAryPos; i < size; i++)
	    {
	      retList.add(idAry[i]);
	    }
	    return aryToStr(retList, false);
	  }

	  public static String aryToStr(Object[] idAry, boolean needSeperate)
	  {
	    if ((idAry == null) || (idAry.length == 0))
	    {
	      return aryToStr(new ArrayList());
	    }
	    List retList = new ArrayList();
	    int i = 0; for (int size = idAry.length; i < size; i++)
	    {
	      if (idAry[i] == null)
	        continue;
	      retList.add(idAry[i].toString());
	    }

	    return aryToStr(retList, needSeperate);
	  }

	  public static String aryToStr(Object[] idAry)
	  {
	    return aryToStr(idAry, true);
	  }

	  public static String aryToStr(Collection col)
	  {
	    if ((col == null) || (col.isEmpty())) return "''";
	    List valueList = toListByObjAry(col.toArray());
	    return aryToStr(valueList);
	  }

	  public static String aryToStr(Collection col, boolean addChar)
	  {
	    if ((col == null) || (col.isEmpty())) return "''";
	    List valueList = toListByObjAry(col.toArray());
	    return aryToStr(valueList, addChar);
	  }

	  public static String aryToStr(List idList, boolean addChar)
	  {
	    if ((idList == null) || (idList.isEmpty()))
	    {
	      return "''";
	    }
	    StringBuffer receivers = new StringBuffer();
	    Iterator iteUser = idList.iterator();

	    while (iteUser.hasNext())
	    {
	      Object obj = iteUser.next();
	      if (obj == null)
	        break;
	      if (addChar)
	      {
	        receivers.append("'").append(obj.toString()).append("',");
	      }
	      else
	      {
	        receivers.append(obj.toString()).append(",");
	      }

	    }

	    String users = receivers.toString();
	    if ((users != null) && (users.trim().length() != 0) && (users.endsWith(",")) && (users.trim().length() > 0))
	    {
	      users = users.substring(0, users.length() - 1);
	    }
	    return users;
	  }

	  public static String aryToStr(List idList)
	  {
	    return aryToStr(idList, true);
	  }



	  public static boolean addSysOrgId(List orgIdList)
	  {
	    if (orgIdList == null)
	    {
	      orgIdList = new ArrayList();
	    }
	    if (!orgIdList.contains("11111111-1111-1111-1111-111111111111CCE7AED4"))
	    {
	      orgIdList.add("11111111-1111-1111-1111-111111111111CCE7AED4");
	      return true;
	    }
	    return false;
	  }
	  public static List orgCol2List(FullOrgUnitCollection fullOrgCol)
	  {
	    List retList = new ArrayList();
	    if ((fullOrgCol == null) || (fullOrgCol.isEmpty())) return retList;
	    Set alreadySet = new HashSet();
	    int i = 0; for (int size = fullOrgCol.size(); i < size; i++)
	    {
	      String keyStr = fullOrgCol.get(i).getId().toString();
	      if (alreadySet.contains(keyStr))
	        continue;
	      retList.add(keyStr);
	      alreadySet.add(keyStr);
	    }

	    return retList;
	  }
	  public static List transCol2ListByPeroperty(List iObjectCol, IObjectCol2List iObjectList)
	  {
	    if ((iObjectCol == null) || (iObjectCol.isEmpty())) return new ArrayList();
	    if (iObjectList == null)
	    {
	      throw new IllegalArgumentException("IObjectCol2List must be implement, is not is null");
	    }
	    List idStrList = new ArrayList();
	    int i = 0; for (int size = iObjectCol.size(); i < size; i++)
	    {
	      Object obj = iObjectCol.get(i);
	      String value = iObjectList.getList(obj);
	      if (StringUtils.isEmpty(value))
	        continue;
	      idStrList.add(value);
	    }

	    return idStrList;
	  }

	  public static Map transCol2ListByPeroperty(List iObjectCol, IObjectCol2Map iObjectMap)
	  {
	    if ((iObjectCol == null) || (iObjectCol.isEmpty())) return new HashMap();
	    if (iObjectMap == null)
	    {
	      throw new IllegalArgumentException("IObjectCol2List must be implement, is not is null");
	    }
	    Map myMap = new HashMap();
	    int i = 0; for (int size = iObjectCol.size(); i < size; i++)
	    {
	      Object obj = iObjectCol.get(i);
	      String keyStr = iObjectMap.getKey(obj);
	      Object value = iObjectMap.getList(obj);
	      if ((StringUtils.isEmpty(keyStr)) || (value == null))
	        continue;
	      myMap.put(keyStr, value);
	    }

	    return myMap;
	  }

	  public static Map transCol2ListByPeroperty(IObjectCollection iObjectCol, IObjectCol2Map iObjectMap)
	  {
	    if ((iObjectCol == null) || (iObjectCol.isEmpty())) return new HashMap();
	    if (iObjectMap == null)
	    {
	      throw new IllegalArgumentException("IObjectCol2List must be implement, is not is null");
	    }
	    Map myMap = new HashMap();
	    int i = 0; for (int size = iObjectCol.size(); i < size; i++)
	    {
	      Object obj = iObjectCol.getObject(i);
	      String keyStr = iObjectMap.getKey(obj);
	      Object value = iObjectMap.getList(obj);
	      if ((StringUtils.isEmpty(keyStr)) || (value == null))
	        continue;
	      myMap.put(keyStr, value);
	    }

	    return myMap;
	  }

	  public static List transCol2ListByPeroperty(IObjectCollection iObjectCol, IObjectCol2List iObjectList)
	  {
	    if ((iObjectCol == null) || (iObjectCol.isEmpty())) return new ArrayList();
	    if (iObjectList == null)
	    {
	      throw new IllegalArgumentException("IObjectCol2List must be implement, is not is null");
	    }
	    List idStrList = new ArrayList();
	    int i = 0; for (int size = iObjectCol.size(); i < size; i++)
	    {
	      Object obj = iObjectCol.getObject(i);
	      String value = iObjectList.getList(obj);
	      if (StringUtils.isEmpty(value))
	        continue;
	      idStrList.add(value);
	    }

	    return idStrList;
	  }

	  public static FullOrgUnitInfo[] transFromOrgUnitInfo(OrgUnitInfo[] orgUnitInfo)
	  {
	    if ((orgUnitInfo == null) || (orgUnitInfo.length == 0)) return null;
	    int size = orgUnitInfo.length;
	    FullOrgUnitInfo[] fullUnitInfo = new FullOrgUnitInfo[size];
	    for (int i = 0; i < size; i++)
	    {
	      fullUnitInfo[i] = orgUnitInfo[i].castToFullOrgUnitInfo();
	    }
	    return fullUnitInfo;
	  }

	  public static Collection filterSameObj(Collection col)
	  {
	    if ((col == null) || (col.size() == 0)) return new ArrayList();
	    Iterator it = col.iterator();
	    Set alreadySet = new HashSet();
	    List retList = new ArrayList();
	    while (it.hasNext())
	    {
	      Object obj = it.next();
	      if (!alreadySet.contains(obj))
	      {
	        retList.add(obj);
	        alreadySet.add(obj);
	      }
	    }
	    return retList;
	  }

	  public static FullOrgUnitCollection filterSameOrg(FullOrgUnitCollection col)
	  {
	    if ((col == null) || (col.isEmpty())) return new FullOrgUnitCollection();
	    Set alreadySet = new HashSet();
	    FullOrgUnitCollection retCol = new FullOrgUnitCollection();
	    int i = 0; for (int size = col.size(); i < size; i++)
	    {
	      String keyStr = StringUtils.cnulls(col.get(i).getId());
	      if (alreadySet.contains(keyStr))
	        continue;
	      retCol.add(col.get(i));
	      alreadySet.add(keyStr);
	    }

	    return retCol;
	  }

	  
}
