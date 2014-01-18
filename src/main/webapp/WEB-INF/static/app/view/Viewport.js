Ext.define('Share.view.Viewport', {
    extend: 'Ext.container.Viewport',

    layout: 'border',
    items: [
        {
            xtype: 'doclist'
        },
        {
            title: 'Navigation',
            region: 'west',
            split: true,
            collapsible: true,
            width: 300,
            layout: 'fit',
            border: false

        }
    ]
});