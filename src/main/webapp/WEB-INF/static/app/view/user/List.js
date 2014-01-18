Ext.define('AM.view.user.List', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.userlist',

    region: 'center',
    title: 'All Users',
    store: 'Users',

    selType: 'checkboxmodel',
    tbar: [
        { id: 'create-user-btn', text: 'Create', iconCls: 'add', tooltip: 'Create a user' },
        { id: 'delete-user-btn', text: 'Delete', iconCls: 'delete', tooltip: 'Delete users'},
        { id: 'edit-user-btn', text: 'Update', iconCls: 'edit', tooltip: 'Update user'},
        '-',
        { id: 'refresh-user-btn', text: 'Refresh', iconCls: 'refresh', tooltip: 'Refresh User list'},
        '->',
        {id: 'group-user-btn', text: 'Group View', iconCls: 'packages', tooltip: 'User list view by group'}
    ],
    columns: [
        {header: 'Username', dataIndex: 'username', flex: 1},
        {header: 'Nickname', dataIndex: 'nickname', flex: 1},
        {header: 'Realname', dataIndex: 'realname', flex: 1},
        {header: 'Gender', dataIndex: 'sex', flex: 0.5, align:'center',renderer: function (value) {
            if (value === 1)
                return 'Male';
            return 'Female';
        }},
        {header: 'Birthday', dataIndex: 'birthday', flex: 1, xtype: 'datecolumn', format: 'Y-m-d', align:'center'}
    ],
    bbar: {
        xtype: 'pagingtoolbar',
        store: 'Users',
        pageSize: 15,
        displayInfo: true,
        plugins: Ext.create('Ext.ux.ProgressBarPager')
    }
});
