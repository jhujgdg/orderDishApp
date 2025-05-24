Page({
  data: {
    dish: {},
    reviews: []
  },

  onLoad(options) {
    console.log('onLoad 接收到参数:', options);
    const dishId = options.id;
    console.log('dishid:',dishId)
    this.fetchDishDetails(dishId);
    this.fetchDishReviews(dishId);
  },
  

  fetchDishDetails(id) {
    wx.request({
      url: `http://localhost:8080/api/dishes/${id}`,
      success: res => this.setData({ dish: res.data })
    });
  },

  fetchDishReviews(id) {
    wx.request({
      url: `http://localhost:8080/api/dishes/${id}/reviews`,
      success: res => this.setData({ reviews: res.data })
    });
  },

  addToOrder() {
    const dish = this.data.dish;
    console.log(dish)
    let orderItems = wx.getStorageSync('localOrderItems') || [];
    
    // 查找是否已存在相同菜品
    const existing = orderItems.find(item => item.dishId === dish.dishId);
    if (existing) {
      existing.quantity += 1;
    } else {
      orderItems.push({
        dishId: dish.dishId,
        name: dish.name,
        price: dish.price,
        quantity: 1,
        imageUrl:dish.imageUrl
      });
    }
  
    wx.setStorageSync('localOrderItems', orderItems);
  
    console.log('当前订单：', orderItems);
  
    wx.navigateBack(); // 返回订单页
  }
  
});
