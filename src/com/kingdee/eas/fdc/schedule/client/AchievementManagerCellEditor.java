package com.kingdee.eas.fdc.schedule.client;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JComponent;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.IKDComponent;
import com.kingdee.bos.ctrl.swing.IKDEditor;
import com.kingdee.bos.ctrl.swing.KDLabel;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;

/**
 * 阶段性成果编辑器
 * <p>
 * 用于编辑单元格中的List
 * 
 * @author emanon
 * 
 */
public class AchievementManagerCellEditor extends JComponent implements
		IKDComponent, IKDEditor {

	public static Color COLOR_RED = new Color(220, 190, 190);
	public static Color COLOR_GREEN = new Color(200, 250, 200);
	public static Color COLOR_YELLOW = new Color(200, 200, 100);
	public static Color COLOR_GRAY = new Color(200, 200, 200);

	// 单元格list值
	private List value;
	// 自定义变量
	private Object userObject;
	// 当前单元格最大行数
	private int maxSize = 1;
	// 父ui
	private AchievementManagerListUI ui;

	public AchievementManagerCellEditor(AchievementManagerListUI ui) {
		super();
		this.ui = ui;
		createUI();
	}

	protected void createUI() {
		initUI();
	}

	private void initUI() {
		removeAll();
		if (value != null) {
			setLayout(new GridLayout(maxSize, 1));
			for (int i = 0; i < value.size(); i++) {
				Map cellData = (Map) value.get(i);
				String id = (String) cellData.get("id");
				String name = (String) cellData.get("name");
				String state = (String) cellData.get("state");
				KDLabel lb = new KDLabel(name);
				if (id.equals(ui.getSelectedID())) {
					lb.setBorder(BorderFactory.createLineBorder(Color.RED));
				} else {
					lb.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				}
				lb.setOpaque(true);
				lb.setUserObject(id);
				// 灰色底色（还未提交的成果）
				if (FDCBillStateEnum.SAVED_VALUE.equals(state)) {
					lb.setBackground(COLOR_GRAY);
					// 黄色底色（审批中的成果）
				} else if (FDCBillStateEnum.SUBMITTED_VALUE.equals(state)
						|| FDCBillStateEnum.AUDITTING_VALUE.equals(state)) {
					lb.setBackground(COLOR_YELLOW);
					// 绿色底色（审批后通过的成果）
				} else if (FDCBillStateEnum.AUDITTED_VALUE.equals(state)) {
					lb.setBackground(COLOR_GREEN);
				}
				lb.addMouseListener(new MouseListener() {
					public void mouseClicked(MouseEvent e) {
						if (e.getClickCount() == 1) {
							// 选中某个
							// 得到容器
							KDLabel compt = (KDLabel) e.getComponent();
							// 得到容器内装的对象Id
							String id = (String) compt.getUserObject();
							ui.setSelectedID(id);
						} else if (e.getClickCount() == 2) {
							// 打开选中的成果
							try {
								actionViewCellData_ActionPerformed(e);
							} catch (BOSException e1) {
								ui.handUIException(e1);
							}
						}
					}

					public void mouseEntered(MouseEvent e) {
						KDLabel compt = (KDLabel) e.getComponent();
						compt.setBorder(BorderFactory
								.createLineBorder(Color.RED));
					}

					public void mouseExited(MouseEvent e) {
						KDLabel compt = (KDLabel) e.getComponent();
						compt.setBorder(BorderFactory
								.createLineBorder(Color.BLACK));
					}

					public void mousePressed(MouseEvent e) {
					}

					public void mouseReleased(MouseEvent e) {
					}

				});
				add(lb);
			}
		}
	}

	public void actionViewCellData_ActionPerformed(MouseEvent e)
			throws BOSException {
		// 得到容器
		KDLabel compt = (KDLabel) e.getComponent();
		// 得到容器内装的对象Id
		String id = (String) compt.getUserObject();
		// MsgBox.showInfo(id);
		if (id != null) {
			IUIFactory uiFactory = null;
			try {
				uiFactory = UIFactory
						.createUIFactory("com.kingdee.eas.base.uiframe.client.UIModelDialogFactory");
				UIContext uicontext = new UIContext(this);
				uicontext.put(UIContext.ID, id);
				IUIWindow uiwindow = uiFactory.create(
						AchievementManagerEditUI.class.getName(), uicontext,
						null, OprtState.VIEW);
				uiwindow.show();
			} catch (UIException e1) {
				e1.printStackTrace();
			}
		}
		// ui.fillTable();
	}

	public void addDataChangeListener(DataChangeListener datachangelistener) {
		// TODO Auto-generated method stub

	}

	public Object getValue() {
		return value;
	}

	public boolean isDisplay() {
		return true;
	}

	public void removeDataChangeListener(DataChangeListener datachangelistener) {
		// TODO Auto-generated method stub

	}

	public void setDisplay(boolean flag) {
		// TODO Auto-generated method stub

	}

	public void setValue(Object obj) {
		if (obj instanceof List) {
			value = (List) obj;
		} else {
			value = null;
		}
		initUI();
	}

	public Object getUserObject() {
		return userObject;
	}

	public void setUserObject(Object obj) {
		userObject = obj;
	}

	/**
	 * 取得当前单元格最大行数
	 * 
	 * @description
	 * @author 李兵
	 * @createDate 2011-8-27
	 * @param maxSize
	 *            void
	 * @version EAS7.0
	 * @see
	 */
	public void setMaxSize(int maxSize) {
		if (maxSize > 0) {
			this.maxSize = maxSize;
		} else {
			this.maxSize = 1;
		}
	}

	public int getMaxSize() {
		return maxSize;
	}

}
