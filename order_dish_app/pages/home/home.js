Page({
  data: {
    isSearching: false,
    searchKeyword: '',
    dishes: [],
    searchResults: [],
  },

  onSearchInput(e) {
    this.setData({ searchKeyword: e.detail.value });
  },

  onSearchConfirm() {
    const keyword = this.data.searchKeyword.trim();
    if (!keyword) {
      this.setData({ isSearching: false });
      return;
    }
    wx.request({
      url: `http://localhost:8080/api/dishes/search?keyword=${keyword}`,
      success: (res) => {
        this.setData({
          isSearching: true,
          searchResults: res.data
        });
      }
    });
  },

  onDishClick(e) {
    const dishId = e.currentTarget.dataset.id;
    wx.navigateTo({
      url: `/pages/dish-detail/dish-detail?id=${dishId}`
    });
  },

  onLoad() {
    wx.request({
      url: 'http://localhost:8080/api/dishes/recommend',
      success: (res) => {
        this.setData({ dishes: res.data });
      }
    });
  }
});