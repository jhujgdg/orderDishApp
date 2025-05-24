const request = require('../../utils/request');

Page({
  data: {
    currentTab: 'new', // 默认显示新建订单
    localOrderItems: [],
    totalAmount: 0,
    ordersPreparing: [],
    ordersCompleted: []
  },

  onShow() {
    if (this.data.currentTab === 'new') {
      let items = wx.getStorageSync('localOrderItems') || [];
      items = this.recalculateSubtotals(items);
      this.setData({ localOrderItems: items }, this.updateTotal);
    } else {
      this.loadOrders(this.data.currentTab);
    }
  },

  switchTab(e) {
    const tab = e.currentTarget.dataset.tab;
    this.setData({ currentTab: tab });

    if (tab === 'new') {
      let items = wx.getStorageSync('localOrderItems') || [];
      items = this.recalculateSubtotals(items);
      this.setData({ localOrderItems: items }, this.updateTotal);
    } else {
      this.loadOrders(tab);
    }
  },

  recalculateSubtotals(items) {
    return items.map(item => ({
      ...item,
      subtotal: (item.price * item.quantity).toFixed(2)
    }));
  },

  increaseQuantity(e) {
    const index = e.currentTarget.dataset.index;
    const items = this.data.localOrderItems;
    items[index].quantity += 1;

    const updatedItems = this.recalculateSubtotals(items);
    this.setData({ localOrderItems: updatedItems }, () => {
      wx.setStorageSync('localOrderItems', updatedItems);
      this.updateTotal();
    });
  },

  decreaseQuantity(e) {
    const index = e.currentTarget.dataset.index;
    let items = this.data.localOrderItems;
    items[index].quantity -= 1;

    if (items[index].quantity <= 0) {
      items.splice(index, 1);
    }

    const updatedItems = this.recalculateSubtotals(items);
    this.setData({ localOrderItems: updatedItems }, () => {
      wx.setStorageSync('localOrderItems', updatedItems);
      this.updateTotal();
    });
  },

  deleteItem(e) {
    const index = e.currentTarget.dataset.index;
    let items = this.data.localOrderItems;
    items.splice(index, 1);

    const updatedItems = this.recalculateSubtotals(items);
    this.setData({ localOrderItems: updatedItems }, () => {
      wx.setStorageSync('localOrderItems', updatedItems);
      this.updateTotal();
    });
  },

  updateTotal() {
    const total = this.data.localOrderItems.reduce((sum, item) => sum + item.price * item.quantity, 0);
    this.setData({ totalAmount: total.toFixed(2) });
  },

  submitOrder() {
    if (this.data.localOrderItems.length === 0) {
      wx.showToast({ title: '订单为空', icon: 'none' });
      return;
    }
    request({
      url: 'http://localhost:8080/api/orders/create',
      method: 'POST',
      data: {
        items: this.data.localOrderItems.map(i => ({
          dishId: i.dishId,
          quantity: i.quantity
        }))
      },
      success: () => {
        wx.showToast({ title: '订单已提交' });
        wx.removeStorageSync('localOrderItems');
        this.setData({ localOrderItems: [], totalAmount: 0 });
      }
    });
  },

  loadOrders(status) {
    wx.showLoading({ title: '加载订单中...' });
    request({
      url: `http://localhost:8080/api/orders/list?status=${status}`,
      method: 'GET',
      success: (res) => {
        if (status === 'preparing') {
          this.setData({ ordersPreparing: res.data });
        } else if (status === 'completed') {
          this.setData({ ordersCompleted: res.data });
        }
      },
      complete: () => {
        wx.hideLoading();
      }
    });
  },
  markOrderCompleted(e) {
    const orderId = e.currentTarget.dataset.id;
    wx.showModal({
      title: '提示',
      content: '确认完成该订单？',
      success: (res) => {
        if (res.confirm) {
          request({
            url: `http://localhost:8080/api/orders/complete/${orderId}`,
            method: 'PUT',
            success: () => {
              wx.showToast({ title: '订单已完成' });
              this.loadOrders('preparing');
              this.loadOrders('completed'); // 重新加载完成订单列表
            }
          });
        }
      }
    });
  },
  
  cancelOrder(e) {
    const orderId = e.currentTarget.dataset.id;
    wx.showModal({
      title: '提示',
      content: '确认删除该订单？',
      success: (res) => {
        if (res.confirm) {
          request({
            url: `http://localhost:8080/api/orders/cancel/${orderId}`,
            method: 'PUT',
            success: () => {
              wx.showToast({ title: '订单已取消' });
              this.loadOrders('preparing'); // 重新加载中订单列表
            }
          });
        }
      }
    });
  },
  goToOrderDetail(event) {
    const orderId = event.currentTarget.dataset.id;
    wx.navigateTo({
      url: `/pages/orderDetail/orderDetail?orderId=${orderId}`
    });
  },
});
