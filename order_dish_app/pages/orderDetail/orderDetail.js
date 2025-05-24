const request = require('../../utils/request');

Page({
  data: {
    orderId: null,
    orderDetail: null,
    orderItems: []
  },

  onLoad(options) {
    const orderId = options.orderId;
    this.setData({ orderId });
    this.fetchOrderDetail(orderId);
  },

  fetchOrderDetail(orderId) {
    request({
      url: `http://localhost:8080/api/orders/${orderId}`, 
      method: 'GET',
      success: (res) => {
        console.log("后端返回数据：",res.data)
        if (res.statusCode === 200) {
          const order = res.data;
          this.setData({
            orderDetail: order,
            orderItems: order.orderItems || []
          });
        } else {
          wx.showToast({
            title: '获取订单详情失败',
            icon: 'error'
          });
        }
      },
      fail: () => {
        wx.showToast({
          title: '请求失败',
          icon: 'error'
        });
      }
    });
  },
  goToComment(e) {
    const dishId = e.currentTarget.dataset.dishid;
    const orderId = this.data.orderId 
    wx.navigateTo({
      url: `/pages/comment/comment?dishId=${dishId}&orderId=${orderId}`
    });
  }
});
