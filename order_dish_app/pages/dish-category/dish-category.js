Page({
  data: {
    categories: [
      { id: 1, name: '肉类' },
      { id: 2, name: '鱼类' },
      { id: 3, name: '蔬菜类' },
      { id: 4, name: '汤类' },
      { id: 5, name: '面类' },
      { id: 6, name: '饮品类' }
    ],
    selectedCategoryId: 1,
    dishes: [],
    page: 0,
    size: 10,
    hasMore: true
  },

  onLoad() {
    this.loadDishes();
  },

  onReachBottom() {
    if (this.data.hasMore) this.loadDishes();
  },

  onCategorySelect(e) {
    const categoryId = e.currentTarget.dataset.id;
    this.setData({
      selectedCategoryId: categoryId,
      page: 0,
      dishes: [],
      hasMore: true
    }, () => {
      this.loadDishes();
    });
  },

  loadDishes() {
    const request = require('../../utils/request');
    const { selectedCategoryId, page, size } = this.data;
    request({
      url: `http://localhost:8080/api/dishes/category/${selectedCategoryId}?page=${page}&size=${size}`,
      method: 'GET',
      success: (res) => {
        if (res.statusCode === 200) {
          const newDishes = res.data.content || [];
          this.setData({
            dishes: [...this.data.dishes, ...newDishes],
            page: this.data.page + 1,
            hasMore: !res.data.last
          });
        } else {
          wx.showToast({ title: '加载失败', icon: 'none' });
        }
      },
      fail: () => wx.showToast({ title: '请求失败', icon: 'none' })
    });
  },

  onDishClick(e) {
    const dishId = e.currentTarget.dataset.id;
    wx.navigateTo({
      url: `/pages/dish-detail/dish-detail?id=${dishId}`
    });
  }
});
