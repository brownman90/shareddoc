Ext.define('AM.view.user.TreeMenu', {
    extend: 'Ext.menu.Menu',
    alias: 'widget.treemenu',
    width: 150,

    initComponent: function () {
        this.items = [
            {
                id: 'tree-menu-refresh',
                text: 'Refresh',
                iconCls: 'refresh'
            }
        ];
        this.callParent(arguments);
    }
}, function () {
    AM.TreeMenu = new this();
});