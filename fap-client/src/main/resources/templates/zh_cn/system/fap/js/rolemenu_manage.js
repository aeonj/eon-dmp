function pageLoad() {
    var treeRole = Ext.create('Ext.vcf.AssistTree',{
        source: 'ROLE',
        renderTo : 'roleDiv'
    });
    var selroleid = '-1';
    treeRole.on('itemclick', function(tree,node) {
        selmenuid = '-1';
        if (node.get('id') == '0') {
            selroleid = '-1';
            return;
        } else {
            selroleid = node.id;
        }
        treeMenu.setParams({role_id: selroleid});
    });
    var treeMenu = Ext.create('Ext.vcf.AssistTree',{
        url : 'rolemenu_menu_tree.htm',
        params : {role_id : '-1'},
        renderTo : 'menuTreeDiv'
    });
    var selmenuid = '-1';
    var int=0;
    treeMenu.on('itemclick', function(tree,node) {
        if (node.get('id') == '0') {
            selmenuid = '-1';
            return;
        } else {
            if (node.get('type')=='1') {
                selmenuid = '-1';
                return;
            }
            selmenuid = node.id;
        }
        int = setInterval(function () {
            treeOperate.setParams({role_id: selroleid,menu_id: selmenuid});
            clearInterval(int);
        },100);
    });
    treeMenu.on('checkchange', function(node,checked) {
        if (selroleid!='-1') {
            if (node.get('type')=='2') {
                Ext.Ajax.request({
                    url : 'rolemenu_save_rm.htm',
                    params : {
                        role_id : selroleid,
                        menu_id : node.id,
                        checked : checked
                    }
                });
            }
        }
    });
    var treeOperate = Ext.create('Ext.vcf.AssistTree',{
        url : 'rolemenu_operate_tree.htm',
        selectModel : 'multiple',
        params : {role_id : '-1',menu_id : '-1'},
        renderTo : 'operateTreeDiv'
    });
    treeOperate.on('checkchange', function(node,checked) {
        if (selroleid!='-1' && selmenuid!='-1') {
                Ext.Ajax.request({
                    url : 'rolemenu_save_ro.htm',
                    params : {
                        role_id : selroleid,
                        operate_id : node.id,
                        checked : checked,
                        fieldName : 'dispname',
                        value : node.get('name')
                    }
                });
        }
    });
    Ext.create('Ext.Viewport',{
        layout: 'border',
        items: [{
            region: 'west',
            title: '<span class="commoncss">角色信息</span>',
            width: 300,
            minSize: 220,
            maxSize: 370,
            collapsible: true,
            split: true,
            layout: 'fit',
            margins: '3 3 3 3',
            border: false,
            items : [treeRole]
        },{
            region: 'center',
            title: '<span class="commoncss">菜单信息</span>',
            layout: 'fit',
            margins: '3 3 3 3',
            border: false,
            items : [treeMenu]
        },{
            region: 'east',
            title: '<span class="commoncss">操作信息</span>',
            width: 300,
            minSize: 220,
            maxSize: 370,
            collapsible: true,
            split: true,
            layout: 'fit',
            margins: '3 3 3 3',
            border: false,
            items : [treeOperate]
        }]
    });
}
Ext.onReady(pageLoad);