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
                text: 'New Folder ...'
            },
            {
                action: 'delete',
                text: 'Delete'
            }
        ];
        this.callParent(arguments);
    }
}, function () {
    Share.TreeMenu = new this();
});