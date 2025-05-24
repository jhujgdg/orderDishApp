const request = require('../../utils/request');

Page({
  data: {
    user: {},
    genderOptions: ['男', '女', '保密'],
    genderIndex: 2,
    avatarUrl: '', 
    birthDate: '', 
    username: '', 
  },

  onLoad() {
    const user = wx.getStorageSync('user') || {};
    this.setData({
      user,
      avatarUrl: user.avatarUrl || '/utils/images/avatar.png', // 默认头像
      genderIndex: this.mapGenderToIndex(user.gender || '保密'),
      birthDate: user.birthDate || '', 
      username: user.username || '',
    });
  },

  mapGenderToIndex(gender) {
    switch (gender) {
      case '男': return 0;
      case '女': return 1;
      default: return 2;
    }
  },

  onGenderChange(e) {
    this.setData({ genderIndex: e.detail.value });
  },

  // 选择头像
  chooseAvatar() {
    wx.chooseImage({
      count: 1, 
      sizeType: ['original', 'compressed'], 
      sourceType: ['album', 'camera'], 
      success: (res) => {
        const avatarUrl = res.tempFilePaths[0];
        this.setData({
          avatarUrl
        });
      },
      fail: (error) => {
        console.log("选择头像失败", error);
      }
    });
  },


  onDateChange(e) {
    const selectedDate = e.detail.value; 
    this.setData({
      birthDate: selectedDate 
    });
  },

  onInputUsername(e) {
    const username = e.detail.value; 
    this.setData({
      username 
    });
  },

  onSubmit(e) {
    const { email, oldPassword, newPassword } = e.detail.value;
    const gender = this.data.genderOptions[this.data.genderIndex];
    const avatarUrl = this.data.avatarUrl;
    const birthDate = this.data.birthDate; // 获取出生日期
    const username = this.data.username; // 获取用户名

    request({
      url: 'http://localhost:8080/api/users/profile/update',
      method: 'PUT',
      data: {
        avatarUrl,
        email,
        birthDate,
        gender,
        username 
      },
      success: (res) => {
        if (res.statusCode === 200) {
          wx.showToast({ title: '信息已更新' });
        } else if (res.statusCode === 401 || res.statusCode === 403) {
          wx.showToast({ title: '登录失效，请重新登录', icon: 'none' });
          wx.redirectTo({ url: '/pages/login/login' });
        } else {
          wx.showToast({ title: '更新失败', icon: 'none' });
        }
      },
      fail: () => {
        wx.showToast({ title: '请求失败', icon: 'none' });
      }
    });

    if (oldPassword && newPassword) {
      request({
        url: 'http://localhost:8080/api/users/password',
        method: 'PUT',
        data: {
          oldPassword,
          newPassword
        },
        success: (res) => {
          if (res.statusCode === 200) {
            wx.showToast({ title: '密码已修改' });
          } else if (res.statusCode === 401 || res.statusCode === 403) {
            wx.showToast({ title: '登录失效，请重新登录', icon: 'none' });
            wx.redirectTo({ url: '/pages/login/login' });
          } else {
            wx.showToast({ title: '密码修改失败', icon: 'none' });
          }
        },
        fail: () => {
          wx.showToast({ title: '请求失败', icon: 'none' });
        }
      });
    }
  }
});
