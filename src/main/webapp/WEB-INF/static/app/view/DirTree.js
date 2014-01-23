Ext.define('Share.view.DirTree', {
    extend: 'Ext.tree.Panel',
    alias: 'widget.dirtree',

    title: 'Directory Navigation',
    width: 300,
    region: 'west',
    split: true,
    collapsible: true,
    layout: 'fit',
    border: false,
    useArrows: true,
    store: 'Dirs',

    tools: [
        {
            action: 'refresh',
            type: 'refresh'
        }
    ],
    viewConfig: {
        loadMask: true
    }
});