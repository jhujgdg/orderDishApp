Page({
  data: {
    user: {}
  },

  onShow() {
    const user = wx.getStorageSync('user') || {};
    if (!user.userId) {
      wx.redirectTo({ url: '/pages/login/login' });
    } else {
      this.fetchUser();
    }
  },
  
  fetchUser() {
    const request = require('../../utils/request');
    request({
      url: `http://localhost:8080/api/users/profile`, 
      method: 'GET',

      success: (res) => {
        if (res.statusCode === 200) {
          this.setData({ user: res.data });
        } else if (res.statusCode === 403 || res.statusCode === 401) {
          wx.showToast({ title: '请重新登录', icon: 'none' });
          wx.redirectTo({ url: '/pages/login/login' });
        } else {
          wx.showToast({ title: '加载失败', icon: 'none' });
        }
      },
      fail: () => {
        wx.showToast({ title: '请求失败', icon: 'none' });
      }
    });
  },  

  editInfo() {
    wx.navigateTo({
      url: '/pages/edit-profile/edit-profile'
    });
  },

  logout() {
    wx.removeStorageSync('user');
    wx.removeStorageSync('token'); 
    wx.redirectTo({ url: '/pages/login/login' });
  }
});
