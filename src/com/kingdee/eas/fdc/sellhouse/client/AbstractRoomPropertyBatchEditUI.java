/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import org.apache.log4j.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.border.*;
import javax.swing.BorderFactory;
import javax.swing.event.*;
import javax.swing.KeyStroke;

import com.kingdee.bos.ctrl.swing.*;
import com.kingdee.bos.ctrl.kdf.table.*;
import com.kingdee.bos.ctrl.kdf.data.event.*;
import com.kingdee.bos.dao.*;
import com.kingdee.bos.dao.query.*;
import com.kingdee.bos.metadata.*;
import com.kingdee.bos.metadata.entity.*;
import com.kingdee.bos.ui.face.*;
import com.kingdee.bos.ui.util.ResourceBundleHelper;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.enums.EnumUtils;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.ctrl.swing.event.*;
import com.kingdee.bos.ctrl.kdf.table.event.*;
import com.kingdee.bos.ctrl.extendcontrols.*;
import com.kingdee.bos.ctrl.kdf.util.render.*;
import com.kingdee.bos.ui.face.IItemAction;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.bos.ui.util.IUIActionPostman;
import com.kingdee.bos.appframework.client.servicebinding.ActionProxyFactory;
import com.kingdee.bos.appframework.uistatemanage.ActionStateConst;
import com.kingdee.bos.appframework.validator.ValidateHelper;
import com.kingdee.bos.appframework.uip.UINavigator;


/**
 * output class name
 */
public abstract class AbstractRoomPropertyBatchEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractRoomPropertyBatchEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPropertyState;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTransactor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contScheme;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contUpdateDate;
    protected com.kingdee.bos.ctrl.swing.KDContainer kdConBooks;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox ckIsFinished;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSellProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCurStep;
    protected com.kingdee.bos.ctrl.swing.KDContainer contData;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDButton btnRoomSelect;
    protected com.kingdee.bos.ctrl.swing.KDButton btnRoomDelete;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contf7AddRoom;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtBatchManage;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboPropertyState;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtTransactor;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtScheme;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkUpdateDate;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtBook;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtSellProject;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboCurStep;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox ckBatchUpdateData;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblData;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7RoomSelect;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddRoom;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRemoveRoom;
    protected com.kingdee.eas.fdc.sellhouse.RoomPropertyBookInfo editData = null;
    protected ActionAddRoom actionAddRoom = null;
    protected ActionRemoveRoom actionRemoveRoom = null;
    /**
     * output class constructor
     */
    public AbstractRoomPropertyBatchEditUI() throws Exception
    {
        super();
        this.defaultObjectName = "editData";
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractRoomPropertyBatchEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionAddRoom
        this.actionAddRoom = new ActionAddRoom(this);
        getActionManager().registerAction("actionAddRoom", actionAddRoom);
         this.actionAddRoom.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionRemoveRoom
        this.actionRemoveRoom = new ActionRemoveRoom(this);
        getActionManager().registerAction("actionRemoveRoom", actionRemoveRoom);
         this.actionRemoveRoom.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPropertyState = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTransactor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contScheme = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contUpdateDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kdConBooks = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.ckIsFinished = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contSellProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCurStep = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contData = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnRoomSelect = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnRoomDelete = new com.kingdee.bos.ctrl.swing.KDButton();
        this.contf7AddRoom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtBatchManage = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.comboPropertyState = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.prmtTransactor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtScheme = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkUpdateDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kdtBook = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.prmtSellProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.comboCurStep = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.ckBatchUpdateData = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.tblData = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.f7RoomSelect = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.btnAddRoom = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnRemoveRoom = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contNumber.setName("contNumber");
        this.contPropertyState.setName("contPropertyState");
        this.contTransactor.setName("contTransactor");
        this.contScheme.setName("contScheme");
        this.contUpdateDate.setName("contUpdateDate");
        this.kdConBooks.setName("kdConBooks");
        this.ckIsFinished.setName("ckIsFinished");
        this.contSellProject.setName("contSellProject");
        this.contCurStep.setName("contCurStep");
        this.contData.setName("contData");
        this.contCreator.setName("contCreator");
        this.contCreateTime.setName("contCreateTime");
        this.btnRoomSelect.setName("btnRoomSelect");
        this.btnRoomDelete.setName("btnRoomDelete");
        this.contf7AddRoom.setName("contf7AddRoom");
        this.prmtBatchManage.setName("prmtBatchManage");
        this.comboPropertyState.setName("comboPropertyState");
        this.prmtTransactor.setName("prmtTransactor");
        this.prmtScheme.setName("prmtScheme");
        this.pkUpdateDate.setName("pkUpdateDate");
        this.kdtBook.setName("kdtBook");
        this.prmtSellProject.setName("prmtSellProject");
        this.comboCurStep.setName("comboCurStep");
        this.ckBatchUpdateData.setName("ckBatchUpdateData");
        this.tblData.setName("tblData");
        this.prmtCreator.setName("prmtCreator");
        this.pkCreateTime.setName("pkCreateTime");
        this.f7RoomSelect.setName("f7RoomSelect");
        this.btnAddRoom.setName("btnAddRoom");
        this.btnRemoveRoom.setName("btnRemoveRoom");
        // CoreUI		
        this.setEnabled(false);
        // contNumber		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));
        // contPropertyState		
        this.contPropertyState.setBoundLabelText(resHelper.getString("contPropertyState.boundLabelText"));		
        this.contPropertyState.setBoundLabelLength(100);		
        this.contPropertyState.setBoundLabelUnderline(true);
        // contTransactor		
        this.contTransactor.setBoundLabelText(resHelper.getString("contTransactor.boundLabelText"));		
        this.contTransactor.setBoundLabelLength(100);		
        this.contTransactor.setBoundLabelUnderline(true);
        // contScheme		
        this.contScheme.setBoundLabelText(resHelper.getString("contScheme.boundLabelText"));		
        this.contScheme.setBoundLabelLength(100);		
        this.contScheme.setBoundLabelUnderline(true);
        // contUpdateDate		
        this.contUpdateDate.setBoundLabelText(resHelper.getString("contUpdateDate.boundLabelText"));		
        this.contUpdateDate.setBoundLabelLength(100);		
        this.contUpdateDate.setBoundLabelUnderline(true);		
        this.contUpdateDate.setEnabled(false);
        // kdConBooks		
        this.kdConBooks.setTitle(resHelper.getString("kdConBooks.title"));		
        this.kdConBooks.setEnableActive(false);
        // ckIsFinished		
        this.ckIsFinished.setText(resHelper.getString("ckIsFinished.text"));
        this.ckIsFinished.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    ckIsFinished_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // contSellProject		
        this.contSellProject.setBoundLabelText(resHelper.getString("contSellProject.boundLabelText"));		
        this.contSellProject.setBoundLabelLength(100);		
        this.contSellProject.setBoundLabelUnderline(true);		
        this.contSellProject.setEnabled(false);
        // contCurStep		
        this.contCurStep.setBoundLabelText(resHelper.getString("contCurStep.boundLabelText"));		
        this.contCurStep.setBoundLabelLength(100);		
        this.contCurStep.setBoundLabelUnderline(true);
        // contData		
        this.contData.setTitle(resHelper.getString("contData.title"));
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);		
        this.contCreator.setEnabled(false);
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(100);		
        this.contCreateTime.setBoundLabelUnderline(true);		
        this.contCreateTime.setEnabled(false);
        // btnRoomSelect		
        this.btnRoomSelect.setText(resHelper.getString("btnRoomSelect.text"));
        this.btnRoomSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnRoomSelect_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnRoomDelete		
        this.btnRoomDelete.setText(resHelper.getString("btnRoomDelete.text"));
        this.btnRoomDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnRoomDelete_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // contf7AddRoom		
        this.contf7AddRoom.setBoundLabelText(resHelper.getString("contf7AddRoom.boundLabelText"));		
        this.contf7AddRoom.setBoundLabelLength(100);		
        this.contf7AddRoom.setBoundLabelUnderline(true);		
        this.contf7AddRoom.setVisible(false);
        // prmtBatchManage		
        this.prmtBatchManage.setRequired(true);		
        this.prmtBatchManage.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.BatchManageQuery");		
        this.prmtBatchManage.setDisplayFormat("$number$");
        this.prmtBatchManage.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtBatchManage_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // comboPropertyState		
        this.comboPropertyState.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.PropertyStateEnum").toArray());
        // prmtTransactor		
        this.prmtTransactor.setRequired(true);		
        this.prmtTransactor.setQueryInfo("com.kingdee.eas.base.permission.app.F7UserQuery");		
        this.prmtTransactor.setCommitFormat("$name$");		
        this.prmtTransactor.setDisplayFormat("$name$");		
        this.prmtTransactor.setEditFormat("$name$");
        // prmtScheme		
        this.prmtScheme.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.F7PropertyDoSchemeQuery");		
        this.prmtScheme.setDisplayFormat("$name$");		
        this.prmtScheme.setEditFormat("$name$");		
        this.prmtScheme.setRequired(true);		
        this.prmtScheme.setCommitFormat("$name$");
        this.prmtScheme.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtScheme_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // pkUpdateDate		
        this.pkUpdateDate.setEnabled(false);
        // kdtBook
		String kdtBookStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol6\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style><c:Style id=\"sCol8\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol9\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"room\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"customer\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"customerPhone\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"contractNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"contractNo\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"signDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" t:styleID=\"sCol6\" /><t:Column t:key=\"propertNum\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"roomId\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" t:styleID=\"sCol8\" /><t:Column t:key=\"bookId\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol9\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{room}</t:Cell><t:Cell>$Resource{customer}</t:Cell><t:Cell>$Resource{customerPhone}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{contractNumber}</t:Cell><t:Cell>$Resource{contractNo}</t:Cell><t:Cell>$Resource{signDate}</t:Cell><t:Cell>$Resource{propertNum}</t:Cell><t:Cell>$Resource{roomId}</t:Cell><t:Cell>$Resource{bookId}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtBook.setFormatXml(resHelper.translateString("kdtBook",kdtBookStrXML));
        this.kdtBook.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    kdtBook_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.kdtBook.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtBook_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        // prmtSellProject		
        this.prmtSellProject.setEnabled(false);
        // comboCurStep		
        this.comboCurStep.setRequired(true);
        this.comboCurStep.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    comboCurStep_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // ckBatchUpdateData		
        this.ckBatchUpdateData.setText(resHelper.getString("ckBatchUpdateData.text"));
        this.ckBatchUpdateData.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    ckBatchUpdateData_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // tblData
		String tblDataStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"isFinish\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"processDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"remark\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{isFinish}</t:Cell><t:Cell>$Resource{processDate}</t:Cell><t:Cell>$Resource{remark}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblData.setFormatXml(resHelper.translateString("tblData",tblDataStrXML));
        this.tblData.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblData_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        // prmtCreator		
        this.prmtCreator.setEnabled(false);
        // pkCreateTime
        // f7RoomSelect		
        this.f7RoomSelect.setVisible(false);
        this.f7RoomSelect.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    f7RoomSelect_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // btnAddRoom
        this.btnAddRoom.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddRoom, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAddRoom.setText(resHelper.getString("btnAddRoom.text"));		
        this.btnAddRoom.setToolTipText(resHelper.getString("btnAddRoom.toolTipText"));		
        this.btnAddRoom.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_addline"));		
        this.btnAddRoom.setEnabled(false);		
        this.btnAddRoom.setVisible(false);
        // btnRemoveRoom
        this.btnRemoveRoom.setAction((IItemAction)ActionProxyFactory.getProxy(actionRemoveRoom, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnRemoveRoom.setText(resHelper.getString("btnRemoveRoom.text"));		
        this.btnRemoveRoom.setToolTipText(resHelper.getString("btnRemoveRoom.toolTipText"));		
        this.btnRemoveRoom.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_deleteline"));		
        this.btnRemoveRoom.setEnabled(false);		
        this.btnRemoveRoom.setVisible(false);
		//Register control's property binding
		registerBindings();
		registerUIState();


    }

	public com.kingdee.bos.ctrl.swing.KDToolBar[] getUIMultiToolBar(){
		java.util.List list = new java.util.ArrayList();
		com.kingdee.bos.ctrl.swing.KDToolBar[] bars = super.getUIMultiToolBar();
		if (bars != null) {
			list.addAll(java.util.Arrays.asList(bars));
		}
		return (com.kingdee.bos.ctrl.swing.KDToolBar[])list.toArray(new com.kingdee.bos.ctrl.swing.KDToolBar[list.size()]);
	}




    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(10, 10, 1000, 700));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1000, 700));
        contNumber.setBounds(new Rectangle(365, 10, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(365, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contPropertyState.setBounds(new Rectangle(10, 70, 270, 19));
        this.add(contPropertyState, new KDLayout.Constraints(10, 70, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contTransactor.setBounds(new Rectangle(720, 10, 270, 19));
        this.add(contTransactor, new KDLayout.Constraints(720, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contScheme.setBounds(new Rectangle(10, 40, 270, 19));
        this.add(contScheme, new KDLayout.Constraints(10, 40, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contUpdateDate.setBounds(new Rectangle(720, 40, 270, 19));
        this.add(contUpdateDate, new KDLayout.Constraints(720, 40, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kdConBooks.setBounds(new Rectangle(10, 121, 980, 242));
        this.add(kdConBooks, new KDLayout.Constraints(10, 121, 980, 242, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        ckIsFinished.setBounds(new Rectangle(365, 70, 270, 19));
        this.add(ckIsFinished, new KDLayout.Constraints(365, 70, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contSellProject.setBounds(new Rectangle(10, 10, 270, 19));
        this.add(contSellProject, new KDLayout.Constraints(10, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCurStep.setBounds(new Rectangle(365, 40, 270, 19));
        this.add(contCurStep, new KDLayout.Constraints(365, 40, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contData.setBounds(new Rectangle(10, 369, 973, 269));
        this.add(contData, new KDLayout.Constraints(10, 369, 973, 269, 0));
        contCreator.setBounds(new Rectangle(13, 657, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(13, 657, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateTime.setBounds(new Rectangle(368, 657, 270, 19));
        this.add(contCreateTime, new KDLayout.Constraints(368, 657, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnRoomSelect.setBounds(new Rectangle(792, 96, 82, 21));
        this.add(btnRoomSelect, new KDLayout.Constraints(792, 96, 82, 21, 0));
        btnRoomDelete.setBounds(new Rectangle(880, 96, 82, 21));
        this.add(btnRoomDelete, new KDLayout.Constraints(880, 96, 82, 21, 0));
        contf7AddRoom.setBounds(new Rectangle(515, 97, 270, 19));
        this.add(contf7AddRoom, new KDLayout.Constraints(515, 97, 270, 19, 0));
        //contNumber
        contNumber.setBoundEditor(prmtBatchManage);
        //contPropertyState
        contPropertyState.setBoundEditor(comboPropertyState);
        //contTransactor
        contTransactor.setBoundEditor(prmtTransactor);
        //contScheme
        contScheme.setBoundEditor(prmtScheme);
        //contUpdateDate
        contUpdateDate.setBoundEditor(pkUpdateDate);
        //kdConBooks
        kdConBooks.getContentPane().setLayout(new KDLayout());
        kdConBooks.getContentPane().putClientProperty("OriginalBounds", new Rectangle(10, 121, 980, 242));        kdtBook.setBounds(new Rectangle(10, 10, 960, 206));
        kdConBooks.getContentPane().add(kdtBook, new KDLayout.Constraints(10, 10, 960, 206, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contSellProject
        contSellProject.setBoundEditor(prmtSellProject);
        //contCurStep
        contCurStep.setBoundEditor(comboCurStep);
        //contData
        contData.getContentPane().setLayout(new KDLayout());
        contData.getContentPane().putClientProperty("OriginalBounds", new Rectangle(10, 369, 973, 269));        ckBatchUpdateData.setBounds(new Rectangle(15, 7, 140, 19));
        contData.getContentPane().add(ckBatchUpdateData, new KDLayout.Constraints(15, 7, 140, 19, 0));
        tblData.setBounds(new Rectangle(10, 29, 953, 210));
        contData.getContentPane().add(tblData, new KDLayout.Constraints(10, 29, 953, 210, 0));
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contCreateTime
        contCreateTime.setBoundEditor(pkCreateTime);
        //contf7AddRoom
        contf7AddRoom.setBoundEditor(f7RoomSelect);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuEdit);
        this.menuBar.add(MenuService);
        this.menuBar.add(menuView);
        this.menuBar.add(menuBiz);
        this.menuBar.add(menuTable1);
        this.menuBar.add(menuTool);
        this.menuBar.add(menuWorkflow);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemAddNew);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemSave);
        menuFile.add(menuItemSubmit);
        menuFile.add(menuSubmitOption);
        menuFile.add(rMenuItemSubmit);
        menuFile.add(rMenuItemSubmitAndAddNew);
        menuFile.add(rMenuItemSubmitAndPrint);
        menuFile.add(separatorFile1);
        menuFile.add(MenuItemAttachment);
        menuFile.add(kDSeparator2);
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemPrintPreview);
        menuFile.add(kDSeparator6);
        menuFile.add(menuItemSendMail);
        menuFile.add(kDSeparator3);
        menuFile.add(menuItemExitCurrent);
        //menuSubmitOption
        menuSubmitOption.add(chkMenuItemSubmitAndAddNew);
        menuSubmitOption.add(chkMenuItemSubmitAndPrint);
        //menuEdit
        menuEdit.add(menuItemCopy);
        menuEdit.add(menuItemEdit);
        menuEdit.add(menuItemRemove);
        menuEdit.add(kDSeparator4);
        menuEdit.add(menuItemReset);
        menuEdit.add(separator1);
        menuEdit.add(menuItemCreateFrom);
        menuEdit.add(menuItemCreateTo);
        menuEdit.add(menuItemCopyFrom);
        menuEdit.add(separatorEdit1);
        menuEdit.add(menuItemEnterToNextRow);
        menuEdit.add(separator2);
        //MenuService
        MenuService.add(MenuItemKnowStore);
        MenuService.add(MenuItemAnwser);
        MenuService.add(SepratorService);
        MenuService.add(MenuItemRemoteAssist);
        //menuView
        menuView.add(menuItemFirst);
        menuView.add(menuItemPre);
        menuView.add(menuItemNext);
        menuView.add(menuItemLast);
        menuView.add(separator3);
        menuView.add(menuItemTraceUp);
        menuView.add(menuItemTraceDown);
        menuView.add(kDSeparator7);
        menuView.add(menuItemLocate);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(MenuItemVoucher);
        menuBiz.add(menuItemDelVoucher);
        menuBiz.add(menuItemAudit);
        menuBiz.add(menuItemUnAudit);
        //menuTable1
        menuTable1.add(menuItemAddLine);
        menuTable1.add(menuItemCopyLine);
        menuTable1.add(menuItemInsertLine);
        menuTable1.add(menuItemRemoveLine);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemMsgFormat);
        menuTool.add(menuItemCalculator);
        //menuWorkflow
        menuWorkflow.add(menuItemStartWorkFlow);
        menuWorkflow.add(separatorWF1);
        menuWorkflow.add(menuItemViewSubmitProccess);
        menuWorkflow.add(menuItemViewDoProccess);
        menuWorkflow.add(MenuItemWFG);
        menuWorkflow.add(menuItemWorkFlowList);
        menuWorkflow.add(separatorWF2);
        menuWorkflow.add(menuItemMultiapprove);
        menuWorkflow.add(menuItemNextPerson);
        menuWorkflow.add(menuItemAuditResult);
        menuWorkflow.add(kDSeparator5);
        menuWorkflow.add(kDMenuItemSendMessage);
        //menuHelp
        menuHelp.add(menuItemHelp);
        menuHelp.add(kDSeparator12);
        menuHelp.add(menuItemRegPro);
        menuHelp.add(menuItemPersonalSite);
        menuHelp.add(helpseparatorDiv);
        menuHelp.add(menuitemProductval);
        menuHelp.add(kDSeparatorProduct);
        menuHelp.add(menuItemAbout);

    }

    /**
     * output initUIToolBarLayout method
     */
    public void initUIToolBarLayout()
    {
        this.toolBar.add(btnAddNew);
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnSave);
        this.toolBar.add(btnReset);
        this.toolBar.add(btnSubmit);
        this.toolBar.add(btnCopy);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnFirst);
        this.toolBar.add(btnPre);
        this.toolBar.add(btnNext);
        this.toolBar.add(btnLast);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnTraceUp);
        this.toolBar.add(btnTraceDown);
        this.toolBar.add(btnWorkFlowG);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(btnSignature);
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(btnAddLine);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnCopyLine);
        this.toolBar.add(btnInsertLine);
        this.toolBar.add(btnRemoveLine);
        this.toolBar.add(separatorFW6);
        this.toolBar.add(separatorFW9);
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(btnWFViewSubmitProccess);
        this.toolBar.add(btnNextPerson);
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnUnAudit);
        this.toolBar.add(btnCalculator);
        this.toolBar.add(btnAddRoom);
        this.toolBar.add(btnRemoveRoom);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.RoomPropertyBatchEditUIHandler";
	}
	public IUIActionPostman prepareInit() {
		IUIActionPostman clientHanlder = super.prepareInit();
		if (clientHanlder != null) {
			RequestContext request = new RequestContext();
    		request.setClassName(getUIHandlerClassName());
			clientHanlder.setRequestContext(request);
		}
		return clientHanlder;
    }
	
	public boolean isPrepareInit() {
    	return false;
    }
    protected void initUIP() {
        super.initUIP();
    }



	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.fdc.sellhouse.RoomPropertyBookInfo)ov;
    }

    /**
     * output loadFields method
     */
    public void loadFields()
    {
        dataBinder.loadFields();
    }
    /**
     * output storeFields method
     */
    public void storeFields()
    {
		dataBinder.storeFields();
    }

	/**
	 * ????????§µ??
	 */
	protected void registerValidator() {
    	getValidateHelper().setCustomValidator( getValidator() );		
	}



    /**
     * output setOprtState method
     */
    public void setOprtState(String oprtType)
    {
        super.setOprtState(oprtType);
        if (STATUS_ADDNEW.equals(this.oprtState)) {
        } else if (STATUS_EDIT.equals(this.oprtState)) {
        } else if (STATUS_VIEW.equals(this.oprtState)) {
        } else if (STATUS_FINDVIEW.equals(this.oprtState)) {
        }
    }

    /**
     * output ckIsFinished_itemStateChanged method
     */
    protected void ckIsFinished_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output btnRoomSelect_actionPerformed method
     */
    protected void btnRoomSelect_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnRoomDelete_actionPerformed method
     */
    protected void btnRoomDelete_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output prmtBatchManage_dataChanged method
     */
    protected void prmtBatchManage_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtScheme_dataChanged method
     */
    protected void prmtScheme_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output kdtBook_editStopped method
     */
    protected void kdtBook_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output kdtBook_tableClicked method
     */
    protected void kdtBook_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output comboCurStep_itemStateChanged method
     */
    protected void comboCurStep_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output ckBatchUpdateData_itemStateChanged method
     */
    protected void ckBatchUpdateData_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output tblData_editStopped method
     */
    protected void tblData_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output f7RoomSelect_dataChanged method
     */
    protected void f7RoomSelect_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        return sic;
    }        
    	

    /**
     * output actionAddRoom_actionPerformed method
     */
    public void actionAddRoom_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionRemoveRoom_actionPerformed method
     */
    public void actionRemoveRoom_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionAddRoom(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAddRoom() {
    	return false;
    }
	public RequestContext prepareActionRemoveRoom(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRemoveRoom() {
    	return false;
    }

    /**
     * output ActionAddRoom class
     */     
    protected class ActionAddRoom extends ItemAction {     
    
        public ActionAddRoom()
        {
            this(null);
        }

        public ActionAddRoom(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAddRoom.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddRoom.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddRoom.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRoomPropertyBatchEditUI.this, "ActionAddRoom", "actionAddRoom_actionPerformed", e);
        }
    }

    /**
     * output ActionRemoveRoom class
     */     
    protected class ActionRemoveRoom extends ItemAction {     
    
        public ActionRemoveRoom()
        {
            this(null);
        }

        public ActionRemoveRoom(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionRemoveRoom.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRemoveRoom.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRemoveRoom.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRoomPropertyBatchEditUI.this, "ActionRemoveRoom", "actionRemoveRoom_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "RoomPropertyBatchEditUI");
    }




}