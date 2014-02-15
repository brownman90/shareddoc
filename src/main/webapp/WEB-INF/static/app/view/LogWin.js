Ext.define('Share.view.LogWin', {
    extend: 'Commons.window.SimpleWindow',
    alias: 'widget.logwin',

    title: 'Logs',
    width: 800,
    height: 500,
    border: false,
    initComponent: function () {
        this.items = {
            xtype: 'tabpanel',
            minTabWidth: 150,
            border: false,
            items: [
                {
                    xtype: 'docloggrid'
                },
                {
                    title: 'System Logs'
                }
            ]
        };
        this.callParent(arguments);
    }
});
