/**
 * Created by 17657 on 2019/7/31.
 */
Ext.onReady(function() {
    var store=Ext.create('Ext.data.Store', {
        storeId:'employeeStore',
        fields:['firstname', 'lastname', 'seniority', 'dep', 'hired'],
        data:[
            {firstname:"Michael", lastname:"Scott", seniority:7, dep:"Management", hired:"01/10/2004"},
            {firstname:"Dwight", lastname:"Schrute", seniority:2, dep:"Sales", hired:"04/01/2004"},
            {firstname:"Jim", lastname:"Halpert", seniority:3, dep:"Sales", hired:"02/22/2006"},
            {firstname:"Kevin", lastname:"Malone", seniority:4, dep:"Accounting", hired:"06/10/2007"},
            {firstname:"Angela", lastname:"Martin", seniority:5, dep:"Accounting", hired:"10/21/2008"}
        ]
    });
    store.load();

    var mainGrid = Ext.create('Ext.vcf.EditorTableGrid', {
        id: 'mainGrid',
        title: '收入项目列表',
        autoHeight:true,
        store:store,
        isPaged : false,
        tbar: Ext.create('Ext.toolbar.Toolbar', {
            items: [{
                text: '新增项目',
                iconCls: 'page_addIcon',
                handler:function(){
                    var p ={
                        firstname:'',
                        lastname:'',
                        seniority:'',
                        dep:'',
                        hired:''
                    };
                    store.insert(store.getCount(),p);
                }

            }, '-', {
                text: '删除项目',
                iconCls: 'page_edit_1Icon',
                handler:function(){
                    Ext.Msg.confirm('系统提示','确定要删除选择的数据？',function(btn){
                        if(btn=='yes'){
                            var sm = mainGrid.getSelectionModel();
                            var record = sm.getSelection()[0];
                            store.remove(record);
                        }
                    });
                }
            }]
        }),
        columns: [
            {text: 'First Name',  dataIndex:'firstname',width:400, editor: {
                xtype:'treefield',source:'ROLE',isPermission : false,isFullLevel:false,
                listeners:{
                    'select':function (tree,node) {
                        var sdata = node.data;
                        var selection = mainGrid.getSelection();
                        var model = selection[0];
                        var record = model.data;
                        record["firstname"] = node.id;
                        record["lastname"] = sdata.code;
                        store.load();
                    }}},renderer: ROLEIdRender},
            {text: 'Last Name',  dataIndex:'lastname',width:200,editor:{allowBlank:true}},
            {text: 'Hired Month',  dataIndex:'hired',width:150, xtype:'datecolumn', format:'M'},
            {text: 'Department (Yrs)', xtype:'templatecolumn',width:150, tpl:'{dep} ({seniority})'}
        ],
        listeners:{
            cellclick: function(view, td, cellIndex, record, tr, rowIndex, e, eOpts) {
                // Ext.Msg.alert('Selected Record', 'Name : ' + record.get('id') + ' ' + record.get('name'));
                if(cellIndex == 1) { // cellIndex starts from 0
                     //Ext.Msg.alert('Selected Record', 'Name : ' + record.get('firstname') + ' ' + record.get('lastname'));
                }
            }
        },
        region: 'center',
        renderTo: 'gridDiv'
    })

});