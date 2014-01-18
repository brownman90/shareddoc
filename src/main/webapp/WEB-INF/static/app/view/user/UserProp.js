Ext.define('AM.view.user.UserProp', {
    extend: 'Commons.SimpleWindow',
    alias: 'widget.userprop',

    title: 'User Properties',
    width: 350,
    initComponent: function () {
        this.items = [
            {
                xtype: 'simplepropertygrid',
                sourceConfig: {
                    Gender: {
                        renderer: function (value) {
                            if (value === 1) return 'Male';
                            else return 'Female';
                        }
                    }
                }

            }
        ];
        this.callParent(arguments);
    }
});