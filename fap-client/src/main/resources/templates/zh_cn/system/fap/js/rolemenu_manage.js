function pageLoad() {
    var treeMenu = Ext.create('Ext.vcf.AssistTree',{
        rootVisible:false,
        url : 'menu_tree_all.htm',
        renderTo : 'menuTreeDiv'
    });
}
Ext.onReady(pageLoad);