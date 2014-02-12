Ext.define('Share.view.DirMenu', {
    extend: 'Ext.menu.Menu',
    alias: 'widget.dirmenu',
    width: 150,

    initComponent: function () {
        this.items = [
            {
                action: 'refresh',
                text: 'Refresh',
                iconCls: 'refresh'
            },
            '-',
            {
                action: 'create',
                text: 'New Folder ...',
                iconCls: 'newfolder'
            },
            {
                action: 'delete',
                text: 'Delete',
                iconCls: 'remove'
            }
        ];
        this.callParent(arguments);
    }
}, function () {
    Share.TreeMenu = new this();
});