Ext.define('Share.view.Viewport', {
    extend: 'Ext.container.Viewport',

    layout: 'border',
    items: [
        {
            xtype: 'docgrid'
        },
        {
            xtype: 'dirtree'
        },
        {
            xtype: 'tabpanel',
            height: '25%',
            region: 'south',
            minTabWidth: 200,
            plain: true,
            split: true,
            items: [
                {
                    xtype: 'docloggrid'
                },
                {
                    title: 'System Logs'
                }
            ]
        }
    ]
});