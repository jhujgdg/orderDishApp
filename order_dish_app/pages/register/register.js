Page({
  data: {
    account: '',       // 手机号或邮箱
    username: '',      // 用户名
    password: '',      // 密码
    confirmPassword: '' // 确认密码
  },

  // 输入手机号或邮箱
  onInputAccount(e) {
    this.setData({ account: e.detail.value });
  },

  // 输入用户名
  onInputUsername(e) {
    this.setData({ username: e.detail.value });
  },

  // 输入密码
  onInputPassword(e) {
    this.setData({ password: e.detail.value });
  },

  // 输入确认密码
  onInputConfirmPassword(e) {
    this.setData({ confirmPassword: e.detail.value });
  },

  // 注册
  onRegister() {
    // 校验密码是否一致
    if (this.data.password !== this.data.confirmPassword) {
      wx.showToast({ title: '两次密码不一致', icon: 'none' });
      return;
    }

    // 调用注册接口
    wx.request({
      url: 'http://localhost:8080/api/users/register',  // 确保地址正确
      method: 'POST',
      data: {
        phoneOrEmail: this.data.account,
        username: this.data.username,
        password: this.data.password
      },
      success: (res) => {
        console.log("注册请求响应：", res);  // 打印响应内容
        if (res.statusCode === 200) {
          wx.showToast({ title: '注册成功' });
          wx.redirectTo({ url: '/pages/login/login' });
        } else {
          wx.showToast({ title: '注册失败', icon: 'none' });
        }
      },
      fail: (err) => {
        console.error("注册请求失败：", err);  // 打印请求失败的错误
        wx.showToast({ title: '请求失败', icon: 'none' });
      }
    });
  }
});
