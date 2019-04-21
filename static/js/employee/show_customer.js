var customer = new Vue({
    el: '#vue_customer',
    data: {
        people: [{
            CustomerId: 'C1',
            CustomerName: 'Fu Zixin',
            gender: 'Male',
            personalId:'110103199903091235',
            Phone:'18600812186',
            Email:'fuzixin000@gmail.com'
        },
            {
                CustomerId: 'C2',
                CustomerName: 'Fu Zixin',
                gender: 'Male',
                personalId:'110103199903091235',
                Phone:'18600812186',
                Email:'fuzixin000@gmail.com'
            },
            {
                CustomerId: 'C3',
                CustomerName: 'Fu Zixin',
                gender: 'Male',
                personalId:'110103199903091235',
                Phone:'18600812186',
                Email:'fuzixin000@gmail.com'
            },
            {
                CustomerId: 'C4',
                CustomerName: 'Fu Zixin',
                gender: 'Male',
                personalId:'110103199903091235',
                Phone:'18600812186',
                Email:'fuzixin000@gmail.com'
            }],
        info:[],
        test:[
            {fxc:"Fxc"},
            {czy:"Czy"}
        ],
        isRefresh: true,
        Customer_Id: "Customer Id"
    }
});
$(document).ready(function () {
    var d = {};
    d.fxc = customer.test.fxc;
    d.czy = customer.test.czy;

$.ajax({ url: "customer/request",
    data: JSON.stringify(d),
    //type、contentType必填,指明传参方式
    type: "POST",
    contentType: "application/json;charset=utf-8",
    success: function(response){
        customer.info = new Function("return" + response)();
        // customer.people.splice(1,0,customer.info[0]);

    },
    error:function(jqXHR){
        console.log("Error: "+ jqXHR.status);
    }

});
});
