const request = require('../../utils/request');

Page({
  data: {
    dishId: null,
    orderId: null,
    rating: 0,
    comment: ''
  },

  onLoad(options) {
    this.setData({
      dishId: parseInt(options.dishId),
      orderId: parseInt(options.orderId),
    });
    console.log("orderID:",this.data.orderId)
  },

  onStarTap(e) {
    const index = e.currentTarget.dataset.index;
    this.setData({ rating: index });
  },

  onCommentInput(e) {
    this.setData({ comment: e.detail.value });
  },

  submitComment() {
    const { dishId, orderId, rating, comment } = this.data;

    if (rating === 0) {
      wx.showToast({ title: '请评分', icon: 'none' });
      return;
    }

    request({
      url: 'http://localhost:8080/api/reviews/add',
      method: 'POST',
      header: { 'content-type': 'application/json' },
      data: {
        dishId,
        orderId,
        rating,
        comment
      },
      success: res => {
        wx.showToast({ title: '评论成功' });
        setTimeout(() => {
          wx.navigateBack(); // 返回上一页
        }, 1000);
      },
      fail: () => {
        wx.showToast({ title: '提交失败', icon: 'none' });
      }
    });
  }
});
