Page({
  data: {
    identifier: '',
    password: '',
  },

  onIdentifierInput: function (e) {
    this.setData({
      identifier: e.detail.value,
    });
  },

  onPasswordInput: function (e) {
    this.setData({
      password: e.detail.value,
    });
  },

  // 登录
  onLogin: function () {
    const { identifier, password } = this.data;
    if (!identifier || !password) {
      wx.showToast({
        title: '请输入邮箱或手机号和密码',
        icon: 'none',
      });
      return;
    }

    wx.request({
      url: 'http://localhost:8080/api/users/login',
      method: 'POST',
      data: {
        identifier: identifier,
        password: password,
      },
      success(res) {
        if (res.statusCode === 200) {
          const token = res.data.token;  
          wx.setStorageSync('token', token); 
          wx.setStorageSync('user', res.data.user);
          console.log('当前请求的 token 是:', token); 
          console.log('登录后拿到的用户数据:', res.data);

          // 跳转到主页
          wx.switchTab({
            url: '/pages/home/home',
          });
        } else {
          wx.showToast({
            title: '登录失败',
            icon: 'none',
          });
        }
      },
      fail() {
        wx.showToast({
          title: '网络错误，请重试',
          icon: 'none',
        });
      }
    });
  },

  goToRegister: function () {
    wx.navigateTo({
      url: '/pages/register/register', // 注册页面
    });
  },
});
