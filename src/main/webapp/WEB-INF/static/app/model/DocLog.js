Ext.define('Share.model.DocLog', {
    extend: 'Ext.data.Model',

    fields: [
        {name: 'id', type: 'int'},
        {name: 'action', type: 'string'},
        {name: 'name', type: 'string'},
        {name: 'path', type: 'string'},
        {name: 'time', type: 'string'},
        {name: 'operator', type: 'string'}


    ]
});