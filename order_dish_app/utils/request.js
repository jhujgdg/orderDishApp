module.exports = function request(options) {
  const token = wx.getStorageSync('token');
  console.log('当前请求的 token 是:', token); // ✅ 看控制台是否有

  options.header = {
    ...options.header,
    'Authorization': 'Bearer ' + token
  };

  console.log('请求头：', options.header); // ✅ 打印请求头，确认 Authorization 是否存在

  wx.request(options);
};
