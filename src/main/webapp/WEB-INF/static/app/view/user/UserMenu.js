Ext.define('AM.view.user.UserMenu', {
    extend: 'Ext.menu.Menu',
    alias: 'widget.usermenu',
    width: 150,

    initComponent: function () {
        this.items = [
            {
                id: 'menu-edit',
                text: 'Update ...',
                iconCls: 'edit'
            },
            {
                id: 'menu-delete',
                text: 'Delete'
            },
            '-',
            {
                id: 'menu-refresh',
                text: 'Refresh',
                iconCls: 'refresh'
            },
            {
                id: 'menu-properties',
                text: 'Properties',
                iconCls: 'eye'
            }
        ];
        this.callParent(arguments);
    }
}, function () {
    AM.UserMenu = new this();
});